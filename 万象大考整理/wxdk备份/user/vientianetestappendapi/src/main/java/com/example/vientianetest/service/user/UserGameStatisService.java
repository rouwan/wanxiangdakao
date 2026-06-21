package com.example.vientianetest.service.user;

import com.example.vientianetest.dao.user.UserAbilityDao;
import com.example.vientianetest.dao.user.UserGameStatisDao;
import com.example.vientianetest.dao.user.UserParagraphDao;
import com.example.vientianetest.domain.Game;
import com.example.vientianetest.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Map;

@Component
public class UserGameStatisService {
    @Autowired
    private UserGameStatisDao userGameStatisDao;
    @Autowired
    private UserAbilityDao userAbilityDao;
    @Autowired
    private UserParagraphDao userParagraphDao;

    public void update(Game game){
        int winUserId = game.getWinUserId();
        int exitUserId = game.getExitUserId();
        int isFlat = game.getIsFlat();
        List<User> listUser = game.getUserList();
        for(int i =0 ;i<listUser.size();i++){
            //统计不是机器人
            if(exitUserId == listUser.get(i).getUser().getUserId()){
                listUser.get(i).getAbility().setScore(0);
            }
            if(!listUser.get(i).getBoot()){
                int userId = listUser.get(i).getUser().getUserId();
                //平局不计胜数
                int winNum = userId == winUserId && isFlat <= 0 ? 1 : 0;
                int exitNum = userId == exitUserId ? 1 : 0;
                int loseNum = winNum <= 0 && isFlat <= 0 ? 1 : 0;
                int flatNum = isFlat > 0 ? 1 : 0;
                if (!userGameStatisDao.isExistsByUserId(userId)) {
                    userGameStatisDao.insert(userId, 1, winNum, loseNum, flatNum, exitNum);
                } else {
                    userGameStatisDao.appendNumByUserId(userId, 1, winNum, loseNum, flatNum, exitNum);
                }

                //更改对象
                listUser.get(i).getAbility().setContinueWinNum(winNum > 0 ? listUser.get(i).getAbility().getContinueWinNum() + 1 : 0);
                listUser.get(i).getAbility().setContinueLoseNum(winNum > 0 ? 0 : listUser.get(i).getAbility().getContinueLoseNum() + 1);
            }
        }
    }

    public User getWinNumByUserId(User user){
        userGameStatisDao.getWinNumByUserId(user);
        Map<String,Object> userAbilityMap = userAbilityDao.getInfoByUserId(user.getUser().getUserId());
        if(!user.getBoot()){
            user.getAbility().setExpValue(Double.valueOf(userAbilityMap.get("expValue").toString()));
            user.getAbility().setGrade(Integer.parseInt(userAbilityMap.get("grade").toString()));
            user.getAbility().setMoney(Double.valueOf(userAbilityMap.get("money").toString()));
        }else{
            user.getAbility().setExpValue(0);
            user.getAbility().setGrade(0);
            user.getAbility().setMoney(0);
        }
        return user;
    }
}
