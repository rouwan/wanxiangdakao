package com.example.vientianetest.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserHitCatDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    public int insert(int userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userhitcat` (`userId`,`modifyTime`,`createTime`) VALUES('%s',NOW(),NOW()) ;",
                userId),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    /*记录表*/
    public int insertRecord(int userId,int money) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userhitcatrecord` (`userId`,`money`,`modifyTime`,`createTime`) VALUES('%s','%s',NOW(),NOW()) ;",
                userId,money),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void updateTime(int userId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        jdbcOperations.update("UPDATE wxdk_userhitcat SET modifyTime = now() WHERE userId = :userId ",paramMap);
    }

    public boolean isExistsByUserId(int userId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_userhitcat WHERE userId = :userId",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    //是否撸猫过
    public boolean isMorningHashHitCatByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(1) ");
        sql.append("FROM `wxdk_userhitcat` a ");
        sql.append("WHERE a.userId = :userId ");
        sql.append("AND (DATE_FORMAT(modifyTime,'%Y-%m-%d %H:00:00') >= DATE_FORMAT(NOW(),'%Y-%m-%d 11:00:00') AND DATE_FORMAT(modifyTime,'%Y-%m-%d %H:00:00') <= DATE_FORMAT(NOW(),'%Y-%m-%d 14:00:00')) ");
        int count = jdbcOperations.queryForObject(sql.toString(),paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean isAfterHashHitCatByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(1) ");
        sql.append("FROM `wxdk_userhitcat` a ");
        sql.append("WHERE a.userId = :userId ");
        sql.append("AND (DATE_FORMAT(modifyTime,'%Y-%m-%d %H:00:00') >= DATE_FORMAT(NOW(),'%Y-%m-%d 17:00:00') AND DATE_FORMAT(modifyTime,'%Y-%m-%d %H:00:00') <= DATE_FORMAT(NOW(),'%Y-%m-%d 22:00:00'))");
        int count = jdbcOperations.queryForObject(sql.toString(),paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }


}
