package com.example.vientianetest.service;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicTagState;
import com.example.common.utils.DesUtils;
import com.example.vientianetest.dao.*;
import com.example.vientianetest.dao.subject.SubjectDao;
import com.example.vientianetest.domain.*;
import com.example.vientianetest.observer.RoomObserverImpl;
import com.example.vientianetest.redis.RoomRedis;
import com.example.vientianetest.service.user.UserAbilityService;
import com.example.vientianetest.service.user.UserGameStatisRoomService;
import com.example.vientianetest.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.*;

@Component
public class WebSocketRoomService {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketRoomService.class);
    DesUtils desUtils = new DesUtils("1234567890");
    @Autowired
    private RoomRedis roomRedis;
    @Autowired
    private UserService userService;
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private GameRoomDao gameRoomDao;
    @Autowired
    private SendMsgService sendMsgToClient;
    @Autowired
    private SubjectRatioService subjectRatioService;
    @Autowired
    private UserAbilityService userAbilityService;
    @Autowired
    private UserGameStatisRoomService userGameStatisRoomService;
    @Autowired
    private RoomObserverImpl roomObserverImpl;
    @Autowired
    private WebSocketRankService webSocketRankService;

    private ExecutorService matchExecutorService = Executors.newFixedThreadPool(50);

    private Map<String, RoomGame> gameMap = new ConcurrentHashMap<>();
    private Map<Integer, User> userMap = new ConcurrentHashMap<>();
    private Map<Integer, User> robotMap = new ConcurrentHashMap<>();
    private CopyOnWriteArrayList<RoomGame> matchingGame = new CopyOnWriteArrayList<>();
    private boolean isSendAll = false;

    public String getSid(WebSocketSession session) {
        return session.getAttributes().get("code").toString();
    }

    public String getUserId(WebSocketSession session) {
        return session.getAttributes().get("userId").toString();
    }

    public int getType(WebSocketSession session) {
        return Integer.parseInt(session.getAttributes().get("type").toString());
    }

    //重连用户
    public void reconnect(User reUser,WebSocketSession session) {
        RoomGame game = null;

        User user = userMap.get(reUser.getUser().getUserId());
        if (user != null) {
            String roomId = user.getRoomId();
            String playRoomId = user.getPlayRoomId();
            if (playRoomId.length() > 0) {
                game = this.getGame(playRoomId);
            } else {
                game = this.getGame(roomId);
            }
            if (roomId.length() > 0) {
                user.setStatus(1);
                user.setSession(session);
                user.setKeepTime(new Date().getTime());
                if (game != null && game.getGame().getStatus() == Game.STATUS_PLAYING /*&& user.getLastReplyTime() > 0*/) {
                    //List<user> userList = game.getRoomSub().getUserList();
                    Date now = new Date();
                    long counter = game.getRoomSub().getCounter();
                    long restSecond = now.getTime() - counter <= Param.GAME_MATCH_SUBJECTSECOND ? Param.GAME_MATCH_SUBJECTSECOND - (now.getTime() - counter) : Param.GAME_MATCH_SUBJECTSECOND;//Param.GAME_MATCH_SUBJECTSECOND - ((now.getTime() - counter)%Param.GAME_MATCH_SUBJECTSECOND);
                    game.getRoomSub().setRestSecond(restSecond);
                    logger.debug("房间_用户重新连接");
                    this.sendToUser(user, new BasicResultObject(BasicTagState.TagSuccess, Protocol.TYPE_RECONNECT, game, "", 0));

                        /*for(int i=0;i<userList.size();i++){
                            int rightAnswer = game.getGame().getSubjectList().get(game.getRoomSub().getAnswerIndex()).getOptionSortId();
                            List<Subject.Answer> answerList = game.getGame().getSubjectList().get(game.getRoomSub().getAnswerIndex()).getAnswerList();
                            if(answerList == null || userList.get(i).getStatus() != 1){continue;}
                            for(int j=0;j<answerList.size();j++){
                                if(answerList.get(j).getUid() == userList.get(i).getUser().getUserId()){
                                    int answer = answerList.get(j).getAnswer() ;
                                    boolean isRight = answer == rightAnswer ? true : false;
                                    Answer answerObj = new Answer(game.getRoomSub().getRoomId(), userList.get(i).getUser().getUserId(), game.getRoomSub().getAnswerIndex(), answer, rightAnswer, GameParam.ReplySubjectSecond, userList.get(i).getAbility().getScore(), true, isRight, false);
                                    logger.debug("房间_模拟下发");
                                    this.sendToUser(user, new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_ANSWER,answerObj,"",0));
                                    break;
                                }
                            }
                        }*/
                } else if (game != null && game.getGame().getStatus() == Game.STATUS_NEW) {
                    this.sendToUser(user, new BasicResultObject(BasicTagState.TagSuccess, Protocol.TYPE_MATCH, game, "", 0));
                } else {
                    this.sendToUser(user, new BasicResultObject(BasicTagState.TagSuccess, Protocol.TYPE_RECONNECT, "", "", 0));
                }
            }
        } else {
            logger.debug("房间_用户重新连接，心跳包超时，重新加入 userId:{}", reUser.getUser().getUserId());
            this.addUser(reUser, session);
            this.sendToUser(reUser, new BasicResultObject(BasicTagState.TagSuccess, Protocol.TYPE_RECONNECT, "", "", 0));
        }
    }

    //添加用户
    public void addUser(User user,WebSocketSession session) {
        this.autoExitUser(user.getUser().getUserId(), user.getRoomId());//删除旧房间数据
        if (this.getUser(user.getUser().getUserId()) == null) {
            userMap.put(user.getUser().getUserId(), user);
        } else {
            user = this.getUser(user.getUser().getUserId());
        }
        user.setStatus(1);//在线
        user.setBoot(false);
        user.setKeepTime(new Date().getTime());
        user.setSession(session);
    }

    //四人混战
    public void createModeTypeRoom(User user,int modeType,WebSocketSession session) {
        //房间改成四位数
        //1.四人混战,2:3v3
        if(modeType != 1 && modeType != 2) {
            logger.debug("未知modeType。userId:{} modeType:{}", user.getUser().getUserId(), modeType);
            return;
        }
        String roomId = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        while (this.getGame(roomId) != null) {
            roomId = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        }
        this.addRoom(roomId, user, modeType, session);
    }

    //匹配状态
    public void matchStatus(User user,WebSocketSession session) {
        RoomGame game = this.getGame(user.getRoomId());
        if (game == null) {
            logger.debug("房间不存在，无法快速开始游戏。roomId:{}", user.getRoomId());
            return;
        }
        if (!game.allReady()) {
            logger.debug("房间_还有人没有准备。roomId:{}", user.getRoomId());
            return;
        }
        if (game.getRoomSub().getRoomMaster() > 0 && user.getUser().getUserId() != game.getRoomSub().getRoomMaster()) {
            logger.debug("不是房主，不能匹配。roomId:{} userId:{} ", user.getRoomId(), user.getUser().getUserId());
            return;
        }
        if (matchingGame.contains(game)) {
            logger.debug("房间_已经在匹配队列中。roomId:{} userId:{} ", user.getRoomId(), user.getUser().getUserId());
            return;
        }
        this.send(game, new BasicResultObject(BasicTagState.TagSuccess, Protocol.TYPE_STATUS_MATCH, -1, "", 0));
    }

    //快速开始游戏
    public void quickStartGame(User user,WebSocketSession session) {
        //1.如果房间有足够人的话，直接开始游戏
        //2.没有足够人的话，先匹配真人，相加超过人数的房间不要，否则匹配机器人
        String roomId = user.getRoomId();
        RoomGame game = this.getGame(roomId);
        if (game == null) {
            logger.debug("房间不存在，无法快速开始游戏。roomId:{}", roomId);
            return;
        }
        /*if(game.getRoomSub().getUserList().size() <= 1){
            logger.debug("房间人数不够，无法开始。roomId:{}", roomId);
            return;
        }*/
        if(!game.allReady()){
            logger.debug("房间_还有人没有准备。roomId:{}", roomId);
            return;
        }
        if (game.getRoomSub().getRoomMaster() > 0 && user.getUser().getUserId() != game.getRoomSub().getRoomMaster()) {
            logger.debug("不是房主，不能快速开始游戏。roomId:{} userId:{} ", roomId, user.getUser().getUserId());
            return;
        }
        if(game.getRoomSub().getRoomStatus() == RoomGame.STATUS_PLAYING) {//就怕用户一直发type:-1
            logger.debug("房间_房间已经开始，单独下发给用户。roomId:{} userId:{} ", user.getRoomId(), user.getUser().getUserId());
            this.sendToUser(user, new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_MATCH, game ,"",0));
            return;
        }
        int maxUserNum = game.getRoomSub().getModeType() == 2 ? 6 : 4;
        if (game.getRoomSub().getModeType() == 1 && game.getRoomSub().getUserList().size() == maxUserNum) {
            //直接开始游戏
            game.getRoomSub().setRoomList(new ArrayList<>());
            game.getRoomSub().getRoomList().add(game.getRoomSub().getRoomId());
            game.setUserPlayRoomId(game.getRoomSub().getRoomId());
            this.start(roomId);
        } else {
            //丢到队列进行匹配
            game.getRoomSub().setJoinTime(new Date().getTime());
            matchingGame.add(game);
        }
    }

    public void runMatch() {
        // 匹配所有能匹配的玩家
        Date now = new Date();
        while (this.match(now)) {
            // nothing
        }
    }

    public Boolean match(Date now) {
        RoomGame matchGame = new RoomGame();
        try {
            synchronized (matchingGame) {
                int size = matchingGame.size();
                if (size == 0) {
                    return false;
                }
                RoomGame game = matchingGame.get(0);
                //等3秒
                //没有足够人的话，先匹配真人，相加超过人数的房间不要，否则匹配机器人
                if (now.getTime() - game.getRoomSub().getJoinTime() < 3 * 1000) {
                    return false;
                }
                int maxUserNum = game.getRoomSub().getModeType() == 2 ? 6 : 4;
                matchingGame.remove(0);
                matchGame.setGame(new Game());
                matchGame.setRoomSub(new RoomSub());
                matchGame.getRoomSub().setUserList(new CopyOnWriteArrayList<>());
                matchGame.getRoomSub().setRoomList(new ArrayList<>());
                matchGame.getRoomSub().setModeType(game.getRoomSub().getModeType());
                matchGame.getRoomSub().getRoomList().add(game.getRoomSub().getRoomId());
                List<User>  leftGroupUserList = new ArrayList<>();//3v3分两边->左边
                List<User>  rightGroupUserList = new ArrayList<>();//3v3分两边->右边
                if(matchGame.getRoomSub().getModeType() == 1){
                    matchGame.getRoomSub().getUserList().addAll(game.getRoomSub().getUserList());
                }else if(matchGame.getRoomSub().getModeType() == 2) {
                    if (game.getRoomSub().getUserList().size() < 6) {
                        leftGroupUserList.addAll(game.setGroupByUserList(game.getRoomSub().getUserList(), 1));//设置分组
                    } else {
                        //这个判断避免用户同时发送type:-1,否则用户会一边倒
                        for (int i = 0; i < game.getRoomSub().getUserList().size(); i++) {
                            if (i < 3) {
                                leftGroupUserList.add(game.getRoomSub().getUserList().get(i));
                            } else {
                                rightGroupUserList.add(game.getRoomSub().getUserList().get(i));
                            }
                        }
                    }
                }
                if (matchingGame.size() > 0) {
                    for (RoomGame g : matchingGame) {
                        //可以匹配
                        if(matchGame.getRoomSub().getModeType() == 1){//四人混战
                            if (matchGame.getRoomSub().getModeType() == g.getRoomSub().getModeType() &&
                                    g.getRoomSub().getUserList().size() + matchGame.getRoomSub().getUserList().size() <= maxUserNum) {
                                matchGame.getRoomSub().getUserList().addAll(g.getRoomSub().getUserList());
                                matchGame.getRoomSub().getRoomList().add(g.getRoomSub().getRoomId());
                                g.getRoomSub().setRoomStatus(RoomGame.STATUS_PLAYING);
                                g.getGame().setStatus(Game.STATUS_PLAYING);
                                matchingGame.remove(g);
                            }
                            if (matchGame.getRoomSub().getUserList().size() == maxUserNum) {
                                break;
                            }
                        }else if(matchGame.getRoomSub().getModeType() == 2) {//3v3
                            if (matchGame.getRoomSub().getModeType() == g.getRoomSub().getModeType() &&
                                    g.getRoomSub().getUserList().size() + leftGroupUserList.size() <= maxUserNum/2) {
                                leftGroupUserList.addAll(g.setGroupByUserList(g.getRoomSub().getUserList(),1));//设置分组
                                matchGame.getRoomSub().getRoomList().add(g.getRoomSub().getRoomId());
                                g.getRoomSub().setRoomStatus(RoomGame.STATUS_PLAYING);
                                g.getGame().setStatus(Game.STATUS_PLAYING);
                                matchingGame.remove(g);
                            }else if(matchGame.getRoomSub().getModeType() == g.getRoomSub().getModeType() &&
                                    g.getRoomSub().getUserList().size() + rightGroupUserList.size() <= maxUserNum/2) {
                                rightGroupUserList.addAll(g.setGroupByUserList(g.getRoomSub().getUserList(),2));//设置分组
                                matchGame.getRoomSub().getRoomList().add(g.getRoomSub().getRoomId());
                                g.getRoomSub().setRoomStatus(RoomGame.STATUS_PLAYING);
                                g.getGame().setStatus(Game.STATUS_PLAYING);
                                matchingGame.remove(g);
                            }
                            if (leftGroupUserList.size() + rightGroupUserList.size() == maxUserNum) {
                                break;
                            }
                        }
                    }
                }
                int robotNum = matchGame.getRoomSub().getModeType() == 2 ? maxUserNum - leftGroupUserList.size() - rightGroupUserList.size() : maxUserNum - matchGame.getRoomSub().getUserList().size();
                if (robotMap.size() < robotNum) {
                    logger.info("快速开始游戏，机器人数量不够！");
                    return false;
                }
                if (robotNum < 0) {
                    return false;
                }
                List<Integer> robotList = new ArrayList<>(); //机器人不能重复
                for (int i = 0; i < robotNum; i++) {
                    Integer[] keys = robotMap.keySet().toArray(new Integer[0]);
                    Integer randomKey = keys[new Random().nextInt(keys.length)];
                    while (robotList.contains(randomKey)) {
                        randomKey = keys[new Random().nextInt(keys.length)];
                    }
                    robotList.add(randomKey);
                    User randomRobot = robotMap.get(randomKey);
                    randomRobot.setStatus(1);
                    randomRobot.setIsReady(1);
                    robotMap.remove(randomKey);
                    randomRobot.setPlayRoomId(game.getRoomSub().getRoomId());
                    if(matchGame.getRoomSub().getModeType() == 1){
                        matchGame.getRoomSub().getUserList().add(randomRobot);
                    }else if(matchGame.getRoomSub().getModeType() == 2) {
                        //3v3分边凑机器人
                        if(leftGroupUserList.size() < maxUserNum/2) {
                            randomRobot.setGroupId(1);
                            leftGroupUserList.add(randomRobot);
                        }else if(rightGroupUserList.size() < maxUserNum/2) {
                            randomRobot.setGroupId(2);
                            rightGroupUserList.add(randomRobot);
                        }
                    }
                }
                if(matchGame.getRoomSub().getModeType() == 2){//3v3按组添加
                    matchGame.getRoomSub().getUserList().addAll(leftGroupUserList);
                    matchGame.getRoomSub().getUserList().addAll(rightGroupUserList);
                    matchGame.getRoomSub().setGroupList(new ArrayList<>());
                    matchGame.getRoomSub().getGroupList().add(new RoomGroup(1,0));
                    matchGame.getRoomSub().getGroupList().add(new RoomGroup(2,0));
                }
                matchGame.setUserPlayRoomId(game.getRoomSub().getRoomId());
                matchGame.getRoomSub().setRoomId(game.getRoomSub().getRoomId());
                matchGame.getRoomSub().setRoomStatus(RoomGame.STATUS_NEW);
                matchGame.getRoomSub().setRoomMaster(game.getRoomSub().getRoomMaster());
                matchGame.getRoomSub().setUserNum(matchGame.getRoomSub().getUserList().size());
                //logger.info("房间_匹配模式。。");
            }
            matchExecutorService.submit(() -> {
                try {
                    //判断用户之前是否重新点击匹配，如果有的话，默认用户逃跑
                    /*if (matchGame.getRoomSub().getUserList().get(0).getPlayRoomId() != null && matchGame.getRoomSub().getUserList().get(0).getPlayRoomId().length() > 0) {
                        logger.info("房间_删除之前的游戏,roomId:{} userId:{}", matchGame.getRoomSub().getUserList().get(0).getPlayRoomId(), matchGame.getRoomSub().getUserList().get(0).getUser().getUserId());
                        this.userExit(matchGame.getRoomSub().getUserList().get(0).getRoomId(), matchGame.getRoomSub().getUserList().get(0).getPlayRoomId(), matchGame.getRoomSub().getUserList().get(0).getUser().getUserId());
                        matchGame.getRoomSub().getUserList().get(0).setStatus(1);
                        matchGame.getRoomSub().getUserList().get(0).setIsReady(1);
                    }*/
                    gameMap.put(matchGame.getRoomSub().getRoomId(), matchGame);
                    this.start(matchGame);
                } catch (Throwable t) {
                    logger.debug("匹配成功后发生异常, t:{}", t);
                }
            });
        } catch (Throwable t) {
            logger.debug("匹配成功后发生异常2, t:{}", t);
        }
        return true;
    }

    //获取群大厅
    /*public void getHallList(user user,WebSocketSession session) {
        synchronized (userMap) {
            user currentUser = this.getUser(user.getUser().getUserId());
            if(currentUser != null){
                currentUser.setKeepTime(new Date().getTime());
                currentUser.setObserver(true);
                this.autoExitUser(user.getUser().getUserId(),currentUser.getRoomId());
            }else {
                this.addUser(user);
            }
            user.setIsReady(0);
            user.setSession(session);
            user.setObserver(true);
            user.setKeepTime(new Date().getTime());
            this.sendToObserver(user);
        }
    }*/

    //加入房间
    public void enterGame(User user,String roomId,WebSocketSession session){
        synchronized (gameMap) {
            int isExpire = 1;
            boolean isExist = false;
            RoomGame room = gameMap.get(roomId);
            if (room == null) {
                isExpire = -1;//空房间
            } else {
                for (int i = 0; i < room.getRoomSub().getUserList().size(); i++) {
                    if (room.getRoomSub().getUserList().get(i).getUser().getUserId() == user.getUser().getUserId()) {
                        isExist = true;
                        break;
                    }
                }
                isExpire = room.getRoomSub().getRoomStatus();
                if(room.getRoomSub().getModeType() == 1) {
                    if (room.getRoomSub().getUserList().size() >= 4) {
                        isExpire = 3;
                    }
                }else if(room.getRoomSub().getModeType() == 2) {
                    if (room.getRoomSub().getUserList().size() >= 3) {
                        isExpire = 3;
                    }
                }

            }
            if(isExpire == -1 || isExpire == 1 || isExpire == 3  ){
                this.sendToUser(user,new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_ROOM_ENTER,isExpire ,"",0));
            }else if(isExpire == 0 || isExpire == 2){
                user.setGroupId(1);
                this.addRoom(roomId,user,1,session);
            }
        }
    }

    //聊天发表情
    public void chat(String roomId,String content){
        RoomGame game = this.getGame(roomId);
        if(game == null){
            return;
        }
        this.noWriteLogSend(game, new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_CHAT,content,"",0));
    }

    //每个用户只能有一个游戏
    public void autoExitUser(int userId,String roomId){
        try {
            User currentUser = this.getUser(userId);
            if (currentUser != null && currentUser.getRoomId() != null && currentUser.getRoomId().length() > 0) {//&& !currentUser.getRoomId().equals(roomId)
                logger.debug("房间_删除用户的旧房间游戏。userId:{} roomId:{} playRoomId:{} ", userId, currentUser.getRoomId(),currentUser.getPlayRoomId());
                this.userExit(currentUser.getRoomId(),currentUser.getPlayRoomId(), userId);
                currentUser.setRoomId("");
            }
        }
        catch (Exception ex){
            logger.debug("房间_删除用户的旧房间游戏异常。userId:{} roomId:{} Exception:{} ",userId,roomId,ex.getMessage());
        }
    }

    //创建房间
    public void addRoom(String roomId,User user,int modeType,WebSocketSession session) {
        synchronized (WebSocketRoomService.class) {
            this.autoExitUser(user.getUser().getUserId(),roomId);
            if(!user.getBoot()) {
                user.setAbility(new Ability());
                userGameStatisRoomService.getWinNumByUserId(user);
            }
            this.initUser(user,roomId,session);
            user.setObserver(false);
            RoomGame game = this.getGame(roomId);
            if(game != null){
                if(game.getRoomSub().getRoomStatus() == RoomGame.STATUS_PLAYING){
                    logger.debug("房间_游戏已经开始或者结束。roomId:{}", roomId);
                    return;
                }
            }

            if(this.getGame(roomId) == null){//房主
                game = this.addGame(roomId,user);
                game.getRoomSub().getUserList().add(user);
                game.getRoomSub().setUserNum(1);
                game.getRoomSub().setRoomStatus(0);
                game.getRoomSub().setRoomMaster(user.getUser().getUserId());
                game.getRoomSub().getUserList().get(0).setStatus(1);
                game.getRoomSub().getUserList().get(0).setIsReady(1);
                game.getRoomSub().getUserList().get(0).setGroupId(1);//第一组
                game.getRoomSub().setModeType(modeType);
                game.getRoomSub().setCounter(0);
            }else{
                game = this.updateGame(roomId,user);
            }
            userMap.put(user.getUser().getUserId(), user);
            if(game == null){
                return;
            }
            if(!user.getBoot()){
                logger.debug("创建房间,roomId:{} userId:{} ", roomId,user.getUser().getUserId());
            }
            //long restSecond = 10*60 - ((new Date().getTime() - game.getCreateTime())/1000);
            //game.setRestSecond(restSecond);
            if(!game.isExistsUserListByUserId(user.getUser().getUserId())){
                this.sendToUser(user, new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_ROOM_FULL,game,"",0));
                return;
            }

            this.send(game, new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_MATCH,game,"",0));
            isSendAll = true;
        }
    }

    //被踢 || 如果是房主退出的话，退位让贤
    public void kickPlayer(User user, int kickUserId){
        String roomId = user.getRoomId();
        int userId = user.getUser().getUserId();
        RoomGame game = this.getGame(roomId);
        if(game == null){return;}
        if(game.getRoomSub().getRoomStatus() != RoomGame.STATUS_NEW){
            logger.debug("房间_游戏已经开始,roomId:{}", roomId);
            return;
        }
        /*if(game.getRoomMaster() != userId){
            logger.debug("当前用户不是房主没有权限踢人, roomId:{}", roomId);
            return;
        }
        if(game.getRoomMaster() == kickUserId){
            logger.debug("房主不能踢自己, roomId:{}", roomId);
            return;
        }*/
        //被踢的人下发条 type = 8 其他人下发 type = 1
        List<User> userList = game.getRoomSub().getUserList();
        for(int i= 0;i<userList.size();i++){
            if(userList.get(i).getUser().getUserId() == kickUserId){
                this.sendToUser(game.getRoomSub().getUserList().get(i), new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_ROOM_KICK,kickUserId,"",0));
                break;
            }
        }
        game = this.kickUser(roomId,kickUserId);
        this.send(game, new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_MATCH,game,"",0));
        isSendAll = true;
    }

    //准备或取消准备
    public void changeStatus(User user){
        String roomId = user.getRoomId();
        int userId = user.getUser().getUserId();
        RoomGame game =this.getGame(roomId);
        if(game == null){return;}
        game.updateChangeReady(userId);
        //long restSecond = 10*60 - ((new Date().getTime() - game.getCreateTime())/1000);
        //game.setRestSecond(restSecond);
        this.send(game, new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_MATCH,game,"",0));
    }

    //点击开始之前游戏
    public void start(String roomId) {
        RoomGame game = this.getGame(roomId);
        synchronized (WebSocketRoomService.class) {
            if(game.getRoomSub().getRoomStatus() == RoomGame.STATUS_PLAYING){
                return;
            }
            matchExecutorService.submit(()-> {
                try{
                    this.start(game);
                }catch(Throwable t){
                    logger.debug("房间_开始前后发生异常, t:{}", t);
                }
            });
        }
    }

    private void start(RoomGame game) {
        User user = game.minWinRatio();
        List<Subject> subjectList = new ArrayList<>();
        if(game.getRoomSub().getModeType() == 1) {
            subjectList = subjectRatioService.getRandSubjectByRightRatio(game.getRoomSub().getUserList(),user, "");//全部类型 正常
        }else if(game.getRoomSub().getModeType() == 2) {
            subjectList = subjectDao.getRandSubjectList(game.getRoomSub().getUserList(),8,"1,3");
        }
        //List<Subject> subjectList = subjectDao.getRandSubjectList(5,4);//全部判断题
        String userStr = game.toStringByUid();
        int gameId = gameRoomDao.insert(Integer.parseInt(game.getRoomSub().getRoomId()), game.getRoomSub().getRoomMaster(), userStr, Game.STATUS_NEW,game.getRoomSub().getModeType(),userMap.size());
        game.getGame().setId(gameId);
        game.getGame().setSubjectList(subjectList);
        this.send(game, new BasicResultObject(BasicTagState.TagSuccess, Protocol.TYPE_MATCH, game, "", 0));
        //roomRedis.updateRoomStatus(game.getRoomSub().getRoomId(),RoomGame.STATUS_PLAYING);
        game.getRoomSub().setAnswerIndex(0);
        game.getGame().setStatus(Game.STATUS_PLAYING);
        game.getRoomSub().setRoomStatus(RoomGame.STATUS_PLAYING);
        logger.info("房间_匹配成功，准备答题, game:{}", game);
        game.getRoomSub().setCounter(new Date().getTime() + 3*1000);//3秒倒计时告诉用户
        for (int i = 0; i < game.getRoomSub().getUserList().size(); i++) {
            game.getRoomSub().getUserList().get(i).setAutoTime(0);
            game.getRoomSub().getUserList().get(i).setGameId(gameId);
        }
        isSendAll = true;
    }

    // 匹配
    public void next(String id,int userId){
        RoomGame game = this.getGame(id);
        if (game == null) {
            logger.debug("房间_游戏不存在, id:{}", id);
            return;
        }

        if(game.getRoomSub().getRoomStatus() == RoomGame.STATUS_END){
            logger.debug("房间_已经结束, id:{}", id);
            return;
        }
        Date now = new Date();
        for (User user : game.getRoomSub().getUserList()) {
            if (user.getBoot()) {
                int second = game.getRobotRandomSecond(user);
                user.setSeconds(second);
                user.setAutoTime(now.getTime() + second);
            }
        }
        game.getRoomSub().setCounter(now.getTime());
        //this.send(game, new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_NEXT,game.getRoomSub().getRoomId(),"",0));
    }

    //退出分为游戏中退出和准备开始退出
    public void userExit(String id,String playRoomId ,int excludeUid) {
        RoomGame game = null;
        if (playRoomId != null && playRoomId.length() > 0) {
            game = this.getGame(playRoomId);
        } else {
            game = this.getGame(id);
        }
        if (game == null) {
            return;
        }
        User user = this.getUser(excludeUid);
        if (user != null && user.getRoomId() != null) {
            user.setRoomId("");
        }
        synchronized (gameMap) {
            if (game.getRoomSub().getRoomStatus() == RoomGame.STATUS_NEW || game.getRoomSub().getRoomStatus() == RoomGame.STATUS_END) {
                logger.info("房间_用户退出, gameId:{} excludeUid:{}", id, excludeUid);
                game = this.kickUser(id, excludeUid);
                //this.removeUser(excludeUid);
                if (game != null && game.getRoomSub().getUserList().size() > 0) {
                    this.send(game, new BasicResultObject(BasicTagState.TagSuccess, Protocol.TYPE_MATCH, game, "", 0));
                }
                isSendAll = true;
            } else if (game.getRoomSub().getRoomStatus() == RoomGame.STATUS_PLAYING) {
                logger.info("房间_用户逃跑, gameId:{} excludeUid:{}", id, excludeUid);
                game.userExit(excludeUid);
                //roomRedis.kickUser(id);
                if (game.onLineUserNum() < 2) { //玩家在线数小于2，结束游戏
                    List<User> userList = game.getRoomSub().getUserList();
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getStatus() == 0 || userList.get(i).getUser().getUserId() == excludeUid) {
                            this.endGame(game, 0);
                            //game = this.kickUser(id,userList.get(i).getUser().getUserId());
                            break;
                        }
                    }
                } else {
                    Subject subject = game.getGame().getSubjectList().get(game.getRoomSub().getAnswerIndex());
                    if (subject.getAnswerList() != null) {
                        for (Subject.Answer sub : subject.getAnswerList()) {
                            if (sub.getUid() == excludeUid) {
                                logger.info("用户退出之前有答题删除记录 userId:{} playRoomId:{}", excludeUid, playRoomId);
                                subject.getAnswerList().remove(sub);
                                break;
                            }
                        }
                    }
                    this.send(game, new BasicResultObject(BasicTagState.TagSuccess, Protocol.TYPE_MATCH, game, "", 0));
                    //把用户从另外一个房间移除
                    if (!id.equals(playRoomId)) {
                        RoomGame userGame = this.getGame(id);
                        if (userGame != null) {
                            logger.info("把用户从另外一个房间移除 userId:{} playRoomId:{}", excludeUid, playRoomId);
                            this.kickUser(id, excludeUid);
                        }
                    }
                    isSendAll = true;
                }
            }
        }
    }

    private void endGame(RoomGame game, int excludeUid) {
        if (game.getGame().getStatus() != Game.STATUS_END) {
            synchronized (gameMap) {
                if (game.getGame().getStatus() != Game.STATUS_END) {
                    game.getGame().setStatus(Game.STATUS_END);
                    game.getRoomSub().setRoomStatus(RoomGame.STATUS_END);
                    //roomRedis.updateRoomStatus(game.getRoomSub().getRoomId(),RoomGame.STATUS_END);
                } else {
                    return;
                }
            }
        } else {
            return;
        }

        //this.removeGame(game.getRoomId());
        //this.removePlayer(game);
        game.endGame(excludeUid); //选出获胜者
        gameRoomDao.update(game);
        userAbilityService.update(game);
        userGameStatisRoomService.update(game); //统计用户比赛次数
        //List<RoomGame> gameList = new ArrayList<>();//用于由几个房间组成游戏，下发信息用
        if (excludeUid == 0) {
            this.send(game, new BasicResultObject(BasicTagState.TagSuccess, Protocol.TYPE_END, game, "", 0));
            //初始化
            List<User> userList = game.getRoomSub().getUserList();
            for (User user : userList) {
                if (!user.getPlayRoomId().equals(user.getRoomId()) || user.getBoot()) {
                    if (user.getBoot()) {
                        this.initUser(user, "", user.getSession());
                        robotMap.put(user.getUser().getUserId(), user);
                    } else {
                        this.initUser(user, user.getRoomId(), user.getSession());
                    }
                    userList.remove(user);
                    //如果是机器人放回机器人队列
                    continue;
                }
                this.initUser(user, user.getRoomId(),user.getSession());
                //游戏结束后，用户各回各的房间
                if (!user.getPlayRoomId().equals(user.getRoomId()) && !user.getBoot()) {
                    RoomGame userGame = this.getGame(user.getRoomId());
                    if (userGame != null) {
                        if (userGame.getRoomSub().getRoomMaster() == user.getUser().getUserId()) {
                            user.setIsReady(1);
                        } else {
                            user.setIsReady(0);
                        }
                    }
                }
            }
            //用户都退完的话，删除房间
            if(userList.size() <= 0) {
                this.removeGame(game.getRoomSub().getRoomId());
            }else{
                game.getRoomSub().setRoomMaster(game.getRoomSub().getUserList().get(0).getUser().getUserId());//避免房主退出房间
                game.getRoomSub().getUserList().get(0).setIsReady(1);//准备状态
                game.getRoomSub().setUserNum(userList.size());
            }
        }

        logger.info("房间_答题结束, game:{}", game);
        //前端直接显示房间的信息
        for (String roomId : game.getRoomSub().getRoomList()) {
            RoomGame g = this.getGame(roomId);
            if(g != null) {
                this.initGameSub(g);
                this.send(g, new BasicResultObject(BasicTagState.TagSuccess, Protocol.TYPE_MATCH, g, "", 0));
            }
        }
        isSendAll = true;
    }

    public void onAnswer(User user, Answer answer) {
        RoomGame game = this.getGame(answer.getRoomId());
        if (game == null) {
            logger.debug("房间_游戏不存在，无法答题。answer:{}", answer);
            return;
        }
        if (game.getGame().getSubjectList() == null || answer.getIndex() >= game.getGame().getSubjectList().size()) {
            logger.debug("房间_题目序号不正确，无法答题。answer:{}", answer);
            return;
        }
        boolean isAgain = game.onAnswer(answer);
        // 重复了就不要下发了
        if(!isAgain) {
            if (answer.getBoot() == null) {
                int subjectId = game.getGame().getSubjectList().get(answer.getIndex()).getId();
                int rightNum = answer.isRight() ? 1 : 0;
                int errorNum = answer.isRight() ? 0 : 1;
                webSocketRankService.updateSubjectRatio(subjectId, rightNum, errorNum);
                //subjectRatioService.update(subjectId,1,rightNum,errorNum);
            }
            answer.setGameId(game.getGame().getId());
            this.send(game, new BasicResultObject(BasicTagState.TagSuccess, Protocol.TYPE_ANSWER, answer, "", 0));
        }
    }

    public void runRobot() {
        synchronized (gameMap) {
            for (RoomGame game : gameMap.values()) {
                if (game.getRoomSub().getCounter() == 0 || game.getGame().getSubjectList() == null || game.getRoomSub().getRoomStatus() != RoomGame.STATUS_PLAYING) {
                    continue;
                }
                for (User user : game.getRoomSub().getUserList()) {
                    if (user.getBoot()) {
                        if((user.getAutoTime() != 0 && new Date().getTime() >= user.getAutoTime())) {
                            user.setAutoTime(0);
                            Subject subject = game.getGame().getSubjectList().get(game.getRoomSub().getAnswerIndex());
                            int option = game.getRobotRandomOption(user, subject);
                            Answer answer = new Answer();
                            answer.setUid(user.getUser().getUserId());
                            answer.setRoomId(game.getRoomSub().getRoomId());
                            answer.setIndex(game.getRoomSub().getAnswerIndex());
                            answer.setAnswer(option);
                            answer.setTimeout(false);
                            answer.setBoot(true);
                            answer.setSeconds(user.getSeconds());
                            this.onAnswer(user, answer);
                        }
                    }
                }
            }
        }
    }

    public void runTimeOut() {
        //synchronized (gameMap) {
        for (RoomGame game : gameMap.values()) {
            if (game.getRoomSub().getCounter() == 0 || game.getGame().getSubjectList() == null || game.getRoomSub().getRoomStatus() != RoomGame.STATUS_PLAYING) {
                continue;
            }
            //答题数目到了或者超时 直接下一题
            if (game.isAnswerByIndexSub(game.getRoomSub().getAnswerIndex()) || new Date().getTime() - game.getRoomSub().getCounter() >= Param.GAME_MATCH_SUBJECTSECOND) {
                Date now = new Date();
                game.getRoomSub().setCounter(now.getTime());
                game.getRoomSub().setAnswerIndex(game.getRoomSub().getAnswerIndex() + 1);
                //结束游戏
                if (game.getRoomSub().getAnswerIndex() - 1 >= (game.getGame().getSubjectList().size() - 1)) {
                    this.endGame(game, 0);
                    continue;
                }
                Map<String, Object> nextMap = new HashMap<>();
                nextMap.put("gameId", game.getGame().getId());
                nextMap.put("index", game.getRoomSub().getAnswerIndex());
                for (User user : game.getRoomSub().getUserList()) {
                    user.setLastReplyTime(new Date().getTime()); //上一次答题的时间
                    if (user.getBoot()) {
                        int second = game.getRobotRandomSecond(user);
                        user.setSeconds(second);
                        user.setAutoTime(now.getTime() + 8000);
                    }
                }

                this.send(game, new BasicResultObject(BasicTagState.TagSuccess, Protocol.TYPE_NEXT, nextMap, "", 0));
            }
        }
        //}
    }

    public void runSendAll(){
        synchronized (userMap) {
            try{
                if(isSendAll){
                    isSendAll = false;
                    List<Room> roomList = new ArrayList<>();
                    for(RoomGame g : gameMap.values()){
                        roomList.add(new Room(g.getRoomSub().getRoomId(),g.getRoomSub().getRoomMaster(),g.getRoomSub().getUserList().get(0).getUser().getNickName(),g.getRoomSub().getUserList().get(0).getUser().getAvatarUrl(),g.getRoomSub().getUserNum(),null,g.getRoomSub().getRoomStatus(),new Date().getTime()));
                    }
                    //List<Object> roomList = roomRedis.getPageList();
                    for(User or : userMap.values()) {
                        if(or.isObserver()){
                            sendMsgToClient.sendMsgToClient(or.getSession(), new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_ROOM_HALL,roomList.size() == 0? "" : roomList,"",roomList.size() == 0 ? 0 : roomList.size()));
                        }
                    }
                }
            }
            catch (Exception ex){
                logger.debug("用户群发异常:{}",ex.getMessage());
            }
        }
    }

    public void runKeepTimeOut() {
        //synchronized (userMap) {
        try {
            for (User user : userMap.values()) {
                if (!user.getBoot() && new Date().getTime() - user.getKeepTime() >= 60 * 1000) {
                    RoomGame game = null;
                    if (user.getRoomId() != null) {
                        game = gameMap.get(user.getRoomId());
                    }
                    logger.info("房间_用户心跳包超时,默认退出游戏 userId:{} ", user.getUser().getUserId());
                    if (game == null) {
                        userMap.remove(user.getUser().getUserId());
                    } else {
                        this.userExit(user.getRoomId(), user.getPlayRoomId(), user.getUser().getUserId());
                    }
                    if (user.getSession().isOpen()) {
                        logger.info("房间_用户心跳包超时,没有关闭 userId:{} ", user.getUser().getUserId());
                        user.getSession().close();
                    }
                }
            }
        } catch (Exception ex) {
            logger.debug("用户心跳异常:{}", ex.getMessage());
        }
        //}
    }

    //用户数据初始化
    public void initUser(User user,String roomId,WebSocketSession session) {
        user.setRoomId(roomId);
        user.setPlayRoomId("");
        user.setStatus(1);//在线
        user.setSession(session);
        user.setKeepTime(new Date().getTime());
        //user.setBoot(false);
        user.setAutoTime(0);
        user.setIndex(0);
        user.setRestSecond(0);
        user.setLastReplyTime(0);
        user.getAbility().setScore(0);
        user.getAbility().setRightNum(0);
        //user.getAbility().setContinueRightNum(0);
        //user.getAbility().setMaxContinueRightNum(0);
        //user.getAbility().setTotalContinueRightScore(0);
    }

    //游戏数据初始化
    public void initGameSub(RoomGame game){
        game.getGame().setStatus(Game.STATUS_NEW);
        game.getRoomSub().setRoomStatus(RoomGame.STATUS_NEW);
        //roomRedis.updateRoomStatus(game.getRoomSub().getRoomId(),RoomGame.STATUS_NEW);
        game.getRoomSub().setRoomList(null);
        game.getRoomSub().setRankList(null);
        game.getRoomSub().setLeftRankList(null);
        game.getRoomSub().setRightRankList(null);
        game.getGame().setId(0);
        game.getRoomSub().setAnswerIndex(0);
        game.getRoomSub().setRestSecond(0);
        game.getRoomSub().setCounter(0);
        game.getGame().setSubjectList(null);
        game.getRoomSub().setGroupList(new ArrayList<>());
        game.setLeftGroupList(null);
        game.setRightGroupList(null);
        game.setExitUserList(null);
    }

    public RoomGame getGame(String id) {
        return gameMap.get(id);
    }

    public RoomGame addGame(String roomId,User user) {
        synchronized (gameMap) {
            RoomGame game = new RoomGame(roomId,user.getUser().getUserId(),1,new CopyOnWriteArrayList<>(),RoomGame.STATUS_NEW,0,new Game());
            game.getGame().setStatus(Game.STATUS_NEW);
            gameMap.put(game.getRoomSub().getRoomId(), game);
            //roomRedis.add(new Room(roomId,user.getUser().getUserId(),user.getUser().getNickName(),user.getUser().getAvatarUrl(),1,null,RoomGame.STATUS_NEW,new Date().getTime()));
            return game;
        }
    }

    public RoomGame updateGame(String roomId,User user) {
        synchronized (gameMap) {
            RoomGame game = this.getGame(roomId);
            if(game != null){
                game.addUserToRoom(user);
                //roomRedis.addUserToRoom(roomId);
            }
            return game;
        }
    }

    //踢玩家
    public RoomGame kickUser(String roomId,int kickUserId){
        synchronized (gameMap){
            RoomGame game = this.getGame(roomId);
            if(game != null){
                //this.removeUser(kickUserId);
                game.kickUser(kickUserId);
                //roomRedis.kickUser(roomId);
                if(game.getRoomSub().getUserNum() <= 0){//没有玩家的话，游戏都清空
                    this.removeGame(roomId);
                    return null;
                }
            }
            return game;
        }
    }

    public void removeGame(String id) {
        synchronized (gameMap) {
            //roomRedis.remove(id);
            gameMap.remove(id);
        }
    }

    public void addUser(User user){
        synchronized (userMap) {
            userMap.put(user.getUser().getUserId(),user);
        }
    }

    public User getUser(int uid) {
        return userMap.get(uid);
    }

    public void removeUser(int id) {
        userMap.remove(id);
    }

    public void addRobot(int id,User user){
        synchronized (robotMap) {
            robotMap.put(id, user);
        }
    }

    //普通发送某人
    public void sendToObserver(User user){
        List<Room> roomList = new ArrayList<>();
        for(RoomGame g : gameMap.values()){
            roomList.add(new Room(g.getRoomSub().getRoomId(),g.getRoomSub().getRoomMaster(),g.getRoomSub().getUserList().get(0).getUser().getNickName(),g.getRoomSub().getUserList().get(0).getUser().getAvatarUrl(),g.getRoomSub().getUserNum(),null,g.getRoomSub().getRoomStatus(),new Date().getTime()));
        }
        //List<Object> roomList = roomRedis.getPageList();
        sendMsgToClient.sendMsgToClient(user.getSession(), new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_ROOM_HALL,roomList.size() == 0? "" : roomList,"",roomList.size() == 0 ? 0 : roomList.size()));
    }

    //普通发送
    public void sendAllObserver(){
        List<Room> roomList = new ArrayList<>();
        for(RoomGame g : gameMap.values()){
            roomList.add(new Room(g.getRoomSub().getRoomId(),g.getRoomSub().getRoomMaster(),g.getRoomSub().getUserList().get(0).getUser().getNickName(),g.getRoomSub().getUserList().get(0).getUser().getAvatarUrl(),g.getRoomSub().getUserNum(),null,g.getRoomSub().getRoomStatus(),new Date().getTime()));
        }
        //List<Object> roomList = roomRedis.getPageList();
        for(User or : userMap.values()) {
            if(or.isObserver()){
                sendMsgToClient.sendMsgToClient(or.getSession(), new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_ROOM_HALL,roomList.size() == 0? "" : roomList,"",roomList.size() == 0 ? 0 : roomList.size()));
            }
        }
    }

    public void noWriteLogSend(RoomGame game, BasicResultObject result) {
        if(game == null){return;}
        for (int i = 0; i < game.getRoomSub().getUserList().size(); i++) {
            if(!game.getRoomSub().getUserList().get(i).getBoot() && game.getRoomSub().getUserList().get(i).getUser() != null && game.getRoomSub().getUserList().get(i).getStatus() != 0){
                sendMsgToClient.sendMsgToClient(game.getRoomSub().getUserList().get(i).getSession(), result);
            }
        }
    }

    public void send(RoomGame game, BasicResultObject result) {
        if(game == null){return;}
        for (int i = 0; i < game.getRoomSub().getUserList().size(); i++) {
            //game.getRoomSub().getUserList().get(i).getPlayRoomId() != null && game.getRoomSub().getUserList().get(i).getPlayRoomId().length() > 0
            /*if(game.getRoomSub().getRoomStatus() == RoomGame.STATUS_PLAYING && game.getRoomSub().getRoomId() != game.getRoomSub().getUserList().get(i).getPlayRoomId()){//避免用户逃跑会重新下发
                System.out.println("game不一样！！");
                continue;
            }*/
            if(!game.getRoomSub().getUserList().get(i).getBoot() && game.getRoomSub().getUserList().get(i).getUser() != null && game.getRoomSub().getUserList().get(i).getStatus() != 0){
                this.sendToUser(game.getRoomSub().getUserList().get(i), result);
            }
        }
    }

    public void sendToUser(User user, BasicResultObject result) {
        if(user.getBoot()){return;}
        String json = JSONObject.toJSONString(result);
        if (user.getSession() == null) {
            logger.debug("房间_给用户发送消息失败，用户未连接，user:{} message:{}", user, json);
            return;
        }
        try {
            sendMsgToClient.sendMsgToClient(user.getSession(), result);
            logger.info("房间_发送消息,sessionId:{} user:{} message:{}",user.getSession().getId(), user.getUser().getUserId(), json);

        } catch (Throwable t) {
            logger.debug("房间_给用户发送消息失败，user:{} message:{} t:{}", user, json, t.getMessage());
        }
    }

}
