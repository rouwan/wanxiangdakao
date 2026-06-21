package com.example.vientianetest.web;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.common.permission.config.PermissionConstants;
import com.example.common.permission.config.RequiredPermission;
import com.example.vientianetest.redis.SessionRedis;
import com.example.vientianetest.service.user.UserDaySignService;
import com.example.vientianetest.service.user.UserHitCatService;
import com.example.vientianetest.service.user.UserLuckNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/vientianetest/mobile/dayWelfare")
@RestController
public class DayWelfareController {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private UserDaySignService userDaySignService;
    @Autowired
    private UserHitCatService userHitCatService;
    @Autowired
    private UserLuckNumberService userLuckNumberService;

    @PostMapping("/getDayWelfareList")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String getDayWelfareList(HttpServletRequest request, int type) {

        BasicUserInfo sessionUser = sessionRedis.getAttribute(request.getCookies());
        int userId = sessionUser.getUserId();
        BasicResultObject obj = new BasicResultObject();
        //处理每日签到
        if (type == 1) {
            List<Map<String, Object>> mapList = userDaySignService.getDaySignList(userId);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("daySignList", mapList);
            resultMap.put("isHitCat", userHitCatService.isHitCatByUserId(userId));
            resultMap.put("isLuckNumber", userLuckNumberService.isLuckNumberByUserId(userId));
            obj.Result = resultMap;
            System.out.println("每日福利测试：类型"+type+"返回值："+JSONObject.toJSONString(obj));
            return JSONObject.toJSONString(obj);
        }
        //处理幸运数字
        else if (type == 3) {
            return userLuckNumberService.getLuckNumberList(userId);
        }
        return "未知";
    }

    @PostMapping("/sign")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String sign(HttpServletRequest request, int type, int day) {
        System.out.println("验证是否领取过了，类型："+type+" 日期："+day);
        return userDaySignService.sign(request, type, day);
    }

    @PostMapping("/hitCat")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String hitCat(HttpServletRequest request, int money) {
        return userHitCatService.hitCat(request, money);
    }

    @PostMapping("/numberReceive")
    @RequiredPermission(PermissionConstants.TAGSIGNOUT)
    public String numberReceive(HttpServletRequest request, int number) {
        return userLuckNumberService.numberReceive(request, number);
    }

}
