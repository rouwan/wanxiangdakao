package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.config.DataConfiguration;
import com.example.vientianetest.dao.user.UserAbilityDao;
import com.example.vientianetest.dao.user.UserDaySignDao;
import com.example.vientianetest.redis.SessionRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaySignService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserDaySignDao daySignDao;
    @Autowired
    private DataConfiguration dataConfiguration;
    @Autowired
    private UserBookService userBookService;
    @Autowired
    private UserAbilityDao userAbilityDao;
    @Autowired
    private UserEquipmentService userEquipmentService;

    public void insert(int userId,int day ) {
        if(!daySignDao.isExistsByUserId(userId)) {
            daySignDao.insert(userId,1);
        }else {
            daySignDao.update(userId,day);
        }
    }

    public int getSignDayByUserId(int userId) {
        //如果等于0的话，从第一天开始签到
        int day = 0;
        if (daySignDao.isExistsByUserId(userId)) {
            day = daySignDao.getSignDayByUserId(userId);
        }
        return day;
    }

    public String sign(HttpServletRequest request,int type ,int day){
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        List<Map<String,Object>> mapList = dataConfiguration.getDaySignList();
        int goodType = 1;
        int num = 1;
        for(int i =0;i<mapList.size();i++) {
            Map<String,Object> map = mapList.get(i);
            int d = Integer.parseInt(map.get("day").toString());
            if(d == day) {
                goodType = Integer.parseInt(map.get("type").toString());
                num =Integer.parseInt(map.get("num").toString());
                break;
            }
        }
        /*双倍领取*/
        if(type == 2) {
            num = num * 2;
        }
        if(goodType >= 1 && goodType <= 6) {
            userEquipmentService.insert(userId, goodType, num);
        }else if (goodType >= 7 && goodType <= 12) {
            //1:德,2:商,3:文,4:理,5:艺,6:体,7:综
            Map<Integer, Integer> bookMap = new HashMap<Integer, Integer>(){{put(7,2);put(8,3);put(9,4);put(10,5);put(11,6);put(12,7);}};
            userBookService.insert(userId, bookMap.get(goodType), num);
        }else if (goodType == 13) {
            userAbilityDao.appendMoney(userId,num);
        }
        this.insert(userId,day);//签到记录
        return JSONObject.toJSONString(obj);
    }

    public List<Map<String,Object>> getDaySignList(int userId) {
        List<Map<String,Object>> mapList = dataConfiguration.getDaySignList();
        int userDay = this.getSignDayByUserId(userId);
        for(int i =0;i<mapList.size();i++) {
            Map<String,Object> map = mapList.get(i);
            int day = Integer.parseInt(map.get("day").toString());
            if(userDay >= day) {
                map.put("status",1);
            }else{
                map.put("status",0);
            }
        }
        return mapList;
    }

    public int isSignedByUserId(int userId) {
        return daySignDao.isSignedByUserId(userId) ? 0 : 1;
    }
}
