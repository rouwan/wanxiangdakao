package com.example.vientianetest.web;

import com.example.common.permission.config.PermissionConstants;
import com.example.common.permission.config.RequiredPermission;
import com.example.vientianetest.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/vientianetest/mobile/room")
@RestController
public class RoomApiController {
    @Autowired
    private RoomService roomService;

    @PostMapping("/getHeadData")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getHeadData(HttpServletRequest request) throws Exception{
        return roomService.getHeadData(request);
    }

    @PostMapping("/rankHallData")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String rankHallData(HttpServletRequest request) throws Exception{
        System.out.println("好友匹配");
        return roomService.rankHallData(request);
    }
}
