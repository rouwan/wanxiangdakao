package io.renren.modules.sys.controller.small.wxdk;

import io.renren.modules.sys.service.small.wxdk.LuckNumberService;
import io.renren.modules.sys.service.small.wxdk.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallVientianeTest/luckNumber")
public class LuckNumberController {
    @Autowired
    private LuckNumberService luckNumberService;

    @PostMapping("/add")
    public String add( HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestBody HashMap<String,Object> paramMap){
        int number  = Integer.parseInt(paramMap.get("number").toString());
        int goodType  = Integer.parseInt(paramMap.get("goodType").toString());
        int num  = Integer.parseInt(paramMap.get("num").toString());
        int isLookNum  = Integer.parseInt(paramMap.get("isLookNum").toString());
        String description = paramMap.get("description").toString();
        return luckNumberService.add(number,goodType,num,isLookNum,description);
    }

    @PostMapping("/update")
    public String update( HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestBody HashMap<String,Object> paramMap){
        int id  = Integer.parseInt(paramMap.get("id").toString());
        int number  = Integer.parseInt(paramMap.get("number").toString());
        int goodType  = Integer.parseInt(paramMap.get("goodType").toString());
        int num  = Integer.parseInt(paramMap.get("num").toString());
        int isLookNum  = Integer.parseInt(paramMap.get("isLookNum").toString());
        String description = paramMap.get("description").toString();
        int sortId  = Integer.parseInt(paramMap.get("sortId").toString());
        return luckNumberService.update(id,number,goodType,num,isLookNum,description,sortId);
    }

    @GetMapping("/delete")
    public String delete(int id){
        return luckNumberService.delete(id);
    }

    @GetMapping("/getLuckNumberById")
    public String getLuckNumberById(int id){
        return luckNumberService.getLuckNumberById(id);
    }

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords){
        return luckNumberService.getPageList(pageIndex,pageSize,keyWords);
    }
}
