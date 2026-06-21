package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.dao.user.UserCombatDayDao;
import com.example.vientianetest.redis.SessionRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserCombatDayService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserCombatDayDao userCombatDayDao;

    public void update(int userId, int combat){
        if(!userCombatDayDao.isExistsByUserId(userId)) {
            userCombatDayDao.insert(userId, combat);
        }else{
            userCombatDayDao.appendCeByUserId(userId, combat);
        }
    }

    public String getRankList(HttpServletRequest request) {
        System.out.println("请求getrankList");
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        obj.Result = userCombatDayDao.getUserCombatDayRankList(50);
        obj.Result2 = userCombatDayDao.getUserCombatDayByUserId(userId);
        System.out.println("获取每日战力排行榜数据"+JSONObject.toJSONString(obj));

        return JSONObject.toJSONString(obj);
    }
}
