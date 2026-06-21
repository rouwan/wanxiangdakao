package com.example.vientianetest.web;

import com.example.common.permission.config.PermissionConstants;
import com.example.common.permission.config.RequiredPermission;
import com.example.vientianetest.service.user.UserDayTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/vientianetest/mobile/dayTask")
@RestController
public class DayTaskController {
    @Autowired
    private UserDayTaskService userDayTaskSercvice;

    @PostMapping("/getTaskList")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getTaskList(HttpServletRequest request) {
        return userDayTaskSercvice.getTaskList(request);
    }

    @PostMapping("/receiveGood")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String receiveGood(HttpServletRequest request) {
        return userDayTaskSercvice.receiveGood(request);
    }
}
