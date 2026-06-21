package com.example.vientianetest.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserFormIdDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId, String formId,int status) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userformid` (`userId`,`formId`,`status`,`modifyTime`,`createTime`) VALUES('%s','%s','%s',NOW(),NOW()) ;",
                userId,formId,status),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void appendFormId(int userId, String formId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("formId",formId);
        jdbcOperations.update("UPDATE `wxdk_userformid` SET `formId` = concat(formId,',',:formId),modifyTime = NOW() WHERE userId = :userId;",paramMap);
    }

    public void updateFormId(int userId, String formId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("formId",formId);
        jdbcOperations.update("UPDATE `wxdk_userformid` SET `formId` = :formId,modifyTime = NOW() WHERE userId = :userId;",paramMap);
    }

    public void delete(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        jdbcOperations.update("DELETE FROM `wxdk_userformid` WHERE userId = :userId;",paramMap);
    }

    public List<Map<String,Object>> getFormIdList(){
       return jdbcOperations.queryForList("SELECT a.id,a.`userId`,formId,`status`,b.`openId` FROM wxdk_userformid a INNER JOIN wxdk_user b ON a.`userId` = b.`id` WHERE DATEDIFF(DATE_FORMAT(NOW(),'%Y-%m-%d'),DATE_FORMAT(a.modifyTime,'%Y-%m-%d')) <= 1",new HashMap<>());
    }

    public boolean isExistsByUserId(int userId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_userformid WHERE userId = :userId",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;

    }
}
