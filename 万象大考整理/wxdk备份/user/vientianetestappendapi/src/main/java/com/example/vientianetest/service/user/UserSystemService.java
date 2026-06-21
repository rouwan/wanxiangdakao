package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.dao.user.UserSystemDao;
import com.example.vientianetest.redis.SessionRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserSystemService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserSystemDao userSystemDao;

    public String insert(HttpServletRequest request, String brand, String model, String pixelRatio, String screenWidth, String screenHeight, String windowWidth, String windowHeight,
                         String statusBarHeight, String language, String version, String system, String platform, String fontSizeSetting, String SDKVersion) {
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        if(!userSystemDao.isExistsByUserId(userId)){
            userSystemDao.insert(userId,brand,model,pixelRatio,screenWidth,screenHeight,windowWidth,windowHeight,
                                 statusBarHeight,language,version,system,platform,fontSizeSetting,SDKVersion);
        }
        return JSONObject.toJSONString(new BasicResultObject());
    }

}
