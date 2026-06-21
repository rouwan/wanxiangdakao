package io.renren.modules.sys.controller.small.wxdk;

import io.renren.modules.sys.service.small.wxdk.BookService;
import io.renren.modules.sys.service.small.wxdk.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallVientianeTest/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/add")
    public String add( HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestBody HashMap<String,Object> paramMap){
        int type  = Integer.parseInt(paramMap.get("type").toString());
        String image = paramMap.get("image").toString();
        String remark = paramMap.get("remark").toString();
        int sortId  = Integer.parseInt(paramMap.get("sortId").toString());
        return bookService.add(type,image,remark,sortId);
    }

    @PostMapping("/update")
    public String update( HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestBody HashMap<String,Object> paramMap){
        int id  = Integer.parseInt(paramMap.get("id").toString());
        int type  = Integer.parseInt(paramMap.get("type").toString());
        String image = paramMap.get("image").toString();
        String remark = paramMap.get("remark").toString();
        int sortId  = Integer.parseInt(paramMap.get("sortId").toString());
        return bookService.update(id,type,image,remark,sortId);
    }

    @GetMapping("/delete")
    public String delete(int id){
        return bookService.delete(id);
    }

    @GetMapping("/getBookById")
    public String getBookById(int id){
        return bookService.getBookById(id);
    }

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords){
        return bookService.getPageList(pageIndex,pageSize,keyWords);
    }

    //region uploadImage
    @PostMapping("/uploadImage")
    public String uploadImage(HttpServletRequest req,
                              @RequestParam("file") MultipartFile[] files){
        return uploadFileService.uploadImage(req,files,"vientianeTestBookImage");
    }
    //endregion
}
