package io.renren.modules.sys.controller.small.wxdk;

import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.service.small.wxdk.UserSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallVientianeTest/userSubject")
public class UserSubjectController extends AbstractController {
    @Autowired
    private UserSubjectService userSubjectService;

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords,int typeId, int status,int diffType){
        String userName = getUser().getUsername();
        return userSubjectService.getPageList(userName, pageIndex,pageSize,keyWords,typeId,status,diffType);
    }

    @GetMapping("/getSubjectById")
    public String getSubjectById(int id){
        return userSubjectService.getSubjectById(id);
    }

    @GetMapping("/getNextSubjectById")
    public String getNextSubjectById(int id){
        return userSubjectService.getNextSubjectById(id);
    }

    @GetMapping("/getPreSubjectById")
    public String getPreSubjectById(int id){
        return userSubjectService.getPreSubjectById(id);
    }

    @PostMapping("/updateRejectRemark")
    public String updateRejectRemark(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestBody HashMap<String,Object> paramMap){
        String ids = paramMap.get("ids").toString();
        String rejectRemark = paramMap.get("rejectRemark").toString();
        return userSubjectService.updateRejectRemark(ids,rejectRemark);
    }
}
