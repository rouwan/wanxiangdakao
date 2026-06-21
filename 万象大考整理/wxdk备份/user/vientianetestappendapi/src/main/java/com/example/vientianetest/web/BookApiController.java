package com.example.vientianetest.web;

import com.example.common.permission.config.PermissionConstants;
import com.example.common.permission.config.RequiredPermission;
import com.example.vientianetest.service.user.UserBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/vientianetest/mobile/book")
@RestController
public class BookApiController {
    @Autowired
    private UserBookService userBookService;

    @PostMapping("/getUserBookList")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getUserBookList(HttpServletRequest request) {
        System.out.println("测试/getUserBookList");
        return userBookService.getUserBookList(request);
    }

    @PostMapping("/upLevelByType")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String upLevelByType(HttpServletRequest request,int type) {
        System.out.println("测试/upLevelByType");

        return userBookService.upLevelByType(request, type);
    }

    @PostMapping("/getBookUpLevelCount")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getBookUpLevelCount(HttpServletRequest request) {
        System.out.println("测试/getBookUpLevelCount");
        return userBookService.getBookUpLevelCountByUserId(request);
    }
}
