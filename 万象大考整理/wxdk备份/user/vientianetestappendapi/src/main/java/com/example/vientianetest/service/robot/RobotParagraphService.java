package com.example.vientianetest.service.robot;

import com.example.vientianetest.config.DataConfiguration;
import com.example.vientianetest.dao.robot.RobotParagraphDao;
import com.example.vientianetest.domain.Game;
import com.example.vientianetest.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RobotParagraphService {
    @Autowired
    private DataConfiguration dataConfiguration;
    @Autowired
    private RobotParagraphDao robotParagraphDao;
    private void update(int userId,int paragraph,int addStarNum,double mmr){
        if(!robotParagraphDao.isExistsByUserId(userId)){
            robotParagraphDao.insert(userId,paragraph,addStarNum,mmr);
        }else{
            robotParagraphDao.update(userId,paragraph,addStarNum,mmr);
        }
    }

    public void update(Game game){
        int winUserId = game.getWinUserId();
        int exitUserId = game.getExitUserId();
        int isFlat = game.getIsFlat();
        List<User> listUser = game.getUserList();
        if(isFlat > 0){ return; } //平局不升星不降星

        for(int i =0 ;i<listUser.size();i++){
            //统计不是机器人
            //Map<String,Object> userInfoMap = userParagraphDao.getInfoByUserId(userId);
            int userId = listUser.get(i).getUser().getUserId();
            int continueWinNum = listUser.get(i).getAbility().getContinueWinNum();
            int paragraph = listUser.get(i).getAbility().getParagraph();
            int star = listUser.get(i).getAbility().getStar();
            double mmr = listUser.get(i).getAbility().getMmr();
            int maxParagraph = dataConfiguration.getMaxParagraph(); //最大段位数
            int maxStar = this.getMaxStarByParagrap(paragraph);     //最大星星数
            int newStar = star;
            int newParagraph = paragraph;
            double newMmr = mmr;

            if(userId == winUserId){
                int starNum = 1;
                //后面6个段位没有连胜机制
                if((continueWinNum >= 3) && (paragraph < (maxParagraph - 6))){
                    starNum = 2;
                }
                //胜利获得一个星,超过星数将升段
                if(((star + starNum) > maxStar) && ((paragraph+1) < maxParagraph)){ //升段
                    //0 1
                    //0 1 2
                    //0 1 2 3
                    int addStarNum = (star + starNum - 1) % maxStar;
                    newParagraph = paragraph + 1 > maxParagraph ? maxParagraph : paragraph + 1;
                    newStar = addStarNum;
                    newMmr = mmr + 1;
                    this.update(userId,newParagraph,addStarNum,newMmr);
                }
                else if(((star + starNum) > maxStar) && (paragraph+1) >= maxParagraph){ //最强王者
                    newParagraph = maxParagraph;
                    newStar = paragraph + 1 == maxParagraph ? 0 : star + 1; //最强王者星星数量不限制
                    newMmr = mmr + 1;
                    this.update(userId,newParagraph,newStar,newMmr);
                }
                else { //升星
                    //int newStarNum = star + starNum == maxStar ? maxStar - 1 :  starNum;
                    newParagraph = paragraph;
                    newStar = star + starNum > maxStar ? maxStar : star + starNum ;
                    newMmr = mmr + 1;
                    this.update(userId, newParagraph, newStar, newMmr);
                }
            }else{
                //前面5个段位不掉段和不降星
                //最强王者依然要掉段
                if(paragraph > 5){
                    if((paragraph+1) >= maxParagraph){ //最强王者
                        newParagraph = paragraph;
                        newStar = star - 1;
                        newMmr = mmr - 1;
                        if(star <= 0 ){
                            newParagraph = paragraph - 1;
                            newStar = this.getMaxStarByParagrap(paragraph - 1);
                        }
                        this.update(userId,newParagraph,newStar,newMmr);
                    }
                    else if((paragraph+1) < maxParagraph && star > 0){
                        newParagraph = paragraph;
                        newStar = star - 1;
                        newMmr = mmr - 1;
                        this.update(userId,newParagraph,newStar,newMmr);
                    }
                    else{
                        newParagraph = paragraph - 1;
                        newStar = this.getMaxStarByParagrap(paragraph - 1);
                        newMmr = mmr - 1;
                        this.update(userId,newParagraph,newStar,newMmr);
                    }
                }
            }
            Map<String,Object> paragraphMap = robotParagraphDao.getParagraphInfoByLevel(newParagraph);
            listUser.get(i).getAbility().setParagraph(newParagraph);
            listUser.get(i).getAbility().setStar(newStar);
            listUser.get(i).getAbility().setMmr(newMmr);
            listUser.get(i).getAbility().setParagraphName(paragraphMap.get("name").toString());
            listUser.get(i).getAbility().setParagraphImage(paragraphMap.get("image").toString());
        }
    }

    public int getMaxStarByParagrap(int paragraph){
        int maxStar = 0;
        if(paragraph <= 5){
            maxStar = 1;
        }else if(paragraph >= 6 && paragraph <= 10){
            maxStar = 2;
        }else if(paragraph >= 11 && paragraph <= 20){
            maxStar = 3;
        }else if(paragraph >= 21 && paragraph <= 30){
            maxStar = 4;
        }else if(paragraph >= 31 && paragraph <= 40){
            maxStar = 5;
        }
        return maxStar;
    }

}
