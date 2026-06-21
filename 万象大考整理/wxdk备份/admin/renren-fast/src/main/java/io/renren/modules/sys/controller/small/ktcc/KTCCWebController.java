package io.renren.modules.sys.controller.small.ktcc;

import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.service.small.ktcc.KTCCWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallLookGuess")
/**
 * 小程序：看图猜词
 */
public class KTCCWebController extends AbstractController {

    @Autowired
    private KTCCWebService  ktccWebService;

    //region getSubjectList
    @GetMapping("/getSubjectList")
    public String getSubjectList(int pageIndex,
                                 int pageSize,
                                 String keyWords,
                                 int type ,
                                 int status,
                                 int modeType ){
       return  ktccWebService.getSubjectList(pageIndex,pageSize,keyWords,type,status,modeType);
    }
    //endregion

    //region addSubject
    @PostMapping("/addSubject")
    public String addSubject(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody HashMap<String,Object> paramMap
            ){
        String answer = paramMap.get("answer").toString();
        String image = paramMap.get("image").toString();
        String audioName = paramMap.get("audioName").toString();
        String promptWord = paramMap.get("promptWord").toString();
        String optionStr = paramMap.get("optionStr").toString();
        String type = paramMap.get("type").toString();
        String status = paramMap.get("status").toString();
        int modeType = Integer.parseInt(paramMap.get("modeType").toString());
        int sortId = Integer.parseInt(paramMap.get("sortId").toString());
        String description = paramMap.get("description").toString();
        return ktccWebService.addSubject(answer,image,audioName,promptWord,optionStr,type,status,modeType,sortId,description);
    }
    //endregion

    //region updateSubjectById
    @PostMapping("/updateSubjectById")
    public String updateSubjectById( HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestBody HashMap<String,Object> paramMap){
        int id = Integer.parseInt(paramMap.get("id").toString());
        String answer = paramMap.get("answer").toString();
        String image = paramMap.get("image").toString();
        String audioName = paramMap.get("audioName").toString();
        String promptWord = paramMap.get("promptWord").toString();
        String optionStr = paramMap.get("optionStr").toString();
        String type = paramMap.get("type").toString();
        String status = paramMap.get("status").toString();
        String sortId = paramMap.get("sortId").toString();
        int modeType = Integer.parseInt(paramMap.get("modeType").toString());
        String description = paramMap.get("description").toString();
        return ktccWebService.updateSubjectById(id,answer,image,audioName,promptWord,optionStr,type,status,sortId,modeType,description);
    }
    //endregion

    //region delSubejctById
    @GetMapping("/delSubejctById")
    public String delSubejctById(String ids){
        return ktccWebService.delSubejctById(ids);
    }
    //endregion

    //region updateSubjectStatus
    @GetMapping("/updateSubjectStatus")
    public String updateSubjectStatus(String ids,int status){
        return ktccWebService.updateSubjectStatus(ids,status);
    }
    //endregion

    //region uploadImage
    @PostMapping("/uploadImage")
    public String uploadImage(HttpServletRequest req,
                              @RequestParam("file") MultipartFile[] files){
        return ktccWebService.uploadImage(req,files);
    }
    //endregion

    //region uploadAudio
    @PostMapping("/uploadAudio")
    public String uploadAudio(HttpServletRequest req,
                              @RequestParam("file") MultipartFile[] files){
        return ktccWebService.uploadAudio(req,files);
    }
    //endregion

    //region getSubjectById
    @GetMapping("/getSubjectById")
    public String getSubjectById(int id){
        return ktccWebService.getSubjectById(id);
    }
    //endregion

    //region saveSort
    @GetMapping("/saveSort")
    public String saveSort(String ids , int pageIndex,int pageSize){
         return ktccWebService.saveSort(ids,pageIndex,pageSize);
    }
    //endregion

    //region getSubjectRatio
    @GetMapping("/getSubjectRatio")
    public String getSubjectRatio(int pageIndex, int pageSize,int orderType,String keyWords){
        return ktccWebService.getSubjectRatio(pageIndex,pageSize,orderType,keyWords);
    }
    //endregion

    //region uploadExcel
    @PostMapping("/uploadExcel")
    public String uploadExcel(HttpServletRequest req,
                              @RequestParam("file") MultipartFile[] files){
        return ktccWebService.uploadExcel(req,files);
    }
    //endregion

}
