package io.renren.modules.sys.controller.small;

import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.service.small.SQCSWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


@RestController
@RequestMapping("/smallMagictest")
/**
 * 小程序：神奇的测试
 */
public class SQCSWebController extends AbstractController {

    @Autowired
    private SQCSWebService sqcsWebService;

    //region titleList
    @GetMapping("/titleList")
    public String titleList(String keyWords,int pageIndex, int pageSize ){
        return sqcsWebService.titleList(keyWords,pageIndex,pageSize);
    }
    //endregion

    //region createTitle
    @PostMapping("/createTitle")
    public String createTitle(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody HashMap<String,Object> paramMap
    ){
        //response.setHeader("Access-Control-Allow-Origin", "*");
        String type = paramMap.get("type").toString();
        String titleName = paramMap.get("titleName").toString();
        String titleDescription = paramMap.get("titleDescription").toString();
        String titleImageUrl= paramMap.get("titleImageUrl").toString();
        String subjectStr = paramMap.get("subjectStr").toString();
        String answerStr = paramMap.get("answerStr").toString();
        String optionStr = paramMap.get("optionStr").toString();
        String titleInitRead = paramMap.get("titleInitRead").toString();
        return sqcsWebService.createTitle(type,titleName,titleDescription,titleImageUrl,subjectStr,answerStr,optionStr,titleInitRead);
    }
    //endregion

    //region updateTitleById
    @PostMapping("/updateTitleById")
    public String updateTitleById(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestBody HashMap<String,Object> paramMap){
        int id =Integer.parseInt(paramMap.get("id").toString());
        String type = paramMap.get("type").toString();
        String titleName = paramMap.get("titleName").toString();
        String titleDescription = paramMap.get("titleDescription").toString();
        String titleImageUrl= paramMap.get("titleImageUrl").toString();
        String subjectStr = paramMap.get("subjectStr").toString();
        String answerStr = paramMap.get("answerStr").toString();
        String optionStr = paramMap.get("optionStr").toString();
        String sortId = paramMap.get("sortId").toString();
        String titleInitRead = paramMap.get("titleInitRead").toString();
       return sqcsWebService.updateTitleById(id,type,titleName,titleDescription,titleImageUrl,subjectStr,answerStr,optionStr,sortId,titleInitRead);
    }
    //endregion

    //region delTitleById
    @GetMapping("/delTitleById")
    public String delTitleById(int id){
        return sqcsWebService.delTitleById(id);
    }
    //endregion

    //region getTitleById
    @GetMapping("/getTitleById")
    public  String getTitleById(int id){
        return sqcsWebService.getTitleById(id);
    }

    //endregion

    //region uploadImage
    @PostMapping("/uploadImage")
    public String uploadImage(HttpServletRequest req,
                              @RequestParam("file") MultipartFile[] files){
        return sqcsWebService.uploadImage(files);
    }
    //endregion
}
