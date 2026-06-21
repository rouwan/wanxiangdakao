package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.dao.user.UserAbilityDao;
import com.example.vientianetest.dao.user.UserLuckNumberDao;
import com.example.vientianetest.domain.Game;
import com.example.vientianetest.domain.User;
import com.example.vientianetest.redis.SessionRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserLuckNumberService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserLuckNumberDao userLuckNumberDao;
    @Autowired
    private UserBookService userBookService;
    @Autowired
    private UserAbilityDao userAbilityDao;
    @Autowired
    private UserEquipmentService userEquipmentService;

    public void insert(int userId,int number,int status,int isWin) {
        if (!userLuckNumberDao.isExistsByNumber(userId, number)) {
            userLuckNumberDao.insert(userId, number, 0,isWin);
        } else {
            userLuckNumberDao.appendNumByUserId(userId, number, status,isWin);
        }
    }

    public void update(Game game) {
        int exitUserId = game.getExitUserId();
        List<User> listUser = game.getUserList();
        List<Map<String, Object>> mapList = userLuckNumberDao.getLuckNumberList();
        for (int i = 0; i < listUser.size(); i++) {
            int userId = listUser.get(i).getUser().getUserId();
            int score = listUser.get(i).getAbility().getScore();
            if (listUser.get(i).getBoot() || exitUserId == userId) {//逃跑的不加
                continue;
            }
            for (int j = 0; j < mapList.size(); j++) {
                Map<String, Object> map = mapList.get(j);
                int number = Integer.parseInt(map.get("number").toString());
                if (score == number) {
                    int isWin = game.getWinUserId() == userId ? 1 : 0;
                    this.insert(userId, number, 0, isWin);
                    break;
                }
            }
        }
    }

    public String numberReceive(HttpServletRequest request,int number) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        if (!userLuckNumberDao.isExistsByNumber(userId, number)) {
            obj.Tag = -1;
            return JSONObject.toJSONString(obj);
        }
        Map<String, Object> map = userLuckNumberDao.getUserLuckNumberByNumber(userId,number);
        int goodType = Integer.parseInt(map.get("goodType").toString());
        int num = Integer.parseInt(map.get("num").toString());
        int isWin = Integer.parseInt(map.get("isWin").toString());
        num = isWin == 1 ? num*2 : num;//赢的获取双倍奖励
        if (goodType >= 1 && goodType <= 6) {
            userEquipmentService.insert(userId, goodType, num);
        } else if (goodType >= 7 && goodType <= 12) {
            //1:德,2:商,3:文,4:理,5:艺,6:体,7:综
            Map<Integer, Integer> bookMap = new HashMap<Integer, Integer>() {{
                put(7, 2);
                put(8, 3);
                put(9, 4);
                put(10, 5);
                put(11, 6);
                put(12, 7);
            }};
            userBookService.insert(userId, bookMap.get(goodType), num);
        } else if (goodType == 13) {
            userAbilityDao.appendMoney(userId, num);
        }
        userLuckNumberDao.updateStatus(userId, number, 1);//修改领取状态
        return JSONObject.toJSONString(obj);
    }

    public String getLuckNumberList(int userId) {
        BasicResultObject obj = new BasicResultObject();
        obj.Result = userLuckNumberDao.getLuckNumberList(userId);
        return JSONObject.toJSONString(obj);
    }

    //更改
    public int isLuckNumberByUserId (int userId) {
        return userLuckNumberDao.isLuckNumberNoReceiveByUserId(userId) ? 1 : 0 ;
    }
}
