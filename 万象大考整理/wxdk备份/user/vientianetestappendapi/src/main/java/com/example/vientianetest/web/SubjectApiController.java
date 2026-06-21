package com.example.vientianetest.web;

import com.example.common.permission.config.PermissionConstants;
import com.example.common.permission.config.RequiredPermission;
import com.example.vientianetest.service.user.UserSubjectLikeService;
import com.example.vientianetest.service.user.UserSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/vientianetest/mobile/subject")
@RestController
public class SubjectApiController {
    @Autowired
    private UserSubjectService userSubjectService;
    @Autowired
    private UserSubjectLikeService userSubjectLikeService;

    @PostMapping("/getSubjectNum")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getSubjectNum(HttpServletRequest request) {
        return userSubjectService.getSubjectNum(request);
    }

    @PostMapping("/getSubjectById")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getSubjectById(HttpServletRequest request, int id) {
        return userSubjectService.getSubjectById(request, id);
    }

    @PostMapping("/getSubjectList")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getSubjectList(HttpServletRequest request, int pageIndex, int pageSize) {
        return userSubjectService.getSubjectList(request, pageIndex, pageSize);
    }

    @PostMapping("/getSubjectListByStatus")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getSubjectListByStatus(HttpServletRequest request, int status, int pageIndex, int pageSize) {
        return userSubjectService.getSubjectListByStatus(request, status, pageIndex, pageSize);
    }

    @PostMapping("/updateSubject")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String updateSubject(HttpServletRequest request,
                                int id,
                                int parentType,
                                int answer,
                                String title,
                                String option,
                                String description) {
        if (id > 0) {
            return userSubjectService.updateSubject(request, id, parentType, answer, title, option, description);
        } else {
            return userSubjectService.addSubject(request, parentType, answer, title, option, description);
        }
    }

    @PostMapping("/receiveGood")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String receiveGood(HttpServletRequest request, int subjectId) {
        return userSubjectService.receiveGood(request, subjectId);
    }

    @PostMapping("/updateLikeStatus")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String updateLikeStatus(HttpServletRequest request, String likeList) {
        return userSubjectLikeService.updateLikeStatus(request, likeList);
    }
}
