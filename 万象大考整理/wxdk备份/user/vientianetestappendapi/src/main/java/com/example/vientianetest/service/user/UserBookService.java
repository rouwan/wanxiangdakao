package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.config.DataConfiguration;
import com.example.vientianetest.dao.user.UserBookDao;
import com.example.vientianetest.redis.SessionRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserBookService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserBookDao userBookDao;
    @Autowired
    private DataConfiguration dataConfiguration;
    @Autowired
    private UserTicketService userTicketService;

    public void insert(int userId,int type,int num) {
        if (!userBookDao.isExistsByUserId(userId, type)) {
            userBookDao.insert(userId, type, 0, num);
        } else {
            userBookDao.appendNumByUserId(userId, type, num);
        }
    }

    public List<Map<String,Object>> getUserBookListSub(int userId) {
        List<Map<String, Object>> resultMap = new ArrayList<>();
        if (userBookDao.isExistsByUserIdSub(userId)) {
            resultMap = userBookDao.getUserBookListByUserIdSub(userId);
        }
        return resultMap;
    }

    public String getUserBookList(HttpServletRequest request) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("bookList", userBookDao.getUserBookListByUserId(userId));
        resultMap.put("ticket", userTicketService.getTicketNumByUserId(userId, 1));
        resultMap.put("maxBookLevel",dataConfiguration.getMaxBookLevel());
        obj.Result = resultMap;
        return JSONObject.toJSONString(obj);
    }

    //知识书升级
    public String upLevelByType(HttpServletRequest request,int type) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        int maxBookLevel = dataConfiguration.getMaxBookLevel();
        if (userBookDao.isExistsByUserId(userId, type)) {
            Map<String, Object> currentBookMap = userBookDao.getUserBookByUserId(userId, type);
            int currentBookLevel = Integer.parseInt(currentBookMap.get("level").toString());
            int currentBookNum = Integer.parseInt(currentBookMap.get("num").toString());
            int nextBookNum = Integer.parseInt(currentBookMap.get("nextNum").toString());
            if (currentBookNum >= nextBookNum && currentBookLevel < maxBookLevel) {
                userBookDao.appendNumByUserId(userId, type, -nextBookNum, 1);
            }
        }
        obj.Result = userBookDao.getUserBookByUserId(userId, type);
        return JSONObject.toJSONString(obj);
    }

    //用户是否知识书升级
    public String getBookUpLevelCountByUserId(HttpServletRequest request) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        obj.Result = userBookDao.getBookUpLevelCountByUserId(userId);
        return JSONObject.toJSONString(obj);
    }

    public int getBookUpLevelCountByUserId(int userId) {
        return userBookDao.getBookUpLevelCountByUserId(userId);
    }
}
