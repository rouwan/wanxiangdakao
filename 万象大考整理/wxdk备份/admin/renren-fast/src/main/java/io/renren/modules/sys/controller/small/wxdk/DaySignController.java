package io.renren.modules.sys.controller.small.wxdk;

import io.renren.modules.sys.service.small.wxdk.DaySignService;
import io.renren.modules.sys.service.small.wxdk.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallVientianeTest/daySign")
public class DaySignController {
    @Autowired
    private DaySignService daySignService;
    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/add")
    public String add( HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestBody HashMap<String,Object> paramMap){
        int day  = Integer.parseInt(paramMap.get("day").toString());
        int goodType  = Integer.parseInt(paramMap.get("goodType").toString());
        int num  = Integer.parseInt(paramMap.get("num").toString());
        int isLookNum  = Integer.parseInt(paramMap.get("isLookNum").toString());
        String description = paramMap.get("description").toString();
        return daySignService.add(day,goodType,num,isLookNum,description);
    }

    @PostMapping("/update")
    public String update( HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestBody HashMap<String,Object> paramMap){
        int id  = Integer.parseInt(paramMap.get("id").toString());
        int day  = Integer.parseInt(paramMap.get("day").toString());
        int goodType  = Integer.parseInt(paramMap.get("goodType").toString());
        int num  = Integer.parseInt(paramMap.get("num").toString());
        int isLookNum  = Integer.parseInt(paramMap.get("isLookNum").toString());
        String description = paramMap.get("description").toString();
        return daySignService.update(id,day,goodType,num,isLookNum,description);
    }

    @GetMapping("/delete")
    public String delete(int id){
        return daySignService.delete(id);
    }

    @GetMapping("/getDaySignById")
    public String getDaySignById(int id){
        return daySignService.getDaySignById(id);
    }

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords){
        return daySignService.getPageList(pageIndex,pageSize,keyWords);
    }
}
