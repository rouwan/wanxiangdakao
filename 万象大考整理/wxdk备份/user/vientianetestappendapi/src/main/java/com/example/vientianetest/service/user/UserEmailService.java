package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.dao.user.UserEmailDao;
import com.example.vientianetest.redis.SessionRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class UserEmailService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserEmailDao userEmailDao;
    @Autowired
    private UserEquipmentService userEquipmentService;

    public void insert(int userId,int emailId,int isRead,int isReceive){
        if(!userEmailDao.isExistsByUserId(userId,emailId)){
            userEmailDao.insert(userId,emailId,isRead,isReceive);
        }else{
            userEmailDao.updateStatus(userId,emailId,isRead,isReceive);
        }

    }

    public int getNoReadNumByUserId(int userId){
        Map<String,Object> map = userEmailDao.getNoReadNumByUserId(userId);
        return Integer.parseInt(map.get("emailNoReadNum").toString());
    }

    public String getEmailList(HttpServletRequest request){
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        obj.Result = "";
        if(userEmailDao.isExists()){
            obj.Result = userEmailDao.getEmailListByUserId(userId);
        }
        return JSONObject.toJSONString(obj);
    }

    public String updateStatus(HttpServletRequest request,int emailId,int type){
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        //type = 1 查看 type = 2 领取
        if(type == 1){
            this.insert(userId,emailId,1,1);
        }else if(type == 2){
            if(!userEmailDao.isExistsReceiveById(emailId,userId)){
                this.insert(userId,emailId,1,1);
                String gift = userEmailDao.getEmailById(emailId).get("gift").toString();
                if(gift.length() > 0 ){
                    String[] giftArr = gift.split(";");
                    for(int i=0;i<giftArr.length;i++){
                        int giftType = Integer.parseInt(giftArr[i].split(",")[0]);
                        int giftNum = Integer.parseInt(giftArr[i].split(",")[1]);
                        userEquipmentService.insert(userId,giftType,giftNum);
                    }
                }
            }
        }
        return JSONObject.toJSONString(obj);
    }
}
