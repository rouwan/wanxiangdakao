package com.example.vientianetest.service;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.dao.FeedBackSubjectDao;
import com.example.vientianetest.redis.SessionRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class FeedBackSubjectService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private FeedBackSubjectDao feedBackSubjectDao;

    public String insert(HttpServletRequest request,int subjectId,String types , String remark){
        HttpSession session = request.getSession();
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        feedBackSubjectDao.insert(subjectId,basicUserInfo.getUserId(),types,remark);
        return JSONObject.toJSONString(new BasicResultObject());
    }
}
