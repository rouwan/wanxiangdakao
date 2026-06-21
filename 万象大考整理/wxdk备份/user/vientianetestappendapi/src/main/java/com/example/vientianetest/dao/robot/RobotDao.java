package com.example.vientianetest.dao.robot;

import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.domain.Ability;
import com.example.vientianetest.domain.RobotSubjectRatio;
import com.example.vientianetest.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 机器人（Bot）数据访问层。
 *
 * 机器人从 wxdk_robot 表加载，表中每条记录代表一个虚拟对手，
 * 包含答题正确率、答题耗时区间、段位/星星、胜率等模拟参数。
 *
 * 关键查询条件：
 *   - status = 1   → 启用中的机器人
 *   - flag < 3      → 未被标记为异常
 *   - 取负的 id 作为 userId → 负值区分真实用户
 *
 * 加载时机：InitDataListener 在服务启动时调用 getRandRobotList() / getHallRobotList()
 */
@Component
public class RobotDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private RobotParagraphDao robotParagraphDao;
    @Autowired
    private RobotGameStatisRankDao robotGameStatisRankDao;

    public User getRandRobot(int type){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("type",type);
        Map<String,Object> map = jdbcOperations.queryForMap("SELECT -id as id,nickname,sexDesc,city,province,country,headImgUrl,`type`,`simpleRatio`,`commonRatio`,`hardRatio`,`simpleTime`,`commonTime`,`hardTime` FROM wxdk_robot WHERE flag<3 and STATUS =1 /*and type = :type*/ ORDER BY RAND() LIMIT 1 ",paramMap);
        User user = new User();
        BasicUserInfo basicUserInfo = new BasicUserInfo();
        basicUserInfo.setUserId(Integer.parseInt(map.get("id").toString()));
        basicUserInfo.setNickName(map.get("nickname").toString());
        basicUserInfo.setGender(map.get("sexDesc").toString());
        basicUserInfo.setAvatarUrl(map.get("headImgUrl").toString());
        user.setAbility(new Ability());
        user.getAbility().setContinueWinNum(0);
        user.getAbility().setContinueLoseNum(0);
        user.setUser(basicUserInfo);
        user.getAbility().setType(Integer.parseInt(map.get("type").toString()));
        user.setJoinTime(new Date().getTime());
        user.setBoot(true);
        user.setStatus(0);//机器人默认离线效果
        user.setRobotSubjectRatio(new RobotSubjectRatio());
        user.getRobotSubjectRatio().setSimpleRatio(Float.parseFloat(map.get("simpleRatio").toString()));
        user.getRobotSubjectRatio().setCommonRatio(Float.parseFloat(map.get("commonRatio").toString()));
        user.getRobotSubjectRatio().setHardRatio(Float.parseFloat(map.get("hardRatio").toString()));
        user.getRobotSubjectRatio().setSimpleMinTime(Integer.parseInt(map.get("simpleTime").toString().split("-")[0]));
        user.getRobotSubjectRatio().setSimpleMaxTime(Integer.parseInt(map.get("simpleTime").toString().split("-")[1]));
        user.getRobotSubjectRatio().setCommonMinTime(Integer.parseInt(map.get("commonTime").toString().split("-")[0]));
        user.getRobotSubjectRatio().setCommonMaxTime(Integer.parseInt(map.get("commonTime").toString().split("-")[1]));
        user.getRobotSubjectRatio().setHardMinTime(Integer.parseInt(map.get("hardTime").toString().split("-")[0]));
        user.getRobotSubjectRatio().setHardMaxTime(Integer.parseInt(map.get("hardTime").toString().split("-")[1]));
        return user;
    }

    //排位
    /**
     * 获取排位赛机器人列表（服务启动时一次性加载到内存）。
     *
     * SQL 条件：wxdk_robot WHERE type IN (0,1) AND flag<3 AND status = 1
     *
     * 关联表获取段位、统计信息：
     *   - wxdk_robotparagraph  → paragraph / star / mmr
     *   - wxdk_robotgamestatisrank → totalNum / winNum / winRatio / 连胜连败
     *   - wxdk_paragraph       → 段位名称/图片
     */
    public List<User> getRandRobotList(){
        Map<String,Object> paramMap = new HashMap<>();
        List<Map<String,Object>> mapList = jdbcOperations.queryForList("SELECT -a.id AS id,a.nickname,a.sexDesc,a.city,a.province,a.country,a.headImgUrl,a.`type`,a.`simpleRatio`,a.`commonRatio`,a.`hardRatio`,a.`simpleTime`,a.`commonTime`,a.`hardTime`,IFNULL(b.`paragraph`,1) AS paragraph,IFNULL(e.`name`,'') AS paragraphName ,IFNULL(e.`image`,'') AS paragraphImage,IFNULL(b.`star`,0) AS star ,IFNULL(b.mmr,0) AS mmr,IFNULL(c.totalNum,0) AS totalNum,IFNULL(c.winNum,0) AS winNum,IFNULL(CONVERT(c.winNum/c.totalNum,DECIMAL(10,2))*100,0) AS winRatio,IFNULL(continueWinNum,0) AS continueWinNum ,IFNULL(continueLoseNum,0) AS continueLoseNum FROM wxdk_robot a LEFT JOIN `wxdk_robotparagraph` b ON a.id = -b.`userId` LEFT JOIN `wxdk_robotgamestatisrank` c ON a.id = -c.`userId` LEFT JOIN `wxdk_paragraph` e ON IFNULL(b.`paragraph`,1) = e.`level` WHERE a.`type` IN (0,1) AND a.flag<3 AND a.`status` = 1 ORDER BY IFNULL(b.`paragraph`,1)",paramMap);
        List<User> userList = new ArrayList<>();
        for (Map<String,Object> map : mapList) {
            User user = new User();
            BasicUserInfo basicUserInfo = new BasicUserInfo();
            basicUserInfo.setUserId(Integer.parseInt(map.get("id").toString()));
            basicUserInfo.setNickName(map.get("nickname").toString());
            basicUserInfo.setGender(map.get("sexDesc").toString());
            basicUserInfo.setAvatarUrl(map.get("headImgUrl").toString());
            basicUserInfo.setCountry(map.get("country").toString());
            basicUserInfo.setProvince(map.get("province").toString());
            basicUserInfo.setCity(map.get("city").toString());
            user.setAbility(new Ability());
            user.getAbility().setContinueWinNum(0);
            user.getAbility().setContinueLoseNum(0);
            user.getAbility().setParagraphName(map.get("paragraphName").toString());
            user.getAbility().setParagraphImage(map.get("paragraphImage").toString());
            user.getAbility().setParagraph(Integer.parseInt(map.get("paragraph").toString()));
            user.getAbility().setStar(Integer.parseInt(map.get("star").toString()));
            user.getAbility().setMmr(Double.valueOf(map.get("mmr").toString()));
            user.getAbility().setTotalNum(Integer.parseInt(map.get("totalNum").toString()));
            user.getAbility().setWinNum(Integer.parseInt(map.get("winNum").toString()));
            user.getAbility().setContinueWinNum(Integer.parseInt(map.get("continueWinNum").toString()));
            user.getAbility().setContinueLoseNum(Integer.parseInt(map.get("continueLoseNum").toString()));
            user.getAbility().setWinRatio(Double.valueOf(map.get("winRatio").toString()));
            user.setUser(basicUserInfo);
            user.getAbility().setType(Integer.parseInt(map.get("type").toString()));
            user.setJoinTime(new Date().getTime());
            user.setBoot(true);
            user.setStatus(0);//机器人默认离线效果
            user.setRobotSubjectRatio(new RobotSubjectRatio());
            user.getRobotSubjectRatio().setSimpleRatio(Float.parseFloat(map.get("simpleRatio").toString()));
            user.getRobotSubjectRatio().setCommonRatio(Float.parseFloat(map.get("commonRatio").toString()));
            user.getRobotSubjectRatio().setHardRatio(Float.parseFloat(map.get("hardRatio").toString()));
            user.getRobotSubjectRatio().setSimpleMinTime(Integer.parseInt(map.get("simpleTime").toString().split("-")[0]));
            user.getRobotSubjectRatio().setSimpleMaxTime(Integer.parseInt(map.get("simpleTime").toString().split("-")[1]));
            user.getRobotSubjectRatio().setCommonMinTime(Integer.parseInt(map.get("commonTime").toString().split("-")[0]));
            user.getRobotSubjectRatio().setCommonMaxTime(Integer.parseInt(map.get("commonTime").toString().split("-")[1]));
            user.getRobotSubjectRatio().setHardMinTime(Integer.parseInt(map.get("hardTime").toString().split("-")[0]));
            user.getRobotSubjectRatio().setHardMaxTime(Integer.parseInt(map.get("hardTime").toString().split("-")[1]));
            userList.add(user);
        }
        return userList;
    }

    //混战
    /**
     * 获取房间/混战机器人列表。
     *
     * SQL 条件：wxdk_robot WHERE type IN (0,2) AND flag<3 AND status = 1
     *
     * 与排位机器人的区别：type 条件不同（2 代替 1），用于群对战时匹配。
     */
    public List<User> getHallRobotList(){
        Map<String,Object> paramMap = new HashMap<>();
        List<Map<String,Object>> mapList = jdbcOperations.queryForList("SELECT -a.id AS id,a.nickname,a.sexDesc,a.city,a.province,a.country,a.headImgUrl,a.`type`,a.`simpleRatio`,a.`commonRatio`,a.`hardRatio`,a.`simpleTime`,a.`commonTime`,a.`hardTime`,IFNULL(b.`paragraph`,1) AS paragraph,IFNULL(e.`name`,'') AS paragraphName ,IFNULL(e.`image`,'') AS paragraphImage,IFNULL(b.`star`,0) AS star ,IFNULL(b.mmr,0) AS mmr,IFNULL(c.totalNum,0) AS totalNum,IFNULL(c.winNum,0) AS winNum,IFNULL(CONVERT(c.winNum/c.totalNum,DECIMAL(10,2))*100,0) AS winRatio,IFNULL(continueWinNum,0) AS continueWinNum ,IFNULL(continueLoseNum,0) AS continueLoseNum FROM wxdk_robot a LEFT JOIN `wxdk_robotparagraph` b ON a.id = -b.`userId` LEFT JOIN `wxdk_robotgamestatisrank` c ON a.id = -c.`userId` LEFT JOIN `wxdk_paragraph` e ON IFNULL(b.`paragraph`,1) = e.`level` WHERE a.`type` IN (0,2) AND a.flag<3 AND a.`status` = 1 ",paramMap);//AND b.`paragraph` >= :paragraph
        List<User> userList = new ArrayList<>();
        for (Map<String,Object> map : mapList) {
            User user = new User();
            BasicUserInfo basicUserInfo = new BasicUserInfo();
            basicUserInfo.setUserId(Integer.parseInt(map.get("id").toString()));
            basicUserInfo.setNickName(map.get("nickname").toString());
            basicUserInfo.setGender(map.get("sexDesc").toString());
            basicUserInfo.setAvatarUrl(map.get("headImgUrl").toString());
            basicUserInfo.setCountry(map.get("country").toString());
            basicUserInfo.setProvince(map.get("province").toString());
            basicUserInfo.setCity(map.get("city").toString());
            user.setAbility(new Ability());
            user.getAbility().setContinueWinNum(0);
            user.getAbility().setContinueLoseNum(0);
            user.getAbility().setParagraphName(map.get("paragraphName").toString());
            user.getAbility().setParagraphImage(map.get("paragraphImage").toString());
            user.getAbility().setParagraph(Integer.parseInt(map.get("paragraph").toString()));
            user.getAbility().setStar(Integer.parseInt(map.get("star").toString()));
            user.getAbility().setMmr(Double.valueOf(map.get("mmr").toString()));
            user.getAbility().setTotalNum(Integer.parseInt(map.get("totalNum").toString()));
            user.getAbility().setWinNum(Integer.parseInt(map.get("winNum").toString()));
            user.getAbility().setContinueWinNum(Integer.parseInt(map.get("continueWinNum").toString()));
            user.getAbility().setContinueLoseNum(Integer.parseInt(map.get("continueLoseNum").toString()));
            user.getAbility().setWinRatio(Double.valueOf(map.get("winRatio").toString()));
            user.setUser(basicUserInfo);
            user.getAbility().setType(Integer.parseInt(map.get("type").toString()));
            user.setJoinTime(new Date().getTime());
            user.setBoot(true);
            user.setStatus(0);//机器人默认离线效果
            user.setRobotSubjectRatio(new RobotSubjectRatio());
            user.getRobotSubjectRatio().setSimpleRatio(Float.parseFloat(map.get("simpleRatio").toString()));
            user.getRobotSubjectRatio().setCommonRatio(Float.parseFloat(map.get("commonRatio").toString()));
            user.getRobotSubjectRatio().setHardRatio(Float.parseFloat(map.get("hardRatio").toString()));
            user.getRobotSubjectRatio().setSimpleMinTime(Integer.parseInt(map.get("simpleTime").toString().split("-")[0]));
            user.getRobotSubjectRatio().setSimpleMaxTime(Integer.parseInt(map.get("simpleTime").toString().split("-")[1]));
            user.getRobotSubjectRatio().setCommonMinTime(Integer.parseInt(map.get("commonTime").toString().split("-")[0]));
            user.getRobotSubjectRatio().setCommonMaxTime(Integer.parseInt(map.get("commonTime").toString().split("-")[1]));
            user.getRobotSubjectRatio().setHardMinTime(Integer.parseInt(map.get("hardTime").toString().split("-")[0]));
            user.getRobotSubjectRatio().setHardMaxTime(Integer.parseInt(map.get("hardTime").toString().split("-")[1]));
            userList.add(user);
        }
        return userList;
    }

    public User getRandRobot2(int type){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("type",type);
        Map<String,Object> map = jdbcOperations.queryForMap("SELECT -id as id,nickname,sexDesc,city,province,country,headImgUrl,`type`,`simpleRatio`,`commonRatio`,`hardRatio`,`simpleTime`,`commonTime`,`hardTime` FROM wxdk_robot WHERE flag<3 and STATUS =1 /* and type = :type */ ORDER BY RAND() LIMIT 1 ",paramMap);
        User user = new User();
        BasicUserInfo basicUserInfo = new BasicUserInfo();
        basicUserInfo.setUserId(Integer.parseInt(map.get("id").toString()));
        basicUserInfo.setNickName(map.get("nickname").toString());
        basicUserInfo.setGender(map.get("sexDesc").toString());
        basicUserInfo.setAvatarUrl(map.get("headImgUrl").toString());
        user.setAbility(new Ability());
        user.getAbility().setContinueWinNum(0);
        user.getAbility().setContinueLoseNum(0);
        user.setUser(basicUserInfo);
        user.getAbility().setType(Integer.parseInt(map.get("type").toString()));
        user.setJoinTime(new Date().getTime());
        user.setBoot(true);
        user.setStatus(0);//机器人默认离线效果
        user.setRobotSubjectRatio(new RobotSubjectRatio());
        user.getRobotSubjectRatio().setSimpleRatio(Float.parseFloat(map.get("simpleRatio").toString()));
        user.getRobotSubjectRatio().setCommonRatio(Float.parseFloat(map.get("commonRatio").toString()));
        user.getRobotSubjectRatio().setHardRatio(Float.parseFloat(map.get("hardRatio").toString()));
        user.getRobotSubjectRatio().setSimpleMinTime(Integer.parseInt(map.get("simpleTime").toString().split("-")[0]));
        user.getRobotSubjectRatio().setSimpleMaxTime(Integer.parseInt(map.get("simpleTime").toString().split("-")[1]));
        user.getRobotSubjectRatio().setCommonMinTime(Integer.parseInt(map.get("commonTime").toString().split("-")[0]));
        user.getRobotSubjectRatio().setCommonMaxTime(Integer.parseInt(map.get("commonTime").toString().split("-")[1]));
        user.getRobotSubjectRatio().setHardMinTime(Integer.parseInt(map.get("hardTime").toString().split("-")[0]));
        user.getRobotSubjectRatio().setHardMaxTime(Integer.parseInt(map.get("hardTime").toString().split("-")[1]));
        user.setIndex(0);
        Map<String,Object> userParagraphMap = new HashMap<>();
        if(robotParagraphDao.isExistsByUserId(user.getUser().getUserId())){
            userParagraphMap = robotParagraphDao.getInfoByUserId(user.getUser().getUserId());
        }else{
            Map<String,Object> paragraphMap = robotParagraphDao.getParagraphInfoByLevel(1);
            userParagraphMap.put("paragraph",1);
            userParagraphMap.put("star",0);
            userParagraphMap.put("mmr",0);
            userParagraphMap.put("name",paragraphMap.get("name"));
            userParagraphMap.put("image",paragraphMap.get("image"));
        }
        user.getAbility().setParagraph(Integer.parseInt(userParagraphMap.get("paragraph").toString()));
        user.getAbility().setStar(Integer.parseInt(userParagraphMap.get("star").toString()));
        user.getAbility().setMmr(Double.valueOf(userParagraphMap.get("mmr").toString()));
        user.getAbility().setParagraphName(userParagraphMap.get("name").toString());
        user.getAbility().setParagraphImage(userParagraphMap.get("image").toString());
        robotGameStatisRankDao.getWinNumByUserId(user);
        return user;
    }

    //获取用户第一次
    public List<User> getFirstRandRobotList(){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT -a.id AS id,a.nickname,a.sexDesc,a.city,a.province,a.country,a.headImgUrl,a.`type`,a.`simpleRatio`,a.`commonRatio`,a.`hardRatio`,a.`simpleTime`,a.`commonTime`,a.`hardTime` ");
        sql.append("FROM `wxdk_robot` a ");
        sql.append("LEFT JOIN `wxdk_robotdayrecord` b ON a.id = -b.userId ");
        sql.append("WHERE a.flag < 3 AND STATUS = 1 AND ((IFNULL(b.firstCounter,0)<a.`firstDayNum`) OR IFNULL(b.id,0) = 0)");
        sql.append("ORDER BY RAND() LIMIT 2");
        List<Map<String,Object>> mapList = jdbcOperations.queryForList(sql.toString(),new HashMap<>());
        List<User> userList = new ArrayList<>();
        for (Map<String,Object> map : mapList) {
            User user = new User();
            BasicUserInfo basicUserInfo = new BasicUserInfo();
            basicUserInfo.setUserId(Integer.parseInt(map.get("id").toString()));
            basicUserInfo.setNickName(map.get("nickname").toString());
            basicUserInfo.setGender(map.get("sexDesc").toString());
            basicUserInfo.setAvatarUrl(map.get("headImgUrl").toString());
            user.setAbility(new Ability());
            user.getAbility().setContinueWinNum(0);
            user.getAbility().setContinueLoseNum(0);
            user.setUser(basicUserInfo);
            user.getAbility().setType(Integer.parseInt(map.get("type").toString()));
            user.setJoinTime(new Date().getTime());
            user.setBoot(true);
            user.setStatus(0);//机器人默认离线效果
            user.setRobotSubjectRatio(new RobotSubjectRatio());
            user.getRobotSubjectRatio().setSimpleRatio(Float.parseFloat(map.get("simpleRatio").toString()));
            user.getRobotSubjectRatio().setCommonRatio(Float.parseFloat(map.get("commonRatio").toString()));
            user.getRobotSubjectRatio().setHardRatio(Float.parseFloat(map.get("hardRatio").toString()));
            user.getRobotSubjectRatio().setSimpleMinTime(Integer.parseInt(map.get("simpleTime").toString().split("-")[0]));
            user.getRobotSubjectRatio().setSimpleMaxTime(Integer.parseInt(map.get("simpleTime").toString().split("-")[1]));
            user.getRobotSubjectRatio().setCommonMinTime(Integer.parseInt(map.get("commonTime").toString().split("-")[0]));
            user.getRobotSubjectRatio().setCommonMaxTime(Integer.parseInt(map.get("commonTime").toString().split("-")[1]));
            user.getRobotSubjectRatio().setHardMinTime(Integer.parseInt(map.get("hardTime").toString().split("-")[0]));
            user.getRobotSubjectRatio().setHardMaxTime(Integer.parseInt(map.get("hardTime").toString().split("-")[1]));
            user.setIndex(0);
            Map<String,Object> userParagraphMap = new HashMap<>();
            if(robotParagraphDao.isExistsByUserId(user.getUser().getUserId())){
                userParagraphMap = robotParagraphDao.getInfoByUserId(user.getUser().getUserId());
            }else{
                Map<String,Object> paragraphMap = robotParagraphDao.getParagraphInfoByLevel(1);
                userParagraphMap.put("paragraph",1);
                userParagraphMap.put("star",0);
                userParagraphMap.put("mmr",0);
                userParagraphMap.put("name",paragraphMap.get("name"));
                userParagraphMap.put("image",paragraphMap.get("image"));
            }
            user.getAbility().setParagraph(Integer.parseInt(userParagraphMap.get("paragraph").toString()));
            user.getAbility().setStar(Integer.parseInt(userParagraphMap.get("star").toString()));
            user.getAbility().setMmr(Double.valueOf(userParagraphMap.get("mmr").toString()));
            user.getAbility().setParagraphName(userParagraphMap.get("name").toString());
            user.getAbility().setParagraphImage(userParagraphMap.get("image").toString());
            robotGameStatisRankDao.getWinNumByUserId(user);
            userList.add(user);
        }
        return userList;

    }

    public List<User> getAfterRandRobotList(){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT -a.id AS id,a.nickname,a.sexDesc,a.city,a.province,a.country,a.headImgUrl,a.`type`,a.`simpleRatio`,a.`commonRatio`,a.`hardRatio`,a.`simpleTime`,a.`commonTime`,a.`hardTime` ");
        sql.append("FROM `wxdk_robot` a ");
        sql.append("LEFT JOIN `wxdk_robotdayrecord` b ON a.id = -b.userId ");
        sql.append("WHERE a.flag < 3 AND STATUS = 1 AND ((IFNULL(b.afterCounter,0)<a.`dayNum`) OR TIMESTAMPDIFF(DAY,DATE_FORMAT(b.`modifyTime`,'%Y-%m-%d'),NOW()) >= 1) AND IFNULL(a.`firstDayNum` ,0) <= IFNULL(b.`firstCounter`,0) ");
        sql.append("ORDER BY RAND() LIMIT 2");
        List<Map<String,Object>> mapList = jdbcOperations.queryForList(sql.toString(),new HashMap<>());
        List<User> userList = new ArrayList<>();
        for (Map<String,Object> map : mapList) {
            User user = new User();
            BasicUserInfo basicUserInfo = new BasicUserInfo();
            basicUserInfo.setUserId(Integer.parseInt(map.get("id").toString()));
            basicUserInfo.setNickName(map.get("nickname").toString());
            basicUserInfo.setGender(map.get("sexDesc").toString());
            basicUserInfo.setAvatarUrl(map.get("headImgUrl").toString());
            user.setAbility(new Ability());
            user.getAbility().setContinueWinNum(0);
            user.getAbility().setContinueLoseNum(0);
            user.setUser(basicUserInfo);
            user.getAbility().setType(Integer.parseInt(map.get("type").toString()));
            user.setJoinTime(new Date().getTime());
            user.setBoot(true);
            user.setStatus(0);//机器人默认离线效果
            user.setRobotSubjectRatio(new RobotSubjectRatio());
            user.getRobotSubjectRatio().setSimpleRatio(Float.parseFloat(map.get("simpleRatio").toString()));
            user.getRobotSubjectRatio().setCommonRatio(Float.parseFloat(map.get("commonRatio").toString()));
            user.getRobotSubjectRatio().setHardRatio(Float.parseFloat(map.get("hardRatio").toString()));
            user.getRobotSubjectRatio().setSimpleMinTime(Integer.parseInt(map.get("simpleTime").toString().split("-")[0]));
            user.getRobotSubjectRatio().setSimpleMaxTime(Integer.parseInt(map.get("simpleTime").toString().split("-")[1]));
            user.getRobotSubjectRatio().setCommonMinTime(Integer.parseInt(map.get("commonTime").toString().split("-")[0]));
            user.getRobotSubjectRatio().setCommonMaxTime(Integer.parseInt(map.get("commonTime").toString().split("-")[1]));
            user.getRobotSubjectRatio().setHardMinTime(Integer.parseInt(map.get("hardTime").toString().split("-")[0]));
            user.getRobotSubjectRatio().setHardMaxTime(Integer.parseInt(map.get("hardTime").toString().split("-")[1]));
            user.setIndex(0);
            Map<String,Object> userParagraphMap = new HashMap<>();
            if(robotParagraphDao.isExistsByUserId(user.getUser().getUserId())){
                userParagraphMap = robotParagraphDao.getInfoByUserId(user.getUser().getUserId());
            }else{
                Map<String,Object> paragraphMap = robotParagraphDao.getParagraphInfoByLevel(1);
                userParagraphMap.put("paragraph",1);
                userParagraphMap.put("star",0);
                userParagraphMap.put("mmr",0);
                userParagraphMap.put("name",paragraphMap.get("name"));
                userParagraphMap.put("image",paragraphMap.get("image"));
            }
            user.getAbility().setParagraph(Integer.parseInt(userParagraphMap.get("paragraph").toString()));
            user.getAbility().setStar(Integer.parseInt(userParagraphMap.get("star").toString()));
            user.getAbility().setMmr(Double.valueOf(userParagraphMap.get("mmr").toString()));
            user.getAbility().setParagraphName(userParagraphMap.get("name").toString());
            user.getAbility().setParagraphImage(userParagraphMap.get("image").toString());
            robotGameStatisRankDao.getWinNumByUserId(user);
            userList.add(user);
        }
        return userList;

    }
}
