package io.renren.modules.sys.controller.small.wxdk;

import io.renren.modules.sys.service.small.wxdk.FeedBackSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/smallVientianeTest/feedBackSubject")
public class FeedBackSubjectController {

    @Autowired
    private FeedBackSubjectService feedBackSubjectService;

    @GetMapping("/updateStatus")
    public String updateStatus(String ids){
        return feedBackSubjectService.updateStatus(ids);
    }

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords,int typeId){
        return feedBackSubjectService.getPageList(pageIndex,pageSize,keyWords,typeId);
    }


}
