package com.example.vientianetest.service;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.dao.FeedBackUserDao;
import com.example.vientianetest.redis.SessionRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class FeedBackUserService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private FeedBackUserDao feedBackUserDao;

    public String insert(HttpServletRequest request, int userId , String types){
        HttpSession session = request.getSession();
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        //机器人不操作
        if(userId <= 0){
            return JSONObject.toJSONString(new BasicResultObject());
        }
        feedBackUserDao.insert(basicUserInfo.getUserId(),userId,types);
        return JSONObject.toJSONString(new BasicResultObject());
    }

}
