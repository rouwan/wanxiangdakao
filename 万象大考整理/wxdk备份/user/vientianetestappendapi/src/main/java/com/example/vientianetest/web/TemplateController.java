package com.example.vientianetest.web;

import com.example.common.permission.config.PermissionConstants;
import com.example.common.permission.config.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.vientianetest.service.user.UserFormIdService;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/vientianetest/mobile/template")
@RestController
public class TemplateController {
    @Autowired
    private UserFormIdService userFormIdService;

    @PostMapping("/updateFormId")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String updateFormId(HttpServletRequest request, String formId) {
        return userFormIdService.updateFormId(request, formId);
    }
}
