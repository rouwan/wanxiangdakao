package com.example.vientianetest.domain;

import java.util.Map;

public class MatchRecord {
    private Map<Object,Integer> userMap; //历史用户<用户Id,用户第几场匹配>
    private int maxMatchNum; //用户匹配的场次数

    public MatchRecord(){}

    public MatchRecord(Map<Object,Integer> userMap, int maxMatchNum) {
        this.userMap = userMap;
        this.maxMatchNum = maxMatchNum;
    }

    public Map<Object, Integer> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<Object, Integer> userMap) {
        this.userMap = userMap;
    }

    public int getMaxMatchNum() {
        return maxMatchNum;
    }

    public void setMaxMatchNum(int maxMatchNum) {
        this.maxMatchNum = maxMatchNum;
    }
}
