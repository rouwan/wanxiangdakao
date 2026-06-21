package com.example.vientianetest.web;

import com.example.common.permission.config.PermissionConstants;
import com.example.common.permission.config.RequiredPermission;
import com.example.vientianetest.service.user.UserEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/vientianetest/mobile/email")
@RestController
public class EmailController {
    @Autowired
    private UserEmailService userEmailService;

    @PostMapping("/getEmailList")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getEmailList(HttpServletRequest request){
        return userEmailService.getEmailList(request);
    }

    @PostMapping("/updateStatus")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String updateStatus(HttpServletRequest request,int emailId,int type){
        //type = 1 查看 type = 2 领取
        return userEmailService.updateStatus(request,emailId,type);
    }

}
