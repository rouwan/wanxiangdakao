package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.dao.user.UserToXcxDao;
import com.example.vientianetest.dao.user.UserToXcxStatusDao;
import com.example.vientianetest.redis.SessionRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserToXcxService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserToXcxDao userToXcxDao;
    @Autowired
    private UserToXcxStatusDao userToXcxStatusDao;
    @Autowired
    private UserEquipmentService userEquipmentService;

    /*
     * type 是否试玩
     * status 是否领取
     * goodType 物品
     * goodNum 数量
     * */
    public String insert(HttpServletRequest request, String name, int type, int status, int goodType, int goodNum) {
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        BasicUserInfo obj = new BasicUserInfo();
        //统计记录
        if (!userToXcxDao.isExistsTodayByUserId(userId, name)) {
            userToXcxDao.insert(userId, type, name, goodType, goodNum, 0);
        }
        //试玩状态并且领取
        if (type == 1 && status == 1) {
            //判断今天是否已领取
            if (!userToXcxDao.isExistsTodayStatusByUserId(userId, name, 1)) {
                userToXcxDao.updateStatusByUserId(userId, name, goodType, goodNum, status);
                userEquipmentService.insert(userId,goodType,goodNum);
            }
        }
        return JSONObject.toJSONString(obj);
    }
}
