package io.renren.modules.sys.controller.small.ktcc;

import io.renren.modules.sys.service.small.ktcc.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/smallLookGuess/testUser")

//测试用户
public class TestUserController {
    @Autowired
    private TestUserService testUserService;

    @PostMapping("/update")
    public String update(HttpServletRequest request,
                         HttpServletResponse response,
                         @RequestBody HashMap<String,Object> paramMap
                         ){

        int userId = Integer.parseInt(paramMap.get("userId").toString());
        int strengthCount=Integer.parseInt(paramMap.get("strengthCount").toString());
        int cardNumber = Integer.parseInt(paramMap.get("cardNumber").toString());
        int type = Integer.parseInt(paramMap.get("type").toString());
        return testUserService.update(userId,strengthCount,cardNumber,type);
    }

    @GetMapping("/delete")
    public String delete(int userId){
        return testUserService.delete(userId);
    }

    @GetMapping("/getUserInfoByName")
    public String getUserInfoByName(String name){
        return testUserService.getUserInfoByName(name);
    }

    @GetMapping("/getPageList")
    public String getPageList(int pageIndex , int pageSize , String keyWords){
        return testUserService.getPageList(pageIndex,pageSize,keyWords);
    }

}
