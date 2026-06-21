package io.renren.modules.sys.controller.small.wxdk;

import io.renren.modules.sys.service.small.wxdk.GoodsService;
import io.renren.modules.sys.service.small.wxdk.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallVientianeTest/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/add")
    public String add( HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestBody HashMap<String,Object> paramMap){
        int type  = Integer.parseInt(paramMap.get("type").toString());
        String image = paramMap.get("image").toString();
        String name = paramMap.get("name").toString();
        return goodsService.add(type,image,name);
    }

    @PostMapping("/update")
    public String update( HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestBody HashMap<String,Object> paramMap){
        int id  = Integer.parseInt(paramMap.get("id").toString());
        int type  = Integer.parseInt(paramMap.get("type").toString());
        String image = paramMap.get("image").toString();
        String name = paramMap.get("name").toString();
        return goodsService.update(id,type,image,name);
    }

    @GetMapping("/delete")
    public String delete(int id){
        return goodsService.delete(id);
    }

    @GetMapping("/getGoodsById")
    public String getGoodsById(int id){
        return goodsService.getGoodsById(id);
    }

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords){
        return goodsService.getPageList(pageIndex,pageSize,keyWords);
    }

    //region uploadImage
    @PostMapping("/uploadImage")
    public String uploadImage(HttpServletRequest req,
                              @RequestParam("file") MultipartFile[] files){
        return uploadFileService.uploadImage(req,files,"vientianeTestGoodsImage");
    }
    //endregion
}
