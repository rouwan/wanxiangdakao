package com.example.vientianetest.service;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.dao.user.UserGameStatisRoomDao;
import com.example.vientianetest.redis.SessionRedis;
import com.example.vientianetest.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class RoomService {
    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserService userService;
    @Autowired
    private UserGameStatisRoomDao userGameStatisRoomDao;

    public String getHeadData(HttpServletRequest request){
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        BasicResultObject obj = new BasicResultObject();
        obj.Result = userService.getHeadDataByUserId(basicUserInfo,false);
        return JSONObject.toJSONString(obj);
    }

    public String rankHallData(HttpServletRequest request){
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        BasicResultObject obj = new BasicResultObject();
        Map<String ,Object> resultMap = new HashMap<>();
        resultMap.put("todayRightSubNum",userGameStatisRoomDao.getWinNumByUserId(userId).get("todayRightSubNum"));
        obj.Result = resultMap;
        return JSONObject.toJSONString(obj);
    }
}
