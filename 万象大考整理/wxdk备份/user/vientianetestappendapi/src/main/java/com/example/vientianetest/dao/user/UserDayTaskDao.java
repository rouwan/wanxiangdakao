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
public class UserDayTaskDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId,int type,int status,int num) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userdaytask` (`userId`,`type`,`status`,`num`,`modifyTime`,`createTime`) VALUES('%s','%s','%s','%s',NOW(),NOW()) ;",
                userId,type,status,num),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public int insertRecord(int userId,int type) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userdaytaskrecord` (`userId`,`type`,`modifyTime`,`createTime`) VALUES('%s','%s',NOW(),NOW()) ;",
                userId,type),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void updateStatusByUserId(int userId, int status,int receive) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("status", status);
        paramMap.put("receive", receive);
        jdbcOperations.update("UPDATE wxdk_userdaytask SET status= :status,receive=:receive,modifyTime = now() WHERE userId = :userId ",paramMap);
    }

    public void updateStatusByUserId(int userId,int type, int status, int receive) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("type", type);
        paramMap.put("status", status);
        paramMap.put("receive", receive);
        jdbcOperations.update("UPDATE wxdk_userdaytask SET status= :status,receive=:receive,modifyTime = now() WHERE userId = :userId and type = :type ",paramMap);
    }

    public List<Map<String,Object>> getTaskListByUserId(int userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.`type`, a.`description`,a.num AS totalNum,IFNULL(b.num,0) AS num,IFNULL(b.status,0) AS `status`,IFNULL(b.receive,0) AS `receive` ");
        sql.append("FROM `wxdk_daytask` a ");
        sql.append("LEFT JOIN `wxdk_userdaytask` b ON a.`type` = b.`type` AND b.`userId` = :userId AND DATEDIFF(DATE_FORMAT(NOW(),'%Y-%m-%d'),DATE_FORMAT( b.modifyTime,'%Y-%m-%d')) = 0 ORDER BY a.sortId; ");
        return jdbcOperations.queryForList(sql.toString(), paramMap);
    }

    public boolean isExistsTodayByUserId(int userId,int type) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("type", type);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_userdaytask WHERE userId = :userId and type = :type AND DATEDIFF(DATE_FORMAT(NOW(),'%Y-%m-%d'),DATE_FORMAT( modifyTime,'%Y-%m-%d')) = 0", paramMap, Integer.class);
        if (count > 0) {
            return true;
        }
        return false;
    }

    public boolean isExistsByUserId(int userId,int type) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("type", type);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_userdaytask WHERE userId = :userId and type = :type ", paramMap, Integer.class);
        if (count > 0) {
            return true;
        }
        return false;
    }

    public boolean isExistsByUserId(int userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_userdaytask WHERE userId = :userId ", paramMap, Integer.class);
        if (count > 0) {
            return true;
        }
        return false;
    }

    //判断是否已经领取
    public boolean isExistsTodayNoReceiveByUserId(int userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_userdaytask WHERE userId = :userId AND `status` = 1 AND receive = 0 AND DATEDIFF(DATE_FORMAT(NOW(),'%Y-%m-%d'),DATE_FORMAT( modifyTime,'%Y-%m-%d')) = 0 ", paramMap, Integer.class);
        //要完成三项任务才能领取
        if (count >= 3) {
            return true;
        }
        return false;
    }

    //判断今天是否有该任务
    public boolean isExistsTaskByType(int type) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("type", type);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_daytask WHERE type = :type and flag < 3 ", paramMap, Integer.class);
        if (count > 0) {
            return true;
        }
        return false;
    }
}
