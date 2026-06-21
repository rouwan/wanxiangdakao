package com.example.vientianetest.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserParagraphHistoryDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId, String historyStr){
        Map<String,Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userparagraphhistory` (`userId`,`history`) VALUES('%s','%s') ;" +
                                userId,historyStr),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public Map<String,Object> getUserParagraphHistoryByUserId(int userId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        return jdbcOperations.queryForMap("SELECT history as historyStr FROM wxdk_userparagraphhistory WHERE userId = :userId",paramMap);
    }

    public Map<String,Object> getUserParagraphHistoryNoReadByUserId(int userId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        return jdbcOperations.queryForMap("SELECT history as historyStr FROM wxdk_userparagraphhistory WHERE userId = :userId and isRead = 0",paramMap);
    }

    public void appendHistoryByUserId(int userId, String historyStr) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("historyStr",historyStr);
        jdbcOperations.update("UPDATE wxdk_userparagraphhistory SET history = history + :historyStr , modifyTime = now() WHERE userId = :userId ",paramMap);
    }

    public void updateStatusByUserId(int userId,int status) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("status",status);
        jdbcOperations.update("UPDATE wxdk_userparagraphhistory SET isRead = :status WHERE userId = :userId",paramMap);
    }

    public boolean isExistsByUserId(int userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_userparagraphhistory WHERE userId = :userId ",paramMap,Integer.class);
        if(count > 0) {
            return true;
        }
        return false;
    }

    public boolean  isExistsNoReadByUserId(int userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_userparagraphhistory WHERE userId = :userId and isRead = 0",paramMap,Integer.class);
        if(count > 0) {
            return true;
        }
        return false;
    }
}
