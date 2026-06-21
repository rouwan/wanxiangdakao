package com.example.vientianetest.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserWeekChallengeDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId,int titleId,int weekCardNumber,String historySubjectId,int isWeekFinish){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userweekchallenge` (`userId`,`titleId`,`weekCardNumber`,`historySubjectId`,`isWeekFinish`,`modifyTime`,`createTime`) VALUES ('%s','%s','%s','%s','%s',NOW(),NOW()) ;",
                userId,titleId,weekCardNumber,historySubjectId,isWeekFinish),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void update(int userId,int titleId,int weekCardNumber,String historySubjectId,int isWeekFinish){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("titleId",titleId);
        paramMap.put("weekCardNumber",weekCardNumber);
        paramMap.put("historySubjectId",historySubjectId);
        paramMap.put("isWeekFinish",isWeekFinish);
        jdbcOperations.update("UPDATE `wxdk_userweekchallenge` SET `weekCardNumber` = :weekCardNumber,`historySubjectId` = :historySubjectId,`isWeekFinish` = :isWeekFinish,`modifyTime` = NOW() WHERE `userId` = :userId and titleId = :titleId;",paramMap);
    }

    public void updateIsWeekFinish(int userId,int titleId,int isWeekFinish){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("titleId",titleId);
        paramMap.put("isWeekFinish",isWeekFinish);
        jdbcOperations.update("UPDATE `wxdk_userweekchallenge` SET `isWeekFinish` = :isWeekFinish,`modifyTime` = NOW() WHERE `userId` = :userId and titleId = :titleId;",paramMap);
    }

    public void appendValueByUserId(int userId,int titleId,int weekCardNumber,String historySubjectId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("titleId",titleId);
        paramMap.put("weekCardNumber",weekCardNumber);
        paramMap.put("historySubjectId",historySubjectId);
        jdbcOperations.update("UPDATE `wxdk_userweekchallenge` SET `weekCardNumber` = weekCardNumber + :weekCardNumber,`historySubjectId` = CONCAT(historySubjectId ,:historySubjectId,','),`modifyTime` = NOW() WHERE `userId` = :userId and titleId = :titleId;",paramMap);
    }

    public List<Map<String,Object>> getInfoByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT *, ");
        sql.append("(CASE WHEN YEARWEEK(DATE_SUB(NOW(),INTERVAL 1 DAY)) != YEARWEEK(DATE_SUB(modifyTime,INTERVAL 1 DAY)) THEN 1 ELSE 0 END) AS isWeekEnd ");
        sql.append("FROM `wxdk_userweekchallenge` ");
        sql.append("WHERE userId = :userId ");
        return jdbcOperations.queryForList(sql.toString(),paramMap);
    }

    public Map<String,Object> getInfoByUserId(int userId,int titleId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("titleId",titleId);
        return jdbcOperations.queryForMap("SELECT * FROM `wxdk_userweekchallenge` where userId = :userId and titleId = :titleId LIMIT 1",paramMap);
    }

    //是否有上周
    public boolean isExistsOverTimeByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userweekchallenge` where userId = :userId AND YEARWEEK(DATE_SUB(NOW(),INTERVAL 1 DAY)) != YEARWEEK(DATE_SUB(modifyTime,INTERVAL 1 DAY))",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean isExistsByUserId(int userId,int titleId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("titleId",titleId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userweekchallenge` where userId = :userId and titleId = :titleId",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }
}
