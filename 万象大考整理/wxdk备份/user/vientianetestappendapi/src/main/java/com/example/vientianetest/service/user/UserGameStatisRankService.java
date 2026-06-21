package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.common.utils.Rand;
import com.example.vientianetest.dao.user.*;
import com.example.vientianetest.dao.robot.RobotGameStatisRankDao;
import com.example.vientianetest.domain.Book;
import com.example.vientianetest.domain.Game;
import com.example.vientianetest.domain.User;
import com.example.vientianetest.redis.SessionRedis;
import com.example.vientianetest.redis.UserContinueWinRankRedis;
import com.example.vientianetest.redis.UserMaxSingleScoreRankRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UserGameStatisRankService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserAbilityDao userAbilityDao;
    @Autowired
    private UserParagraphDao userParagraphDao;
    @Autowired
    private UserCombatDao userCombatDao;
    @Autowired
    private UserBookDao userBookDao;
    @Autowired
    private UserDayTaskService userDayTaskService;
    @Autowired
    private UserGameStatisRankDao userGameStatisRankDao;
    @Autowired
    private RobotGameStatisRankDao robotGameStatisRankDao;
    @Autowired
    private UserContinueWinRankRedis userContinueWinRankRedis;
    @Autowired
    private UserMaxSingleScoreRankRedis userMaxSingleScoreRankRedis;

    public void insertSub(int userId){
        if(!userGameStatisRankDao.isExistsByUserId(userId)){
            userGameStatisRankDao.insertSub(userId,0,0,0,0,0,0,0,0,0,0,1);
        }
    }

    public void update(Game game){
        int winUserId = game.getWinUserId();
        int exitUserId = game.getExitUserId();
        int isFlat = game.getIsFlat();
        List<User> listUser = game.getUserList();
        for(int i =0 ;i<listUser.size();i++){
            if(exitUserId == listUser.get(i).getUser().getUserId()){
                //listUser.get(i).getAbility().setScore(0);
            }
            int userId = listUser.get(i).getUser().getUserId();
            //平局不计胜数
            int winNum = userId == winUserId && isFlat <= 0 ? 1 : 0;
            int exitNum = userId == exitUserId ? 1 : 0;
            int loseNum = winNum <= 0 && isFlat <= 0 ? 1 : 0;
            int flatNum = isFlat > 0 ? 1 : 0;
            int allRightNum = listUser.get(i).getAbility().getRightNum() >= 5 ? 1 : 0;

            if(!listUser.get(i).getBoot()){
                if (!userGameStatisRankDao.isExistsByUserId(userId)) {
                    userGameStatisRankDao.insert(userId, 1, winNum, loseNum, flatNum, exitNum,listUser.get(i).getAbility().getScore(),allRightNum,listUser.get(i).getAbility().getParagraph());
                } else {
                    int withUserId = i == 0 ? listUser.get(1).getUser().getUserId() : listUser.get(0).getUser().getUserId();//终结者
                    userGameStatisRankDao.appendNumByUserId(userId, 1, winNum, loseNum, flatNum, exitNum,listUser.get(i).getAbility().getScore(),allRightNum,listUser.get(i).getAbility().getParagraph(),withUserId);
                }
                if(winNum > 0) {
                    double currentContinueWinNum = listUser.get(i).getAbility().getContinueWinNum() + 1;
                    userContinueWinRankRedis.addUserRankRecord(listUser.get(i).getUser().getUserId(),currentContinueWinNum);
                }
                userMaxSingleScoreRankRedis.addUserRankRecord(listUser.get(i).getUser().getUserId(),listUser.get(i).getAbility().getScore());
                if(exitUserId != listUser.get(i).getUser().getUserId()) {
                    userDayTaskService.insert(userId, 3, 1, 1);
                }
            }else{
                if (!robotGameStatisRankDao.isExistsByUserId(userId)) {
                    robotGameStatisRankDao.insert(userId, 1, winNum, loseNum, flatNum, exitNum,listUser.get(i).getAbility().getScore(),allRightNum,listUser.get(i).getAbility().getParagraph());
                } else {
                    robotGameStatisRankDao.appendNumByUserId(userId, 1, winNum, loseNum, flatNum, exitNum,listUser.get(i).getAbility().getScore(),allRightNum,listUser.get(i).getAbility().getParagraph());
                }
            }
            //更改对象
            listUser.get(i).getAbility().setContinueWinNum(winNum > 0 ? listUser.get(i).getAbility().getContinueWinNum() + 1 : 0);
            listUser.get(i).getAbility().setContinueLoseNum(winNum > 0 ? 0 : listUser.get(i).getAbility().getContinueLoseNum() + 1);
        }
    }

    public User getWinNumByUserId(User user){
        userGameStatisRankDao.getWinNumByUserId(user);
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
        Map<String,Object> userParagraphMap = userParagraphDao.getInfoByUserId(user.getUser().getUserId());
        if(!user.getBoot()){
            user.getAbility().setRealParagraph(Integer.parseInt(userParagraphMap.get("realParagraph").toString()));
            user.getAbility().setRealStar(Integer.parseInt(userParagraphMap.get("realStar").toString()));
            user.getAbility().setParagraph(Integer.parseInt(userParagraphMap.get("paragraph").toString()));
            user.getAbility().setStar(Integer.parseInt(userParagraphMap.get("star").toString()));
            user.getAbility().setMmr(Double.valueOf(userParagraphMap.get("mmr").toString()));
            user.getAbility().setLevelTotalNum(Integer.parseInt(userParagraphMap.get("totalNum").toString()));
            user.getAbility().setLevelWinNum(Integer.parseInt(userParagraphMap.get("winNum").toString()));
            user.getAbility().setReceiveStar(Integer.parseInt(userParagraphMap.get("receiveStar").toString()));
            user.getAbility().setPlay(userParagraphMap.get("play").toString());
            user.getAbility().setParagraphName(userParagraphMap.get("name").toString());
            user.getAbility().setParagraphImage(userParagraphMap.get("image").toString());
            user.getAbility().setCreateTime(Long.parseLong(userParagraphMap.get("createTime").toString()));
            user.getAbility().setIsReceive(Integer.parseInt(userParagraphMap.get("isReceive").toString()));
            user.getAbility().setIsRefine(Integer.parseInt(userParagraphMap.get("isRefine").toString()));

        }else{
            user.getAbility().setRealParagraph(1);
            user.getAbility().setRealStar(0);
            user.getAbility().setParagraph(1);
            user.getAbility().setStar(0);
            user.getAbility().setMmr(0);
            user.getAbility().setParagraphName("");
            user.getAbility().setParagraphImage("");
            user.getAbility().setCreateTime(0);
        }
        Map<String,Object> userCombatMap = userCombatDao.getUserCombatByUserId(user.getUser().getUserId());
        if(!user.getBoot()) {
            user.getAbility().setCombat(Integer.parseInt(userCombatMap.get("combat").toString()));
        }
        List<Map<String,Object>> bookListMap = userBookDao.getUserBookListByUserId(user.getUser().getUserId());
        if(!user.getBoot()) {
            List<Book> bookList = new ArrayList<>();
            for (int i = 0; i < bookListMap.size(); i++) {
                Map<String, Object> map = bookListMap.get(i);
                bookList.add(new Book(Integer.parseInt(map.get("type").toString()),Integer.parseInt(map.get("level").toString()),Float.parseFloat(map.get("addition").toString())));
            }
            user.setBookList(bookList);
        }
        return user;
    }

    public void initRobotParagraphInfo(int paragraph,User robot){
        if(robot.getBoot()){
            robot.getAbility().setWinNum(Rand.nextInt(1000));
            robot.getAbility().setTotalNum(robot.getAbility().getWinNum());
            robot.getAbility().setWinRatio(Rand.nextInt(100));
            //设置机器人排位名称和排位图片
            int minParagraph = Math.abs(paragraph-11);
            int randomParagraph = (int)(minParagraph+Math.random()*(paragraph-minParagraph+1));
            randomParagraph = randomParagraph <= 0 ||  randomParagraph > 41 ? 1 : randomParagraph;
            Map<String,Object> randomMap = userParagraphDao.getParagraphInfoByLevel(randomParagraph);
            robot.getAbility().setParagraph(randomParagraph);
            robot.getAbility().setParagraphName(randomMap.get("name").toString());
            robot.getAbility().setParagraphImage(randomMap.get("image").toString());
        }
    }

    //获取排行榜-连胜榜单
    public String getRankList(HttpServletRequest request){
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        obj.Result = userContinueWinRankRedis.getRankList();
        obj.Result2 = userContinueWinRankRedis.getUserRankIndex(userId);

//        obj.Result = userParagraphDao.getRankList(30);
//        obj.Result2 = userParagraphDao.getRankListByUserId(userId);
        System.out.println("获取连胜排行榜数据"+JSONObject.toJSONString(obj));

        return JSONObject.toJSONString(obj);
    }

    //高分排行榜
    public String getMaxSingleScoreRankList(HttpServletRequest request){
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        obj.Result = userMaxSingleScoreRankRedis.getRankList();
        obj.Result2 = userMaxSingleScoreRankRedis.getUserRankIndex(userId);
        System.out.println("获取高分排行榜数据"+JSONObject.toJSONString(obj));

        return JSONObject.toJSONString(obj);
    }
}
