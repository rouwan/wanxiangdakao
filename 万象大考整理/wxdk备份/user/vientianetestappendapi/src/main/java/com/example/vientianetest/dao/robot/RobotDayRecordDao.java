package com.example.vientianetest.dao.robot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RobotDayRecordDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId,int counter,int afterCounter){
        Map<String,Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_robotdayrecord` (`userId`,`firstCounter`,`afterCounter`,`modifyTime`,`createTime`) VALUES ('%s','%s','%s',NOW(),NOW()) ;",
                userId,counter,afterCounter),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void appendFistCounterByUserId(int userId,int counter){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("counter",counter);
        jdbcOperations.update("UPDATE `wxdk_robotdayrecord` SET firstCounter = ifnull(firstCounter,0)+:counter,modifyTime = NOW() WHERE userId = :userId ",paramMap);
    }

    public void appendAfterCounterByUserId(int userId,int counter){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("counter",counter);
        jdbcOperations.update("UPDATE `wxdk_robotdayrecord` a INNER JOIN `wxdk_robot` b ON b.`id` = -a.`userId` SET a.afterCounter = CASE WHEN TIMESTAMPDIFF(DAY,DATE_FORMAT(a.modifyTime,'%Y-%m-%d'),NOW()) >= 1 THEN 1 ELSE ifnull(afterCounter,0)+:counter END ,a.modifyTime = NOW() WHERE userId = :userId ",paramMap);
    }

    //今天是否已经跑完
    public boolean isTodayFinish(int userId,int maxCounter){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("maxCounter",maxCounter);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_robotdayrecord` WHERE userId = :userId AND counter>=:maxCounter",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    //获取第一次还没跑完的机器人
    public int getFirstCount(){
        Map<String,Object> paramMap = new HashMap<>();
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_robot` a LEFT JOIN `wxdk_robotdayrecord` b ON a.id = -b.userId WHERE a.flag < 3 AND STATUS = 1 AND ((IFNULL(b.firstCounter,0)<a.`firstDayNum`) OR IFNULL(b.id,0) = 0) ",paramMap,Integer.class);
        return count;
    }

    public int getAfterCount(){
        Map<String,Object> paramMap = new HashMap<>();
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_robot` a LEFT JOIN `wxdk_robotdayrecord` b ON a.id = -b.userId WHERE a.flag < 3 AND STATUS = 1 AND ((IFNULL(b.afterCounter,0)<a.`dayNum`) OR TIMESTAMPDIFF(DAY,DATE_FORMAT(b.`modifyTime`,'%Y-%m-%d'),NOW()) >= 1) AND IFNULL(a.`firstDayNum` ,0) <= IFNULL(b.`firstCounter`,0);",paramMap,Integer.class);
        return count;
    }

    public boolean isExistsByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_robotdayrecord` WHERE userId = :userId ",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }
}
