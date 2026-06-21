package com.example.vientianetest.dao;

import com.alibaba.fastjson.JSONObject;
import com.example.vientianetest.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(String uidStr,int status){
        Map<String,Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_game` (`users`,`status`,`modifyTime`,`createTime`) " +
                                             "VALUES('%s','%s',now(),now()) ;",uidStr,status),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void updateStatus(int id , int status){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("status",status);
        paramMap.put("id",id);
        jdbcOperations.update("UPDATE `wxdk_game` SET status = :status,modifyTime = NOW() WHERE id = :id ",paramMap);
    }

    public void update(Game game){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("detail",JSONObject.toJSONString(game));
        paramMap.put("status",game.getStatus());
        paramMap.put("id",game.getId());
        paramMap.put("winUserId",game.getWinUserId());
        paramMap.put("exitUserId",game.getExitUserId());
        paramMap.put("isFlat",game.getIsFlat());
        jdbcOperations.update("UPDATE `wxdk_game` SET detail = :detail , status = :status,winUserId=:winUserId,exitUserId=:exitUserId,isFlat = :isFlat,modifyTime = NOW() WHERE id = :id ",paramMap);
    }
}
