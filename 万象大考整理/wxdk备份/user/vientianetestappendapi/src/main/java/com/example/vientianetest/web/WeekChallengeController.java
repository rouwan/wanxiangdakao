package com.example.vientianetest.web;

import com.example.common.permission.config.PermissionConstants;
import com.example.common.permission.config.RequiredPermission;
import com.example.vientianetest.service.user.UserWeekChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/vientianetest/mobile/weekChallenge")
@RestController
public class WeekChallengeController {
    @Autowired
    private UserWeekChallengeService  userWeekChallengeService;

    @PostMapping("/getWeekTitleList")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getWeekTitleList(HttpServletRequest request){
        return userWeekChallengeService.getWeekTitleList(request);
    }

    @PostMapping("/getSubject")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getSubject(HttpServletRequest request,int titleId){
        return userWeekChallengeService.getSubject(request,titleId);
    }

    @PostMapping("/submitSubject")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String submitSubject(HttpServletRequest request,int subjectId,int answer,int cardNumber){
        return userWeekChallengeService.submitSubject(request,subjectId,answer,cardNumber);
    }
}
