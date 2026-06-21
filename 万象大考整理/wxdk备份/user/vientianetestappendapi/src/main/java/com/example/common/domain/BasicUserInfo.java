package com.example.common.domain;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.alibaba.fastjson.JSONObject;
import com.example.vientianetest.domain.User;

import java.util.List;
import java.util.Map;

public class BasicUserInfo extends WxMaUserInfo {
    private int userId ; // wx_id
    private int isTest;

    public BasicUserInfo(){ }

    public BasicUserInfo(int userId,String nickName,String gender,String avatarUrl,String city,String province,String country) {
        this.userId = userId;
        super.setNickName(nickName);
        super.setGender(gender);
        super.setAvatarUrl(avatarUrl);
        super.setCity(city);
        super.setProvince(province);
        super.setCountry(country);
    }

    public BasicUserInfo(String openId,int userId,String nickName,String gender,String avatarUrl,String city,String province,String country) {
        super.setOpenId(openId);
        this.userId = userId;
        super.setNickName(nickName);
        super.setGender(gender);
        super.setAvatarUrl(avatarUrl);
        super.setCity(city);
        super.setProvince(province);
        super.setCountry(country);
    }

    public BasicUserInfo(Map<String, Object> map) {
        this.userId =  map.get("id") == null ? 0 : Integer.parseInt(map.get("id").toString());
        this.isTest = map.get("isTest") == null ? 0 : Integer.parseInt(map.get("isTest").toString());
        super.setOpenId(map.get("openId") == null ? "" : map.get("openId").toString());
        super.setAvatarUrl(map.get("headImgUrl") == null ? "" : map.get("headImgUrl").toString());
        super.setNickName(map.get("nickname") == null ? "" : map.get("nickname").toString());
        super.setGender( map.get("sexDesc") == null ? "" : map.get("sexDesc").toString());
        super.setCity(map.get("city") == null ? "" : map.get("city").toString());
        super.setProvince(map.get("province") == null ? "" : map.get("province").toString());
        super.setCountry(map.get("country") == null ? "" : map.get("country").toString());
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public int getIsTest() {
        return isTest;
    }

    public void setIsTest(int isTest) {
        this.isTest = isTest;
    }

    public BasicUserInfo getUser(JSONObject map){
        this.userId = Integer.parseInt(map.get("userId").toString());
        super.setOpenId(map.get("openId").toString());
        super.setNickName(map.get("nickName").toString());
        super.setGender(map.get("gender").toString());
        super.setAvatarUrl(map.get("avatarUrl").toString());
        return this;
    }

}
