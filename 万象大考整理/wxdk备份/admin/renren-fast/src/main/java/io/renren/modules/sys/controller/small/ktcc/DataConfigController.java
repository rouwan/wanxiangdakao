package io.renren.modules.sys.controller.small.ktcc;

import io.renren.modules.sys.service.small.ktcc.DataConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallLookGuess/dataConfig")

//系统数据配置
public class DataConfigController {
    @Autowired
    private DataConfigService dataConfigService;

    @PostMapping("/add")
    public String add( HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestBody HashMap<String,Object> paramMap){
        String name = paramMap.get("name").toString();
        String value = paramMap.get("value").toString();
        String description = paramMap.get("description").toString();
        int type =Integer.parseInt(paramMap.get("type").toString());
        return dataConfigService.add(name,value,description,type);
    }

    @PostMapping("/update")
    public String update( HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestBody HashMap<String,Object> paramMap){
        int id = Integer.parseInt(paramMap.get("id").toString());
        String name = paramMap.get("name").toString();
        String value = paramMap.get("value").toString();
        String description = paramMap.get("description").toString();
        int type =Integer.parseInt(paramMap.get("type").toString());
        return dataConfigService.update(id,name,value,description,type);
    }

    @GetMapping("/delete")
    public String delete(String ids){
        return dataConfigService.delete(ids);
    }

    @GetMapping("/getDataConfigById")
    public String getDataConfigById(int id){
        return dataConfigService.getDataConfigById(id);
    }

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords){
        return dataConfigService.getPageList(pageIndex,pageSize,keyWords);
    }
}
