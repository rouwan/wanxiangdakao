package io.renren.modules.sys.controller.small.wxdk;

import io.renren.modules.sys.service.small.wxdk.SubjectRatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/smallVientianeTest/subjectLevelTypeRatio")
public class SubjectLevelTypeRatioController {
    @Autowired
    private SubjectRatioService subjectRatioService;

    @GetMapping("/get")
    public String get(){
        return subjectRatioService.getSubjectLevelTypeRatio();
    }
}
