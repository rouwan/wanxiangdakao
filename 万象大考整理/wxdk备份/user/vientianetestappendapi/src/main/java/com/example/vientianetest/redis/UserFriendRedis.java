package com.example.vientianetest.redis;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.vientianetest.dao.user.UserFriendDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserFriendRedis {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private UserFriendDao userFriendDao;
    private String KEY_PACKAGE = "server:userRecord:friendRecords";

    public void add (int userId, int withUserId) {
        String userFriendStr = String.valueOf(withUserId);
        String withUserFriendStr = String.valueOf(userId);
        boolean userHasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE, String.valueOf(userId));
        if (userHasKey) {
            userFriendStr = redisTemplate.opsForHash().get(KEY_PACKAGE, String.valueOf(userId)).toString();
            userFriendStr = userFriendStr + "," + String.valueOf(withUserId);
        }
        boolean withUserHasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE, String.valueOf(withUserId));
        if (withUserHasKey) {
            withUserFriendStr = redisTemplate.opsForHash().get(KEY_PACKAGE, String.valueOf(withUserId)).toString();
            withUserFriendStr = withUserFriendStr + "," + String.valueOf(userId);
        }
        redisTemplate.opsForHash().put(KEY_PACKAGE, String.valueOf(userId), userFriendStr);
        redisTemplate.opsForHash().put(KEY_PACKAGE, String.valueOf(withUserId), withUserFriendStr);
    }

    public void init(int userId,String friendList) {
        redisTemplate.opsForHash().put(KEY_PACKAGE, String.valueOf(userId), friendList);
    }

    public String getRankList(int userId) {
        BasicResultObject obj = new BasicResultObject();
        boolean hasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE, String.valueOf(userId));
        System.out.println("测试if是否执行："+hasKey);
        if (hasKey) {
            String userIds = redisTemplate.opsForHash().get(KEY_PACKAGE, String.valueOf(userId)).toString();
            if (userIds.endsWith(",")) {
                userIds = userIds.substring(0, userIds.length() - 1);
            }
            userIds = userIds + "," + userId;
            obj.Result = userFriendDao.getRankListByUserIds(userIds);
            obj.Result2 = userFriendDao.getRankListByUserId(userIds,userId);
        }
        return JSONObject.toJSONString(obj);
    }

    public String getFriendByUserId(int userId) {
        String friendStr = "";
        boolean hasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE, String.valueOf(userId));
        if (hasKey) {
            String userIds = redisTemplate.opsForHash().get(KEY_PACKAGE, String.valueOf(userId)).toString();
            if (userIds.endsWith(",")) {
                userIds = userIds.substring(0, userIds.length() - 1);
            }
            friendStr = userIds;
        }
        return friendStr;
    }
}
