package com.example.vientianetest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataConfigDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public Map<String,Object> getDataConfig(){
        Map<String,Object> resultMap = new HashMap<>();
        List<Map<String,Object>> listMap = jdbcOperations.queryForList("SELECT * FROM wxdk_dataconfig WHERE flag < 3",new HashMap<>());
        for (int i = 0; i < listMap.size(); i++) {
            Map<String, Object> map = listMap.get(i);
            resultMap.put(map.get("name").toString(),map.get("value"));
        }
        return resultMap;
    }

    public Map<String,Object> getInfoByName(String name){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        return jdbcOperations.queryForMap("SELECT * FROM wxdk_dataconfig WHERE name = :name and flag < 3 limit 1",paramMap);
    }

    public Map<String,Object> getGradeByName(){
        Map<String,Object> resultMap = new HashMap<>();
        Map<String,Object> paramMap = new HashMap<>();
        List<Map<String,Object>> listMap = jdbcOperations.queryForList("SELECT * FROM wxdk_dataconfig WHERE name = 'grade' and flag < 3",paramMap);
        for (int i = 0; i < listMap.size(); i++) {
            Map<String, Object> map = listMap.get(i);
            resultMap.put(map.get("sortId").toString(),map.get("value"));
        }
        return resultMap;
    }
}
