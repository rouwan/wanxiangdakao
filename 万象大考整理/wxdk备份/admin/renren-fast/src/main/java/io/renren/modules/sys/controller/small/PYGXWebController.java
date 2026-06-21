package io.renren.modules.sys.controller.small;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.domain.BasicResultObject;
import io.renren.modules.sys.service.small.PYGXWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/friendrelationship")
/**
 * 小程序：朋友关系
 */
public class PYGXWebController {

    @Autowired
    private PYGXWebService pygxWebService;

    //region getContentList
    @GetMapping("/getContentList")
    public String getContentList(int pageIndex, int pageSize,String keyWords){
        return pygxWebService.getContentList(pageIndex,pageSize,keyWords);
    }
    //endregion

    //region addContent
    @PostMapping("/addContent")
    public String addContent(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody HashMap<String,Object> paramMap
            ){
        String relation = paramMap.get("relation").toString();
        int type = Integer.parseInt(paramMap.get("type").toString());
        String image = paramMap.get("image").toString();
        String writing = paramMap.get("writing").toString();
        return pygxWebService.addContent(relation, type, image, writing);
    }
    //endregion

    //region updateContent
    @PostMapping("/updateContent")
    public String updateContent(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody HashMap<String,Object> paramMap
            ){
        int id = Integer.parseInt(paramMap.get("id").toString());
        String relation = paramMap.get("relation").toString();
        int type = Integer.parseInt(paramMap.get("type").toString());
        String image = paramMap.get("image").toString();
        String writing = paramMap.get("writing").toString();
        return pygxWebService.updateContent(id, relation, type, image, writing);
    }
    //endregion

    //region deleteContent
    @GetMapping("/deleteContent")
    public String deleteContent(int id){
        return pygxWebService.deleteContent(id);
    }
    //endregion

    //region updateContentStatus
    @GetMapping("/updateContentStatus")
    public String updateContentStatus(String ids,int status){
        return pygxWebService.updateContentStatus(ids,status);
    }
    //endregion

    //region uploadImage
    @PostMapping("/uploadImage")
    public String uploadImage(HttpServletRequest req,
                              @RequestParam("file") MultipartFile[] files){
        return pygxWebService.uploadImage(files);
    }
    //endregion

    //region getContentById
    @GetMapping("/getContentById")
    public String getContentById(int id){
        return pygxWebService.getContentById(id);
    }
    //endregion
}
