package com.example.vientianetest.web;


import com.example.common.permission.config.PermissionConstants;
import com.example.common.permission.config.RequiredPermission;
import com.example.vientianetest.service.user.UserDayChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/vientianetest/mobile/dayChallenge")
@RestController
public class DayChallengeApiController {
    @Autowired
    private UserDayChallengeService userDayChallengeService;

    @PostMapping("/getRankList")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getRankList(HttpServletRequest request){
        return userDayChallengeService.getRankList(request);
    }

    @PostMapping("/getSubject")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getSubject(HttpServletRequest request){
        return userDayChallengeService.getSubject(request);
    }

    @PostMapping("/submitSubject")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String submitSubject(HttpServletRequest request,int subjectId,int answer){
        return userDayChallengeService.submitSubject(request,subjectId,answer);
    }

    @PostMapping("/updateGoldCoin")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String updateGoldCoin(HttpServletRequest request,double number){
        return userDayChallengeService.updateGoldCoin(request,number);
    }
}
