package com.example.vientianetest.config;

import com.example.vientianetest.dao.*;
import com.example.vientianetest.dao.subject.SubjectWeightDao;
import com.example.vientianetest.dao.user.*;
import com.example.vientianetest.dao.robot.RobotDao;
import com.example.vientianetest.domain.User;
import com.example.vientianetest.redis.*;
import com.example.vientianetest.redis.subject.SubjectRedis;
import com.example.vientianetest.service.WebSocketRankService;
import com.example.vientianetest.service.WebSocketRoomService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

/**
 * 服务启动初始化：在 Web 上下文加载后执行。
 *
 * 核心工作：
 *   1. 从 wxdk_robot 表加载机器人到内存池（排位 + 房间）
 *   2. 初始化各类数据字典（段位、签到、抽奖、权重等）
 *   3. 初始化 Redis 排行榜（初次部署时放开注释，运行一次后注释掉）
 */
@Service
public class InitDataListener implements InitializingBean, ServletContextAware {
    @Autowired
    private RobotDao robotDao;
    @Autowired
    private UpLevelDao upLevelDao;
    @Autowired
    private DataConfigDao dataConfigDao;
    @Autowired
    private UserCombatDao userCombatDao;
    @Autowired
    private UserPrizeDao userPrizeDao;
    @Autowired
    private ParagraphDao paragraphDao;
    @Autowired
    private UserFriendDao userFriendDao;
    @Autowired
    private UserDaySignDao userDaySignDao;
    @Autowired
    private UserParagraphDao userParagraphDao;
    @Autowired
    private DataConfiguration dataConfiguration;
    @Autowired
    private SubjectWeightDao subjectWeightDao;
    @Autowired
    private SubjectRedis subjectRedis;
    @Autowired
    private UserFriendRedis userFriendRedis;
    @Autowired
    private UserLuckNumberDao userLuckNumberDao;
    @Autowired
    private UserRoomWinRankRedis userRoomWinRankRedis;
    @Autowired
    private UserGameStatisRoomDao userGameStatisRoomDao;
    @Autowired
    private WebSocketRankService webSocketRankService;
    @Autowired
    private WebSocketRoomService webSocketRoomService;
    @Autowired
    private UserGameStatisRankDao userGameStatisRankDao;
    @Autowired
    private UserCombatRankRedis userCombatRankRedis;
    @Autowired
    private UserParagraphRankRedis userParagraphRankRedis;
    @Autowired
    private UserMaxSingleScoreRankRedis userMaxSingleScoreRankRedis;
    @Autowired
    private UserContinueWinRankRedis userContinueWinRankRedis;


    public InitDataListener() {
    }

    @Override
    public void afterPropertiesSet() {

    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.getRandRobotList();
        this.initDataConfig();
        subjectRedis.initSubject();//Redis重启后题库缓存丢失，启动时必须初始化
        //程序运行一次后，要注释掉
        /*this.initUserCombat();
        this.initUserMMR();
        this.initUserContinueWinNum();
        this.initMaxSingleScore();
        this.initFriend();
        this.initGroupWinNum();*/
    }

    /**
     * 从数据库加载机器人到内存池（robotMap），供匹配时使用。
     *
     * - 排位机器人：wxdk_robot WHERE type IN (0,1) AND status=1 AND flag<3
     * - 房间机器人：wxdk_robot WHERE type IN (0,2) AND status=1 AND flag<3
     *
     * 如果 [INIT] 排位机器人加载数量: 0，说明库表无数据，匹配将只能等真人。
     */
    private void getRandRobotList() {
        List<User> userList = robotDao.getRandRobotList();//获取排位机器人
        System.out.println("[INIT] 排位机器人加载数量: " + userList.size());
        for (User user : userList) {
            webSocketRankService.addRobot(user.getUser().getUserId(), user); //初始化机器人
            System.out.println("[INIT] 排位机器人: " + user.getUser().getUserId() + " " + user.getUser().getNickName());
        }
        List<User> hallUserList = robotDao.getHallRobotList();//获取混战机器人
        System.out.println("[INIT] 房间机器人加载数量: " + hallUserList.size());
        for (User user : hallUserList) {
            webSocketRoomService.addRobot(user.getUser().getUserId(), user); //初始化机器人
        }
    }

    //数据字典初始化
    private void initDataConfig() {
        dataConfiguration.initData(dataConfigDao.getDataConfig());//数据字典
        dataConfiguration.initGradeData(upLevelDao.getLevelByName("grade"));
        dataConfiguration.initPrizeRatioData(userPrizeDao.getPrize());
        dataConfiguration.initDaySignData(userDaySignDao.getDaySignList());
        dataConfiguration.initParagraphData(paragraphDao.getParagraph());
        dataConfiguration.initSubjectWeightData(subjectWeightDao.getSubjectWeight());
        //dataConfiguration.initLuckNumberData(userLuckNumberDao.getLuckNumberList());
    }

    //初始化MMR值
    private void initUserMMR(){
        List<Map<String,Object>> mapList = userParagraphDao.getRankList(1000);
        for(Map<String,Object> map : mapList){
            userParagraphRankRedis.addUserRankRecord(Integer.parseInt(map.get("userId").toString()),Double.valueOf(map.get("mmr").toString()));
        }

    }

    //初始化连胜
    private void initUserContinueWinNum(){
        List<Map<String,Object>> mapList = userGameStatisRankDao.getMaxContinueNumRankList(1000);
        for(Map<String,Object> map : mapList){
            userContinueWinRankRedis.addUserRankRecord(Integer.parseInt(map.get("userId").toString()),Double.valueOf(map.get("maxContinueWinNum").toString()));
        }
    }

    //初始化单场最高分
    private void initMaxSingleScore(){
        List<Map<String,Object>> mapList = userGameStatisRankDao.getMaxSingleScoreRankList(1000);
        for(Map<String,Object> map : mapList) {
            userMaxSingleScoreRankRedis.addUserRankRecord(Integer.parseInt(map.get("userId").toString()),Double.valueOf(map.get("maxSingleScore").toString()));
        }
    }

    //第一次初始化战斗力
    private void initUserCombatRankList(){
        List<Map<String,Object>> mapList = userCombatDao.initUserCombatRankList(1000);
        for(Map<String,Object> map : mapList) {
            userCombatRankRedis.addUserRankRecord(Integer.parseInt(map.get("userId").toString()),Double.valueOf(map.get("combat").toString()));
        }
    }

    //之后初始化战斗力
    private void initUserCombat(){
        List<Map<String,Object>> mapList = userCombatDao.getUserCombatRankList(1000);
        for(Map<String,Object> map : mapList) {
            userCombatRankRedis.addUserRankRecord(Integer.parseInt(map.get("userId").toString()),Double.valueOf(map.get("combat").toString()));
        }
    }

    //获取全部人的好友列表初始化redis
    private void initFriend() {
        List<Map<String,Object>> mapList = userFriendDao.getFriendList();
        for(Map<String,Object> map : mapList) {
            userFriendRedis.init(Integer.parseInt(map.get("userId").toString()),map.get("friendList").toString());
        }
    }

    //初始化群对战胜场
    private void initGroupWinNum() {
        List<Map<String,Object>> mapList = userGameStatisRoomDao.initRedisWinNum(1000);
        for(Map<String,Object> map : mapList) {
            userRoomWinRankRedis.addUserRankRecord(Integer.parseInt(map.get("userId").toString()),Double.valueOf(map.get("winNum").toString()));
        }
    }
}
