package com.example.vientianetest.service;

import com.example.vientianetest.config.DataConfiguration;
import com.example.vientianetest.dao.RankSeasonTimeDao;
import com.example.vientianetest.dao.user.UserParagraphDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RankSeasonTimeService {
    @Autowired
    private RankSeasonTimeDao rankSeasonTimeDao;
    @Autowired
    private UserParagraphDao userParagraphDao;
    @Autowired
    private DataConfiguration dataConfiguration;

    public Map<String,Object> getSeasonTime(){
        return rankSeasonTimeDao.getSeasonTime();
    }

    public Map<String,Object> getSeasonTime(int userId){
        return rankSeasonTimeDao.getSeasonTime(userId);
    }

    public void updateAblity(int userId){
        Map<String,Object> seasonTimeMap =  rankSeasonTimeDao.getSeasonTime(userId);
        int isTimeEnd = Integer.parseInt(seasonTimeMap.get("isTimeEnd").toString());
        if(isTimeEnd == 1){//改赛季的时间
            rankSeasonTimeDao.appendMonth();
            userParagraphDao.seasonTimeClear();
        }
        /*int isUserEnd = Integer.parseInt(seasonTimeMap.get("isUserEnd").toString());
        if(isUserEnd == 1) {//改用户的时间
            Map<String, Object> ParagraphMap = userParagraphDao.getInfoByUserId(userId);
            int paragraph = Integer.parseInt(ParagraphMap.get("paragraph").toString());
            int MaxParagraph = dataConfiguration.getMaxParagraph();
            int star = Integer.parseInt(ParagraphMap.get("star").toString());
            //double mmr = Double.valueOf(ParagraphMap.get("mmr").toString());
            if (paragraph == MaxParagraph) { // 最强王者 降到【黑金I】
                userParagraphDao.update2(userId, 30, 3, -10);
            } else if (paragraph >= 16 ) { //【黄金Ⅲ】降到【黑铁Ⅲ】
                int newParagraph = (paragraph/5 - 3 )*5 + (paragraph%5);
                userParagraphDao.update2(userId, newParagraph, 3, -10);
            } else { //【白银、青铜、黑铁】降到【黑铁V】
                userParagraphDao.update2(userId, 1, paragraph > 1 ? 3 : star, 0);
            }
        }*/
    }
}
