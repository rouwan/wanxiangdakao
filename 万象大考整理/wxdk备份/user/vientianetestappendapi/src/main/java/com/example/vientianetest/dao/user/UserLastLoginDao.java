package com.example.vientianetest.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserLastLoginDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userlastlogin` (`userId`,`loginTime`,`createTime`) VALUES('%s',NOW(),NOW()) ;" ,
                userId),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void updateLoginTimeByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        jdbcOperations.update("UPDATE wxdk_userlastlogin SET loginTime = now() where userId = :userId ",paramMap);
    }

    public boolean isTodayLoginByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userlastlogin` WHERE userId = :userId  AND TIMESTAMPDIFF(DAY,DATE_FORMAT(loginTime,'%Y-%m-%d'),NOW()) >= 1",paramMap,Integer.class);
        if(count <= 0){
            return true;
        }
        return false;
    }

    public boolean isExistsByUserId(int userId ){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userlastlogin` WHERE userId = :userId ",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }


}
