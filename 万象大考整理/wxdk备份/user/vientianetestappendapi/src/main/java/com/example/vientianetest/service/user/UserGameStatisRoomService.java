package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.dao.user.UserGameStatisRoomDao;
import com.example.vientianetest.domain.RoomGame;
import com.example.vientianetest.domain.User;
import com.example.vientianetest.redis.SessionRedis;
import com.example.vientianetest.redis.UserFriendRedis;
import com.example.vientianetest.redis.UserRoomWinRankRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class UserGameStatisRoomService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserGameStatisRoomDao userGameStatisRoomDao;
    @Autowired
    private UserDayTaskService userDayTaskService;
    @Autowired
    private UserRoomWinRankRedis userRoomWinRankRedis;
    @Autowired
    private UserFriendRedis userFriendRedis;

    public void insertToday(int userId,int totalNum,int winNum,int loseNum,int flatNum,int exitNum, int todayNum,int todayRightSubNum) {
        if (!userGameStatisRoomDao.isExistsByUserId(userId)) {
            userGameStatisRoomDao.insert(userId, totalNum, winNum, loseNum, flatNum, exitNum, todayNum, todayRightSubNum);
        } else {
            userGameStatisRoomDao.appendTodayNumByUserId(userId, totalNum, winNum, todayNum, todayRightSubNum);
        }
    }

    public void update(RoomGame game) {
        List<User> listUser = game.getRoomSub().getUserList();
        for (int i = 0; i < listUser.size(); i++) {
            if(listUser.get(i).getBoot()){
                continue;
            }
            int userId = listUser.get(i).getUser().getUserId();
            int winNum = game.getFirstUserId() == userId ? 1 : 0;
            int rightSubNum = game.getRoomSub().getUserList().get(i).getAbility().getRightNum();
            this.insertToday(userId, 1, winNum, 0, 0, 0, 1,rightSubNum);
            if (game.getExitUserList() != null && game.getExitUserList().contains("," + userId + ",")) {
               //逃跑
            }else{
                //玩一局四人混战
                userDayTaskService.insert(userId, 2, 1, 1);
                //第一名
                if (game.getFirstUserId() == userId) {
                    userDayTaskService.insert(userId, 5, 1, 1);
                }
            }
            if(winNum > 0){//插入redis
                userRoomWinRankRedis.addUserRankRecord(userId,listUser.get(i).getAbility().getWinNum() + winNum);
            }
            listUser.get(i).getAbility().setWinNum(listUser.get(i).getAbility().getWinNum() + winNum);
        }
    }

    public User getWinNumByUserId(User user) {
        userGameStatisRoomDao.getWinNumByUserId(user);
        return user;
    }

    //世界榜
    public String getRankList(HttpServletRequest request){
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        obj.Result = userRoomWinRankRedis.getRankList();
        obj.Result2 = userRoomWinRankRedis.getUserRankIndex(userId);
        System.out.println("获取群对战世界排行榜数据"+JSONObject.toJSONString(obj));

        return JSONObject.toJSONString(obj);
    }

    //好友榜
    public String getFriendRankList(HttpServletRequest request){
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        String friendStr = userFriendRedis.getFriendByUserId(userId);
        obj.Result = userRoomWinRankRedis.getFriendRankList(friendStr,userId);
        obj.Result2 = userRoomWinRankRedis.getFriendRankByUserId(friendStr,userId);
        System.out.println("获取群对战好友排行榜数据"+JSONObject.toJSONString(obj));

        return JSONObject.toJSONString(obj);
    }
}
