package io.renren.modules.sys.controller.small.wxdk;

import io.renren.modules.sys.service.small.wxdk.PrizeRatioService;
import io.renren.modules.sys.service.small.wxdk.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallVientianeTest/prize")
public class PrizeRatioController {
    @Autowired
    private PrizeRatioService prizeRatioService;
    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/add")
    public String add( HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestBody HashMap<String,Object> paramMap){
        int minValue  = Integer.parseInt(paramMap.get("minValue").toString());
        int maxValue  = Integer.parseInt(paramMap.get("maxValue").toString());
        int num  = Integer.parseInt(paramMap.get("num").toString());
        String description = paramMap.get("description").toString();
        int goodType  = Integer.parseInt(paramMap.get("goodType").toString());
        return prizeRatioService.add(minValue,maxValue,num,goodType,description);
    }

    @PostMapping("/update")
    public String update( HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestBody HashMap<String,Object> paramMap){
        int id  = Integer.parseInt(paramMap.get("id").toString());
        int minValue  = Integer.parseInt(paramMap.get("minValue").toString());
        int maxValue  = Integer.parseInt(paramMap.get("maxValue").toString());
        int num  = Integer.parseInt(paramMap.get("num").toString());
        String description = paramMap.get("description").toString();
        int goodType  = Integer.parseInt(paramMap.get("goodType").toString());
        return prizeRatioService.update(id,minValue,maxValue,num,goodType,description);
    }

    @GetMapping("/delete")
    public String delete(int id){
        return prizeRatioService.delete(id);
    }

    @GetMapping("/getPrizeById")
    public String getPrizeById(int id){
        return prizeRatioService.getPrizeById(id);
    }

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords){
        return prizeRatioService.getPageList(pageIndex,pageSize,keyWords);
    }

    //region uploadImage
    @PostMapping("/uploadImage")
    public String uploadImage(HttpServletRequest req,
                              @RequestParam("file") MultipartFile[] files){
        return uploadFileService.uploadImage(req,files,"vientianeTestPrizeImage");
    }
    //endregion
}
