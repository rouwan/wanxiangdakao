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
public class UserDayChallengeDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId,int todayCardNumber,int weekMaxCardNumber,String historySubjectId){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userdaychallenge` (`userId`,`todayCardNumber`,`weekMaxCardNumber`,`historySubjectId`,`isDayFinish`,`modifyTime`,`createTime`) VALUES('%s','%s','%s','%s',0,NOW(),NOW()) ;",
                                            userId,todayCardNumber,weekMaxCardNumber,historySubjectId),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void update(int userId,int todayCardNumber,int weekMaxCardNumber,String historySubjectId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("todayCardNumber",todayCardNumber);
        paramMap.put("weekMaxCardNumber",weekMaxCardNumber);
        paramMap.put("historySubjectId",historySubjectId);
        jdbcOperations.update("UPDATE `wxdk_userdaychallenge`  SET `todayCardNumber` = :todayCardNumber,`weekMaxCardNumber` = :weekMaxCardNumber,`historySubjectId` = :historySubjectId,`modifyTime` = NOW() WHERE `userId` = :userId ;",paramMap);
    }

    public void updateTodayCardNumber(int userId,int todayCardNumber,String historySubjectId,int isDayFinish){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("todayCardNumber",todayCardNumber);
        paramMap.put("historySubjectId",historySubjectId);
        paramMap.put("isDayFinish",isDayFinish);
        jdbcOperations.update("UPDATE `wxdk_userdaychallenge` SET `todayCardNumber` = :todayCardNumber,`historySubjectId` = :historySubjectId,`isDayFinish`=:isDayFinish,`modifyTime` = NOW() WHERE `userId` = :userId ;",paramMap);
    }

    public void updateWeekMaxCardNumber(int userId,int weekMaxCardNumber){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("weekMaxCardNumber",weekMaxCardNumber);
        jdbcOperations.update("UPDATE `wxdk_userdaychallenge`  SET `weekMaxCardNumber` = :weekMaxCardNumber,`modifyTime` = NOW() WHERE `userId` = :userId ;",paramMap);
    }

    public void updateHistorySubjectId(int userId,String historySubjectId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("historySubjectId",historySubjectId);
        jdbcOperations.update("UPDATE `wxdk_userdaychallenge`  SET `historySubjectId` = :historySubjectId,`modifyTime` = NOW() WHERE `userId` = :userId ;",paramMap);
    }

    public void updateIsDayFinish(int userId,int isDayFinish){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("isDayFinish",isDayFinish);
        jdbcOperations.update("UPDATE `wxdk_userdaychallenge`  SET `isDayFinish` = :isDayFinish,`modifyTime` = NOW() WHERE `userId` = :userId ;",paramMap);
    }

    public void appendCardNumber(int userId,int todayCardNumber,String historySubjectId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("todayCardNumber",todayCardNumber);
        paramMap.put("historySubjectId",historySubjectId);
        jdbcOperations.update("UPDATE `wxdk_userdaychallenge` SET `weekMaxCardNumber` = case when todayCardNumber + :todayCardNumber > weekMaxCardNumber then todayCardNumber + :todayCardNumber else weekMaxCardNumber end,`todayCardNumber` = todayCardNumber + :todayCardNumber,`historySubjectId` = CONCAT(historySubjectId ,:historySubjectId,','),`modifyTime` = NOW() WHERE `userId` = :userId ;",paramMap);
    }

    public Map<String,Object> getUserDayChallenge(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("a.*, ");
        sb.append("(CASE WHEN DATE_FORMAT(NOW(),'%Y-%m-%d') != DATE_FORMAT(modifyTime,'%Y-%m-%d') THEN 1 ELSE 0 END)AS isDayEnd, ");
        sb.append("(CASE WHEN YEARWEEK(DATE_SUB(NOW(),INTERVAL 1 DAY)) != YEARWEEK(DATE_SUB(modifyTime,INTERVAL 1 DAY))  THEN 1 ELSE 0 END ) AS isWeekEnd ");
        sb.append("FROM `wxdk_userdaychallenge` a ");
        sb.append("WHERE a.userId = :userId ");

        return jdbcOperations.queryForMap(sb.toString(),paramMap);
    }

    public boolean isExistByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_userdaychallenge WHERE userId = :userId",paramMap,Integer.class);
        if(count > 0 ){
            return true;
        }
        return false;
    }

    public List<Map<String,Object>> getRankList(){
        Map<String,Object> paramMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.`userId`,b.nickname ,b.`headImgUrl`, ");
        sb.append("CASE WHEN YEARWEEK(DATE_SUB(NOW(),INTERVAL 1 DAY)) != YEARWEEK(DATE_SUB(a.modifyTime,INTERVAL 1 DAY)) THEN 0 ELSE a.weekMaxCardNumber END AS cardNumber ");
        sb.append("FROM wxdk_userdaychallenge a ");
        sb.append("LEFT JOIN wxdk_user b ON a.`userId` = b.id ");
        sb.append("ORDER BY CASE WHEN YEARWEEK(DATE_SUB(NOW(),INTERVAL 1 DAY)) != YEARWEEK(DATE_SUB(a.modifyTime,INTERVAL 1 DAY)) THEN 0 ELSE a.weekMaxCardNumber END DESC,a.modifyTime ASC LIMIT 30 ");
        return jdbcOperations.queryForList(sb.toString(),paramMap);
    }


    public Map<String,Object> getRankListByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.`userId`,b.nickname ,b.`headImgUrl`, ");
        sb.append("CASE WHEN YEARWEEK(DATE_SUB(NOW(),INTERVAL 1 DAY)) != YEARWEEK(DATE_SUB(a.modifyTime,INTERVAL 1 DAY)) THEN 0 ELSE a.weekMaxCardNumber END AS cardNumber, ");
        sb.append("(SELECT a.index FROM (SELECT (@i:=@i+1) `index`,a.userId FROM wxdk_userdaychallenge a,(SELECT @i:=0)  b ORDER BY CASE WHEN YEARWEEK(DATE_SUB(NOW(),INTERVAL 1 DAY)) != YEARWEEK(DATE_SUB(a.modifyTime,INTERVAL 1 DAY)) THEN 0 ELSE a.weekMaxCardNumber END DESC , a.modifyTime ASC) a WHERE a.userId = :userId LIMIT 1) AS `index` ");
        sb.append("FROM wxdk_userdaychallenge a ");
        sb.append("LEFT JOIN wxdk_user b ON a.`userId` = b.id  ");
        sb.append("WHERE a.userId = :userId LIMIT 1");
        return jdbcOperations.queryForMap(sb.toString(),paramMap);
    }
}

