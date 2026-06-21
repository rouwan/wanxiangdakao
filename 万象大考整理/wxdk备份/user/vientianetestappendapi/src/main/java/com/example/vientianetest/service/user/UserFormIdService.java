package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.redis.SessionRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.vientianetest.dao.user.UserFormIdDao;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserFormIdService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserFormIdDao userFormIdDao;

    public void insert(int userId, String formId, int status) {
        if (!userFormIdDao.isExistsByUserId(userId)) {
            userFormIdDao.insert(userId, formId, status);
        } else {
            userFormIdDao.appendFormId(userId, formId);
        }
    }

    public String updateFormId(HttpServletRequest request, String formId) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        this.insert(userId, formId, 0);
        return JSONObject.toJSONString(obj);
    }

}
