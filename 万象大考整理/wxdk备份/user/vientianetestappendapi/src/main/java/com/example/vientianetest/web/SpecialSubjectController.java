package com.example.vientianetest.web;

import com.example.common.permission.config.PermissionConstants;
import com.example.common.permission.config.RequiredPermission;
import com.example.vientianetest.service.specialSubject.UserSpecialSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/vientianetest/mobile/specialSubject")
@RestController
public class SpecialSubjectController {
    @Autowired
    private UserSpecialSubjectService userSpecialSubjectService;

    @PostMapping("/getSubject")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getSubject(HttpServletRequest request) {
        return userSpecialSubjectService.getSubject(request);
    }

    @PostMapping("/submitSubject")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String submitSubject(HttpServletRequest request, int subjectId, String answer) {
        return userSpecialSubjectService.submitSubject(request, subjectId, answer);
    }

    @PostMapping("/promptSubject")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String promptSubject(HttpServletRequest request, int subjectId) {
        return userSpecialSubjectService.promptSubject(request, subjectId);
    }
}
