package com.example.vientianetest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UpLevelDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public Map<String,Object> getLevelByName(String name){
        Map<String,Object> resultMap = new HashMap<>();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        List<Map<String,Object>> listMap = jdbcOperations.queryForList("SELECT * FROM wxdk_uplevel WHERE `name` = :name",paramMap);
        for (int i = 0; i < listMap.size(); i++) {
            Map<String, Object> map = listMap.get(i);
            resultMap.put(map.get("level").toString(),map.get("value"));
        }
        return resultMap;
    }
}
