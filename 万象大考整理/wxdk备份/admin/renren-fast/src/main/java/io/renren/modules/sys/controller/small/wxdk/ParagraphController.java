package io.renren.modules.sys.controller.small.wxdk;

import io.renren.modules.sys.service.small.wxdk.ParagraphService;
import io.renren.modules.sys.service.small.wxdk.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallVientianeTest/paragraph")
public class ParagraphController {
    @Autowired
    private ParagraphService paragraphService;
    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/add")
    public String add( HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestBody HashMap<String,Object> paramMap){
        String name = paramMap.get("name").toString();
        String image  = paramMap.get("image").toString();
        int level  = Integer.parseInt(paramMap.get("level").toString());
        int status  = Integer.parseInt(paramMap.get("status").toString());
        return paragraphService.add(name,image,level,status);
    }

    @PostMapping("/update")
    public String update( HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestBody HashMap<String,Object> paramMap){
        int id  = Integer.parseInt(paramMap.get("id").toString());
        String name = paramMap.get("name").toString();
        String image  = paramMap.get("image").toString();
        int level  = Integer.parseInt(paramMap.get("level").toString());
        int status  = Integer.parseInt(paramMap.get("status").toString());
        return paragraphService.update(id,name,image,level,status);
    }

    @GetMapping("/delete")
    public String delete(int id){
        return paragraphService.delete(id);
    }

    @GetMapping("/getParagraphById")
    public String getParagraphById(int id){
        return paragraphService.getParagraphById(id);
    }

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords){
        return paragraphService.getPageList(pageIndex,pageSize,keyWords);
    }

    //region uploadImage
    @PostMapping("/uploadImage")
    public String uploadImage(HttpServletRequest req,
                              @RequestParam("file") MultipartFile[] files){
        return uploadFileService.uploadImage(req,files,"vientianeTestParagraphImage");
    }
    //endregion
}
