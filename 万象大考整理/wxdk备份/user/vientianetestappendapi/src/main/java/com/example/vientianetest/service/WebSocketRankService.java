package com.example.vientianetest.service;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicTagState;
import com.example.common.utils.DesUtils;
import com.example.common.utils.Rand;
import com.example.vientianetest.dao.*;
import com.example.vientianetest.dao.user.UserDao;
import com.example.vientianetest.dao.robot.RobotDao;
import com.example.vientianetest.dao.subject.SubjectDao;
import com.example.vientianetest.dao.subject.SubjectOptionDao;
import com.example.vientianetest.domain.*;
import com.example.vientianetest.redis.UserRankMatchRecordRedis;
import com.example.vientianetest.redis.UserRankSubjectRecordRedis;
import com.example.vientianetest.service.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 排位赛 WebSocket 核心服务。
 *
 * 维护 4 个内存数据结构：
 *   - userMap       : 全部在线用户（真人），key=userId
 *   - robotMap      : 全部机器人（Bot），key=userId（负数），服务启动时加载，不销毁
 *   - gameMap       : 所有进行中的游戏对局，key=gameId
 *   - matchingUsers : 等待匹配的用户队列（CopyOnWriteArrayList 防并发修改异常）
 *
 * 匹配流程（match 方法）：
 *   1. 取队列第一个用户，等待 5 秒（GAME_MATCH_WAITSECOND）
 *   2. 先找段位匹配的真人（按 getMatchMaxMinParagraph 查段位区间）
 *   3. 找不到真人 → 从 robotMap 找段位匹配的机器人
 *   4. 找到对手 → 取 5 道题 → 生成 Game → 放入 gameMap → 发送 TYPE_MATCH 给双方
 *
 * 答题流程（onAnswer 方法）：
 *   前端发 type=4 → 存储答案 → 双方都答完或超时 → 推下一题
 *   5 题结束后 → endGame → 计算段位/星星/战斗力变化 → 推送结算
 */
