package com.example.vientianetest.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserRankSubjectRecordRedis {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    private String KEY_PACKAGE = "server:userRecord:rankSubjectRecords";

    public void addUserSubjectRecord(int userId,String newSubjectIds){
        boolean hasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE,String.valueOf(userId));
        String subjectIds = newSubjectIds;
        if(hasKey){
            String oldSubjectIds = redisTemplate.opsForHash().get(KEY_PACKAGE,String.valueOf(userId)).toString();
            String[] oldSubjectIdsArr = oldSubjectIds.split(";");
            if(oldSubjectIdsArr.length >= 10){
                oldSubjectIds = "";
                for(int i =0;i<oldSubjectIdsArr.length;i++){
                    if(i == 0){continue;} //第一条去掉了
                    oldSubjectIds += oldSubjectIdsArr[i] + ";";
                }
            }
            subjectIds = oldSubjectIds + newSubjectIds;
        }
        redisTemplate.opsForHash().put(KEY_PACKAGE,String.valueOf(userId),subjectIds);
    }

    public String getUserSubjectRecord(int userId){
        boolean hasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE,String.valueOf(userId));
        String result = "";
        if(hasKey) {
            result = redisTemplate.opsForHash().get(KEY_PACKAGE,String.valueOf(userId)).toString();
            result = result.replaceAll(";",",");
            if(!result.startsWith(",")){
                result = "," + result;
            }
        }
        return result;
    }

}
