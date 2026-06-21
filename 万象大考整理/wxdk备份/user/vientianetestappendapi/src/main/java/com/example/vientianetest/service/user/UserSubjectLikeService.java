package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.dao.user.UserSubjectLikeDao;
import com.example.vientianetest.redis.SessionRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class UserSubjectLikeService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserSubjectLikeDao userSubjectLikeDao;

    public void insert(int userId, int subjectId, int isLike) {
        if (!userSubjectLikeDao.isExistsByUserId(userId, subjectId)) {
            if (isLike >= 0) {
                userSubjectLikeDao.insert(userId, subjectId, isLike);
            }
        } else {
            userSubjectLikeDao.update(userId, subjectId, isLike);
        }
    }

    public String updateLikeStatus (HttpServletRequest request, String likeList) {
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        Map<String, Object> map = JSONObject.parseObject(likeList);
        for (String key : map.keySet()) {
            int subjectId = Integer.parseInt(key);
            int likeStatus = Integer.parseInt(map.get(key).toString());
            this.insert(userId, subjectId, likeStatus);
        }
        return JSONObject.toJSONString(new BasicResultObject());
    }
}
