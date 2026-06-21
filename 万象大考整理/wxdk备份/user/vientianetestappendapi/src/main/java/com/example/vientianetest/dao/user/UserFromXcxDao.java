package com.example.vientianetest.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserFromXcxDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId,String from, int num){
        Map<String,Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userfromxcx` (`userId`,`from`,`num`) VALUES('%s','%s','%s') ;",
                                userId,from,num),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void appendNumByUserId(int userId,String from, int num) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("from",from);
        paramMap.put("num",num);
        jdbcOperations.update("UPDATE wxdk_userfromxcx SET num = num + :num ,modifyTime = NOW() WHERE userId = :userId AND `from` = :from ",paramMap);
    }

    public boolean isExistsByUserId(int userId,String from) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("from",from);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_userfromxcx WHERE userId = :userId AND `from` = :from ",paramMap,Integer.class);
        if(count > 0) {
            return true;
        }
        return false;
    }
}
