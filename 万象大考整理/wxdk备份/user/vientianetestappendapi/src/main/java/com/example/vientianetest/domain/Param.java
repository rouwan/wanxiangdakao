package com.example.vientianetest.domain;

/**
 * 游戏全局常量（接口形式，字段默认 public static final）
 */
public interface Param {
    int GAME_MATCH_WAITSECOND = 1;    // 匹配机器人前的等待秒数（1-4秒，Dev环境加速）
    int GAME_MATCH_SUBJECTNUM = 5;    // 每局对战题目数量
    int GAME_MATCH_SUBJECTSECOND = 10 * 1000; // 每题作答限时（毫秒）
    int GAME_MATCH_MAX_SCORE = 1340;  // 单局最高分（1200基础 + 140加成）
    int GAME_MATCH_STRENGTHCOUNT = 3; // 每局消耗的体力值
    int GAME_EMOJI_NUM = 8;           // 表情包数量
    int GAME_ROOM_NUM_ADDMONEY = 15;  // 群对战前N局才获得金币
    int GLOBAL_STRENGTHINTERVALTIMER = 120; // 体力恢复间隔（秒）
    int GLOBAL_MAX_STRENGTHCOUNT = 30;      // 体力上限
    int GLOBAL_STRENGTH_SMALL = 1;   // 小体力瓶回复量
    int GLOBAL_STRENGTH_MIDDLE = 2;  // 中体力瓶回复量
    int GLOBAL_STRENGTH_BIG = 3;     // 大体力瓶回复量
    int GLOBAL_PRIZE_MONEY = 50;     // 普通抽奖消耗金币
    int GLOBAL_BIGPRIZE_MONEY = 500; // 高级抽奖消耗金币
    int GLOBAL_MAX_ADD_SUBJECTNUM = 50; // 用户每天最多出题数
}

