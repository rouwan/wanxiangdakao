package com.example.vientianetest.domain;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 用于存放客户上报的答案
 */
public class Answer {
    private String roomId;      //加密的房间Id
    //@JSONField(serialize=false)
    //private String roomId2;      //没加密的房间Id
    private int gameId;         //游戏Id
    private int uid;            //用户Id
    private int index;          //第几题 从0开始
    private int answer;         //答案   从1开始
    private int rightAnswer;    //对的答案
    private int seconds;        //用时
    private int score;          //累加得分
    private int scoreSub;       //单题得分
    private boolean timeout;    //是否超时
    private boolean right;      //是否回答正确
    private Boolean isBoot;     //是否机器人
    private int continueRightNum; //连对次数
    private int continueRightScore; //连对获得的分数
    private int bookLevel;      //知识书等级
    private int bookType;       //知识书类型 (1:德,2:商,3:文,4:理,5:艺,6:体,7:综)
    private int bookScore;      //知识书加成
    private int groupId;        //分组Id
    private int groupScore;     //分组总得分

    public Answer(){}

    public Answer(int gameId, int uid, int index, int answer, int rightAnswer, int seconds, int score, boolean timeout, boolean right, Boolean isBoot) {
        this.gameId = gameId;
        this.uid = uid;
        this.index = index;
        this.answer = answer;
        this.rightAnswer = rightAnswer;
        this.seconds = seconds;
        this.score = score;
        this.timeout = timeout;
        this.right = right;
        this.isBoot = isBoot;
    }

    public Answer(String roomId, int uid, int index, int answer, int rightAnswer, int seconds, int score, boolean timeout, boolean right, Boolean isBoot) {
        this.roomId = roomId;
        this.uid = uid;
        this.index = index;
        this.answer = answer;
        this.rightAnswer = rightAnswer;
        this.seconds = seconds;
        this.score = score;
        this.timeout = timeout;
        this.right = right;
        this.isBoot = isBoot;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScoreSub() {
        return scoreSub;
    }

    public void setScoreSub(int scoreSub) {
        this.scoreSub = scoreSub;
    }

    public boolean isTimeout() {
        return timeout;
    }

    public void setTimeout(boolean timeout) {
        this.timeout = timeout;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public Boolean getBoot() {
        return isBoot;
    }

    public void setBoot(Boolean boot) {
        isBoot = boot;
    }

    public void normalize() {
        /*if (this.timeout) {
            this.setAnswer(Subject.ANSWER_TIMEOUT);
        }
        int score = 60;
        if (this.seconds <= 3) {
            score = 100;
        } else if (this.seconds <= 7) {
            score = 80;
        }
        this.score = score;*/
    }

    public int getContinueRightNum() {
        return continueRightNum;
    }

    public void setContinueRightNum(int continueRightNum) {
        this.continueRightNum = continueRightNum;
    }

    public int getContinueRightScore() {
        return continueRightScore;
    }

    public void setContinueRightScore(int continueRightScore) {
        this.continueRightScore = continueRightScore;
    }

    public int getBookLevel() {
        return bookLevel;
    }

    public void setBookLevel(int bookLevel) {
        this.bookLevel = bookLevel;
    }

    public int getBookType() {
        return bookType;
    }

    public void setBookType(int bookType) {
        this.bookType = bookType;
    }

    public int getBookScore() {
        return bookScore;
    }

    public void setBookScore(int bookScore) {
        this.bookScore = bookScore;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupScore() {
        return groupScore;
    }

    public void setGroupScore(int groupScore) {
        this.groupScore = groupScore;
    }

    public Answer getAnswer(JSONObject map){
        this.answer = Integer.parseInt(map.get("answer").toString());
        this.gameId = Integer.parseInt(map.get("gameId").toString());
        this.index = Integer.parseInt(map.get("index").toString());
        this.seconds = Integer.parseInt(map.get("seconds").toString());
        this.timeout =Boolean.valueOf(map.get("timeout").toString());
        this.uid = Integer.parseInt(map.get("uid").toString());
        return this;
    }

    public Answer getAnswer2(JSONObject map){
        this.answer = Integer.parseInt(map.get("answer").toString());
        this.roomId = map.get("roomId").toString();
        //this.roomId2 = map.get("roomId2").toString();
        this.index = Integer.parseInt(map.get("index").toString());
        this.seconds = Integer.parseInt(map.get("seconds").toString());
        this.timeout =Boolean.valueOf(map.get("timeout").toString());
        this.uid = Integer.parseInt(map.get("uid").toString());
        return this;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "roomId='" + roomId + '\'' +
                ", gameId=" + gameId +
                ", uid=" + uid +
                ", index=" + index +
                ", answer=" + answer +
                ", rightAnswer=" + rightAnswer +
                ", seconds=" + seconds +
                ", score=" + score +
                ", scoreSub=" + scoreSub +
                ", timeout=" + timeout +
                ", right=" + right +
                ", isBoot=" + isBoot +
                ", continueRightNum=" + continueRightNum +
                ", continueRightScore=" + continueRightScore +
                ", bookLevel=" + bookLevel +
                ", bookType=" + bookType +
                ", bookScore=" + bookScore +
                ", groupId=" + groupId +
                ", groupScore=" + groupScore +
                '}';
    }
}
