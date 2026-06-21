package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicTagState;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.dao.subject.SubjectDao;
import com.example.vientianetest.dao.user.UserWeekChallengeDao;
import com.example.vientianetest.dao.WeekSubjectDao;
import com.example.vientianetest.dao.WeekTitleDao;
import com.example.vientianetest.redis.SessionRedis;
import com.example.vientianetest.service.SubjectRatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Component
public class UserWeekChallengeService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private WeekTitleDao weekTitleDao;
    @Autowired
    private WeekSubjectDao weekSubjectDao;
    @Autowired
    private SubjectRatioService subjectRatioService;
    @Autowired
    private UserWeekChallengeDao userWeekChallengeDao;

    //一周清空一次
    public void updateWeekTitle(int userId){
        if(userWeekChallengeDao.isExistsOverTimeByUserId(userId)) {
            List<Map<String, Object>> mapList = userWeekChallengeDao.getInfoByUserId(userId);
            for (int i = 0; i < mapList.size(); i++) {
                int isWeekEnd = Integer.parseInt(mapList.get(i).get("isWeekEnd").toString());
                int titleId = Integer.parseInt(mapList.get(i).get("titleId").toString());
                if (isWeekEnd == 1) {
                    userWeekChallengeDao.update(userId, titleId, 0, ",", 0);
                }
            }
        }
    }

    public String getWeekTitleList(HttpServletRequest request){
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        obj.Result = weekTitleDao.getWeekTitleList(userId);
        return JSONObject.toJSONString(obj);
    }

    public String getSubject(HttpServletRequest request,int titleId){
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        if(!userWeekChallengeDao.isExistsByUserId(userId,titleId)){
            userWeekChallengeDao.insert(userId,titleId,0,",",0);
        }
        obj.Result = subjectDao.getWeekRandSubjectListByUserId(userId,titleId,1);
        return JSONObject.toJSONString(obj);
    }

    public String submitSubject(HttpServletRequest request,int subjectId,int answer,int cardNumber){
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        //判断答案
        Map<String,Object> subjectMap = subjectDao.getInfoById(subjectId);
        int optionSortId = Integer.parseInt(subjectMap.get("optionSortId").toString());
        int titleId = Integer.parseInt(weekSubjectDao.getWeekSubjectBySubjectId(subjectId).get("titleId").toString());
        if(optionSortId != answer){
            obj.Tag = BasicTagState.TagAnswerError;
            obj.Message = "答案错误";

            if(cardNumber < 10){
                subjectRatioService.update(subjectId,1,0,1);
                userWeekChallengeDao.update(userId,titleId,0,",",0);
            }
            return JSONObject.toJSONString(obj);
        }

        userWeekChallengeDao.appendValueByUserId(userId,titleId,1,String.valueOf(subjectId));
        //如果答满10题就结束
        /*Map<String,Object> weekMap = userWeekChallengeDao.getInfoByUserId(userId,titleId);*/
        /*int weekCardNumber = Integer.parseInt(weekMap.get("weekCardNumber").toString());*/
        if(cardNumber >= 10){
            userWeekChallengeDao.updateIsWeekFinish(userId,titleId,1);
        }
        subjectRatioService.update(subjectId,1,1,0);
        return JSONObject.toJSONString(obj);
    }
}
