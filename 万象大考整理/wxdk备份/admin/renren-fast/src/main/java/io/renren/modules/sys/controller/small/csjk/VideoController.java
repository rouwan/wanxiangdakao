package io.renren.modules.sys.controller.small.csjk;

import io.renren.modules.sys.service.small.csjk.UploadFileService;
import io.renren.modules.sys.service.small.csjk.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallLongHealth/video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/add")
    public String add(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody HashMap<String,Object> paramMap
    ){
        String video = paramMap.get("video").toString();
        String title = paramMap.get("title").toString();
        int seconds = Integer.parseInt(paramMap.get("seconds").toString());
        int status = Integer.parseInt(paramMap.get("status").toString());
        String image =  paramMap.get("image").toString();
        int frame =  Integer.parseInt(paramMap.get("frame").toString());
        return videoService.add(video,title,seconds,status,image,frame);
    }

    @PostMapping("/update")
    public String update(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody HashMap<String,Object> paramMap
    ){
        int id = Integer.parseInt(paramMap.get("id").toString());
        String video = paramMap.get("video").toString();
        String title = paramMap.get("title").toString();
        int seconds = Integer.parseInt(paramMap.get("seconds").toString());
        int sortId = Integer.parseInt(paramMap.get("sortId").toString());
        int status = Integer.parseInt(paramMap.get("status").toString());
        String image =  paramMap.get("image").toString();
        int frame =  Integer.parseInt(paramMap.get("frame").toString());
        return videoService.update(id,video,title,seconds,sortId,status,image,frame);
    }

    @GetMapping("/delete")
    public String delete(int id){
        return videoService.delete(id);
    }

    @GetMapping("/getVideoById")
    public String getVideoById(int id){
        return videoService.getVideoById(id);
    }

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords){
        return videoService.getPageList(pageIndex,pageSize,keyWords);
    }

    @PostMapping("/uploadVideo")
    public String uploadVideo(HttpServletRequest req,
                              @RequestParam("file") MultipartFile[] files){
        return uploadFileService.uploadVideo(req,files,"longHealthVideo");
    }

    @GetMapping("/getProcessImg")
    public String getProcessImg(String filePath,int frame){

        return uploadFileService.getProcessImg(filePath,frame);
    }

}
