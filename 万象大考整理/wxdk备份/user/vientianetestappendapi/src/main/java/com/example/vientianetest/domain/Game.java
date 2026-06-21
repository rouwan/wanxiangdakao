package com.example.vientianetest.domain;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game {
    public static final int STATUS_NEW = 1;
    public static final int STATUS_PLAYING = 2;
    public static final int STATUS_END = 3;

    @JSONField(name = "gameId")
    private int id; // 游戏 id
    private int status; // 状态
    @JSONField(serialize=false)
    private int winUserId; //赢的玩家
    //@JSONField(serialize=false)
    private int exitUserId; //退出玩家
    @JSONField(serialize=false)
    private int isFlat; //是否平局
    @JSONField(serialize=false)
    private long counter;//每局的倒计时
    private int answerIndex;//答题序号
    private List<User> userList; // 玩家列表
    private List<Subject> subjectList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getWinUserId() {
        return winUserId;
    }

    public void setWinUserId(int winUserId) {
        this.winUserId = winUserId;
    }

    public int getExitUserId() {
        return exitUserId;
    }

    public void setExitUserId(int exitUserId) {
        this.exitUserId = exitUserId;
    }

    public int getIsFlat() {
        return isFlat;
    }

    public void setIsFlat(int isFlat) {
        this.isFlat = isFlat;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public void endGame(int excludeUid){
        //逃跑的人输
        if(excludeUid>0){
            int tempWinUserId = this.userList.get(0).getUser().getUserId() ==  excludeUid ?  this.userList.get(1).getUser().getUserId() : this.userList.get(0).getUser().getUserId() ;
            this.setWinUserId(tempWinUserId);
            this.setIsFlat(0);
            this.setExitUserId(excludeUid);
        }else{
            this.setExitUserId(0);
            if(this.userList.get(0).getAbility().getScore()>this.userList.get(1).getAbility().getScore()){
                this.setWinUserId(this.userList.get(0).getUser().getUserId());
                this.setIsFlat(0);
            }else if(this.userList.get(0).getAbility().getScore()<this.userList.get(1).getAbility().getScore()){
                this.setWinUserId(this.userList.get(1).getUser().getUserId());
                this.setIsFlat(0);
            }else{ // 平局
                this.setWinUserId(this.userList.get(0).getUser().getUserId());
                this.setIsFlat(1);
            }
        }
    }
    public Boolean complete(){
        Subject lastSubject = this.subjectList.get(this.subjectList.size() - 1); // 最后一题
        Boolean result = false;
        if(lastSubject.getAnswerList() != null) {
            result = lastSubject.getAnswerList().size() == 2;
        }
        return result;
    }

    public boolean isAnswerByIndex(int index){
        if(index < 0)
            return true;
        if(index >= Param.GAME_MATCH_SUBJECTNUM )
            return false;
        if(this.subjectList.size()<=0){return false;}
        Subject subject = this.subjectList.get(index);
        boolean result = false;
        if(subject.getAnswerList() != null) {
            result = subject.getAnswerList().size() == 2;
        }
        return result;
    }

    public boolean isAnswerByIndexSub(int index) {
        if (this.subjectList.size() <= 0) {
            return false;
        }
        Subject subject = this.subjectList.get(index);
        boolean result = false;
        if (subject.getAnswerList() != null) {
            result = subject.getAnswerList().size() == 2;
        }
        return result;
    }

    public boolean isAnswerByUserId(int index,int userId) {
        if (this.subjectList.size() <= 0) {
            return false;
        }
        Subject subject = this.subjectList.get(index);
        boolean result = false;
        if (subject.getAnswerList() != null) {
            for (int i = 0; i < subject.getAnswerList().size(); i++) {
                result = subject.getAnswerList().get(i).getUid() == userId;
                break;
            }
        }
        return result;
    }

    public void reSetCountDown(int index){
        Subject subject = this.subjectList.get(index);
        if(subject.getAnswerList() != null && subject.getAnswerList().size() == this.userList.size()) {
            this.counter = new Date().getTime();
            this.userList.get(0).setLastReplyTime(new Date().getTime());
            this.userList.get(1).setLastReplyTime(new Date().getTime());
        }
    }
}

