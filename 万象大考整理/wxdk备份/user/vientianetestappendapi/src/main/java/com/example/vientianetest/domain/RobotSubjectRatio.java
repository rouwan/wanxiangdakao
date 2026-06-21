package com.example.vientianetest.domain;

//机器人答题效率
public class RobotSubjectRatio {
    private float simpleRatio;
    private float commonRatio;
    private float hardRatio;
    private int simpleMinTime;
    private int simpleMaxTime;
    private int commonMinTime;
    private int commonMaxTime;
    private int hardMinTime;
    private int hardMaxTime;

    public RobotSubjectRatio(){}

    public RobotSubjectRatio(float simpleRatio, float commonRatio, float hardRatio, int simpleMinTime, int simpleMaxTime, int commonMinTime, int commonMaxTime, int hardMinTime, int hardMaxTime) {
        this.simpleRatio = simpleRatio;
        this.commonRatio = commonRatio;
        this.hardRatio = hardRatio;
        this.simpleMinTime = simpleMinTime;
        this.simpleMaxTime = simpleMaxTime;
        this.commonMinTime = commonMinTime;
        this.commonMaxTime = commonMaxTime;
        this.hardMinTime = hardMinTime;
        this.hardMaxTime = hardMaxTime;
    }

    public float getSimpleRatio() {
        return simpleRatio;
    }

    public void setSimpleRatio(float simpleRatio) {
        this.simpleRatio = simpleRatio;
    }

    public float getCommonRatio() {
        return commonRatio;
    }

    public void setCommonRatio(float commonRatio) {
        this.commonRatio = commonRatio;
    }

    public float getHardRatio() {
        return hardRatio;
    }

    public void setHardRatio(float hardRatio) {
        this.hardRatio = hardRatio;
    }

    public int getSimpleMinTime() {
        return simpleMinTime;
    }

    public void setSimpleMinTime(int simpleMinTime) {
        this.simpleMinTime = simpleMinTime;
    }

    public int getSimpleMaxTime() {
        return simpleMaxTime;
    }

    public void setSimpleMaxTime(int simpleMaxTime) {
        this.simpleMaxTime = simpleMaxTime;
    }

    public int getCommonMinTime() {
        return commonMinTime;
    }

    public void setCommonMinTime(int commonMinTime) {
        this.commonMinTime = commonMinTime;
    }

    public int getCommonMaxTime() {
        return commonMaxTime;
    }

    public void setCommonMaxTime(int commonMaxTime) {
        this.commonMaxTime = commonMaxTime;
    }

    public int getHardMinTime() {
        return hardMinTime;
    }

    public void setHardMinTime(int hardMinTime) {
        this.hardMinTime = hardMinTime;
    }

    public int getHardMaxTime() {
        return hardMaxTime;
    }

    public void setHardMaxTime(int hardMaxTime) {
        this.hardMaxTime = hardMaxTime;
    }

}
