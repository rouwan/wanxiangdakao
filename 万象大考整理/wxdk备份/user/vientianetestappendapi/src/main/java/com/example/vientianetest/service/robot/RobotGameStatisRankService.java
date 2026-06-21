package com.example.vientianetest.service.robot;

import com.example.vientianetest.dao.robot.RobotGameStatisRankDao;
import com.example.vientianetest.domain.Game;
import com.example.vientianetest.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RobotGameStatisRankService {
    @Autowired
    private RobotGameStatisRankDao robotGameStatisRankDao;

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
            int userId = listUser.get(i).getUser().getUserId();
            //平局不计胜数
            int winNum = userId == winUserId && isFlat <= 0 ? 1 : 0;
            int exitNum = userId == exitUserId ? 1 : 0;
            int loseNum = winNum <= 0 && isFlat <= 0 ? 1 : 0;
            int flatNum = isFlat > 0 ? 1 : 0;
            int allRightNum = listUser.get(i).getAbility().getRightNum() >= 5 ? 1 : 0;
            if (!robotGameStatisRankDao.isExistsByUserId(userId)) {
                robotGameStatisRankDao.insert(userId, 1, winNum, loseNum, flatNum, exitNum,listUser.get(i).getAbility().getScore(),allRightNum,listUser.get(i).getAbility().getParagraph());
            } else {
                robotGameStatisRankDao.appendNumByUserId(userId, 1, winNum, loseNum, flatNum, exitNum,listUser.get(i).getAbility().getScore(),allRightNum,listUser.get(i).getAbility().getParagraph());
            }

            //更改对象
            listUser.get(i).getAbility().setContinueWinNum(winNum > 0 ? listUser.get(i).getAbility().getContinueWinNum() + 1 : 0);
            listUser.get(i).getAbility().setContinueLoseNum(winNum > 0 ? 0 : listUser.get(i).getAbility().getContinueLoseNum() + 1);
        }
    }
}
