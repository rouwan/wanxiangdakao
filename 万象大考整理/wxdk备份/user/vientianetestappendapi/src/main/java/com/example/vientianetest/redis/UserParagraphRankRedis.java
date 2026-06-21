package com.example.vientianetest.redis;

import com.example.vientianetest.dao.user.UserParagraphDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class UserParagraphRankRedis {
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;
    @Autowired
    private UserParagraphDao userParagraphDao;
    private int KEY_NUM = 1000; //添加条数
    private String KEY_PACKAGE = "server:rank:paragraphList";

    public void addUserRankRecord(int userId,double mmr){
        long card = zSetOperations.zCard(KEY_PACKAGE);
        Object currentScore = zSetOperations.score(KEY_PACKAGE, userId);
        if(currentScore == null || Double.valueOf(currentScore.toString()) == 0) {
            String time = "0."+String.valueOf(1999999999 - new Date().getTime()/1000); //String.valueOf(1999999999 - createTime);
            if (card < KEY_NUM) {
                zSetOperations.add(KEY_PACKAGE, userId, mmr + Double.valueOf(time));
            }else{
                Set<ZSetOperations.TypedTuple<Object>> mapList = zSetOperations.rangeWithScores(KEY_PACKAGE,0,0);
                int lastUserId = 0;
                double lastScore = 0;
                for(Object object : mapList){
                    lastScore = ((DefaultTypedTuple) object).getScore();//最后一名
                    lastUserId = (int)((DefaultTypedTuple) object).getValue();
                }
                if(mmr > lastScore){
                    //删除最后一名
                    zSetOperations.remove(KEY_PACKAGE,lastUserId);
                    zSetOperations.add(KEY_PACKAGE, userId, mmr + Double.valueOf(time));
                }
            }
        }else {
            String smallScore = currentScore.toString().split("\\.").length > 1 ? currentScore.toString().split("\\.")[1] : currentScore.toString();
            smallScore = "0."+String.valueOf(smallScore);
            zSetOperations.add(KEY_PACKAGE, userId, mmr + Double.valueOf(smallScore));
        }

    }

    public Map<String,Object> getUserRankIndex(int userId){
        boolean hasKey = zSetOperations.getOperations().hasKey(KEY_PACKAGE);
        Map<String,Object> map = null;
        long index = -1;
        if(hasKey) {
            Object currentScore = zSetOperations.score(KEY_PACKAGE, userId);
            if(currentScore == null){
                return null;
            }
            index = zSetOperations.reverseRank(KEY_PACKAGE,userId);
            map = userParagraphDao.getRankListByUserIdSub(userId);
            map.put("index",index < 0 ? -1 : index + 1);
        }
        return map;
    }

    public List<Map<String,Object>> getRankList(){
        System.out.println("userParagraphRankRedis.getRankList方法");
        boolean hasKey = zSetOperations.getOperations().hasKey(KEY_PACKAGE);
        List<Map<String,Object>> mapList = null;
        if(hasKey) {
            Set<Object> objectSet = zSetOperations.reverseRange(KEY_PACKAGE,0,49);
            String userIds = "";
            for(Object object : objectSet){
                int userId = ((Integer) object).intValue();
                userIds += userId + ",";
            }
            if(userIds.endsWith(",")){
                userIds = userIds.substring(0 , userIds.length() - 1);
            }
            mapList = userParagraphDao.getRankListByUserIds(userIds);
        }
        System.out.println("mapList"+mapList);

        return mapList;
    }
}
