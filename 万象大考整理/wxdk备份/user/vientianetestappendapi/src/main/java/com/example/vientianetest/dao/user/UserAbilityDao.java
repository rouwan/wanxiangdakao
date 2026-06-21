package com.example.vientianetest.dao.user;

import com.example.vientianetest.config.DataConfiguration;
import com.example.vientianetest.domain.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserAbilityDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private DataConfiguration dataConfiguration;

    public int insert(int userId,double expValue,double money,int grade){
        Map<String,Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userability` (`userId`,`expValue`,`money`,`grade`,`strengthCount`,`strengthModifyTime`) VALUES('%s','%s','%s','%s','%s',now()) ;" ,
                                            userId,expValue,money,grade,Param.GLOBAL_MAX_STRENGTHCOUNT),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void update(int userId,double expValue,int grade){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("expValue",expValue);
        paramMap.put("grade",grade);
        paramMap.put("maxExpValue",dataConfiguration.getMaxExpValue());
        paramMap.put("maxGrade",dataConfiguration.getMaxGrade());
        jdbcOperations.update("UPDATE `wxdk_userability` SET `expValue` = case when :expValue > :maxExpValue then :maxExpValue else :expValue end ,`grade` = case when :grade > :maxGrade then :maxGrade else :grade end,`modifyTime` = NOW() WHERE  `userId` = :userId ",paramMap);
    }

    public void appendMoney(int userId,double money){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("money",money);
        jdbcOperations.update("UPDATE `wxdk_userability` SET `money` = CASE WHEN money + :money <= 0 THEN 0 ELSE money + :money END,`modifyTime` = NOW() WHERE  `userId` = :userId ",paramMap);
    }

    public void appendValueByUserId(int userId,double expValue,double money,int grade){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("expValue",expValue);
        paramMap.put("money",money);
        paramMap.put("grade",grade);
        paramMap.put("maxExpValue",dataConfiguration.getMaxExpValue());
        paramMap.put("maxGrade",dataConfiguration.getMaxGrade());
        jdbcOperations.update("UPDATE `wxdk_userability` SET `expValue` = case when expValue+:expValue > :maxExpValue then :maxExpValue else expValue+:expValue end ,`money` = money+:money,`grade` = case when grade+:grade > :maxGrade then :maxGrade else grade+:grade end,`modifyTime` = NOW() WHERE  `userId` = :userId ",paramMap);
    }

    public void appendStrengthCountByUserId(int userId,int isTest,int strengthCount){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("strengthCount",strengthCount);
        paramMap.put("MaxStrengthCount",Param.GLOBAL_MAX_STRENGTHCOUNT);
        paramMap.put("isTest",isTest);
        jdbcOperations.update("UPDATE `wxdk_userability` SET `strengthCount` = case when strengthCount + :strengthCount >= :MaxStrengthCount and :isTest != 1 then :MaxStrengthCount else strengthCount + :strengthCount end ,`strengthModifyTime` = NOW(),`modifyTime` = NOW() WHERE userId = :userId ",paramMap);
    }

    public void updateStrengthModifyTime(int userId, int second){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("second",second);
        jdbcOperations.update("UPDATE wxdk_userability set `strengthModifyTime` = DATE_SUB(NOW(),interval :second second) WHERE userId = :userId",paramMap);
    }

    //体力满的话重置strengthModifyTime
    public void appendStrengthCountByUserIdSub(int userId,int isTest,int strengthCount){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("strengthCount",strengthCount);
        paramMap.put("MaxStrengthCount",Param.GLOBAL_MAX_STRENGTHCOUNT);
        paramMap.put("isTest",isTest);
        jdbcOperations.update("UPDATE `wxdk_userability` SET `strengthModifyTime` = case when strengthCount >= :MaxStrengthCount OR strengthCount + :strengthCount >= :MaxStrengthCount then NOW() ELSE strengthModifyTime END ,`strengthCount` = case when strengthCount + :strengthCount >= :MaxStrengthCount and :isTest != 1 then :MaxStrengthCount WHEN strengthCount + :strengthCount <=0 THEN 0 ELSE strengthCount + :strengthCount end ,`modifyTime` = NOW() WHERE userId = :userId ",paramMap);
    }

    //恢复体力
    public Map<String,Object> updateIntervalTimerStrengthCountByUserId(int userId,int isTest){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("intervalTimerConfig",Param.GLOBAL_STRENGTHINTERVALTIMER);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("CASE WHEN UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(`strengthModifyTime`) >= :intervalTimerConfig THEN ");//AND strengthIntervalTimer >0
        sb.append("FLOOR((UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(`strengthModifyTime`))/:intervalTimerConfig) ELSE 0 END AS addStrength, ");
        sb.append("CASE WHEN (UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(strengthModifyTime)) > :intervalTimerConfig THEN :intervalTimerConfig - (UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(strengthModifyTime)) % :intervalTimerConfig ELSE :intervalTimerConfig - (UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(strengthModifyTime)) END  AS strengthIntervalTimer ");
        sb.append("FROM `wxdk_userability` a WHERE userId = :userId limit 1");
        Map<String,Object> strengthMap = jdbcOperations.queryForMap(sb.toString(),paramMap);
        int addStrength = Integer.parseInt(strengthMap.get("addStrength").toString());
        if(addStrength > 0 ){
            addStrength = addStrength > Param.GLOBAL_MAX_STRENGTHCOUNT ? Param.GLOBAL_MAX_STRENGTHCOUNT : addStrength;
            this.appendStrengthCountByUserIdSub(userId,isTest,addStrength);
            this.updateStrengthModifyTime(userId,Param.GLOBAL_STRENGTHINTERVALTIMER - Integer.parseInt(strengthMap.get("strengthIntervalTimer").toString()));
        }
        Map<String,Object> resultMap = new HashMap<String,Object>(){{put("strengthIntervalTimer",Integer.parseInt(strengthMap.get("strengthIntervalTimer").toString()));}};
        return resultMap;
    }

    public void updateStrengthIntervalTimer(int userId,int strengthIntervalTimer){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("strengthIntervalTimer",strengthIntervalTimer);
        jdbcOperations.update("UPDATE `wxdk_userability` SET `strengthModifyTime` = NOW(),`modifyTime` = NOW() WHERE userId = :userId limit 1",paramMap);
    }

    public Map<String,Object> getInfoByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("maxGrade",dataConfiguration.getMaxGrade());
        paramMap.put("maxExpValue",dataConfiguration.getMaxExpValue());
        paramMap.put("MaxStrengthCount",Param.GLOBAL_MAX_STRENGTHCOUNT);
        paramMap.put("intervalTimerConfig",Param.GLOBAL_STRENGTHINTERVALTIMER);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.id,a.`userId`,CASE WHEN a.grade >= :maxGrade THEN :maxExpValue ELSE a.expValue END AS expValue,a.money,a.grade,CASE WHEN a.grade >= :maxGrade THEN :maxExpValue ELSE b.value END AS maxExpValue,a.strengthCount ");
        sql.append("FROM `wxdk_userability` a LEFT JOIN `wxdk_uplevel` b ON a.grade+1 = b.level and b.name='grade' WHERE a.userId = :userId LIMIT 1 ");
        return jdbcOperations.queryForMap(sql.toString(),paramMap);
    }

    public boolean isHashStrengthByCount(int userId,int num){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("num",num);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userability` WHERE userId = :userId and strengthCount>=:num",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean isHashMoneyByUserId(int userId,int money){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("money",money);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userability` WHERE userId = :userId and money>=:money",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean isExistsByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userability` WHERE userId = :userId",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }
}
