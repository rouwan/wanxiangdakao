package com.example.vientianetest.dao.user;

import com.example.common.domain.BasicUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insertOpenId(String openId){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO wxdk_user(openId,createTime) VALUES('%s',now());",openId),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public int update(BasicUserInfo userInfo){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userInfo.getUserId());
        //paramMap.put("unionId",userInfo.getUnionId());
        paramMap.put("nickName",userInfo.getNickName());
        paramMap.put("sexDesc",userInfo.getGender().equals("1") ? "男" : "女");
        paramMap.put("city",userInfo.getCity());
        paramMap.put("province",userInfo.getProvince());
        paramMap.put("country",userInfo.getCountry());
        paramMap.put("headImgUrl",userInfo.getAvatarUrl());
        StringBuilder sql = new StringBuilder();
        sql.append("update wxdk_user ");
        sql.append("set nickname = :nickName,sexDesc = :sexDesc,city = :city , province = :province, country = :country,headImgUrl = :headImgUrl,modifyTime = now() ");
        sql.append("where id = :userId;");
        return jdbcOperations.update(sql.toString(),paramMap);
    }

    public Map<String,Object> getUserInfoByOpenId(String openId){
        Map<String, Object> paramMap = new HashMap<>();
        System.out.println("测试用户登录"+openId);

        paramMap.put("openId", openId);
        return jdbcOperations.queryForMap("select id,openId,IFNULL(unionId,'') AS unionId,IFNULL(nickname,'') AS nickname,IFNULL(sexDesc,'') AS sexDesc,IFNULL(city,'') AS city,IFNULL(province,'') AS province,IFNULL(country,'') AS country,IFNULL(headImgUrl,'') AS headImgUrl,IFNULL(subscribeTime,'') AS subscribeTime,isTest from wxdk_user where openId = :openId",paramMap);
    }

    public Map<String,Object> getUserInfoById(int id){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return jdbcOperations.queryForMap("select id,openId,IFNULL(unionId,'') AS unionId,IFNULL(nickname,'') AS nickname,IFNULL(sexDesc,'') AS sexDesc,IFNULL(city,'') AS city,IFNULL(province,'') AS province,IFNULL(country,'') AS country,IFNULL(headImgUrl,'') AS headImgUrl,IFNULL(subscribeTime,'') AS subscribeTime,isTest from wxdk_user where id = :id",paramMap);
    }

    //是否已授权
    public Boolean isUpdateByUserId(BasicUserInfo userInfo){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userInfo.getUserId());
        //paramMap.put("unionId",userInfo.getUnionId());
        paramMap.put("nickName",userInfo.getNickName());
        paramMap.put("sexDesc",userInfo.getGender().equals("1") ? "男" : "女");
        paramMap.put("city",userInfo.getCity());
        paramMap.put("province",userInfo.getProvince());
        paramMap.put("country",userInfo.getCountry());
        paramMap.put("headImgUrl",userInfo.getAvatarUrl());
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_user WHERE id = :userId AND (IFNULL(nickName,'') != :nickName  OR IFNULL(headImgUrl,'') != :headImgUrl OR IFNULL(sexDesc,'') != :sexDesc OR IFNULL(city,'') != :city OR IFNULL(province,'') != :province OR IFNULL(country,'') != :country )",paramMap,Integer.class);
        if(count > 0 ){
            return true;
        }
        return false;
    }

    public Boolean isExistsByOpenId(String openId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("openId", openId);
        int count = jdbcOperations.queryForObject("select count(1) from wxdk_user where openId = :openId", paramMap, Integer.class);
        if (count > 0) {
            return true;
        }
        return false;
    }
}
