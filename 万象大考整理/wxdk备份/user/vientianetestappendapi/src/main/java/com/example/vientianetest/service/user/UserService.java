package com.example.vientianetest.service.user;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicTagState;
import com.example.common.domain.BasicUserInfo;
import com.example.common.utils.DesUtils;
import com.example.vientianetest.dao.*;
import com.example.vientianetest.dao.user.UserAbilityDao;
import com.example.vientianetest.dao.user.UserDao;
import com.example.vientianetest.dao.user.UserGameStatisRankDao;
import com.example.vientianetest.redis.SessionRedis;
import com.example.vientianetest.service.MessageBoardService;
import com.example.vientianetest.service.RankSeasonTimeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserService {

    Logger logger = LogManager.getLogger(UserService.class);
    DesUtils desUtils = new DesUtils("1234567890");
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserDao userDao;
    @Autowired
    private GameRankDao gameRankDao;
    @Autowired
    private UserAbilityDao userAbilityDao;
    @Autowired
    private WxMaService wxService;
    @Autowired
    private UserBookService userBookService;
    @Autowired
    private UserEmailService userEmailService;
    @Autowired
    private UserFriendService userFriendService;
    @Autowired
    private UserAbilityService userAbilityService;
    @Autowired
    private UserCombatService userCombatService;
    @Autowired
    private UserPrizeService userPrizeService;
    @Autowired
    private UserDaySignService userDaySignService;
    @Autowired
    private UserCombatDetailService userCombatDetailService;
    @Autowired
    private UserLastLoginService userLastLoginService;
    @Autowired
    private RankSeasonTimeService rankSeasonTimeService;
    @Autowired
    private UserParagraphService userParagraphService;
    @Autowired
    private UserDayChallengeService userDayChallengeService;
    @Autowired
    private UserGameStatisRankDao userGameStatisRankDao;
    @Autowired
    private UserGameStatisRankService userGameStatisRankService;
    @Autowired
    private UserParagraphHistoryService userParagraphHistoryService;
    @Autowired
    private UserHitCatService userHitCatService;
    @Autowired
    private UserLuckNumberService userLuckNumberService;
    @Autowired
    private MessageBoardService messageBoardService;
    @Autowired
    private UserDayTaskService userDayTaskService;

    @Value("${small.vientianetest.version}")
    private String version;

    @Value("${env}")
    private String env;

    //region login
    public String login(HttpServletRequest request, String code){
        BasicResultObject obj =new BasicResultObject();
        if (StringUtils.isBlank(code)) {
            obj.Tag = BasicTagState.TagSignOut;
            obj.Message = "empty jscode";

        }else{
            try {
                String openId;
                // Dev mode: skip WeChat OAuth, use code directly as openId
                if (code != null && code.startsWith("dev_")) {
                    openId = code;
                    logger.info("[DEV] Login bypassed WeChat OAuth, openId=" + openId);
                } else {
                    WxMaJscode2SessionResult session = this.wxService.getUserService().getSessionInfo(code);
                    openId = session.getOpenid();
                }

                Map<String, Object> userMap = new HashMap<String, Object>() {{put("id", 0);put("openId",openId);put("isTest",0);put("headImgUrl", "");put("nickname","");put("sexDesc","");put("city","");put("province","");put("country","");}};
                if(userDao.isExistsByOpenId(openId)){
                    userMap = userDao.getUserInfoByOpenId(openId);
                }else{
                    int id = userDao.insertOpenId(openId);
                    userMap.put("id",id);
                }
                BasicUserInfo userInfo = new BasicUserInfo(userMap);
                //初始化能力值
                userCombatService.insert(userInfo.getUserId(),0);
                userAbilityService.insert(userInfo.getUserId());
                userParagraphService.insert(userInfo.getUserId());
                //userDayChallengeService.insert(userInfo.getUserId());
                userGameStatisRankService.insertSub(userInfo.getUserId());
                sessionRedis.add(userInfo,request.getSession().getId());
                obj.SessionId = request.getSession().getId();/*由于小程序每次的request不同，用sessionId识别*/
                obj.Result = JSONObject.parseObject("{Version:"+version+",userId:"+userInfo.getUserId()+",code:'"+desUtils.encrypt(request.getSession().getId())+"'}");
                //System.out.println("sessionId = "+request.getSession().getId());
            } catch (Exception e) {
                System.out.println("LOGIN_ERROR: " + e.getMessage());
                e.printStackTrace();
                logger.info("login : "+e.getMessage());
                obj.Tag = BasicTagState.TagSignOut;
                obj.Message = "login Exception";
            }
        }
        return JSONObject.toJSONString(obj);
    }
    //endregion

    //region authorize
    public String authorize(HttpServletRequest request,
                            String nickName,
                            String avatarUrl,
                            String gender,
                            String city,
                            String province,
                            String country
    ) {
        BasicResultObject obj = new BasicResultObject();
        try {
            BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
            BasicUserInfo userInfo = new BasicUserInfo(sessionUser.getOpenId(), sessionUser.getUserId(), nickName, gender, avatarUrl, city, province, country);
            //修改基础信息
            if (userDao.isUpdateByUserId(userInfo)) {
                userDao.update(userInfo);
                sessionRedis.update(userInfo, request.getCookies());
            }
        } catch (Exception e) {
            logger.info("authorize : " + e.getMessage());
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "authorize Exception";
        }
        return JSONObject.toJSONString(obj);

    }
    //endregion

    //region init
    public String init(HttpServletRequest request,int userId , int isNew) {
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        BasicResultObject obj = new BasicResultObject();
        int currentUserId = basicUserInfo.getUserId();
        Map<String, Object> resultMap = this.getHeadDataByUserId(basicUserInfo, true);
        Map<String, Object> userGameStaticRankMap = userGameStatisRankDao.getGameStatisRankByUserId(currentUserId);
        resultMap.put("maxParagraph", userGameStaticRankMap.get("maxParagraph"));
        //resultMap.put("combat",Integer.parseInt(userCombatService.getUserCombatByUserId(userId).get("combat").toString()));
        resultMap.put("seasonTime", rankSeasonTimeService.getSeasonTime().get("seasonTime"));
        resultMap.put("seasonEnd", userParagraphHistoryService.getNoReadRecordByUserId(currentUserId));
        resultMap.put("prizeList", userPrizeService.getPrizeList());
        userFriendService.insert(userId, currentUserId, isNew);//好友列表
        obj.Result = resultMap;
        return JSONObject.toJSONString(obj);
    }
    //endregion

    //region wsLogin
    public String wsLogin(HttpServletRequest request,int type){
        BasicResultObject obj = new BasicResultObject();
        try{
            BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
            obj.Result = JSONObject.parseObject("{code:\""+desUtils.encrypt(String.valueOf(basicUserInfo.getUserId()))+"\"}");
        }
        catch (Exception ex){
            logger.debug("wsLogin:{}",ex.getMessage());
        }
        return JSONObject.toJSONString(obj);
    }
    //endregion

    //region exit
    public String exit(HttpServletRequest request,int strengthIntervalTimer){
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        BasicResultObject obj = new BasicResultObject();
        userAbilityDao.updateStrengthIntervalTimer(userId,strengthIntervalTimer);
        return JSONObject.toJSONString(obj);
    }
    //endregion

    //region getUser
    public String getUser(HttpServletRequest request,int userId,int type){
        //1.竞技数据,2.战斗力,3.赛季历史,4.对战历史
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        if(userId != basicUserInfo.getUserId()) {//不是本人的话，请求数据库
            Map<String,Object> userMap = userDao.getUserInfoById(userId);
            basicUserInfo.setUserId(userId);
            basicUserInfo.setOpenId(userMap.get("openId").toString());
            basicUserInfo.setNickName(userMap.get("nickname").toString());
            basicUserInfo.setGender(userMap.get("sexDesc").toString());
            basicUserInfo.setAvatarUrl(userMap.get("headImgUrl").toString());
            basicUserInfo.setCity(userMap.get("city").toString());
            basicUserInfo.setProvince(userMap.get("province").toString());
            basicUserInfo.setCountry(userMap.get("country").toString());
        }
        Map<String,Object> resultMap = new HashMap<>();
        if(type == 1){
            resultMap = userGameStatisRankDao.getGameStatisRankByUserId(userId);
            resultMap.put("avatarUrl",basicUserInfo.getAvatarUrl());
            resultMap.put("nickname",basicUserInfo.getNickName());
            resultMap.put("gender",basicUserInfo.getGender());
            resultMap.put("city",basicUserInfo.getCity());
            resultMap.put("province",basicUserInfo.getProvince());
            resultMap.put("country",basicUserInfo.getCountry());
            Map<String,Object> userAbilityMap = userAbilityService.getInfoByUserId(userId);
            resultMap.put("grade",Integer.parseInt(userAbilityMap.get("grade").toString()));
            resultMap.put("expValue",new Double(userAbilityMap.get("expValue").toString()).intValue());
            resultMap.put("maxExpValue",Integer.parseInt(userAbilityMap.get("maxExpValue").toString()));
            Map<String,Object> paragraphMap = userParagraphService.getInfoByUserId(userId);
            resultMap.put("paragraph",paragraphMap.get("paragraph").toString());
            resultMap.put("paragraphName",paragraphMap.get("name").toString());
            resultMap.put("paragraphImage",paragraphMap.get("image").toString());
        }else if(type == 2) {
            List<Map<String,Object>> mapList = userCombatDetailService.getUserCombatDetailByUserId(userId);
            resultMap.put("combatList",mapList == null ? "" : mapList);
        }else if(type == 3) {
            List<Map<String,Object>> mapList = userParagraphHistoryService.getUserParagraphHistoryByUserId(userId);
            resultMap.put("paragraphHistoryList",mapList == null ? "" : mapList);
        }else if(type == 4){
            resultMap.put("rankHistoryList",gameRankDao.getGameRankByUserId(userId,10));
        }
        obj.Result = resultMap;
        return JSONObject.toJSONString(obj);
    }
    //endregion

    //region getStrength
    public String getStrength(HttpServletRequest request){
        BasicResultObject obj = new BasicResultObject();
        Map<String,Object> resultMap = new HashMap<>();
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        Map<String,Object> strengthMap = userAbilityService.updateIntervalTimerStrengthCountByUserId(basicUserInfo.getUserId(),basicUserInfo.getIsTest());//恢复体力
        Map<String,Object> userAbilityMap = userAbilityService.getInfoByUserId(basicUserInfo.getUserId());
        resultMap.put("strengthCount",Integer.parseInt(userAbilityMap.get("strengthCount").toString()));
        resultMap.put("strengthIntervalTimer",Integer.parseInt(strengthMap.get("strengthIntervalTimer").toString()));
        obj.Result = resultMap;
        return JSONObject.toJSONString(obj);
    }
    //endregion

    //获取应用头部数据,是否放在首页
    public Map<String,Object> getHeadDataByUserId(BasicUserInfo userInfo,boolean isHomePage){
        System.out.println("访问getHeadDataByUserId");
        Map<String,Object> resultMap = new HashMap<>();
        int userId = userInfo.getUserId();
        resultMap.put("userId",userId);
        resultMap.put("headImgUrl",userInfo.getAvatarUrl());
        resultMap.put("nickname",userInfo.getNickName());
        resultMap.put("province",userInfo.getProvince());
        resultMap.put("city",userInfo.getCity());
        if(isHomePage) {
            Map<String, Object> userParagraphMap = userParagraphService.getInfoByUserId(userId);
            resultMap.put("paragraph", Integer.parseInt(userParagraphMap.get("paragraph").toString()));
            resultMap.put("star", Integer.parseInt(userParagraphMap.get("star").toString()));
            resultMap.put("image", userParagraphMap.get("image").toString());
            resultMap.put("paragraphName", userParagraphMap.get("name").toString());
        }
        return resultMap;
    }

    /*获取实时数据*/
    public String getRealTimeData(HttpServletRequest request) {
        try {
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        BasicResultObject obj = new BasicResultObject();
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> statusMap = new HashMap<>();
        Map<String, Object> strengthMap = userAbilityService.updateIntervalTimerStrengthCountByUserId(userId, 0);//恢复体力
        Map<String, Object> userAbilityMap = userAbilityService.getInfoByUserId(userId);
        userAbilityMap.put("strengthIntervalTimer", Integer.parseInt(strengthMap.get("strengthIntervalTimer").toString()));
        statusMap.put("emailNoReadNum", userEmailService.getNoReadNumByUserId(userId));//邮箱
        statusMap.put("isDaySign", userDaySignService.isSignedByUserId(userId));//签到
        statusMap.put("isHitCat", userHitCatService.isHitCatByUserId(userId));//撸猫
        statusMap.put("isLuckNumber", userLuckNumberService.isLuckNumberByUserId(userId));//幸运数字
        statusMap.put("isBookUpLevel", userBookService.getBookUpLevelCountByUserId(userId));//知识书升级
        statusMap.put("isFinishDayTask", userDayTaskService.isFinishDayTaskByUserId(userId));//每日任务完成未领取
        resultMap.put("abilityInfo", userAbilityMap);
        resultMap.put("statusInfo", statusMap);
        resultMap.put("messageBoardList", messageBoardService.getMessageBoardList(0, 30));
        obj.Result = resultMap;
        return JSONObject.toJSONString(obj);
        } catch (Exception e) {
            System.out.println("REALTIME_ERROR: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

}

