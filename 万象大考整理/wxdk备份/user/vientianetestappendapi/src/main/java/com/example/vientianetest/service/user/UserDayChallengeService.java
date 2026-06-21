package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicTagState;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.config.DataConfiguration;
import com.example.vientianetest.dao.subject.SubjectDao;
import com.example.vientianetest.dao.user.UserAbilityDao;
import com.example.vientianetest.dao.user.UserDayChallengeDao;
import com.example.vientianetest.domain.Subject;
import com.example.vientianetest.redis.SessionRedis;
import com.example.vientianetest.service.SubjectRatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDayChallengeService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private UserAbilityDao userAbilityDao;
    @Autowired
    private DataConfiguration dataConfiguration;
    @Autowired
    private UserDayChallengeDao userDayChallengeDao;
    @Autowired
    private SubjectRatioService subjectRatioService;


    public void insert(int userId){
        if(!userDayChallengeDao.isExistByUserId(userId)){
            userDayChallengeDao.insert(userId,0,0,",");
        }
    }

    public void updateDayChallenge(int userId){
        Map<String,Object> userDayMap = userDayChallengeDao.getUserDayChallenge(userId);
        int isDayEnd = Integer.parseInt(userDayMap.get("isDayEnd").toString());
        int isWeekEnd = Integer.parseInt(userDayMap.get("isWeekEnd").toString());
        if(isDayEnd == 1){
            userDayChallengeDao.updateTodayCardNumber(userId,0,",",0);
        }
        if(isWeekEnd == 1){
            userDayChallengeDao.updateWeekMaxCardNumber(userId,0);
        }
    }

    public Map<String,Object> getUserDayChallenge(int userId){
       return userDayChallengeDao.getUserDayChallenge(userId);
    }

    //返回API
    public String getRankList(HttpServletRequest request){
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        obj.Result = userDayChallengeDao.getRankList();

        Map<String,Object> map = userDayChallengeDao.getRankListByUserId(userId);
        int isUserDayFinish = Integer.parseInt(userDayChallengeDao.getUserDayChallenge(userId).get("isDayFinish").toString());
        boolean isDayFinish = dataConfiguration.isDayChallenge();
        isUserDayFinish = isUserDayFinish > 0 && !isDayFinish ? 0 : isUserDayFinish;
        map.put("isDayFinish",isUserDayFinish);
        map.put("goldCoin",Double.valueOf(userAbilityDao.getInfoByUserId(userId).get("money").toString()));
        obj.Result2 = map;
        return JSONObject.toJSONString(obj);
    }

    public String getSubject(HttpServletRequest request){
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        Map<String,Object> resultMap = new HashMap<>();
        Map<String,Object> userDayMap = userDayChallengeDao.getUserDayChallenge(userId);
        int dayCardNumber = Integer.parseInt(userDayMap.get("todayCardNumber").toString());
        int weekMaxCardNumber = Integer.parseInt(userDayMap.get("weekMaxCardNumber").toString());

        List<Subject> subjectList = subjectDao.getRandSubjectListByUserId(userId,1);
        resultMap.put("subject",subjectList);
        resultMap.put("dayCardNumber",dayCardNumber + 1);
        resultMap.put("weekMaxCardNumber",weekMaxCardNumber);
        obj.Result = resultMap;
        return JSONObject.toJSONString(obj);
    }

    public String submitSubject(HttpServletRequest request,int subjectId,int answer){
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        //判断答案
        Map<String,Object> subjectMap = subjectDao.getInfoById(subjectId);
        int optionSortId = Integer.parseInt(subjectMap.get("optionSortId").toString());
        if(optionSortId != answer){
            obj.Tag = BasicTagState.TagAnswerError;
            obj.Message = "答案错误";
            //是否限制
            if(dataConfiguration.isDayChallenge()){
                userDayChallengeDao.updateIsDayFinish(userId,1);
            }
            userDayChallengeDao.updateHistorySubjectId(userId,",");
            subjectRatioService.update(subjectId,1,0,1);
            return JSONObject.toJSONString(obj);
        }
        userDayChallengeDao.appendCardNumber(userId,1,String.valueOf(subjectId));
        //如果今天都答完的话，重复来
        Map<String,Object> userDayMap = userDayChallengeDao.getUserDayChallenge(userId);
        String historySubjectStr = userDayMap.get("historySubjectId").toString();
        if(historySubjectStr.startsWith(",")){
            historySubjectStr = historySubjectStr.substring(1,historySubjectStr.length());
        }
        if(historySubjectStr.endsWith(",")){
            historySubjectStr = historySubjectStr.substring(0,historySubjectStr.length()-1);
        }
        int totalCount = subjectDao.getTotalCount();
        if(historySubjectStr.split(",").length >= totalCount){
            userDayChallengeDao.updateHistorySubjectId(userId,",");
        }
        subjectRatioService.update(subjectId,1,1,0);
        return JSONObject.toJSONString(obj);
    }

    public String updateGoldCoin(HttpServletRequest request,double number){
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        double userGoldCoin = Double.valueOf(userAbilityDao.getInfoByUserId(userId).get("money").toString());
        if(number < 0 && Math.abs(number) > userGoldCoin ){
            return JSONObject.toJSONString(obj);
        }
        userAbilityDao.appendMoney(userId,number);
        return JSONObject.toJSONString(obj);
    }

}
