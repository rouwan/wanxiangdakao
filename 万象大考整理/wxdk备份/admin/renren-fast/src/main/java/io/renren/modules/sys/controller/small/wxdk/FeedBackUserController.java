package io.renren.modules.sys.controller.small.wxdk;

import io.renren.modules.sys.service.small.wxdk.FeedBackUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/smallVientianeTest/feedBackUser")

public class FeedBackUserController {

    @Autowired
    private FeedBackUserService feedBackUserService;

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords){
        return feedBackUserService.getPageList(pageIndex,pageSize,keyWords);
    }
}
