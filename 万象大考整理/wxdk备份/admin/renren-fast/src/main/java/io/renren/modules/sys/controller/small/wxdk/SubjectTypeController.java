package io.renren.modules.sys.controller.small.wxdk;

import io.renren.modules.sys.service.small.wxdk.SubjectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallVientianeTest/subjectType")
public class SubjectTypeController {
    @Autowired
    private SubjectTypeService subjectTypeService;

    @PostMapping("/add")
    public String add(HttpServletRequest request,
                      HttpServletResponse response,
                      @RequestBody HashMap<String,Object> paramMap){
        int parentType = Integer.parseInt(paramMap.get("parentType").toString());
        String name = paramMap.get("name").toString();
        return subjectTypeService.add(parentType,name);
    }

    @GetMapping("/delete")
    public String delete(int id){
        return subjectTypeService.delete(id);
    }

    @PostMapping("/update")
    public String update(HttpServletRequest request,
                         HttpServletResponse response,
                         @RequestBody HashMap<String,Object> paramMap){
        int id = Integer.parseInt(paramMap.get("id").toString());
        int parentType = Integer.parseInt(paramMap.get("parentType").toString());
        String name = paramMap.get("name").toString();
        int sortId = Integer.parseInt(paramMap.get("sortId").toString());
        return subjectTypeService.update(id,parentType,name,sortId);
    }

    @GetMapping("/getSubjectTypeById")
    public String getSubjectTypeById(int id){
        return subjectTypeService.getSubjectTypeById(id);
    }

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords,int parentType){
        return subjectTypeService.getPageList(pageIndex,pageSize,keyWords,parentType);
    }

    @GetMapping("/getSubjectTypeByTypeId")
    public String getSubjectTypeByTypeId(int parentType){
        return subjectTypeService.getSubjectTypeByTypeId(parentType);
    }
}
