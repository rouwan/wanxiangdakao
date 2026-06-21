package io.renren.modules.sys.controller.small.wxdk;

import io.renren.modules.sys.service.small.wxdk.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallVientianeTest/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/add")
    public String add( HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestBody HashMap<String,Object> paramMap){
        String title = paramMap.get("title").toString();
        String content = paramMap.get("content").toString();
        String gift = paramMap.get("gift").toString();
        int status =Integer.parseInt(paramMap.get("status").toString());
        return emailService.add(title,content,gift,status);
    }

    @PostMapping("/update")
    public String update( HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestBody HashMap<String,Object> paramMap){
        int id = Integer.parseInt(paramMap.get("id").toString());
        String title = paramMap.get("title").toString();
        String content = paramMap.get("content").toString();
        String gift = paramMap.get("gift").toString();
        int status =Integer.parseInt(paramMap.get("status").toString());
        return emailService.update(id,title,content,gift,status);
    }

    @GetMapping("/delete")
    public String delete(String ids){
        return emailService.delete(ids);
    }

    @GetMapping("/getEmailById")
    public String getEmailById(int id){
        return emailService.getEmailById(id);
    }

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords){
        return emailService.getPageList(pageIndex,pageSize,keyWords);
    }
}
