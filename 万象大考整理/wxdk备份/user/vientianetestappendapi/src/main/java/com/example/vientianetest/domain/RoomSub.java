package com.example.vientianetest.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoomSub {
    private String roomId;  //每次开房都不一样
    private int roomMaster; //房主Id
    private int userNum;    //人数
    private CopyOnWriteArrayList<User> leftRankList; //3v3左边排序
    private CopyOnWriteArrayList<User> rightRankList; //3v3右边排序
    private CopyOnWriteArrayList<User> rankList; //排行榜
    private CopyOnWriteArrayList<User> userList; //选手
    private int roomStatus;  //0:游戏未开始 1:开始游戏 2:结束游戏
    @JSONField(serialize=false)
    private long counter;    //房间倒计时
    private long restSecond; //房间剩余时间（秒）
    private int answerIndex; //答题序号
    @JSONField(serialize=false)
    private boolean isBoot;  //是否是机器人的创建的
    private int modeType;    //游戏模式 1.四人混战 2.3v3
    @JSONField(serialize=false)
    private long joinTime;   //加入匹配的时间
    @JSONField(serialize=false)
    private List<String> roomList;//由哪些房间组成
    @JSONField(serialize=false)
    private List<RoomGroup> groupList;//分组

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getRoomMaster() {
        return roomMaster;
    }

    public void setRoomMaster(int roomMaster) {
        this.roomMaster = roomMaster;
    }

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public CopyOnWriteArrayList<User> getLeftRankList() {
        return leftRankList;
    }

    public void setLeftRankList(CopyOnWriteArrayList<User> leftRankList) {
        this.leftRankList = leftRankList;
    }

    public CopyOnWriteArrayList<User> getRightRankList() {
        return rightRankList;
    }

    public void setRightRankList(CopyOnWriteArrayList<User> rightRankList) {
        this.rightRankList = rightRankList;
    }

    public List<User> getRankList() {
        return rankList;
    }

    public void setRankList(CopyOnWriteArrayList<User> rankList) {
        this.rankList = rankList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(CopyOnWriteArrayList<User> userList) {
        this.userList = userList;
    }

    public int getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public long getRestSecond() {
        return restSecond;
    }

    public void setRestSecond(long restSecond) {
        this.restSecond = restSecond;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }

    public boolean isBoot() {
        return isBoot;
    }

    public void setBoot(boolean boot) {
        isBoot = boot;
    }

    public int getModeType() {
        return modeType;
    }

    public void setModeType(int modeType) {
        this.modeType = modeType;
    }

    public long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(long joinTime) {
        this.joinTime = joinTime;
    }

    public List<String> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<String> roomList) {
        this.roomList = roomList;
    }

    public List<RoomGroup> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<RoomGroup> groupList) {
        this.groupList = groupList;
    }
}
