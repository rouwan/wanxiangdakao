package io.renren.modules.sys.controller.small.wxdk;

import io.renren.modules.sys.service.small.wxdk.ClearDataTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallVientianeTest/clearDataTime")
public class ClearDataTimeController {
    @Autowired
    private ClearDataTimeService clearDataTimeService;

    @PostMapping("/add")
    public String add( HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestBody HashMap<String,Object> paramMap){
        String clearTime = paramMap.get("clearTime").toString();
        int type =Integer.parseInt(paramMap.get("type").toString());
        return clearDataTimeService.add(clearTime,type);
    }

    @PostMapping("/update")
    public String update( HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestBody HashMap<String,Object> paramMap){
        int id = Integer.parseInt(paramMap.get("id").toString());
        String clearTime = paramMap.get("clearTime").toString();
        int type =Integer.parseInt(paramMap.get("type").toString());
        return clearDataTimeService.update(id,clearTime,type);
    }

    @GetMapping("/delete")
    public String delete(String ids){
        return clearDataTimeService.delete(ids);
    }

    @GetMapping("/getClearDataTimeById")
    public String getClearDataTimeById(int id){
        return clearDataTimeService.getClearDataTimeById(id);
    }

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords){
        return clearDataTimeService.getPageList(pageIndex,pageSize,keyWords);
    }
}
