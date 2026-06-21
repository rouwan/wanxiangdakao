package com.example.vientianetest.web;

import com.example.common.permission.config.PermissionConstants;
import com.example.common.permission.config.RequiredPermission;
import com.example.vientianetest.service.user.UserEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/vientianetest/mobile/equipment")
@RestController
public class EquipmentController {
    @Autowired
    private UserEquipmentService userEquipmentService;

    @PostMapping("/getEquipmentList")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getEquipmentList(HttpServletRequest request){
        return userEquipmentService.getEquipmentList(request);
    }

    @PostMapping("/useEquipment")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String useEquipment(HttpServletRequest request,int count, int type){
        return userEquipmentService.useEquipment(request,count,type);
    }

}
