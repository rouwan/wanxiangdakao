package com.example.vientianetest.redis;

import com.alibaba.fastjson.JSONObject;
import com.example.vientianetest.domain.MatchRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserRankMatchRecordRedis {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    private String KEY_PACKAGE = "server:userRecord:rankMatchRecords";

    public void addUserMatchRecord(int userId,int withUserId){
        boolean hasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE,String.valueOf(userId));
        int userMaxMatchNum = 0;
        int withUserMaxMatchNum = 0;
        Map<Object,Integer> withUserMap = null;
        if(hasKey){
            JSONObject jsonMap = JSONObject.parseObject(redisTemplate.opsForHash().get(KEY_PACKAGE ,String.valueOf(userId)).toString());
            userMaxMatchNum = jsonMap.get("maxMatchNum") != null ? Integer.parseInt(jsonMap.get("maxMatchNum").toString()) : 0;
            withUserMap = (Map<Object,Integer>)jsonMap.get("userMap");
            withUserMaxMatchNum = userMaxMatchNum; //withUserMap.get(String.valueOf(withUserId)) == null ? 0 : userMaxMatchNum;
        }
        if(withUserMap == null){
            withUserMap = new HashMap<>();
        }
        withUserMap.put(String.valueOf(withUserId), withUserMaxMatchNum + 1);
        /*if(withUserId > 0 ){ //机器人不统计
            withUserMap.put(String.valueOf(withUserId), withUserMaxMatchNum + 1);
        }*/
        MatchRecord matchRecord =  new MatchRecord(withUserMap, userMaxMatchNum + 1);

        redisTemplate.opsForHash().put(KEY_PACKAGE,String.valueOf(userId),JSONObject.toJSONString(matchRecord));
    }

    public void setUserMatchMaxNum(int userId,int maxMatchNum){
        boolean hasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE ,String.valueOf(userId));
        if(hasKey){
            JSONObject jsonMap = JSONObject.parseObject(redisTemplate.opsForHash().get(KEY_PACKAGE,String.valueOf(userId)).toString());
            Map<Object,Integer> userMap = (Map<Object,Integer>)jsonMap.get("userMap");
            MatchRecord matchRecord =  new MatchRecord(userMap, maxMatchNum);
            redisTemplate.opsForHash().put(KEY_PACKAGE,String.valueOf(userId),JSONObject.toJSONString(matchRecord));
        }

    }

    public int getUserMaxMatchNum(int userId){
        boolean hasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE ,String.valueOf(userId));
        if(hasKey) {
            JSONObject jsonMap = JSONObject.parseObject(redisTemplate.opsForHash().get(KEY_PACKAGE ,String.valueOf(userId)).toString());
            return jsonMap.get("maxMatchNum") != null ? Integer.parseInt(jsonMap.get("maxMatchNum").toString()) : 0;
        }
        return 0;
    }

    public int getWithUserMatchIndex(int userId,int withUserId){
        boolean hasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE ,String.valueOf(userId));
        if(hasKey) {
            JSONObject jsonMap = JSONObject.parseObject(redisTemplate.opsForHash().get(KEY_PACKAGE,String.valueOf(userId)).toString());
            Map<Object,Integer> userMap = (Map<Object,Integer>)jsonMap.get("userMap");
            return userMap.get(String.valueOf(withUserId)) == null ? 0 : userMap.get(String.valueOf(withUserId));
        }
        return 0;
    }
}
