package io.renren.modules.sys.controller.small.wxdk;

import io.renren.modules.sys.service.small.wxdk.UploadFileService;
import io.renren.modules.sys.service.small.wxdk.WeekSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallVientianeTest/weekSubject")
public class WeekSubjectController {
    @Autowired
    private WeekSubjectService weekSubjectService;
    @Autowired
    private UploadFileService uploadFileService;

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords,int typeId,int type , int status, int modeType){
        return weekSubjectService.getPageList(pageIndex,pageSize,keyWords,typeId,type,status,modeType);
    }

    @PostMapping("/add")
    public String add(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody HashMap<String,Object> paramMap
    ){
        int answer = Integer.parseInt(paramMap.get("answer").toString());
        String image = paramMap.get("image").toString();
        String audioName = paramMap.get("audioName").toString();
        String promptWord = paramMap.get("promptWord").toString();
        String optionStr = paramMap.get("optionStr").toString();
        int typeId = Integer.parseInt(paramMap.get("typeId").toString());
        int type = Integer.parseInt(paramMap.get("type").toString());
        int modeType = Integer.parseInt(paramMap.get("modeType").toString());
        int sortId = Integer.parseInt(paramMap.get("sortId").toString());
        String description = paramMap.get("description").toString();
        String createUserName = paramMap.get("createUserName").toString();
        int titleId = Integer.parseInt(paramMap.get("titleId").toString());
        int isLevelType = Integer.parseInt(paramMap.get("isLevelType").toString());
        int levelType = Integer.parseInt(paramMap.get("levelType").toString());
        return weekSubjectService.add(answer,image,audioName,promptWord,optionStr,typeId,type,modeType,sortId,description,createUserName,titleId,isLevelType,levelType);
    }

    @PostMapping("/update")
    public String update( HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestBody HashMap<String,Object> paramMap){
        int id = Integer.parseInt(paramMap.get("id").toString());
        int answer = Integer.parseInt(paramMap.get("answer").toString());
        String image = paramMap.get("image").toString();
        String audioName = paramMap.get("audioName").toString();
        String promptWord = paramMap.get("promptWord").toString();
        String optionStr = paramMap.get("optionStr").toString();
        int typeId = Integer.parseInt(paramMap.get("typeId").toString());
        int type = Integer.parseInt(paramMap.get("type").toString());
        int modeType = Integer.parseInt(paramMap.get("modeType").toString());
        int sortId = Integer.parseInt(paramMap.get("sortId").toString());
        String description = paramMap.get("description").toString();
        String createUserName = paramMap.get("createUserName").toString();
        int titleId = Integer.parseInt(paramMap.get("titleId").toString());
        int isLevelType = Integer.parseInt(paramMap.get("isLevelType").toString());
        int levelType = Integer.parseInt(paramMap.get("levelType").toString());
        return weekSubjectService.update(id,answer,image,audioName,promptWord,optionStr,typeId,type,modeType,sortId,description,createUserName,titleId,isLevelType,levelType);
    }

    @GetMapping("/getSubjectById")
    public String getSubjectById(int id){
        return weekSubjectService.getSubjectById(id);
    }

    @GetMapping("/delete")
    public String delete(String ids){
        return weekSubjectService.delete(ids);
    }

    @GetMapping("/delete2")
    public String delete2(String ids){
        return weekSubjectService.delete2(ids);
    }

    @GetMapping("/updateStatus")
    public String updateStatus(String ids,int status){
        return weekSubjectService.updateStatus(ids,status);
    }

    @GetMapping("/saveSort")
    public String saveSort(String ids , int pageIndex,int pageSize){
        return weekSubjectService.saveSort(ids,pageIndex,pageSize);
    }

    @PostMapping("/uploadExcel")
    public String uploadExcel(HttpServletRequest req,
                              @RequestParam("file") MultipartFile[] files){
        return uploadFileService.uploadWeekExcel(req,files);
    }

    //region uploadImage
    @PostMapping("/uploadImage")
    public String uploadImage(HttpServletRequest req,
                              @RequestParam("file") MultipartFile[] files){
        return uploadFileService.uploadImage(req,files,"vientianeTestImage");
    }
    //endregion

    //region uploadAudio
    @PostMapping("/uploadAudio")
    public String uploadAudio(HttpServletRequest req,
                              @RequestParam("file") MultipartFile[] files){
        return uploadFileService.uploadAudio(req,files);
    }
    //endregion
}
