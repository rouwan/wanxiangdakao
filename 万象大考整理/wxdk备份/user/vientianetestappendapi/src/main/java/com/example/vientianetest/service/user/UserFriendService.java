package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.dao.user.UserFriendDao;
import com.example.vientianetest.redis.SessionRedis;
import com.example.vientianetest.redis.UserFriendRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserFriendService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserFriendDao userFriendDao;
    @Autowired
    private UserFriendRedis userFriendRedis;
    @Autowired
    private UserEquipmentService userEquipmentService;

    public void insert(int userId, int withUserId,int isNew) {
        if (userId > 0 && withUserId > 0 && userId != withUserId) {
            if (!userFriendDao.isExistsFriendByUserId(userId, withUserId)) {
                userFriendDao.insert(userId, withUserId, isNew);
                userFriendRedis.add(userId, withUserId);//插入redis
            }
        }
    }

    public String getRankList(HttpServletRequest request) {
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        System.out.println("获取好友排行榜数据"+userFriendRedis.getRankList(userId));

        return userFriendRedis.getRankList(userId);
    }

    public String getFriendList(HttpServletRequest request) {
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        BasicResultObject obj = new BasicResultObject();
        obj.Result = userFriendDao.getNewFriendList(userId, 100);
        return JSONObject.toJSONString(obj);
    }

    //邀请好友领取奖品
    public String receiveFriendGood(HttpServletRequest request,int userId,int goodType,int goodNum){
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int currentUserId = basicUserInfo.getUserId();
        //判断是否已领取
        if(userFriendDao.isStatusNewFriendByUserId(currentUserId,userId,0)){
            userEquipmentService.insert(currentUserId,goodType,goodNum);
            userFriendDao.updateStatus(currentUserId,userId,goodType,goodNum,1);
        }
        return JSONObject.toJSONString(new BasicResultObject());
    }
}
