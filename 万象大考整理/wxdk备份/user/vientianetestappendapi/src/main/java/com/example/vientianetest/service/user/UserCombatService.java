package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.dao.user.UserCombatDao;
import com.example.vientianetest.dao.user.UserParagraphDao;
import com.example.vientianetest.domain.CombatSub;
import com.example.vientianetest.domain.Game;
import com.example.vientianetest.domain.User;
import com.example.vientianetest.redis.SessionRedis;
import com.example.vientianetest.redis.UserCombatRankRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class UserCombatService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserCombatDao userCombatDao;
    @Autowired
    private UserParagraphDao userParagraphDao;
    @Autowired
    private UserCombatDetailService userCombatDetailService;
    @Autowired
    private UserCombatRankRedis userCombatRankRedis;
    @Autowired
    private UserCombatDayService userCombatDayService;

    public void insert(int userId, int combat){
        if(!userCombatDao.isExistsByUserId(userId)) {
            userCombatDao.insert(userId, combat);
        }
    }

    public void appendCeByUserId(int userId, int combat){
        if(!userCombatDao.isExistsByUserId(userId)) {
            userCombatDao.insert(userId, combat);
        }else{
            userCombatDao.appendCeByUserId(userId, combat);
        }
    }

    //游戏结束增加战斗力
    public void update(Game game) {
        int winUserId = game.getWinUserId();
        int exitUserId = game.getExitUserId();
        int isFlat = game.getIsFlat();
        List<User> listUser = game.getUserList();
        for(int i =0 ;i<listUser.size();i++) {
            if(listUser.get(i).getBoot() || exitUserId == listUser.get(i).getUser().getUserId() || listUser.get(i).getCombatSubList() == null){
                continue;
            }
            List<CombatSub> csl = listUser.get(i).getCombatSubList();
            int totalCs = 0;
            for(CombatSub cs : csl){
                userCombatDetailService.update(listUser.get(i).getUser().getUserId(),cs.getParentType(),cs.getCombat());//记录题目类型战斗力
                totalCs += cs.getCombat();
            }
            if(totalCs > 0) {
                listUser.get(i).getAbility().setCombat(listUser.get(i).getAbility().getCombat() + totalCs); //重置对象战斗力
                this.appendCeByUserId(listUser.get(i).getUser().getUserId(),totalCs);
                userCombatRankRedis.addUserRankRecord(listUser.get(i).getUser().getUserId(),listUser.get(i).getAbility().getCombat());
                userCombatDayService.update(listUser.get(i).getUser().getUserId(),totalCs);
            }
        }
    }

    //获取排位排行榜=战力榜
    public String getRankList(HttpServletRequest request){
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        obj.Result = userCombatRankRedis.getRankList();
        obj.Result2 = userCombatRankRedis.getUserRankIndex(userId);
        //之前下两行被注释 战力表wxdk_usercombat
//        obj.Result = userCombatDao.getRankList(30);
//        obj.Result2 = userCombatDao.getRankListByUserId(userId);


        System.out.println("获取战力排行榜数据"+JSONObject.toJSONString(obj));

        return JSONObject.toJSONString(obj);
    }
}
