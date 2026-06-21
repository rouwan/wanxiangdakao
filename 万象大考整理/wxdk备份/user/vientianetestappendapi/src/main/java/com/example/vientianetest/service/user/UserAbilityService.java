package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.config.DataConfiguration;
import com.example.vientianetest.dao.user.UserAbilityDao;
import com.example.vientianetest.dao.user.UserEquipmentDao;
import com.example.vientianetest.dao.user.UserGameStatisRoomDao;
import com.example.vientianetest.dao.user.UserParagraphDao;
import com.example.vientianetest.domain.Game;
import com.example.vientianetest.domain.Param;
import com.example.vientianetest.domain.RoomGame;
import com.example.vientianetest.domain.User;
import com.example.vientianetest.redis.SessionRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserAbilityService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserAbilityDao userAbilityDao;
    @Autowired
    private DataConfiguration dataConfiguration;
    @Autowired
    private UserEquipmentDao userEquipmentDao;
    @Autowired
    private UserGameStatisRoomDao userGameStatisRoomDao;
    @Autowired
    private UserParagraphDao userParagraphDao;

    public void insert(int userId) {
        if (!userAbilityDao.isExistsByUserId(userId)) {
            userAbilityDao.insert(userId, 0, 0, 1);
        }
    }

    public Map<String, Object> getInfoByUserId(int userId) {
        return userAbilityDao.getInfoByUserId(userId);
    }

    public Map<String, Object> updateIntervalTimerStrengthCountByUserId(int userId, int isTest) {
        return userAbilityDao.updateIntervalTimerStrengthCountByUserId(userId, isTest);
    }

    //排位结束
    public void update(Game game) {
        int winUserId = game.getWinUserId();
        int exitUserId = game.getExitUserId();
        int isFlat = game.getIsFlat();
        List<User> listUser = game.getUserList();
        Map<String, Object> gradeLevelMap = dataConfiguration.getGradeLevel();
        for (int i = 0; i < listUser.size(); i++) {
            if (!listUser.get(i).getBoot()) {
                int userId = listUser.get(i).getUser().getUserId();
                if (userId == exitUserId) { //逃跑不加经验 && 要扣3个体力
                    userAbilityDao.appendStrengthCountByUserIdSub(userId, 0, -Param.GAME_MATCH_STRENGTHCOUNT);
                    listUser.get(i).getAbility().setRewardStrength(0);
                    continue;
                }
                int addExp = dataConfiguration.getAddExp();
                int maxGrade = dataConfiguration.getMaxGrade();
                int maxMaxExpValue = dataConfiguration.getMaxExpValue();
                int currentGrade = listUser.get(i).getAbility().getGrade();
                int nextGrade = currentGrade + 1 > maxGrade ? maxGrade : currentGrade + 1;
                //double money = listUser.get(i).getAbility().getMoney();
                double expValue = listUser.get(i).getAbility().getExpValue();
                int maxExpValue = Integer.parseInt(gradeLevelMap.get(String.valueOf(nextGrade)).toString());
                //int addMoney = listUser.get(i).getAbility().getRightNum() * 2;//答对一题金币加2
                int newGrade = currentGrade;
                double newExpValue = expValue;
                //double newMoney = money + addMoney;
                //2018-12-27 排位不加金币
                if (currentGrade + 1 > maxGrade) {//最高等级
                    //userAbilityDao.appendValueByUserId(userId, 0, addMoney, 0);
                } else {
                    if ((expValue + addExp) >= maxExpValue) { //加等级
                        newGrade = currentGrade + 1;
                        newExpValue = 0;
                        userAbilityDao.update(userId, 0,  currentGrade + 1);
                    } else {
                        newGrade = currentGrade;
                        newExpValue = expValue + addExp > maxMaxExpValue ? maxMaxExpValue : expValue + addExp;
                        userAbilityDao.appendValueByUserId(userId, addExp, 0, 0);
                    }
                }
                //对的次数加体力
                int reduceStrengthCount = this.getReduceStrengthCountByRightNum(listUser.get(i).getAbility().getRightNum());
                if (userId != winUserId) {//输的用户一定扣体力
                    reduceStrengthCount = Param.GAME_MATCH_STRENGTHCOUNT;
                }
                if (exitUserId > 0) { //逃跑的话，对方不扣体力
                    reduceStrengthCount = 0;
                }
                if (reduceStrengthCount > 0) {
                    userAbilityDao.appendStrengthCountByUserIdSub(userId, 0, -reduceStrengthCount);
                }
                listUser.get(i).getAbility().setGrade(newGrade);
                //listUser.get(i).getAbility().setMoney(newMoney);
                listUser.get(i).getAbility().setExpValue(newExpValue);
                listUser.get(i).getAbility().setRewardStrength(Param.GAME_MATCH_STRENGTHCOUNT - reduceStrengthCount);
            }
        }
    }

    //群对战结束
    public void update(RoomGame game) {
        for (User user : game.getRoomSub().getUserList()) {
            if (game.getExitUserList() != null && game.getExitUserList().contains("," + user.getUser().getUserId() + ",")) {//逃跑不加金币
                continue;
            }
            int addMoney = user.getAbility().getRightNum() * 4;
            if (!user.getBoot() && addMoney > 0) {
                //仅每天15局做结算
                if (!userGameStatisRoomDao.isTodayOverCountByUserId(user.getUser().getUserId(), Param.GAME_ROOM_NUM_ADDMONEY)) {
                    userAbilityDao.appendValueByUserId(user.getUser().getUserId(), 0, addMoney, 0);
                    user.getAbility().setMoney(user.getAbility().getMoney() + addMoney);
                }
            }
        }
    }

    public String beforeMatch(HttpServletRequest request) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("isBegin", 1);
        int userId = sessionUser.getUserId();
        int isTest = sessionUser.getIsTest();
        //倒计时的体力在这边要加上去
        userAbilityDao.updateIntervalTimerStrengthCountByUserId(userId, isTest);//恢复体力
        //体力不足
        if (!userAbilityDao.isHashStrengthByCount(userId, Param.GAME_MATCH_STRENGTHCOUNT)) {
            resultMap.put("isBegin", 0);
            obj.Result = resultMap;
            return JSONObject.toJSONString(obj);
            //直接扣体力数
            //userAbilityDao.appendStrengthCountByUserId(userId,isTest,-GameParam.MatchStrengthCount);
        }
        //段位奖励未领取，不能开始游戏
        if (userParagraphDao.isExistsNoReceive(userId)) {
            resultMap.put("isBegin", 0);
            obj.Result = resultMap;
            return JSONObject.toJSONString(obj);
        }
        obj.Result = resultMap;
        return JSONObject.toJSONString(obj);
    }

    //答对次数获取体力数
    public int getReduceStrengthCountByRightNum(int rightNum) {
        int strengthCount = Param.GAME_MATCH_STRENGTHCOUNT;
        if (rightNum == 5) {
            strengthCount = Param.GAME_MATCH_STRENGTHCOUNT - 2;
        } else if (rightNum == 4) {
            strengthCount = Param.GAME_MATCH_STRENGTHCOUNT - 1;
        }
        return strengthCount;
    }
}
