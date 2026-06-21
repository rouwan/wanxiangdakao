package com.example.vientianetest.dao;

import com.alibaba.fastjson.JSONObject;
import com.example.vientianetest.domain.Game;
import com.example.vientianetest.domain.RoomGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameRoomDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int roomId,int userId,String userStr, int status,int modeType,int onLineNum){
        Map<String,Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_gameroom` (`roomId`,`userId`,`userList`,`status`,`modeType`,`onLineNum`,`modifyTime`,`createTime`) VALUES('%s','%s','%s','%s','%s','%s',NOW(),NOW()); ",
                            roomId,userId,userStr,status,modeType,onLineNum),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void updateStatus(int id , int status){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("status",status);
        paramMap.put("id",id);
        jdbcOperations.update("UPDATE `wxdk_gameroom` SET status = :status,modifyTime = NOW() WHERE id = :id ",paramMap);
    }

    public void update(RoomGame game){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",game.getGame().getId());
        paramMap.put("detail",JSONObject.toJSONString(game));
        paramMap.put("status",game.getRoomSub().getRoomStatus());
        paramMap.put("leftRankUserList",game.getLeftGroupList());
        paramMap.put("rightRankUserList",game.getRightGroupList());
        paramMap.put("exitUserList",game.getExitUserList());
        paramMap.put("firstUserId",game.getFirstUserId());
        paramMap.put("isFlat",game.getIsFlat());
        jdbcOperations.update("UPDATE `wxdk_gameroom` SET detail = :detail , status = :status,leftRankUserList=:leftRankUserList,rightRankUserList=:rightRankUserList,firstUserId=:firstUserId,exitUserList=:exitUserList,isFlat = :isFlat,modifyTime = NOW() WHERE id = :id ",paramMap);
    }

}
