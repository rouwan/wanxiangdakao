package com.example.vientianetest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ParagraphDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public Map<String,Object> getParagraph(){
        Map<String,Object> resultMap = new HashMap<>();
        List<Map<String,Object>> listMap = jdbcOperations.queryForList("SELECT `id`,`name`,`image`,`status`,`level`,`star`,`goodType`,`goodNum`,`totalNum`,`winNum`,`isRefine`,`modifyTime`,`createTime`,`flag` FROM `wxdk_paragraph` WHERE flag < 3 ",new HashMap<>());
        for (int i = 0; i < listMap.size(); i++) {
            Map<String, Object> map = listMap.get(i);
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("name", map.get("name").toString());
            objectMap.put("image", map.get("image").toString());
            objectMap.put("status", map.get("status").toString());
            objectMap.put("level", map.get("level").toString());
            objectMap.put("star", map.get("star").toString());
            objectMap.put("goodType", map.get("goodType").toString());
            objectMap.put("goodNum", map.get("goodNum").toString());
            objectMap.put("totalNum", map.get("totalNum").toString());
            objectMap.put("winNum", map.get("winNum").toString());
            objectMap.put("isRefine", map.get("isRefine").toString());
            resultMap.put(map.get("level").toString(),objectMap);
        }
        return resultMap;
    }
}
