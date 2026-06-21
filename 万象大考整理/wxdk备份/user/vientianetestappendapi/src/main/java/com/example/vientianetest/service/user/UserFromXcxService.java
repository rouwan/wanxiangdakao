package com.example.vientianetest.service.user;

import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.dao.user.UserFromXcxDao;
import com.example.vientianetest.redis.SessionRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserFromXcxService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserFromXcxDao userFromXcxDao;

    public void insert(HttpServletRequest request,String from){
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        if(!userFromXcxDao.isExistsByUserId(userId,from)){
            userFromXcxDao.insert(userId,from,1);
        }else{
            userFromXcxDao.appendNumByUserId(userId,from,1);
        }
    }
}
