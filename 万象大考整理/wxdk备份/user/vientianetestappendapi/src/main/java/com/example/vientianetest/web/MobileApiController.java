package com.example.vientianetest.web;

import com.alibaba.fastjson.JSONObject;
import com.example.vientianetest.redis.subject.SubjectRedis;
import com.example.vientianetest.service.user.UserParagraphService;
import com.example.vientianetest.service.user.UserService;
import com.example.vientianetest.service.user.UserSystemService;
import com.example.vientianetest.template.HitCatTemplate;
import com.example.common.domain.BasicResultObject;
import com.example.common.permission.config.PermissionConstants;
import com.example.common.permission.config.RequiredPermission;
import com.example.vientianetest.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("/vientianetest/mobile")
@RestController
public class MobileApiController {
    private static final Logger logger = LoggerFactory.getLogger(MobileApiController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private UserSystemService userSystemService;
    @Autowired
    private FeedBackUserService feedBackUserService;
    @Autowired
    private MessageBoardService messageBoardService;
    @Autowired
    private UserParagraphService userParagraphService;
    @Autowired
    private FeedBackSubjectService feedBackSubjectService;
    @Autowired
    private HitCatTemplate hitCatTemplate;
    @Autowired
    private SubjectRedis subjectRedis;

    //region login
    @GetMapping("/login")
    public String login(HttpServletRequest request, String code){
        return userService.login(request,code);
    }
    //endregion

    //region authorize
    @PostMapping("/authorize")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String authorize(
            HttpServletRequest request,
            String nickName,
            String avatarUrl,
            String gender,
            String city,
            String province,
            String country
    )
    {
        return userService.authorize(request,nickName,avatarUrl,gender,city,province,country);
    }
    //endregion

    //region init
    @PostMapping("/init")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String init(HttpServletRequest request,
                       @RequestParam(value="userId",required = false,defaultValue = "0") int userId ,
                       @RequestParam(value="isNew",required = false,defaultValue = "0") int isNew) {
        return userService.init(request, userId, isNew);
    }

    //endregion

    //region system
    @PostMapping("/system")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String system(HttpServletRequest request,
                         String brand,
                         String model,
                         String pixelRatio,
                         String screenWidth,
                         String screenHeight,
                         String windowWidth,
                         String windowHeight,
                         String statusBarHeight,
                         String language,
                         String version,
                         String system,
                         String platform,
                         String fontSizeSetting,
                         String SDKVersion)
    {
        return userSystemService.insert(request,brand,model,pixelRatio,screenWidth,screenHeight,windowWidth,windowHeight,
                                        statusBarHeight,language,version,system,platform,fontSizeSetting,SDKVersion);
    }
    //endregion

    //region feedBackSubject
    @PostMapping("/feedBackSubject")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String feedBackSubject( HttpServletRequest request,int subjectId, String types , String remark){
        return feedBackSubjectService.insert(request,subjectId,types,remark);
    }
    //endregion

    //region feedBackUser
    @PostMapping("/feedBackUser")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String feedBackUser(HttpServletRequest request, int userId , String types){
        return feedBackUserService.insert(request,userId,types);
    }
    //endregion

    //region wsLogin
    /***
     * webSocket登录前调用全局user
     */
    @PostMapping("/wsLogin")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String wsLogin(HttpServletRequest request,int type){
        return userService.wsLogin(request,type);
    }
    //endregion

    //region getSeasonParagraphInfo
    @PostMapping("/getSeasonParagraphInfo")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getSeasonParagraphInfo(HttpServletRequest request){
        return userParagraphService.getSeasonParagraphInfo(request);
    }
    //endregion

    //region addMessageBoard
    @PostMapping("/addMessageBoard")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String addMessageBoard(HttpServletRequest request,String message){
        return messageBoardService.addMessageBoard(request,message);
    }
    //endregion

    //region getMessageBoardList
    @PostMapping("/getMessageBoardList")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getMessageBoardList(HttpServletRequest request,
                                      @RequestParam(value="pageIndex",required = false,defaultValue = "0") int pageIndex,
                                      @RequestParam(value="pageSize",required = false,defaultValue = "30") int pageSize){
        return messageBoardService.getMessageBoardList(request,pageIndex,pageSize);
    }
    //endregion

    //@GetMapping("/updateSubjectDb")
    //@RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String updateSubjectDb(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        subjectRedis.initSubject();
        logger.info("updateSubjectDb 更新题库");
        return JSONObject.toJSONString(new BasicResultObject());
    }
}
