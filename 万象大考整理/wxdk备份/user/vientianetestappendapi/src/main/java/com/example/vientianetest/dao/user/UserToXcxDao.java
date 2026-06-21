package com.example.vientianetest.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//小程序统计
@Component
public class UserToXcxDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId,int type,String name, int goodType,int goodNum, int status){
        Map<String,Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_usertoxcx` (`userId`,`type`,`name`,`goodType`,`goodNum`,`status`,`modifyTime`,`createTime`) VALUES('%s','%s','%s','%s','%s','%s',NOW(),NOW()) ;",
                userId,type,name,goodType,goodNum,status),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void updateStatusByUserId(int userId,String name,int goodType, int goodNum,int status) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("name",name);
        paramMap.put("goodType",goodType);
        paramMap.put("goodNum",goodNum);
        paramMap.put("status",status);
        jdbcOperations.update("UPDATE wxdk_usertoxcx SET goodType=:goodType,goodNum=:goodNum,status=:status,modifyTime = NOW() WHERE userId = :userId AND name = :name and DATEDIFF(DATE_FORMAT(NOW(),'%Y-%m-%d'),DATE_FORMAT(createTime,'%Y-%m-%d')) = 0",paramMap);
    }

    //判断今天是否存在
    public boolean isExistsTodayByUserId(int userId,String name) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("name",name);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_usertoxcx WHERE userId =:userId AND name = :name AND DATEDIFF(DATE_FORMAT(NOW(),'%Y-%m-%d'),DATE_FORMAT(createTime,'%Y-%m-%d')) = 0",paramMap,Integer.class);
        if(count > 0) {
            return true;
        }
        return false;
    }

    //判断今天是否领取
    public boolean isExistsTodayStatusByUserId(int userId,String name,int status) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("name",name);
        paramMap.put("status",status);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_usertoxcx WHERE userId =:userId AND name = :name AND status=:status and DATEDIFF(DATE_FORMAT(NOW(),'%Y-%m-%d'),DATE_FORMAT(createTime,'%Y-%m-%d')) = 0",paramMap,Integer.class);
        if(count > 0) {
            return true;
        }
        return false;
    }

}
