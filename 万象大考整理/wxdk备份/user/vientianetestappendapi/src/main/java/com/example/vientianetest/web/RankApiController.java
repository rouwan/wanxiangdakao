package com.example.vientianetest.web;

import com.example.common.permission.config.PermissionConstants;
import com.example.common.permission.config.RequiredPermission;
import com.example.vientianetest.service.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/vientianetest/mobile/rank")
@RestController
public class RankApiController {
    @Autowired
    private UserAbilityService userAbilityService;
    @Autowired
    private UserParagraphService userParagraphService;
    @Autowired
    private UserCombatService userCombatService;
    @Autowired
    private UserFriendService userFriendService;
    @Autowired
    private UserCombatDayService userCombatDayService;
    @Autowired
    private UserGameStatisRankService userGameStatisRankService;
    @Autowired
    private UserGameStatisRoomService userGameStatisRoomService;

    @PostMapping("/beforeMatch")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String beforeMatch(HttpServletRequest request){
        return userAbilityService.beforeMatch(request);
    }


    //获取段位排行榜
    @PostMapping("/getRankList")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getRankList(HttpServletRequest request,int type) {
        System.out.println("测试排行榜"+type);
        //1.段位榜,2.战力榜,3.连胜榜,4.高分榜,5:好友榜,6:每日战力榜,7:群对战好友榜,8:群对战世界榜
        if (type == 1) {
            return userParagraphService.getRankList(request);
        } else if (type == 2) {
            return userCombatService.getRankList(request);
        } else if (type == 3) {
            return userGameStatisRankService.getRankList(request);
        } else if (type == 4) {
            return userGameStatisRankService.getMaxSingleScoreRankList(request);
        } else if (type == 5) {
            return userFriendService.getRankList(request);
        } else if (type == 6) {
            return userCombatDayService.getRankList(request);
        } else if (type == 7) {
            return userGameStatisRoomService.getFriendRankList(request);
        } else if (type == 8) {
            return userGameStatisRoomService.getRankList(request);
        }
        return "未知";
    }

    @PostMapping("/receiveGood")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String receiveGood(HttpServletRequest request){
        System.out.println("测试/receiveGood");

        return userParagraphService.receiveGood(request);
    }
}
