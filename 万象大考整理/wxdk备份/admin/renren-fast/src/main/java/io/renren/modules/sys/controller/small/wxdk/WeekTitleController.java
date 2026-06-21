package io.renren.modules.sys.controller.small.wxdk;

import io.renren.modules.sys.service.small.wxdk.UploadFileService;
import io.renren.modules.sys.service.small.wxdk.WeekTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallVientianeTest/weekTitle")
public class WeekTitleController {
    @Autowired
    private WeekTitleService weekTitleService;
    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/add")
    public String add( HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestBody HashMap<String,Object> paramMap){
        String bigName = paramMap.get("bigName").toString();
        String smallName  = paramMap.get("smallName").toString();
        String image  = paramMap.get("image").toString();
        int status  = Integer.parseInt(paramMap.get("status").toString());
        int sortId  = Integer.parseInt(paramMap.get("sortId").toString());
        return weekTitleService.add(bigName,smallName,image,status,sortId);
    }

    @PostMapping("/update")
    public String update( HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestBody HashMap<String,Object> paramMap){
        int id  = Integer.parseInt(paramMap.get("id").toString());
        String bigName = paramMap.get("bigName").toString();
        String smallName  = paramMap.get("smallName").toString();
        String image  = paramMap.get("image").toString();
        int status  = Integer.parseInt(paramMap.get("status").toString());
        int sortId  = Integer.parseInt(paramMap.get("sortId").toString());
        return weekTitleService.update(id,bigName,smallName,image,status,sortId);
    }

    @GetMapping("/delete")
    public String delete(int id){
        return weekTitleService.delete(id);
    }

    @GetMapping("/getWeekTitleById")
    public String getWeekTitleById(int id){
        return weekTitleService.getWeekTitleById(id);
    }

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords){
        return weekTitleService.getPageList(pageIndex,pageSize,keyWords);
    }

    //region uploadImage
    @PostMapping("/uploadImage")
    public String uploadImage(HttpServletRequest req,
                              @RequestParam("file") MultipartFile[] files){
        return uploadFileService.uploadImage(req,files,"vientianeTestWeekImage");
    }
    //endregion
}
