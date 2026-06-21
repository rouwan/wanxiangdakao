package com.example.vientianetest.redis;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.concurrent.TimeUnit;

@Component
public class SessionRedis {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    private int KEY_TIMEOUT = 60; //分钟
    private String KEY_PACKAGE_USERID = "server:session:userIds:";
    private String KEY_PACKAGE_SESSSION = "server:session:sessions:";

    public void add(BasicUserInfo userInfo, String sessionId){
        redisTemplate.opsForHash().put(KEY_PACKAGE_USERID + String.valueOf(userInfo.getUserId()),String.valueOf(userInfo.getUserId()),sessionId);
        redisTemplate.opsForHash().put(KEY_PACKAGE_SESSSION + sessionId,sessionId,JSONObject.toJSONString(userInfo));
        this.updateTimeOut(userInfo.getUserId(), sessionId);
    }

    public void update(BasicUserInfo userInfo, Cookie[] cookies){
        String sessionId = null;
        for(Cookie cookie : cookies) {
            if (cookie.getName().equals("JSESSIONID")) {
                sessionId = cookie.getValue();
                break;
            }
        }
        redisTemplate.opsForHash().put(KEY_PACKAGE_USERID + String.valueOf(userInfo.getUserId()),String.valueOf(userInfo.getUserId()),sessionId);
        redisTemplate.opsForHash().put(KEY_PACKAGE_SESSSION + sessionId,sessionId,JSONObject.toJSONString(userInfo));
    }

    public void updateTimeOut(int userId, String sessionId){
        redisTemplate.expire(KEY_PACKAGE_USERID + String.valueOf(userId),KEY_TIMEOUT,TimeUnit.MINUTES);
        redisTemplate.expire(KEY_PACKAGE_SESSSION + sessionId,KEY_TIMEOUT,TimeUnit.MINUTES);
    }

    public BasicUserInfo getAttribute(Cookie[] cookies){
        String sessionId = null;
        for(Cookie cookie : cookies) {
            if (cookie.getName().equals("JSESSIONID")) {
                sessionId = cookie.getValue();
                break;
            }
        }
        JSONObject jsonObject = JSONObject.parseObject(redisTemplate.opsForHash().get(KEY_PACKAGE_SESSSION + sessionId,sessionId).toString());
        return new BasicUserInfo(jsonObject.get("openId").toString(),
                                 Integer.parseInt(jsonObject.get("userId").toString()),
                                 jsonObject.get("nickName").toString(),
                                 jsonObject.get("gender").toString(),
                                 jsonObject.get("avatarUrl").toString(),
                                 jsonObject.get("city").toString(),
                                 jsonObject.get("province").toString(),
                                 jsonObject.get("country").toString());
    }

    public BasicUserInfo getAttribute(int userId){
        boolean hasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE_USERID + String.valueOf(userId),String.valueOf(userId));
        if(!hasKey){
            return null;
        }
        String sessionId = redisTemplate.opsForHash().get(KEY_PACKAGE_USERID + String.valueOf(userId),String.valueOf(userId)).toString();
        JSONObject jsonObject = JSONObject.parseObject(redisTemplate.opsForHash().get(KEY_PACKAGE_SESSSION + sessionId,sessionId).toString());
        return new BasicUserInfo(jsonObject.get("openId").toString(),
                                 Integer.parseInt(jsonObject.get("userId").toString()),
                                 jsonObject.get("nickName").toString(),
                                 jsonObject.get("gender").toString(),
                                 jsonObject.get("avatarUrl").toString(),
                                 jsonObject.get("city").toString(),
                                 jsonObject.get("province").toString(),
                                 jsonObject.get("country").toString());
    }

    public BasicUserInfo getAttribute(String sessionId){
        JSONObject jsonObject = JSONObject.parseObject(redisTemplate.opsForHash().get(KEY_PACKAGE_SESSSION + sessionId,sessionId).toString());
        return new BasicUserInfo(jsonObject.get("openId").toString(),
                Integer.parseInt(jsonObject.get("userId").toString()),
                jsonObject.get("nickName").toString(),
                jsonObject.get("gender").toString(),
                jsonObject.get("avatarUrl").toString(),
                jsonObject.get("city").toString(),
                jsonObject.get("province").toString(),
                jsonObject.get("country").toString());
    }

    public boolean isLogin(String sessionId){
        boolean hasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE_SESSSION + sessionId,sessionId);
        if(hasKey){
            JSONObject jsonObject = JSONObject.parseObject(redisTemplate.opsForHash().get(KEY_PACKAGE_SESSSION + sessionId,sessionId).toString());
            int userId = Integer.parseInt(jsonObject.get("userId").toString());
            this.updateTimeOut(userId,sessionId);
            return true;
        }
        return false;
    }
}
