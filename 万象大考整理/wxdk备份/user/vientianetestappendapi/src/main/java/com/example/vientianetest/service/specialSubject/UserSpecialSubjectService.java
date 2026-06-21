package com.example.vientianetest.service.specialSubject;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.dao.user.UserAbilityDao;
import com.example.vientianetest.dao.specialSubject.UserSpecialSubjectDao;
import com.example.vientianetest.dao.user.UserEmailDao;
import com.example.vientianetest.redis.SessionRedis;
import com.example.vientianetest.service.user.UserEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserSpecialSubjectService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserSpecialSubjectDao userSpecialSubjectDao;
    @Autowired
    private UserAbilityDao userAbilityDao;
    @Autowired
    private UserEmailService userEmailService;

    public void insert(int userId,int cardNumber) {
        if(!userSpecialSubjectDao.isExistsByUserId(userId,1)) {
            userSpecialSubjectDao.insert(userId,cardNumber,1);
        }else{
            userSpecialSubjectDao.appendCardNumber(userId,cardNumber,1);
        }
    }

    public String getSubject(HttpServletRequest request) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        int cardNumber = userSpecialSubjectDao.getCardNumberByUserId(userId,1);
        if(cardNumber >= 101) {//通关
            obj.Tag = 2;
            return JSONObject.toJSONString(obj);
        }
        obj.Result = userSpecialSubjectDao.getSubjectByUserId(userId, cardNumber + 1,1);
        return JSONObject.toJSONString(obj);
    }

    public String submitSubject(HttpServletRequest request,int subjectId,String answer) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        if(!userSpecialSubjectDao.isRightAnswerByIdAndAnswer(subjectId,answer)) {
            obj.Tag = -1;
            return JSONObject.toJSONString(obj);
        }
        this.insert(userId,1);//追加1
        int cardNumber = userSpecialSubjectDao.getCardNumberByUserId(userId,1);
        if(cardNumber >= 101) {//通关
            obj.Tag = 2;
            //通过的话邮件告诉用户
            userEmailService.insert(userId,6,0,0);
            return JSONObject.toJSONString(obj);
        }
        return JSONObject.toJSONString(obj);
    }

    public String promptSubject(HttpServletRequest request,int subjectId) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        if(!userAbilityDao.isHashMoneyByUserId(userId,10)) {
            obj.Tag = -1;
            return JSONObject.toJSONString(obj);
        }
        userAbilityDao.appendMoney(userId,-10);
        return JSONObject.toJSONString(obj);
    }
}
