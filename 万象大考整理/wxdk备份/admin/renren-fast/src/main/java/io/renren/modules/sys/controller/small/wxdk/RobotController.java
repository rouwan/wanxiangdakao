package io.renren.modules.sys.controller.small.wxdk;

import io.renren.modules.sys.service.small.wxdk.RobotService;
import io.renren.modules.sys.service.small.wxdk.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallVientianeTest/robot")

public class RobotController {
    @Autowired
    private RobotService robotService;
    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/add")
    public String add( HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestBody HashMap<String,Object> paramMap){
        String nickName = paramMap.get("nickName").toString();
        String sexDesc  = paramMap.get("sexDesc").toString();
        String headImgUrl  = paramMap.get("headImgUrl").toString();
        int type  = Integer.parseInt(paramMap.get("type").toString());
        int status  = Integer.parseInt(paramMap.get("status").toString());
        String country  = paramMap.get("country").toString();
        String province  = paramMap.get("province").toString();
        String city  = paramMap.get("city").toString();
        float simpleRatio  = Float.valueOf(paramMap.get("simpleRatio").toString());
        float commonRatio  = Float.valueOf(paramMap.get("commonRatio").toString());
        float hardRatio  = Float.valueOf(paramMap.get("hardRatio").toString());
        String simpleTime  = paramMap.get("simpleTime").toString();
        String commonTime  = paramMap.get("commonTime").toString();
        String hardTime  = paramMap.get("hardTime").toString();
        int paragraph  = Integer.parseInt(paramMap.get("paragraph").toString());
        int star  = Integer.parseInt(paramMap.get("star").toString());
        return robotService.add(nickName,sexDesc,headImgUrl,type,status,country,province,city,simpleRatio,commonRatio,hardRatio,simpleTime,commonTime,hardTime,paragraph,star);
    }

    @PostMapping("/update")
    public String update( HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestBody HashMap<String,Object> paramMap){
        int id  = Integer.parseInt(paramMap.get("id").toString());
        String nickName = paramMap.get("nickName").toString();
        String sexDesc  = paramMap.get("sexDesc").toString();
        String headImgUrl  = paramMap.get("headImgUrl").toString();
        int type  = Integer.parseInt(paramMap.get("type").toString());
        int status  = Integer.parseInt(paramMap.get("status").toString());
        String country  = paramMap.get("country").toString();
        String province  = paramMap.get("province").toString();
        String city  = paramMap.get("city").toString();
        float simpleRatio  = Float.valueOf(paramMap.get("simpleRatio").toString());
        float commonRatio  = Float.valueOf(paramMap.get("commonRatio").toString());
        float hardRatio  = Float.valueOf(paramMap.get("hardRatio").toString());
        String simpleTime  = paramMap.get("simpleTime").toString();
        String commonTime  = paramMap.get("commonTime").toString();
        String hardTime  = paramMap.get("hardTime").toString();
        int paragraph  = Integer.parseInt(paramMap.get("paragraph").toString());
        int star  = Integer.parseInt(paramMap.get("star").toString());
        return robotService.update(id,nickName,sexDesc,headImgUrl,type,status,country,province,city,simpleRatio,commonRatio,hardRatio,simpleTime,commonTime,hardTime,paragraph,star);
    }

    @GetMapping("/delete")
    public String delete(int id){
        return robotService.delete(id);
    }

    @GetMapping("/getRobotById")
    public String getRobotById(int id){
        return robotService.getRobotById(id);
    }

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize ,int type, String keyWords){
        return robotService.getPageList(pageIndex,pageSize,type,keyWords);
    }

    //region uploadImage
    @PostMapping("/uploadImage")
    public String uploadImage(HttpServletRequest req,
                              @RequestParam("file") MultipartFile[] files){
        return uploadFileService.uploadImage(req,files,"vientianeTestRobotImage");
    }
    //endregion
}
