package com.example.common.domain;

public class BasicTagState {

    //成功状态
    public static int TagSuccess = 1;

    //全部通关
    public static int TagPass = 2;

    //重复状态
    public static int TagRepeat = 4;

    //没有用户信息 状态
    public static int TagSignOut = -8;

    //失败状态
    public static int TagFailure = -1;

    //异常状态
    public static int TagAbnormal = -2;

    //答案错误
    public static int TagAnswerError = -3;

    //不能重复玩
    public static int TagNotRepeat = -4;

    //提示已用完
    public static int TagPromptOut = -5;

    //体力已用完
    public static int TagStrengthOut = -6;

    //体力已满
    public static int TagStrengthFull = -7;

    //活动专用--失败
    public static int ActivityFail = 20;
}
