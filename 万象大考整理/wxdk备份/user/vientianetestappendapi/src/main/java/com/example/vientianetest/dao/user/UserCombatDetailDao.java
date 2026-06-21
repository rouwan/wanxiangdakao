package com.example.vientianetest.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserCombatDetailDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId, int type, int combat){
        Map<String,Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_usercombatdetail` (`userId`,`type`,`combat`) VALUES('%s','%s','%s') ;",
                                            userId,type,combat),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void appendCeByUserId(int userId, int type,int combat) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("type",type);
        paramMap.put("combat",combat);
        jdbcOperations.update("UPDATE wxdk_usercombatdetail SET combat = combat + :combat , modifyTime = now() WHERE userId = :userId AND type = :type",paramMap);
    }

    public List<Map<String,Object>> getUserCombatDetailByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        return jdbcOperations.queryForList("SELECT type as parentType,combat FROM wxdk_usercombatdetail WHERE userId = :userId",paramMap);
    }

    public boolean isExistsByUserId(int userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_usercombatdetail WHERE userId = :userId ",paramMap,Integer.class);
        if(count > 0) {
            return true;
        }
        return false;
    }

    public boolean isExistsByUserId(int userId, int type) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("type",type);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_usercombatdetail WHERE userId = :userId and type = :type ",paramMap,Integer.class);
        if(count > 0) {
            return true;
        }
        return false;
    }

}
