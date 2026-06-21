package com.example.vientianetest.dao.robot;

import com.alibaba.fastjson.JSONObject;
import com.example.vientianetest.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RobotGameRankDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId,int withUserId, int status){
        Map<String,Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_robotgamerank` (`userId`,`withUserId`,`status`,`modifyTime`,`createTime`) " +
                "VALUES('%s','%s','%s',now(),now()) ;",userId,withUserId,status),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void updateStatus(int id , int status){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("status",status);
        paramMap.put("id",id);
        jdbcOperations.update("UPDATE `wxdk_robotgamerank` SET status = :status,modifyTime = NOW() WHERE id = :id ",paramMap);
    }

    public void update(Game game){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("detail",JSONObject.toJSONString(game));
        paramMap.put("status",game.getStatus());
        paramMap.put("id",game.getId());
        paramMap.put("winUserId",game.getWinUserId());
        paramMap.put("exitUserId",game.getExitUserId());
        paramMap.put("isFlat",game.getIsFlat());
        paramMap.put("userScore",game.getUserList().get(0).getAbility().getScore());
        paramMap.put("withUserScore",game.getUserList().get(1).getAbility().getScore());
        jdbcOperations.update("UPDATE `wxdk_robotgamerank` SET detail = :detail , status = :status,winUserId=:winUserId,exitUserId=:exitUserId,isFlat = :isFlat,userScore= :userScore,withUserScore = :withUserScore,modifyTime = NOW() WHERE id = :id ",paramMap);
    }

    public List<Map<String,Object>> getGameRankByUserId(int userId , int limit){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("limit",limit);
        List<Map<String,Object>> mapList = null;
        if(this.isExistByUserId(userId)){
            mapList = jdbcOperations.queryForList("SELECT userId,withUserId,detail,winUserId,exitUserId,isFlat FROM `wxdk_robotgamerank` WHERE CHAR_LENGTH(detail) > 0 AND userId = :userId OR withUserId = :userId  ORDER BY createTime DESC LIMIT :limit",paramMap);
        }
        return mapList;
    }

    public List<Map<String,Object>> getGameRankByUserId(){
        Map<String,Object> paramMap = new HashMap<>();
        return jdbcOperations.queryForList("SELECT userId,withUserId,detail,winUserId,exitUserId,isFlat FROM `wxdk_robotgamerank` WHERE ifnull(withUserScore,-1) = -1 limit 100 ",paramMap);
    }

    public boolean isExistByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_robotgamerank` WHERE userId = :userId",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }
}