@Component
public class WebSocketRankService {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketRankService.class);
    DesUtils desUtils = new DesUtils("1234567890");

    @Autowired
    private GameRankDao gameRankDao;
    @Autowired
    private RobotDao robotDao;
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private SendMsgService sendMsgToClient;
    @Autowired
    private SubjectOptionDao subjectOptionDao;
    @Autowired
    private UserAbilityService userAbilityService;
    @Autowired
    private UserParagraphService userParagraphService;
    @Autowired
    private SubjectRatioService subjectRatioService;
    @Autowired
    private UserRankMatchRecordRedis userRankMatchRecordRedis;
    @Autowired
    private UserRankSubjectRecordRedis userRankSubjectRecordRedis;
    @Autowired
    private UserGameStatisRankService userGameStatisRankService;
    @Autowired
    private UserRankSubjectRecordService userRankSubjectRecordService;
    @Autowired
    private UserCombatService userCombatService;
    @Autowired
    private UserLuckNumberService userLuckNumberService;
    @Autowired
    private UserDao userDao;

    /** 匹配执行线程池（100 线程上限） */
    private ExecutorService matchExecutorService = Executors.newFixedThreadPool(100);

    /** 所有进行中的游戏对局，key=gameId */
    private Map<Integer, Game> gameMap = new ConcurrentHashMap<>();
    /** 全部在线用户（含离线但未超时的用户），key=userId */
    private Map<Integer, User> userMap = new ConcurrentHashMap<>();
    /** 机器人池（服务启动时加载，不回收），key=userId（负数） */
    private Map<Integer, User> robotMap = new ConcurrentHashMap<>();
    /** 题目正确率统计缓冲区，定时批量写入 DB */
    private Map<Integer, Map<String, Object>> subjectRatioMap = new ConcurrentHashMap<>();
    /** 匹配队列：等待配对的用户 ID 列表，ConcurrentModificationException */
    private CopyOnWriteArrayList<Integer> matchingUsers = new CopyOnWriteArrayList<>();

    public String getSid(WebSocketSession session) {
        return session.getAttributes().get("code").toString();
    }

    public String getType(WebSocketSession session) {
        return session.getAttributes().get("type").toString();
    }

    //聊天发表情
    /** 发送表情：把表情数据广播给对局的另一方 */
    public void chat(int id, String content) {
        Game game = this.getGame(id);
        if (game == null) {
            return;
        }
        this.noWriteLogSend(game, new BasicResultObject(BasicTagState.TagSuccess, Protocol.TYPE_CHAT, content, "", 0));
    }

    /**
     * 断线重连：用户重新建立 WebSocket 后调用。
     * 检查是否有未完成的游戏，如果有则恢复对局状态（剩余时间、题目进度），
     * 发送 TYPE_RECONNECT 给前端。
     */
    public void reconnect(int uid, WebSocketSession session) {
        User user = null;
        User withUser = null;
        Game resultGame = null;
        boolean isMoni = false;
        //synchronized (userMap) {
        user = userMap.get(uid);
        if (user != null) {
            int gameId = user.getGameId();
            if (gameId > 0) {
                Game game = this.getGame(gameId);
                if (game != null && game.getStatus() != Game.STATUS_END) {
                    user.setStatus(1);//重连
                    Date now = new Date();
                    long counter = game.getCounter();
                    long restSecond = now.getTime() - counter <= Param.GAME_MATCH_SUBJECTSECOND ? Param.GAME_MATCH_SUBJECTSECOND - (now.getTime() - counter) : Param.GAME_MATCH_SUBJECTSECOND;
                    restSecond = restSecond >= Param.GAME_MATCH_SUBJECTSECOND ? 5000 : restSecond;
                    System.out.println("重新连接秒数：" + restSecond);
                    resultGame = game;
                    //剩余的时间
                    user.setRestSecond(restSecond);
                    logger.debug("用户重新连接");
                }
            }
        }
        //}
        user.setSession(session);
        this.sendToUser(user, new BasicResultObject(BasicTagState.TagSuccess, Protocol.TYPE_RECONNECT, resultGame == null ? "" : resultGame, "", 0));
    }

    /**
     * 添加/更新在线用户。
     * - 如果用户之前有旧对局且 type=1（重新匹配），先调用 endGame 逃跑
     * - 新用户放入 userMap，已有用户更新状态和 session
     * - 计算胜率
     */
    public void addUser(User user, int type, WebSocketSession session) {
        //测压工具
        /*for(int i=28570;i<=28770;i++) {
            System.out.println("idnex = " + i);
            int userId = i;
            BasicUserInfo basicUserInfo = new BasicUserInfo();
            Map<String, Object> userMapObj = userDao.getUserInfoById(userId);
            basicUserInfo.setUserId(userId);
            basicUserInfo.setOpenId(userMapObj.get("openId").toString());
            basicUserInfo.setNickName(userMapObj.get("nickname").toString());
            basicUserInfo.setGender(userMapObj.get("sexDesc").toString());
            basicUserInfo.setAvatarUrl(userMapObj.get("headImgUrl").toString());
            basicUserInfo.setCity(userMapObj.get("city").toString());
            basicUserInfo.setProvince(userMapObj.get("province").toString());
            basicUserInfo.setCountry(userMapObj.get("country").toString());
            user user = new user();
            user.setUser(basicUserInfo);

            //判断用户之前是否有旧的对局，有的话，默认逃跑，删除游戏对局
            user mapUser = this.getUser(user.getUser().getUserId());
            if(mapUser != null && type == 1){
                logger.info("userId:{} 判断用户之前是否有旧的对局，有的话，默认逃跑，删除游戏对局",mapUser.getUser().getUserId());
                this.endGame(mapUser.getGameId(),mapUser.getUser().getUserId());
            }
            if(this.getUser(user.getUser().getUserId()) == null){
                user.setStatus(1);//在线
                user.setBoot(false);
                user.setAbility(new Ability());
                userMap.put(user.getUser().getUserId(), user);
            }else{
                user = this.getUser(user.getUser().getUserId());
                user.setStatus(1);//在线
            }
            user.setSession(null);
            userGameStatisRankService.getWinNumByUserId(user);//计算胜率
            this.addMatchingUser(userId);

        }*/
        //synchronized (userMap) {
        //判断用户之前是否有旧的对局，有的话，默认逃跑，删除游戏对局
        User mapUser = this.getUser(user.getUser().getUserId());
        if (mapUser != null && type == 1) {
            logger.info("userId:{} 判断用户之前是否有旧的对局，有的话，默认逃跑，删除游戏对局", mapUser.getUser().getUserId());
            this.endGame(mapUser.getGameId(), mapUser.getUser().getUserId());
        }
        if (this.getUser(user.getUser().getUserId()) == null) {
            user.setStatus(1);//在线
            user.setBoot(false);
            user.setAbility(new Ability());
            userMap.put(user.getUser().getUserId(), user);
        } else {
            user = this.getUser(user.getUser().getUserId());
            user.setStatus(1);//在线
        }
        user.setKeepTime(new Date().getTime());
        user.setSession(session);
        userGameStatisRankService.getWinNumByUserId(user);//计算胜率
        //}
    }

    /**
     * 将用户加入匹配队列 matchingUsers，同时重置对战状态。
     *
     * 关键：设置 randWaitSecond（随机等待秒数 = 5~8），
     * 超过这个时间还没匹配到真人，才会匹配机器人。
     *
     * 用户加入队列后，由 TaskTimer 每秒调用 runMatch() 驱动匹配轮询。
     */
    public void addMatchingUser(int uid) {
        synchronized (matchingUsers) {
            boolean found = false;
            for (int id : matchingUsers) {
                if (id == uid) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                matchingUsers.add(uid);
            }
            User user = userMap.get(uid);
            user.setStatus(1);
            user.setIndex(0);
            user.setRestSecond(0);
            user.setLastReplyTime(0);
            user.getAbility().setScore(0);
            user.getAbility().setRightNum(0);
            user.getAbility().setRewardStrength(0);
            user.getAbility().setBookTotalScore(0);
            user.setBoot(false);
            user.setJoinTime(new Date().getTime());
            user.setRandWaitSecond(Rand.nextInt(3) + Param.GAME_MATCH_WAITSECOND); //等待游戏的时长 5-8 秒
            //user.setRandWaitSecond(3);
        }
    }

    /** 轮询匹配：每次调用尝试匹配一对，可能多次调用直到队列为空或无人可配 */
    public void runMatch() {
        // 匹配所有能匹配的玩家
        Date now = new Date();
        while (this.match(now)) {
            // nothing
        }
    }

    /**
     * 段位匹配区间计算。
     *
     * 段位从低到高：1黑铁 → 2青铜 → 3白银 → 4黄金 → 5白金 → 6黑金 → 7钻石 → 8大师 → 9王者
     *
     * 每段位可向下匹配 1 段 + 向上匹配 1 段（王者段只能匹配大师和王者）
     */
    public Map<String, Integer> getMatchMaxMinParagraph(int paragraph) {
        Map<String, Integer> resultMap = new HashMap<>();
        if (paragraph == 1) {//黑铁→黑铁、青铜
            resultMap.put("minParagraph", 1);
            resultMap.put("maxParagraph", 2);
        } else if (paragraph == 2) {//青铜→黑铁、青铜、白银
            resultMap.put("minParagraph", 1);
            resultMap.put("maxParagraph", 3);
        } else if (paragraph == 3) {//黄金试炼、白银→青铜、白银、黄金
            resultMap.put("minParagraph", 2);
            resultMap.put("maxParagraph", 4);
        } else if (paragraph == 4) {//黄金→白银、黄金、白金
            resultMap.put("minParagraph", 3);
            resultMap.put("maxParagraph", 5);
        } else if (paragraph == 5) {//白金→黄金、白金、黑金
            resultMap.put("minParagraph", 4);
            resultMap.put("maxParagraph", 6);
        } else if (paragraph == 6) {//钻石试炼、黑金→白金、黑金、钻石
            resultMap.put("minParagraph", 5);
            resultMap.put("maxParagraph", 6);
        } else if (paragraph == 7) {//钻石→黑金、钻石、大师
            resultMap.put("minParagraph", 6);
            resultMap.put("maxParagraph", 8);
        } else if (paragraph == 8) {//王者试炼、大师→钻石、大师、王者
            resultMap.put("minParagraph", 7);
            resultMap.put("maxParagraph", 9);
        } else if (paragraph > 8) {//王者→大师、王者
            resultMap.put("minParagraph", 8);
            resultMap.put("maxParagraph", 9);
        } else {
            resultMap.put("minParagraph", 1);
            resultMap.put("maxParagraph", 2);
        }
        return resultMap;
    }

    /**
     * 核心匹配逻辑（每次调用处理匹配队列第一个用户）。
     *
     * 流程：
     *   1. 等待 5 秒（加入队列后 minimum wait）
     *   2. 优先从 matchingUsers 中找段位匹配的真人
     *      - 跳过已离线的用户
     *      - 跳过最近 3 局内已对战过的对手（防重复匹配）
     *      - 每 5 局必匹配机器人、连输 3 盘以上也匹配机器人
     *      - 王者段位按星星数最接近来配对
     *   3. 找不到真人 → 从 robotMap 找段位匹配的机器人
     *   4. 找到对手 → 异步线程执行：取题 → 生成 Game → 入库 → 发送 TYPE_MATCH
     *
     * @return true=处理了一对（可能继续轮询）/ false=无可配对
     */
    public Boolean match(Date now) {
        List<User> userList = new ArrayList<>();
        User robot = null;
        synchronized (matchingUsers) {
            int size = matchingUsers.size();
            if (size == 0) {
                return false;
            }
            int uid = matchingUsers.get(0);
            User user = this.getUser(uid);
            /*if (user == null || now.getTime() - user.getJoinTime() > user.getRandWaitSecond() * 1000 * 3) {
                logger.debug("用户不存在或者等待太久, user:{} uid:{}", user, uid);
                matchingUsers.remove(0);
                return true;
            }*/
            if (size >= 1) {
                //等 N 秒后开始匹配
                if (now.getTime() - user.getJoinTime() < Param.GAME_MATCH_WAITSECOND * 1000) {
                    System.out.println("[MATCH] uid="+uid+" waiting("+Param.GAME_MATCH_WAITSECOND+"s) queue="+size);
                    return false;
                }
                int userParagraph = user.getAbility().getParagraph();
                int userStarNum = user.getAbility().getStar();
                Map<String, Integer> paragraphMap = this.getMatchMaxMinParagraph(userParagraph);
                int MIN_PARAGRAPH = paragraphMap.get("minParagraph");
                int MAX_PARAGRAPH = paragraphMap.get("maxParagraph");
                System.out.println("[MATCH] uid="+uid+" para="+userParagraph+" range=["+MIN_PARAGRAPH+"-"+MAX_PARAGRAPH+"] queue="+size+" robotPool="+robotMap.size());
                int userMaxMatchNum1 = userRankMatchRecordRedis.getUserMaxMatchNum(user.getUser().getUserId());
                List<User> matchUserList = new ArrayList<>();

                //region 匹配人
                //五局的时候必匹配机器人,连输3盘以上都匹配机器人
                if((user.getAbility().getTotalNum() + 1) % 5 != 0 && user.getAbility().getContinueLoseNum() < 3) {
                    for (int i = 1; i < matchingUsers.size(); i++) {
                        Integer id = matchingUsers.get(i);
                        User matchUser = this.getUser(id);
                        int withUserParagraph = matchUser.getAbility().getParagraph();

                        if (matchUser.getStatus() == 0) {
                            logger.info("userId:{} 用户匹配时退出", matchUser.getUser().getUserId());
                            matchingUsers.remove(id);
                            continue;
                        }
                        int withUserMatchIndex1 = userRankMatchRecordRedis.getWithUserMatchIndex(user.getUser().getUserId(), id);
                        int userMaxMatchNum2 = userRankMatchRecordRedis.getUserMaxMatchNum(id);
                        int withUserMatchIndex2 = userRankMatchRecordRedis.getWithUserMatchIndex(id, user.getUser().getUserId());
                        //用户要过玩超过3场匹配
                        if (userMaxMatchNum1 > 0 && withUserMatchIndex1 > 0 && userMaxMatchNum2 > 0 && withUserMatchIndex2 > 0 &&
                                (Math.abs(userMaxMatchNum1 - withUserMatchIndex1) <= 3 || Math.abs(userMaxMatchNum2 - withUserMatchIndex2) <= 3)) {
                            continue;
                        }
                        if (withUserParagraph >= MIN_PARAGRAPH && withUserParagraph <= MAX_PARAGRAPH) {//范围内的段位
                            if (userParagraph >= 9) {
                                matchUserList.add(matchUser);
                            } else {
                                matchUserList.add(matchUser);
                                break;
                            }
                        }
                    }
                }
                //endregion

                //如果有真人优先匹配
                if (matchUserList.size() > 0) {
                    int minIndex = 0;
                    if(userParagraph >= 9 && matchUserList.size() > 1) {//王者段位按星星数最近来匹配
                        int lastInterStarNum = -1;
                        for (int i = 0; i < matchUserList.size(); i++) {
                            if (matchUserList.get(i).getAbility().getParagraph() >= 9) {
                                int withUserStarNum = matchUserList.get(i).getAbility().getStar();
                                int interStarNum = Math.abs(userStarNum - withUserStarNum);
                                if (lastInterStarNum < 0 || lastInterStarNum > interStarNum) {
                                    lastInterStarNum = interStarNum;
                                    minIndex = i;
                                }
                            }
                        }
                        logger.info("匹配王者段位按星星数最近来匹配 userId:{} minIndex:{} ", user.getUser().getUserId(), minIndex);
                    }
                    //System.out.println("正常匹配到真人。。。");
                    logger.info("正常匹配到真人。。。");
                    userList.add(user);
                    userList.add(matchUserList.get(minIndex));
                    matchingUsers.remove((Integer) matchUserList.get(minIndex).getUser().getUserId());
                    matchingUsers.remove(0);
                } else {
                    //region 匹配机器人
                    System.out.println("[MATCH] uid="+uid+" no human match, trying "+robotMap.size()+" robots (range=["+MIN_PARAGRAPH+"-"+MAX_PARAGRAPH+"])");
                    for (User r : robotMap.values()) {
                        int withUserParagraph = r.getAbility().getParagraph();
                        //用户要过玩超过3场匹配
                        int withUserMatchIndex1 = userRankMatchRecordRedis.getWithUserMatchIndex(user.getUser().getUserId(), r.getUser().getUserId());
                        if (userMaxMatchNum1 > 0 && withUserMatchIndex1 > 0 && (Math.abs(userMaxMatchNum1 - withUserMatchIndex1) <= 3)) {
                            continue;
                        }
                        if (withUserParagraph >= MIN_PARAGRAPH && withUserParagraph <= MAX_PARAGRAPH) {//范围内的段位
                            matchUserList.add(r);
                        }
                    }
                    //endregion
                    System.out.println("[MATCH] uid="+uid+" robot candidates found: "+matchUserList.size());
                    if (matchUserList.size() > 0) {
                        System.out.println("[MATCH] uid="+uid+" matched with robot!");
                        logger.info("正常匹配到机器人。。。");
                        int index = Rand.nextInt(matchUserList.size());
                        User copyRobot = JSONObject.parseObject(JSONObject.toJSONString(matchUserList.get(index)), User.class);//机器人可以重复
                        copyRobot.setRobotSubjectRatio(matchUserList.get(index).getRobotSubjectRatio());
                        userList.add(user);
                        userList.add(copyRobot);
                        matchingUsers.remove(0);
                    }
                }
            } else {
                logger.info("1匹配玩家太少，可以再等等 userId:{}",uid);
                // 匹配玩家太少，可以再等等
                return false;
            }

            if (userList.size() < 2) {
                logger.info("2匹配玩家太少，可以再等等 userId:{}",uid);
                // 匹配玩家太少，可以再等等
                return false;
            }

            //记录用户玩的场次
            for (int i = 0; i < userList.size(); i++) {
                if (!userList.get(i).getBoot()) {
                    int userId = i == 0 ? userList.get(0).getUser().getUserId() : userList.get(1).getUser().getUserId();
                    int withUid = i == 0 ? userList.get(1).getUser().getUserId() : userList.get(0).getUser().getUserId();
                    if (userId <= 0) { //机器人不统计
                        continue;
                    }
                    //记录对手匹配记录
                    userRankMatchRecordRedis.addUserMatchRecord(userId, withUid);
                }
            }
        }
        matchExecutorService.submit(() -> {
            try {
                if (this.cancelMatch(userList)) {
                    return;
                }
                System.out.println("[MATCH-TASK] uid="+userList.get(0).getUser().getUserId()+" step1: getSubjectRecord...");
                User user = userList.get(0).getAbility().getWinRatio() > userList.get(1).getAbility().getWinRatio() ? userList.get(1) : userList.get(0);
                String noExistsSubjectId = userRankSubjectRecordRedis.getUserSubjectRecord(userList.get(0).getUser().getUserId());//不重复题目
                System.out.println("[MATCH-TASK] uid="+userList.get(0).getUser().getUserId()+" step2: getRandSubject...");
                List<Subject> subjectList = subjectRatioService.getRandSubjectByRightRatio(userList, user, noExistsSubjectId);//全部类型
                System.out.println("[MATCH-TASK] uid="+userList.get(0).getUser().getUserId()+" step3: subjects="+subjectList.size()+" insert game...");
                //List<Subject> subjectList = subjectDao.getRandSubjectList(5,1);
                int gameId = gameRankDao.insert(userList.get(0).getUser().getUserId(), userList.get(1).getUser().getUserId(), Game.STATUS_PLAYING,userMap.size());
                System.out.println("[MATCH-TASK] uid="+userList.get(0).getUser().getUserId()+" step4: gameId="+gameId+" send...");
                Game game = new Game();
                game.setId(gameId);
                game.setStatus(Game.STATUS_PLAYING);
                game.setUserList(userList);
                game.setSubjectList(subjectList);
                game.setAnswerIndex(0);
                game.setCounter(new Date().getTime() + 8000);//延迟8秒
                this.addGame(game);
                this.send(game, new BasicResultObject(BasicTagState.TagSuccess, Protocol.TYPE_MATCH, game, "", 0));
                System.out.println("[MATCH-TASK] uid="+userList.get(0).getUser().getUserId()+" DONE! gameId="+gameId);
                //System.out.println(String.format("匹配成功，准备答题 userList = %s ",userStr));
                logger.info("匹配成功，准备答题, game:{}", game);
                //记录当前玩的游戏
                for (int i = 0; i < userList.size(); i++) {
                    userList.get(i).setAutoTime(0);
                    userList.get(i).setGameId(gameId);
                    userList.get(i).setCombatSubList(null);
                    userList.get(i).setLastReplyTime(new Date().getTime());
                    userList.get(i).setStatus(userList.get(i).getBoot() ? 0 : 1);
                }
                userRankSubjectRecordService.update(userList, subjectList); //修改重复题目
            } catch (Throwable t) {
                System.out.println("[MATCH-TASK] ERROR: " + t.toString());
                t.printStackTrace(System.out);
                logger.debug("匹配成功后发生异常, userList:{} t:{}", userList, t);
            }
        });
        return true;
    }

    /** 校验匹配是否被取消（用户离线或机器人已被回收） */
    private boolean cancelMatch(List<User> userList) {
        boolean isCancel = false;
        if (userList.get(0).getStatus() == 0 && userList.get(1).getBoot()) {
            this.removeUser(userList.get(0).getUser().getUserId());
            //robotMap.put(userList.get(1).getUser().getUserId(),userList.get(1));
            logger.info("userId:{} 用户跟机器人取消匹配", userList.get(0).getUser().getUserId());
            isCancel = true;
        } else if (!userList.get(1).getBoot() && (userList.get(0).getStatus() == 0 || userList.get(1).getStatus() == 0)) {
            if (userList.get(0).getStatus() == 0 && userList.get(1).getStatus() == 0) {
                this.removeUser(userList.get(0).getUser().getUserId());
                this.removeUser(userList.get(1).getUser().getUserId());
                logger.info("userId1:{} userId2:{} 两个同时用户取消匹配", userList.get(0).getUser().getUserId(), userList.get(1).getUser().getUserId());
            } else if (userList.get(0).getStatus() == 0) {
                this.removeUser(userList.get(0).getUser().getUserId());
                matchingUsers.add(userList.get(1).getUser().getUserId());
                logger.info("userId:{} 两个其中一个用户取消匹配", userList.get(0).getUser().getUserId());
            } else if (userList.get(1).getStatus() == 0) {
                this.removeUser(userList.get(1).getUser().getUserId());
                matchingUsers.add(userList.get(0).getUser().getUserId());
                logger.info("userId:{} 两个其中一个用户取消匹配", userList.get(1).getUser().getUserId());
            }
            isCancel = true;
        }
        return isCancel;
    }

    /** 开始游戏：更新 DB 状态、启动 Bot 第一题倒计时、发送 TYPE_START */
    public void start(int gameId) {
        Game game = this.getGame(gameId);
        if (game == null) {
            logger.debug("游戏不存在，无法开始。gameId:{}", gameId);
            return;
        }
        this.start(game);
    }

    private void start(Game game) {
        gameRankDao.updateStatus(game.getId(),Game.STATUS_PLAYING);
        game.setStatus(Game.STATUS_PLAYING);
        List<User> userList = game.getUserList();
        //logger.debug("答题开始");
        game.setCounter(new Date().getTime());
        userList.get(0).setLastReplyTime(new Date().getTime());
        userList.get(1).setLastReplyTime(new Date().getTime());
        //机器人第一题开始回答
        if(userList.get(1).getBoot()) {
            userList.get(1).setIndex(0);
            userList.get(1).setLastReplyTime(0);
            userList.get(1).getAbility().setScore(0);
            int second = userList.get(1).getRobotRandomSecond(game);
            userList.get(1).setSeconds(second);
            userList.get(1).setAutoTime(new Date().getTime() + second);
        }
        this.send(game,new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_START,game,"",0) );
    }

    /** 下一题：前端请求切题时调用，重置双方答题计时器，Bot 启动自动答题倒计时 */
    public void next(int id, User userSub) {
        Game game = this.getGame(id);
        if (game == null) {
            logger.debug("游戏不存在, id:{}", id);
            return;
        }
        /*if(!game.isAnswerByIndexSub(userSub.getIndex() - 1)){
            logger.debug("用户还有未打完的题不能请求下一题, id:{}", id);
            return;
        }*/

        /*设置机器人的自动答题属性*/
        for (User user : game.getUserList()) {
            user.setLastReplyTime(new Date().getTime()); //上一次答题的时间
            if (user.getBoot()) {
                int second = user.getRobotRandomSecond(game);
                user.setSeconds(second);
                user.setAutoTime(new Date().getTime() + second);
            }
        }
        game.setCounter(new Date().getTime());
    }

    /** 轮询检查所有进行中游戏的 Bot，到时间自动替 Bot 答题 */
    public void runRobot() {
        Date now = new Date();
        while (this.robot(now) != null) {
            // nothing
        }
    }

    /**
     * 机器人自动答题：遍历 gameMap 中所有进行中的游戏，
     * 找到有 Bot 的对局，检查是否到达自动答题时间点，到达则随机选答案提交。
     */
    public Game robot(Date now) {
        Game game = null;
        User withUser = null;
        synchronized (gameMap){
            for(Game g : gameMap.values()) {
                if (g.getCounter() == 0 || g.getStatus() != Game.STATUS_PLAYING) {
                    continue;
                }
                for (User user : g.getUserList()) {
                    if (user.getBoot()) {
                        withUser = user;
                        break;
                    }
                }
                if (withUser == null) {
                    continue;
                }

                if (g.getAnswerIndex() < g.getSubjectList().size()) {
                    if ((withUser.getAutoTime() != 0 && new Date().getTime() >= withUser.getAutoTime())) {
                        withUser.setAutoTime(0);
                        game = g;
                        break;
                    }
                }
            }
        }
        if(game != null && withUser != null){
            //System.out.println("机器人答=="+subjectIndex);
            Subject subject = game.getSubjectList().get(game.getAnswerIndex());
            int option = withUser.getRobotRandomOption(subject);
            Answer answer = new Answer();
            answer.setUid(withUser.getUser().getUserId());
            answer.setGameId(game.getId());
            answer.setIndex(game.getAnswerIndex());
            answer.setAnswer(option);
            answer.setSeconds(withUser.getSeconds());
            answer.setTimeout(false);
            answer.setBoot(true);
            this.onAnswer(withUser,answer);
            //logger.info("虚拟玩家答题: {}", answer);
        }
        return game;
    }

    /**
     * 处理答题（真人或 Bot）：
     * - 调用 user.onAnswer(game, answer) 内部记录答案
     * - 双方都答完或超时 → game.reSetCountDown(answer.getIndex()) 推进倒计时
     * - 发送 TYPE_ANSWER 给对局双方（让对方看到对手的作答状态）
     */
    public void onAnswer(User user, Answer answer) {
        Game game = this.getGame(answer.getGameId());
        if (game == null) {
            logger.debug("游戏不存在，无法答题。answer:{}", answer);
            return;
        }
        if (answer.getIndex() >= game.getSubjectList().size()) {
            logger.debug("题目序号不正确，无法答题。answer:{}", answer);
            return;
        }
        boolean isAgain = user.onAnswer(game, answer);
        // 重复了就不要下发了
        if(!isAgain){
            if(!answer.getBoot()){
                Subject subject = game.getSubjectList().get(answer.getIndex());
                int rightNum = answer.isRight() ? 1 : 0;
                int errorNum = answer.isRight() ? 0 : 1;
                this.updateSubjectRatio(subject.getId(),rightNum,errorNum);
                //subjectRatioService.update(subject.getId(),1,rightNum,errorNum);
            }
            game.reSetCountDown(answer.getIndex());
            this.send(game, new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_ANSWER,answer,"",0));
        }
    }

    /**
     * 结束游戏入口：
     * - 如果用户在第 5 题答完后正常退出 → 不算逃跑（excludeUid=0）
     * - 否则视为逃跑（excludeUid=逃跑者 userId）
     */
    public void endGame(int id, int excludeUid) {
        Game game = this.getGame(id);
        if (game != null) {
            //如果用户在最后一道题都答完了，点击返回，不视为逃跑。
            if(game.getStatus() == Game.STATUS_PLAYING && excludeUid > 0 && game.isAnswerByIndex(4)){
                excludeUid = 0;
                logger.info("如果用户在最后一道题点击返回，不视为逃跑, gameId:{} excludeUid:{}", id, excludeUid);
            }else{
                logger.info("用户逃跑, gameId:{} excludeUid:{}", id, excludeUid);
            }
            this.endGame(game, excludeUid);
        }
    }

    /**
     * 游戏结算核心：
     * 1. 标记游戏结束，从 gameMap 移除
     * 2. 调用 game.endGame(excludeUid) 判定胜负
     * 3. 依次更新：战斗力、段位/星星、胜率统计、幸运数字
     * 4. 写入 gameRankDao
     * 5. 逃跑时单独通知未逃跑用户 TYPE_EXIT，正常结束群发 TYPE_END
     * 6. 从 userMap 移除真人玩家
     */
    private void endGame(Game game, int excludeUid) {
        if (game.getStatus() != Game.STATUS_END) {
            synchronized (gameMap) {
                if (game.getStatus() != Game.STATUS_END) {
                    game.setStatus(Game.STATUS_END);
                } else {
                    return;
                }
            }
        } else {
            return;
        }
        game.setCounter(0);
        this.removeGame(game.getId());
        game.endGame(excludeUid); //选出获胜者

        // 以下更新操作可能因表不存在而失败，逐条 try-catch 保证 type=7 一定能发出
        try { userCombatService.update(game); } catch (Throwable t) { logger.warn("endGame userCombat fail: {}", t.getMessage()); }
        try { userAbilityService.update(game); } catch (Throwable t) { logger.warn("endGame userAbility fail: {}", t.getMessage()); }
        try { userParagraphService.update(game); } catch (Throwable t) { logger.warn("endGame userParagraph fail: {}", t.getMessage()); }
        try { userGameStatisRankService.update(game); } catch (Throwable t) { logger.warn("endGame userStatisRank fail: {}", t.getMessage()); }
        try { userLuckNumberService.update(game); } catch (Throwable t) { logger.warn("endGame userLuckNumber fail: {}", t.getMessage()); }
        try { gameRankDao.update(game); } catch (Throwable t) { logger.warn("endGame gameRankDao fail: {}", t.getMessage()); }
        if(excludeUid > 0 ){
            List<User> userList = game.getUserList();
            for (int i = 0; i < userList.size(); i++) {
                if(userList.get(i).getUser().getUserId() == excludeUid){
                    continue;
                }
                this.sendToUser(game.getUserList().get(i), new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_EXIT,game,"",0));
            }
        }else if(excludeUid == 0){
            this.send(game, new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_END,game,"",0));
        }
        this.removePlayer(game.getUserList());
        logger.info("答题结束, game:{}", game);
    }

    /**
     * 答题超时检测：遍历所有进行中的游戏，
     * 双方都答完当前题或超过 10 秒（GAME_MATCH_SUBJECTSECOND）→ 自动推进到下一题，
     * 第 5 题答完 → 触发 endGame。
     */
    public void runTimeOut() {
        //synchronized (gameMap) {
        for (Game game : gameMap.values()) {
            if (game.getCounter() == 0 || game.getSubjectList() == null || game.getStatus() != Game.STATUS_PLAYING) {
                continue;
            }
            //如果用户都答完直接下一题
            if (game.isAnswerByIndexSub(game.getAnswerIndex()) || (new Date().getTime() - game.getCounter()) >= Param.GAME_MATCH_SUBJECTSECOND) {
                Date now = new Date();
                game.setCounter(now.getTime() + 8000);
                game.setAnswerIndex(game.getAnswerIndex() + 1);
                //结束游戏
                if (game.getAnswerIndex() - 1 >= 4) {
                    this.endGame(game, 0);
                    continue;
                }
                Map<String, Object> nextMap = new HashMap<>();
                nextMap.put("gameId", game.getId());
                nextMap.put("index", game.getAnswerIndex());
                for (User user : game.getUserList()) {
                    user.setLastReplyTime(new Date().getTime()); //上一次答题的时间
                    if (user.getBoot()) {
                        int second = user.getRobotRandomSecond(game);
                        user.setSeconds(second);
                        user.setAutoTime(now.getTime() + 8000);
                    }
                }
                this.send(game, new BasicResultObject(BasicTagState.TagSuccess, Protocol.TYPE_NEXT, nextMap, "", 0));
            }
        }
        //}
    }

    /**
     * 心跳超时检测：遍历 userMap，超过 120 秒没心跳的用户
     * 从 userMap 移除、关闭 WebSocket、清理对应游戏
     */
    public void runKeepTimeOut() {
        //synchronized (userMap) {
        try {
            for (User user : userMap.values()) {
                if (!user.getBoot() && new Date().getTime() - user.getKeepTime() >= 2 * 60 * 1000) {
                    //超过120秒不发心跳
                    logger.info("用户心跳包超时,默认退出游戏 userId:{} ", user.getUser().getUserId());
                    userMap.remove(user.getUser().getUserId());
                    if (user.getGameId() > 0) {
                        gameMap.remove(user.getGameId());
                    }
                    if (user.getSession().isOpen()) {
                        logger.info("用户心跳包超时,没有关闭 userId:{} ", user.getUser().getUserId());
                        user.getSession().close();
                    }
                }
            }
        } catch (Exception ex) {
            logger.debug("用户心跳异常:{}", ex.getMessage());
        }
        //}
    }

    //避免每个月1号,凌晨2点时,有玩家,初始化段位(备注:段位都要清零)
    public void initUserParagraph() {
        try{
            for(Game game : gameMap.values()){
                if (game.getSubjectList() == null || game.getUserList() == null) {
                    continue;
                }
                for (User user : game.getUserList()) {
                    user.getAbility().setRealParagraph(1);
                    user.getAbility().setParagraph(1);
                    user.getAbility().setRealStar(0);
                    user.getAbility().setStar(0);
                    user.getAbility().setMmr(100);
                    user.getAbility().setReceiveStar(0);
                }
                logger.info("避免凌晨2点时,有玩家,初始化段位 gameId:{}",game.getId());
            }
        }catch (Exception ex){
            ex.printStackTrace();
            logger.info("避免凌晨2点时,有玩家,初始化段位 异常:{}",ex.getMessage());
        }

    }

    public void addUser(int id,User user) {
        synchronized (userMap) {
            userMap.put(id,user);
        }
    }

    public void runSubjectRatio() {
        synchronized (subjectRatioMap) {
            try {
                int counter = 0;
                for (Map<String, Object> map : subjectRatioMap.values()) {
                    int subjectId = Integer.parseInt(map.get("subjectId").toString());
                    int rightNum = Integer.parseInt(map.get("rightNum").toString());
                    int errorNum = Integer.parseInt(map.get("errorNum").toString());
                    int totalNum = rightNum + errorNum;
                    subjectRatioService.update(subjectId, totalNum, rightNum, errorNum);
                    subjectRatioMap.remove(subjectId);
                    counter = counter + 1;
                    if (counter >= 5) {//每次修改五道题
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.info("runSubjectRatio修改题目正确率 异常:{}", ex.getMessage());
            }
        }
    }

    public void updateSubjectRatio(int subjectId,int rightNum,int errorNum) {
        Map<String, Object> subjectMap = subjectRatioMap.get(subjectId);
        int currentRightNum = subjectMap == null ? 0 : Integer.parseInt(subjectMap.get("rightNum").toString());
        int currentErrorNum = subjectMap == null ? 0 : Integer.parseInt(subjectMap.get("errorNum").toString());
        Map<String, Object> map = new HashMap<>();
        map.put("subjectId",subjectId);
        map.put("rightNum", rightNum > 0 ? currentRightNum + rightNum : currentRightNum);
        map.put("errorNum", errorNum > 0 ? currentErrorNum + errorNum : currentErrorNum);
        //System.out.println("subjectId = " + subjectId + " rightNum = "+ map.get("rightNum") + " errorNum = "+ map.get("errorNum"));
        subjectRatioMap.put(subjectId, map);
    }

    public User getUser(int uid) {
        return userMap.get(uid);
    }

    public void removeUser(int id) {
        userMap.remove(id);
    }

    public Game getGame(int id) {
        return gameMap.get(id);
    }

    public void addGame(Game game) {
        synchronized (gameMap) {
            gameMap.put(game.getId(), game);
        }
    }

    public void removeGame(int id) {
            gameMap.remove(id);
    }

    public void addRobot(int id,User user){
        synchronized (robotMap) {
            robotMap.put(id, user);
        }
    }

    //删除玩家
    public void removePlayer(List<User> userList){
        //用户信息删除
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            if(!user.getBoot()){
                userMap.remove(user.getUser().getUserId());
            }else{
                user.setIndex(0);
                user.setRestSecond(0);
                user.setAutoTime(0);
                user.setLastReplyTime(0);
                user.getAbility().setScore(0);
                user.getAbility().setRightNum(0);
                user.getAbility().setBookTotalScore(0);
                //user.getAbility().setContinueRightNum(0);
                //user.getAbility().setMaxContinueRightNum(0);
                //user.getAbility().setTotalContinueRightScore(0);
                //robotMap.put(user.getUser().getUserId(),user);//机器人重新放回队列
            }

        }
    }

    public void noWriteLogSend(Game game, BasicResultObject result) {
        if (game == null) {
            return;
        }
        for (int i = 0; i < game.getUserList().size(); i++) {
            if (game.getUserList().get(i).getBoot()) {
                continue;
            }
            sendMsgToClient.sendMsgToClient(game.getUserList().get(i).getSession(), result);
        }
    }

    public void send(Game game, BasicResultObject result) {
        List<User> userList = game.getUserList();
        for (int i = 0; i < userList.size(); i++) {
            this.sendToUser(game.getUserList().get(i), result);
        }
    }

    public void sendToUser(User user, BasicResultObject result) {
        String json = JSONObject.toJSONString(result);
        if (user.getBoot()) {
            logger.debug("不用给虚拟玩家发送消息");
            return;
        }
        if (user.getSession() == null) {
            logger.debug("给用户发送消息失败，用户未连接，user:{} message:{}", user, json);
            return;
        }
        try {
            sendMsgToClient.sendMsgToClient(user.getSession(), result);
            logger.info("发送消息,sessionId:{} user:{} message:{}",user.getSession().getId(), user.getUser().getUserId(), json);

        } catch (Throwable t) {
            logger.debug("给用户发送消息失败，user:{} message:{} t:{}", user, json, t.getMessage());
        }
    }

}
