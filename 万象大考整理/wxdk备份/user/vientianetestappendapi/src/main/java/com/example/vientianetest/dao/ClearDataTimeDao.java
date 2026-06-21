package com.example.vientianetest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ClearDataTimeDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public Map<String,Object> getClearDataTimeByType(int type) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("type",type);
        return jdbcOperations.queryForMap("SELECT DATE_FORMAT(clearTime,'%Y-%m-%d') AS clearTime FROM wxdk_cleardatatime  WHERE type = :type LIMIT 1 ",paramMap);
    }

    public boolean isArriveClearTimeByType(int type){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("type",type);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_cleardatatime WHERE DATE_FORMAT(clearTime,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d') AND type = :type ",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean isExistsByType(int type) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("type",type);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_cleardatatime` WHERE type = :type",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }
}
