package com.example.vientianetest.service;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.dao.MessageBoardDao;
import com.example.vientianetest.redis.SessionRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Component
public class MessageBoardService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private MessageBoardDao messageBoardDao;

    public String addMessageBoard(HttpServletRequest request,String message){
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        obj.Result = messageBoardDao.insert(basicUserInfo.getUserId(),message,0);
        return JSONObject.toJSONString(obj);
    }

    public String getMessageBoardList(HttpServletRequest request,int pageIndex,int pageSize) {
        BasicResultObject obj = new BasicResultObject();
        int count = messageBoardDao.getPageTotal();
        if (count > 0) {
            obj.Result = messageBoardDao.getMessageBoardPageList(pageIndex, pageSize);
            obj.TotalCount = count;
        }
        return JSONObject.toJSONString(obj);
    }

    public List<Map<String,Object>> getMessageBoardList(int pageIndex, int pageSize) {
        return messageBoardDao.getMessageBoardPageList(pageIndex, pageSize);
    }
}
