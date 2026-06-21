package com.example.vientianetest.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserTicketDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public Map<String, Object> getTicketByUserId(int userId,int type){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("type",type);
        return jdbcOperations.queryForMap("SELECT userId,num FROM `wxdk_userticket` where userId = :userId and type = :type limit 1 ",paramMap);
    }

    public boolean isExistsByUserId(int userId, int type){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("type",type);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userticket` where userId = :userId and type = :type ",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }
}
