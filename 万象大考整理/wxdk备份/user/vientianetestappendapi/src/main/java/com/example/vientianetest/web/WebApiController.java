package com.example.vientianetest.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/vientianetest/web")
@RestController
public class WebApiController {

    //公众号备用网页
    @GetMapping("/versionLowTips")
    public String versionLowTips(){
        return "由于您当前微信客户端版本过低，不能跳转小程序，请升级到最新的版本！谢谢您的配合！";
    }
}
