package com.example.vientianetest.domain;

public class RoomGroup {
    private int groupId;//分组号
    private int score;//分数

    public RoomGroup(){}

    public RoomGroup(int groupId, int score) {
        this.groupId = groupId;
        this.score = score;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
