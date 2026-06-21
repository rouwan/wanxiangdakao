package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.common.utils.DateUtils;
import com.example.vientianetest.dao.user.UserAbilityDao;
import com.example.vientianetest.dao.user.UserHitCatDao;
import com.example.vientianetest.redis.SessionRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UserHitCatService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserAbilityDao userAbilityDao;
    @Autowired
    private UserHitCatDao userHitCatDao;

    public void insert(int userId, int money) {
        if (!userHitCatDao.isExistsByUserId(userId)) {
            userHitCatDao.insert(userId);
        } else {
            userHitCatDao.updateTime(userId);
        }
        userHitCatDao.insertRecord(userId, money);
    }

    public String hitCat(HttpServletRequest request, int money) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        this.insert(userId, money);
        userAbilityDao.appendMoney(userId, money);
        return JSONObject.toJSONString(obj);
    }

    //1:可以撸猫,0:不可以
    public int isHitCatByUserId(int userId) {
        int result = 0;
        try {
            //11:00 - 14:00
            //17:00 - 22:00
            //判断当前时间是否在时间段
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
            Date nowTime = df.parse(df.format(new Date()));
            boolean flag1 = DateUtils.belongCalendar(nowTime, df.parse("11:00"), df.parse("14:00"));
            boolean flag2 = DateUtils.belongCalendar(nowTime, df.parse("17:00"), df.parse("22:00"));
            if (flag1 || flag2) {
                if (flag1) {
                    result = userHitCatDao.isMorningHashHitCatByUserId(userId) ? 0 : 1;
                } else if (flag2) {
                    result = userHitCatDao.isAfterHashHitCatByUserId(userId) ? 0 : 1;
                }
            }
        } catch (Exception ex) {
            result = 0;
        }
        return result;
    }
}
