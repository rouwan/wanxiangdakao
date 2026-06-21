package com.example.vientianetest.web;

import com.example.common.permission.config.PermissionConstants;
import com.example.common.permission.config.RequiredPermission;
import com.example.vientianetest.service.user.UserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/vientianetest/mobile/friend")
@RestController
public class FriendController {
    @Autowired
    private UserFriendService userFriendService;

    @PostMapping("/getFriendList")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getFriendList(HttpServletRequest request) {
        return userFriendService.getFriendList(request);
    }

    @PostMapping("/receiveFriendGood")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String receiveFriendGood(HttpServletRequest request, int userId, int goodType, int goodNum) {
        return userFriendService.receiveFriendGood(request, userId, goodType, goodNum);
    }
}
