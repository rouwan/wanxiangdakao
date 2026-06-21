package com.example.vientianetest.web;

import com.example.common.permission.config.PermissionConstants;
import com.example.common.permission.config.RequiredPermission;
import com.example.vientianetest.service.*;
import com.example.vientianetest.service.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/vientianetest/mobile/user")
@RestController
public class UserApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private MobileLogService mobileLogService;
    @Autowired
    private UserAbilityService userAbilityService;
    @Autowired
    private UserToXcxService userToXcxService;
    @Autowired
    private UserPrizeService userPrizeService;
    @Autowired
    private UserFromXcxService userFromXcxService;

    @PostMapping("/getUser")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getUser(HttpServletRequest request,int userId,int type){
        return userService.getUser(request,userId,type);
    }

    @PostMapping("/getStrength")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getStrength(HttpServletRequest request){
        return userService.getStrength(request);
    }

    @PostMapping("/writeLog")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public void writeLog(HttpServletRequest request,String content){
        mobileLogService.writeLog(content);
    }

    @PostMapping("/userToXcx")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String userToXcx(HttpServletRequest request,String name, int type, int status,int goodType, int goodNum) {
        return userToXcxService.insert(request, name, type, status, goodType, goodNum);
    }

    @PostMapping("/userFromXcx")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public void userFromXcx(HttpServletRequest request,@RequestParam(value="from",required = false,defaultValue = "") String from) {
        if (from.replaceAll(" ", "").length() <= 0) {
            return;
        }
        userFromXcxService.insert(request, from.replaceAll(" ", ""));
    }

    @PostMapping("/randomPrize")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String randomPrize(HttpServletRequest request,@RequestParam(value="type",required = false,defaultValue = "1") int type) {
        return userPrizeService.userRandomPrize(request, type);
    }

    @PostMapping("/testUserRandomPrize")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public void testUserRandomPrize(HttpServletRequest request) {
        userPrizeService.testUserRandomPrize();
    }

    @PostMapping("/getRealTimeData")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getRealTimeData(HttpServletRequest request){
        return userService.getRealTimeData(request);
    }

    /*@PostMapping("/exit")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String exit(HttpServletRequest request,int strengthIntervalTimer){
        return userService.exit(request,strengthIntervalTimer);
    }*/

}
