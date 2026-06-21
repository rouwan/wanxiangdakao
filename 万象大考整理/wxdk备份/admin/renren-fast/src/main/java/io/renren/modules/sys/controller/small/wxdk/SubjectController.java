package io.renren.modules.sys.controller.small.wxdk;

import io.renren.modules.sys.service.small.wxdk.SubjectService;
import io.renren.modules.sys.service.small.wxdk.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallVientianeTest/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private UploadFileService uploadFileService;

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords,int typeId,int type , int status, int modeType,int diffType){
        return subjectService.getPageList(pageIndex,pageSize,keyWords,typeId,type,status,modeType,diffType);
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
        int isLevelType = Integer.parseInt(paramMap.get("isLevelType").toString());
        int levelType = Integer.parseInt(paramMap.get("levelType").toString());
        return subjectService.add(answer,image,audioName,promptWord,optionStr,typeId,type,modeType,sortId,description,createUserName,isLevelType,levelType);
    }

    @PostMapping("/updateSubjectById")
    public String updateSubjectById( HttpServletRequest request,
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
        int isLevelType = Integer.parseInt(paramMap.get("isLevelType").toString());
        int levelType = Integer.parseInt(paramMap.get("levelType").toString());
        return subjectService.updateSubjectById(id,answer,image,audioName,promptWord,optionStr,typeId,type,modeType,sortId,description,createUserName,isLevelType,levelType);
    }

    @PostMapping("/updateSubjectTypeById")
    public String updateSubjectTypeById(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestBody HashMap<String,Object> paramMap){
        String ids = paramMap.get("ids").toString();
        int typeId = Integer.parseInt(paramMap.get("typeId").toString());
        int isLevelType = Integer.parseInt(paramMap.get("isLevelType").toString());
        int levelType = Integer.parseInt(paramMap.get("levelType").toString());
        return subjectService.updateSubjectTypeById(ids,typeId,isLevelType,levelType);
    }

    @GetMapping("/getSubjectById")
    public String getSubjectById(int id){
        return subjectService.getSubjectById(id);
    }

    @GetMapping("/getSubjectBySortId")
    public String getSubjectBySortId(int sortId){
        return subjectService.getSubjectBySortId(sortId);
    }

    @GetMapping("/delete")
    public String delete(String ids){
        return subjectService.delete(ids);
    }

    @GetMapping("/updateStatus")
    public String updateStatus(String ids,int status){
        return subjectService.updateStatus(ids,status);
    }

    @GetMapping("/saveSort")
    public String saveSort(String ids , int pageIndex,int pageSize){
        return subjectService.saveSort(ids,pageIndex,pageSize);
    }

    @PostMapping("/uploadExcel")
    public String uploadExcel(HttpServletRequest req,
                              @RequestParam("file") MultipartFile[] files){
        return uploadFileService.uploadExcel(req,files);
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

    //region getModeTypeNumList
    @GetMapping("/getModeTypeNumList")
    public String getModeTypeNumList(HttpServletRequest req){
        return subjectService.getModeTypeNumList();
    }
    //endregion

    //region batchUpdateStatus
    @GetMapping("/batchUpdateStatus")
    public String batchUpdateStatus(HttpServletRequest req,int modeType, int num){
        return subjectService.batchUpdateStatus(modeType,num);
    }
    //endregion
}
