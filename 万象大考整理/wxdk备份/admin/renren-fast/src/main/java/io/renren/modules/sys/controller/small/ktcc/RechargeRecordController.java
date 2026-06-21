package io.renren.modules.sys.controller.small.ktcc;

import io.renren.modules.sys.service.small.ktcc.RechargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/smallLookGuess/rechargerecord")

//充值记录
public class RechargeRecordController {
    @Autowired
    private RechargeRecordService rechargeRecordService;

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords,String date){
        return rechargeRecordService.getPageList(pageIndex,pageSize,keyWords,date);
    }
}
