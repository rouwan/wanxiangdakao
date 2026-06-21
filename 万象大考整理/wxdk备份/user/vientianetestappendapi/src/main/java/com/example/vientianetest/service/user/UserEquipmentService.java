package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.common.utils.Rand;
import com.example.vientianetest.dao.user.UserAbilityDao;
import com.example.vientianetest.dao.user.UserBookDao;
import com.example.vientianetest.dao.user.UserEquipmentDao;
import com.example.vientianetest.redis.SessionRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class UserEquipmentService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserEquipmentDao userEquipmentDao;
    @Autowired
    private UserAbilityDao userAbilityDao;
    @Autowired
    private UserBookService userBookService;
    @Autowired
    private UserBookDao userBookDao;

    public void insert(int userId,int type,int num){
        if(!userEquipmentDao.isExistsByUserId(userId,type)){
            userEquipmentDao.insert(userId,type,num);
        }else{
            userEquipmentDao.appendNumByUserId(userId,type,num);
        }
    }

    public String getEquipmentList(HttpServletRequest request) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        obj.Result = userEquipmentDao.getEquipmentByUserId(userId);
        obj.Result2 = userBookService.getUserBookListSub(userId);
        return JSONObject.toJSONString(obj);
    }

    //使用体力瓶
    public String useEquipment(HttpServletRequest request,int count, int type) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        // type 1:小体力瓶,2:中体力瓶,3:大体力瓶,4:小宝箱,5:中宝箱,6:大宝箱
        if(!userEquipmentDao.isHashNumByUserId(sessionUser.getUserId(), type, count)) {
            obj.Tag = -1;
            return JSONObject.toJSONString(obj);
        }
        int addStrength = 1;
        if (type == 2) {
            addStrength = 15;
        } else if (type == 3) {
            addStrength = 30;
        }
        if (type <= 3) {
            userAbilityDao.appendStrengthCountByUserIdSub(sessionUser.getUserId(), 0, addStrength);
        }else if (type > 3) {
            //大宝箱 9-12 中宝箱 5-8 小宝箱 3-4
            //2:商,3:文,4:理,5:艺,6:体,7:综
            if (userBookDao.isAllFullGradeByUserId(userId)) {//是否所有书满级
                //小宝箱 1:小体力瓶 中宝箱 2:中体力瓶 大宝箱 3:大体力瓶
                int num = 1;
                int bottleType = 1;
                if (type == 4) {
                    bottleType = 1;
                } else if (type == 5) {
                    bottleType = 2;
                } else if (type == 6) {
                    bottleType = 3;
                }
                this.insert(userId, bottleType, num);
                Map<String, Integer> bottleMap = new HashMap<>();
                bottleMap.put(String.valueOf(bottleType), num);
                obj.Type = 1;//满级了
                obj.Result = bottleMap;
            } else {
                List<Integer> bookTypeList = new ArrayList<Integer>() {{
                    add(2);
                    add(3);
                    add(4);
                    add(5);
                    add(6);
                    add(7);
                }};
                Map<String, Integer> userBookMap = new HashMap<>();
                int randomNum = 4;
                if (type == 4) {
                    randomNum = (int) (3 + Math.random() * (4 - 3 + 1));
                } else if (type == 5) {
                    randomNum = (int) (5 + Math.random() * (8 - 5 + 1));
                } else if (type == 6) {
                    randomNum = (int) (9 + Math.random() * (12 - 9 + 1));
                }
                for (int i = 0; i < randomNum; i++) {
                    int bookType = bookTypeList.get(Rand.nextInt(bookTypeList.size()));
                    if (userBookMap.get(String.valueOf(bookType)) == null) {
                        userBookMap.put(String.valueOf(bookType), 1);
                    } else {
                        int bookNum = userBookMap.get(String.valueOf(bookType));
                        userBookMap.put(String.valueOf(bookType), bookNum + 1);
                    }
                }
                Iterator<Map.Entry<String, Integer>> entries = userBookMap.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, Integer> entry = entries.next();
                    int bookType = Integer.parseInt(entry.getKey());
                    int bookNum = entry.getValue();
                    if (bookNum > 0) {
                        userBookService.insert(userId, bookType, bookNum);
                    }
                }
                obj.Result = userBookMap;
            }
        }
        userEquipmentDao.appendNumByUserId(sessionUser.getUserId(), type, -count);//扣减背包
        return JSONObject.toJSONString(obj);
    }

}
