package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.config.DataConfiguration;
import com.example.vientianetest.dao.user.UserAbilityDao;
import com.example.vientianetest.dao.user.UserPrizeDao;
import com.example.vientianetest.domain.Param;
import com.example.vientianetest.redis.SessionRedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Component
public class UserPrizeService {
    private static final Logger logger = LoggerFactory.getLogger(UserPrizeService.class);
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserPrizeDao userPrizeDao;
    @Autowired
    private UserAbilityDao userAbilityDao;
    @Autowired
    private UserBookService userBookService;
    @Autowired
    private DataConfiguration dataConfiguration;
    @Autowired
    private UserEquipmentService userEquipmentService;
    @Autowired
    private UserDayTaskService userDayTaskService;

    public String userRandomPrize(HttpServletRequest request, int prizeType) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        /*判断金币*/
        if(prizeType == 2) {
            if (!userAbilityDao.isHashMoneyByUserId(userId, Param.GLOBAL_BIGPRIZE_MONEY)) {
                logger.info("抽奖十次用户抽奖金币不足 userId:{}", userId);
                obj.Tag = -1;
                return JSONObject.toJSONString(obj);
            }
        }else {
            if (!userAbilityDao.isHashMoneyByUserId(userId, Param.GLOBAL_PRIZE_MONEY)) {
                logger.info("抽奖一次用户抽奖金币不足 userId:{}", userId);
                obj.Tag = -1;
                return JSONObject.toJSONString(obj);
            }
        }

        if (prizeType == 2) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                //1:小体力瓶,2:中体力瓶,3:大体力瓶,4:小宝箱,5:中宝箱,6:大宝箱,7:商,8:文,9:理,10:艺,11:体,12:综,
                Map<String, Object> prizeMap = this.getRandomIndex();
                int type = Integer.parseInt(prizeMap.get("type").toString());
                int index = Integer.parseInt(prizeMap.get("index").toString());
                int num = Integer.parseInt(prizeMap.get("num").toString());
                this.insertPrize(userId, prizeType, type, num);
                arrayList.add(index);
            }
            obj.Result = arrayList;
        } else {
            //1:小体力瓶,2:中体力瓶,3:大体力瓶,4:小宝箱,5:中宝箱,6:大宝箱,7:商,8:文,9:理,10:艺,11:体,12:综,
            Map<String, Object> prizeMap = this.getRandomIndex();
            int type = Integer.parseInt(prizeMap.get("type").toString());
            int index = Integer.parseInt(prizeMap.get("index").toString());
            int num = Integer.parseInt(prizeMap.get("num").toString());
            this.insertPrize(userId, prizeType, type, num);
            obj.Result = index;
        }
        return JSONObject.toJSONString(obj);
    }

    public void insertPrize(int userId,int prizeType, int type,int num) {
        type = type == 0 ? 1 : type;
        if (type > 0) {
            if (type >= 1 && type <= 6) {
                userEquipmentService.insert(userId, type, num);
            } else if (type >= 7 && type <= 12) {
                //1:德,2:商,3:文,4:理,5:艺,6:体,7:综
                Map<Integer, Integer> bookMap = new HashMap<Integer, Integer>() {{
                    put(7, 2);
                    put(8, 3);
                    put(9, 4);
                    put(10, 5);
                    put(11, 6);
                    put(12, 7);
                }};
                userBookService.insert(userId, bookMap.get(type), num);
            }
            userAbilityDao.appendMoney(userId, -Param.GLOBAL_PRIZE_MONEY);//扣减金币
            userPrizeDao.insert(userId, type);//抽奖记录
            //每日任务
            userDayTaskService.insert(userId, 6, 1, 1);
        }
    }

    public Map<String, Object> getRandomIndex() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("type", 1);
        resultMap.put("index", 13);
        resultMap.put("num", 1);
        int randomValue = (int) (1 + Math.random() * (100 - 1 + 1));
        Iterator<Map.Entry<String, Object>> entries = dataConfiguration.getPrizeRatio().entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Object> entry = entries.next();
            int sortId = Integer.parseInt(entry.getKey());
            Map<String, Object> valueMap = (Map<String, Object>) entry.getValue();
            int minValue = Integer.parseInt(valueMap.get("minValue").toString());
            int maxValue = Integer.parseInt(valueMap.get("maxValue").toString());
            int type = Integer.parseInt(valueMap.get("type").toString());
            int num = Integer.parseInt(valueMap.get("num").toString());
            String remark = valueMap.get("remark").toString();
            if (randomValue >= minValue && randomValue <= maxValue) {
                resultMap.put("type", type);
                resultMap.put("index", sortId - 1);
                resultMap.put("num", num);
                resultMap.put("remark", remark);
                break;
            }
        }
        return resultMap;
    }

    public List<Map<String, Object>> getPrizeList() {
        List<Map<String, Object>> resultMap = new ArrayList<>();
        Iterator<Map.Entry<String, Object>> entries = dataConfiguration.getPrizeRatio().entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Object> entry = entries.next();
            Map<String, Object> valueMap = (Map<String, Object>) entry.getValue();
            Map<String, Object> newMap = new HashMap<>();
            newMap.put("index", Integer.parseInt(entry.getKey()) - 1);
            newMap.put("type", Integer.parseInt(valueMap.get("type").toString()));
            newMap.put("num", Integer.parseInt(valueMap.get("num").toString()));
            newMap.put("remark", valueMap.get("remark").toString());
            newMap.put("image", valueMap.get("image").toString());
            newMap.put("description", valueMap.get("description").toString());
            resultMap.add(newMap);
        }
        return resultMap;
    }

    //模拟测试用
    public void testUserRandomPrize() {
        //1:小体力瓶,2:中体力瓶,3:大体力瓶,4:小宝箱,5:中宝箱,6:大宝箱,7:商,8:文,9:理,10:艺,11:体,12:综,
        int userId = 17;//徐慧梅
        System.out.println("testUserRandomPrize begin...");
        int smallBox = 1;
        for (int i=0;i<100;i++) {
            Map<String,Object> prizeMap = this.getRandomIndex();
            int type = Integer.parseInt(prizeMap.get("type").toString());
            int index = Integer.parseInt(prizeMap.get("index").toString());
            int num = Integer.parseInt(prizeMap.get("num").toString());
            if (type > 0) {
                if (type >= 1 && type <= 6) {
                    userEquipmentService.insert(userId, type, num);
                } else if (type >= 7 && type <= 12) {
                    //1:德,2:商,3:文,4:理,5:艺,6:体,7:综
                    Map<Integer, Integer> bookMap = new HashMap<Integer, Integer>(){{put(7,2);put(8,3);put(9,4);put(10,5);put(11,6);put(12,7);}};
                    userBookService.insert(userId, bookMap.get(type), num);
                }
                userPrizeDao.insert(userId,type);//抽奖记录
            }
        }
        System.out.println("testUserRandomPrize end...");
    }
}
