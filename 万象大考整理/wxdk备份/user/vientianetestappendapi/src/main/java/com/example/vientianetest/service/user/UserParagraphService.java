package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.config.DataConfiguration;
import com.example.vientianetest.dao.MessageBoardDao;
import com.example.vientianetest.dao.user.UserAbilityDao;
import com.example.vientianetest.dao.user.UserParagraphDao;
import com.example.vientianetest.dao.robot.RobotParagraphDao;
import com.example.vientianetest.domain.Game;
import com.example.vientianetest.domain.User;
import com.example.vientianetest.redis.SessionRedis;
import com.example.vientianetest.redis.UserParagraphRankRedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserParagraphService {
    private static final Logger logger = LoggerFactory.getLogger(UserParagraphService.class);
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserParagraphDao userParagraphDao;
    @Autowired
    private RobotParagraphDao robotParagraphDao;
    @Autowired
    private DataConfiguration dataConfiguration;
    @Autowired
    private MessageBoardDao messageBoardDao;
    @Autowired
    private UserParagraphRankRedis userParagraphRankRedis;
    @Autowired
    private UserEquipmentService userEquipmentService;
    @Autowired
    private UserAbilityDao userAbilityDao;

    public void insert(int userId){
        if(!userParagraphDao.isExistsByUserId(userId)){
            userParagraphDao.insert(userId,1,0,100);
        }
    }

    public Map<String,Object> getInfoByUserId(int userId){
        return userParagraphDao.getInfoByUserId(userId);
    }

    public void update(Game game) {
        int winUserId = game.getWinUserId();
        int exitUserId = game.getExitUserId();
        int isFlat = game.getIsFlat();
        List<User> listUser = game.getUserList();
        if (isFlat > 0) {
            return;
        } //平局不升星不降星
        Map<String, Object> paragraphMapList = dataConfiguration.getParagraph();//段位基础表
        for (int i = 0; i < listUser.size(); i++) {
            if (listUser.get(i).getBoot() || listUser.get(i).getAbility().getIsReceive() == 1) {//未领取状态，不做任何操作
                if(!listUser.get(i).getBoot() && listUser.get(i).getAbility().getIsReceive() == 1) {
                    logger.info("用户当前是未领取状态，不做任何处理 userId:{}",listUser.get(i).getUser().getUserId());
                }
                continue;
            }
            int maxParagraph = dataConfiguration.getMaxParagraph();
            int userId = listUser.get(i).getUser().getUserId();
            int paragraph = listUser.get(i).getAbility().getRealParagraph();
            Map<String, Object> paragraphMap = (Map<String, Object>) paragraphMapList.get(String.valueOf(paragraph));
            int maxStar = Integer.parseInt(paragraphMap.get("star").toString());
            int isRefine = listUser.get(i).getAbility().getIsRefine(); //Integer.parseInt(paragraphMap.get("isRefine").toString());
            int maxTotalNum = Integer.parseInt(paragraphMap.get("totalNum").toString());
            int maxWinNum = Integer.parseInt(paragraphMap.get("winNum").toString());
            int currentIsRefine = listUser.get(i).getAbility().getIsRefine();
            int totalNum = listUser.get(i).getAbility().getLevelTotalNum();
            int winNum = listUser.get(i).getAbility().getLevelWinNum();
            String play = listUser.get(i).getAbility().getPlay();
            int star = listUser.get(i).getAbility().getRealStar();
            double mmr = listUser.get(i).getAbility().getMmr();
            int receiveStar = listUser.get(i).getAbility().getReceiveStar();
            int newTotalNum = totalNum;
            int newWinNum = winNum;
            int newStar = star;
            int newReceiveStar = receiveStar;
            String newPlay = play;
            double newMmr = mmr;
            boolean isReceive = false;
            boolean isReceiveNumber = false;
            //王者的话，每50颗星要试炼一次 50 5/3 , 100 10/6
            if(paragraph >= maxParagraph) {
                if (star > 0 && star % 50 == 0) {
                    if ((star / 50) % 2 == 0) {
                        maxTotalNum = 10;
                        maxWinNum = 6;
                        isRefine = 1;
                    } else {
                        maxTotalNum = 5;
                        maxWinNum = 3;
                        isRefine = 1;
                    }
                }
            }
            if (isRefine == 1) {//一个段位当两个段位来用
                isRefine = currentIsRefine;
            }
            //新的排位机制,结算页不做段位升级，用户自己点解锁
            if (userId == winUserId) {
                if (isRefine == 1) {//试炼阶段
                    if ((winNum + 1) <= maxWinNum) {
                        //用户总场数+1，胜利场次+1
                        newTotalNum = totalNum + 1;
                        newWinNum = winNum + 1;
                        newPlay = newPlay.length() <= 0 ? "1" : newPlay + ",1";
                    }
                    if((winNum + 1) >= maxWinNum){
                        isReceive = true;
                    }
                } else {
                    if ((star + 1) <= maxStar || paragraph == maxParagraph) {
                        //星星数+1
                        newStar = star + 1;
                    }
                    if ((star + 1) >= maxStar && paragraph != maxParagraph) {//(paragraph == maxParagraph && (star + 1) > 0 && (star + 1) % 50 == 0)
                        isReceive = true;
                    }
                }
                if(paragraph >= maxParagraph && (star + 1) >= 10 &&(star + 1)%10 == 0 && (star + 1)%50 != 0) {//最强王者试炼有4个领金币阶段
                    if(star > receiveStar){
                        isReceive = true;
                        //isReceiveNumber = true;
                    }
                }else if(paragraph >= maxParagraph && (star + 1) > 0 && (star + 1) % 50 == 0) {//被50整除的直接进入试炼状态，没有领取奖励
                    newReceiveStar = star + 1;
                    isRefine = 1;
                }
            } else {
                if (isRefine == 1) {//试炼阶段
                    if ((maxTotalNum - (totalNum + 1) + winNum) >= maxWinNum) {
                        //用户总场数+1，胜利场次不动
                        newTotalNum = totalNum + 1;
                        newPlay = newPlay.length() <= 0 ? "0" : newPlay + ",0";
                    } else {
                        //用户总场数清空，胜利场次清空
                        newTotalNum = 0;
                        newWinNum = 0;
                        newPlay = "";
                    }
                } else {
                    if (star > 0) {
                        //不加段位，星星数-1
                        if (paragraph >= maxParagraph && star > 0 && star % 50 == 0) {//上王者50相当于一个小段位，不能再掉星
                        } else {
                            newStar = star - 1;
                        }
                    }
                }
            }
            //如果不是试炼状态就要更改MMR，否则是试炼状态不要更改MMR值还是上一个段位的值（注：MMR值影响排名）
            newMmr = isRefine == 1 && paragraph <= maxParagraph && star <=0 ? newMmr :  paragraph * 100 + newStar;
            userParagraphDao.update(userId, paragraph, newStar, newMmr, newTotalNum, newWinNum, newPlay ,isReceive ? 1 : 0,isRefine,newReceiveStar);
            userParagraphRankRedis.addUserRankRecord(userId, newMmr);
            listUser.get(i).getAbility().setLevelTotalNum(newTotalNum);
            listUser.get(i).getAbility().setLevelWinNum(newWinNum);
            listUser.get(i).getAbility().setPlay(newPlay);
            listUser.get(i).getAbility().setStar(newStar);
            listUser.get(i).getAbility().setMmr(newMmr);
            listUser.get(i).getAbility().setIsReceive(isReceive ? 1 : 0);
            listUser.get(i).getAbility().setIsRefine(isRefine);
            listUser.get(i).getAbility().setReceiveStar(newReceiveStar);
        }
    }

    //获取排位排行榜
    public String getRankList(HttpServletRequest request) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        obj.Result = userParagraphRankRedis.getRankList();
        obj.Result2 = userParagraphRankRedis.getUserRankIndex(userId);
        //原本下两行被注释--导致段位榜单不显示
        obj.Result = userParagraphDao.getRankList(30);
        obj.Result2 = userParagraphDao.getRankListByUserId(userId);
        System.out.println("获取段位排行榜数据"+JSONObject.toJSONString(obj));
        return JSONObject.toJSONString(obj);
    }

    //七天没有玩排位就掉一颗心
    public String updateOverTimePlayStar(int userId) {
        BasicResultObject obj = new BasicResultObject();
        if (userParagraphDao.isOverTimePlayByDay(userId, 7)) {
            userParagraphDao.updateOverTimePlayStar(userId);
        }
        return JSONObject.toJSONString(obj);
    }

    public String getSeasonParagraphInfo(HttpServletRequest request) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        List<Map<String, Object>> mapList = userParagraphDao.getSeasonParagraphByUserId(userId);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("paragraphList",userParagraphDao.getBigParagraphList());
        resultMap.put("userParagraphList",mapList);
        obj.Result = resultMap;
        return JSONObject.toJSONString(obj);
    }

    public String receiveGood(HttpServletRequest request) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        Map<String, Object> paragraphMap = userParagraphDao.getInfoByUserId(userId);
        int paragraph = Integer.parseInt(paragraphMap.get("realParagraph").toString());
        int star = Integer.parseInt(paragraphMap.get("realStar").toString());
        double mmr = Double.valueOf(paragraphMap.get("mmr").toString());
        int totalNum = Integer.parseInt(paragraphMap.get("totalNum").toString());
        int winNum = Integer.parseInt(paragraphMap.get("winNum").toString());
        int isReceive = Integer.parseInt(paragraphMap.get("isReceive").toString());
        int receiveStar = Integer.parseInt(paragraphMap.get("receiveStar").toString());
        int currentIsRefine = Integer.parseInt(paragraphMap.get("isRefine").toString());
        String play = paragraphMap.get("play").toString();
        Map<String, Object> configParagraphMap = (Map<String, Object>) dataConfiguration.getParagraph().get(String.valueOf(paragraph));
        int maxStar = Integer.parseInt(configParagraphMap.get("star").toString());
        int maxTotalNum = Integer.parseInt(configParagraphMap.get("totalNum").toString());
        int maxWinNum = Integer.parseInt(configParagraphMap.get("winNum").toString());
        int isRefine = Integer.parseInt(configParagraphMap.get("isRefine").toString());
        int goodType = Integer.parseInt(configParagraphMap.get("goodType").toString());
        int goodNum = Integer.parseInt(configParagraphMap.get("goodNum").toString());
        int maxParagraph = dataConfiguration.getMaxParagraph();
        int newParagraph = paragraph;
        int newStar = star;
        double newMmr = mmr;
        int newTotalNum = 0;
        int newWinNum = 0;
        String newPlay = "";
        boolean isUpdate = false;
        boolean isBox = true;//是否领箱子
        //王者的话，每50颗星要试炼一次 50 5/3 , 100 10/6
        if (paragraph >= maxParagraph) {
            if (star > 0 && star % 50 == 0) {
                if ((star / 50) % 2 == 0) {
                    maxTotalNum = 10;
                    maxWinNum = 6;
                    isRefine = 1;
                    goodType = 6;
                } else {
                    maxTotalNum = 5;
                    maxWinNum = 3;
                    isRefine = 1;
                    goodType = 5;
                }
            }
        }
        if (isRefine == 1) {//一个段位当两个段位来用
            isRefine = currentIsRefine;
        }
        //logger.info("req1");
        if (isReceive == 1) {//可以领取状态
            //logger.info("req2");
            if (isRefine == 1) {//试炼阶段
                if (winNum >= maxWinNum) {
                    //试炼成功，直接下一个阶段(加段位，星星数+0)，用户总场数清0，胜利场次清0
                    isUpdate = true;
                }
            } else {
                if (star >= maxStar) {
                    isUpdate = true;
                }
            }
            if (paragraph >= maxParagraph && star >= 10 && star % 10 == 0 && star % 50 != 0) {//最强王者试炼有4个领金币阶段
                if (star < receiveStar) {//避免重复领取
                    return JSONObject.toJSONString(obj);
                } else {
                    isBox = false;
                    receiveStar = star;
                }
            }
            if (isUpdate) {
                newStar = 0;
                if (paragraph < maxParagraph) {
                    if(isRefine == 0) {//试炼段位是一个段位当两个段位来用
                        newParagraph = paragraph + 1;
                        isRefine = Integer.parseInt(((Map<String, Object>) dataConfiguration.getParagraph().get(String.valueOf(paragraph + 1))).get("isRefine").toString());
                    }else if(isRefine == 1 && winNum >= maxWinNum) {//试炼完后改变试炼状态
                        isRefine = 0;
                    }
                } else {
                    if(star>0) {
                        newStar = star;
                    }
                    /*if (isBox && star>0) {//试炼状态
                        newStar = star;
                    }else if(!isBox && star>0) {//领取金币状态
                        newStar = star;
                    }*/
                    //升最强王者要在留言提示三条
                    if(isRefine == 1 && star == 0) {
                        for (int i = 0; i < 3; i++) {
                            messageBoardDao.insert(userId, "升最强王者啦！", 1);
                        }
                    }
                }
                isRefine = paragraph >= maxParagraph ? 0 : isRefine;
                newMmr = isRefine == 1 ? newMmr : newParagraph * 100 + newStar;
                userParagraphDao.update(userId, newParagraph, newStar, newMmr, newTotalNum, newWinNum, newPlay, 0, isRefine, receiveStar);
                userParagraphRankRedis.addUserRankRecord(userId, newMmr);
            } else if (paragraph >= maxParagraph && isRefine == 1) {//王者50转为试炼状态
                userParagraphDao.update(userId, newParagraph, newStar, newMmr, newTotalNum, newWinNum, newPlay, 0, 1, receiveStar);
            }
            if (isBox) {
                userEquipmentService.insert(userId, goodType, goodNum);
            } else {
                userAbilityDao.appendMoney(userId, 10);
            }

        }
        return JSONObject.toJSONString(obj);
    }

}
