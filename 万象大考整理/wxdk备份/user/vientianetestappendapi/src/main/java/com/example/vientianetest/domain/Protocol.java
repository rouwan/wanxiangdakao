package com.example.vientianetest.domain;

public interface Protocol {
    int TYPE_KEEPALIVE = 0; // 保活
    int TYPE_MATCH = 1; // 匹配
    int TYPE_RECONNECT = 2; // 重连
    int TYPE_START = 3; // 开始
    int TYPE_ANSWER = 4; // 答题
    int TYPE_EXIT = 5; // 逃跑
    int TYPE_NEXT = 6; // 下一题
    int TYPE_END = 7; // 正常结束
    int TYPE_ROOM_KICK = 8;//踢玩家
    int TYPE_RESTART = 9;//再来一局
    int TYPE_ROOM_CHANGESTATUS = 10;//准备
    int TYPE_ROOM_FULL = 11; //人数已满
    int TYPE_ROOM_HALL = 12; //游戏大厅
    int TYPE_ROOM_CREATE = 13; //创建房间
    int TYPE_ROOM_QUICKSTART = 14; //快速开始
    int TYPE_CHAT = 15; //聊天
    int TYPE_ROOM_ENTER = 16; //加入房间
    int TYPE_MINISTATUS = 17; //最小化(点击home键或者左上角的按钮)
    int TYPE_STATUS_MATCH = -1; //匹配状态
}
