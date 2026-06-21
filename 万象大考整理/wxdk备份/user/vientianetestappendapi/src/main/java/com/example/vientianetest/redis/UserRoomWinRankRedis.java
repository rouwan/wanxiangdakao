package com.example.vientianetest.redis;

import com.example.vientianetest.dao.user.UserGameStatisRoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class UserRoomWinRankRedis {
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;
    @Autowired
    private UserGameStatisRoomDao userGameStatisRoomDao;
    private int KEY_NUM = 1000; //添加条数
    private String KEY_PACKAGE = "server:rank:roomWinList";

    public void addUserRankRecord(int userId,double winNum) {
        long card = zSetOperations.zCard(KEY_PACKAGE);
        Object currentScore = zSetOperations.score(KEY_PACKAGE, userId);
        if (currentScore == null || Double.valueOf(currentScore.toString()) == 0) {
            if (card < KEY_NUM) {
                zSetOperations.add(KEY_PACKAGE, userId, winNum);
            } else {
                Set<ZSetOperations.TypedTuple<Object>> mapList = zSetOperations.rangeWithScores(KEY_PACKAGE, 0, 0);
                int lastUserId = 0;
                double lastScore = 0;
                for (Object object : mapList) {
                    lastScore = ((DefaultTypedTuple) object).getScore();//最后一名
                    lastUserId = (int) ((DefaultTypedTuple) object).getValue();
                }
                if (winNum > lastScore) {
                    //删除最后一名
                    zSetOperations.remove(KEY_PACKAGE, lastUserId);
                    zSetOperations.add(KEY_PACKAGE, userId, winNum);
                }
            }
        } else {
            zSetOperations.add(KEY_PACKAGE, userId, winNum);
        }
    }

    public Map<String,Object> getUserRankIndex(int userId){
        boolean hasKey = zSetOperations.getOperations().hasKey(KEY_PACKAGE);
        Map<String,Object> map = null;
        long index = -1;
        if(hasKey) {
            Object currentScore = zSetOperations.score(KEY_PACKAGE, userId);
            if(currentScore == null){
                index = -1;
            }else {
                index = zSetOperations.reverseRank(KEY_PACKAGE,userId);
            }
        }
        map = userGameStatisRoomDao.getWinNumByUserId(userId);
        map.put("index",index < 0 ? -1 : index + 1);
        return map;
    }

    public List<Map<String,Object>> getRankList(){
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
            mapList = userGameStatisRoomDao.getWinNumListOrderByUserIds(userIds);
        }
        return mapList;
    }

    public Map<String,Object> getFriendRankByUserId(String userIds,int userId){
        Map<String,Object> map = null;
        if(userIds.length() >0 ){//有好友
            userIds = userIds + "," + userId;
            map = userGameStatisRoomDao.getWinNumByUserId(userIds,userId);
        }
        return map;
    }

    public List<Map<String,Object>> getFriendRankList(String userIds,int userId){
        List<Map<String,Object>> mapList = null;
        if(userIds.length() >0 ){//有好友
            userIds = userIds + "," + userId;
            mapList = userGameStatisRoomDao.getWinNumListOrderByWiNum(userIds);
        }
        return mapList;
    }
}
