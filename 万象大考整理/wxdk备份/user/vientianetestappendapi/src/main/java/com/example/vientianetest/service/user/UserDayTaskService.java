package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.config.DataConfiguration;
import com.example.vientianetest.dao.user.UserDayTaskDao;
import com.example.vientianetest.redis.SessionRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserDayTaskService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private DataConfiguration dataConfiguration;
    @Autowired
    private UserDayTaskDao userDayTaskDao;
    @Autowired
    private UserEquipmentService userEquipmentService;

    public void insert(int userId, int type, int status, int num) {
        if (userDayTaskDao.isExistsTaskByType(type)) {//是否有该任务
            if (!userDayTaskDao.isExistsByUserId(userId, type)) {
                userDayTaskDao.insert(userId, type, status, num);
                userDayTaskDao.insertRecord(userId, type);//记录
            } else {
                if (!userDayTaskDao.isExistsTodayByUserId(userId, type)) {//今天没有做任务
                    userDayTaskDao.updateStatusByUserId(userId, type, 1, 0);//状态改成已完成
                    userDayTaskDao.insertRecord(userId, type);//记录
                }
            }
        }
    }

    public String getTaskList(HttpServletRequest request) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("taskList", userDayTaskDao.getTaskListByUserId(userId));
        resultMap.put("good", dataConfiguration.getDayTaskGood());
        obj.Result = resultMap;
        return JSONObject.toJSONString(obj);
    }

    public String receiveGood(HttpServletRequest request) {
        BasicResultObject obj = new BasicResultObject();
        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        if (userDayTaskDao.isExistsTodayNoReceiveByUserId(userId)) {//判断今天是否没有领
            userDayTaskDao.updateStatusByUserId(userId, 1, 1);
            userEquipmentService.insert(userId, dataConfiguration.getDayTaskGood(), 1);
        }
        return JSONObject.toJSONString(obj);
    }

    public int isFinishDayTaskByUserId(int userId) {
        return !userDayTaskDao.isExistsTodayNoReceiveByUserId(userId) ? 0 : 1;
    }
}
