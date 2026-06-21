package com.example.vientianetest.dao.user;

import com.example.vientianetest.config.DataConfiguration;
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
public class UserBookDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private DataConfiguration dataConfiguration;

    public int insert(int userId,int type,int level,int num) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userbook` (`userId`,`type`,`level`,`num`,`modifyTime`,`createTime`) VALUES('%s','%s','%s','%s',NOW(),NOW());",
                userId, type, level, num), new BeanPropertySqlParameterSource(new Object()), keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void appendNumByUserId(int userId,int type,int num) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("type", type);
        paramMap.put("num", num);
        jdbcOperations.update("UPDATE `wxdk_userbook` SET num = :num + num ,modifyTime=NOW() WHERE userId = :userId AND `type` = :type ", paramMap);
    }

    public void appendNumByUserId(int userId,int type,int num,int level) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("type", type);
        paramMap.put("num", num);
        paramMap.put("level", level);
        jdbcOperations.update("UPDATE `wxdk_userbook` SET num = case when :num + num <= 0 then 0 else :num + num end , level = :level + level, modifyTime=NOW() WHERE userId = :userId AND `type` = :type ", paramMap);
    }

    public Map<String, Object> getUserBookByUserId(int userId,int type) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("type", type);
        paramMap.put("maxLevel", dataConfiguration.getMaxBookLevel());
        paramMap.put("maxNum", dataConfiguration.getMaxBookNum());
        return jdbcOperations.queryForMap("SELECT a.`userId`,a.`type`,a.`level`,a.`num`,IFNULL((SELECT IFNULL(secondValue,0) FROM wxdk_uplevel WHERE IFNULL(`level`,0) = a.`level` AND `name` = 'book' LIMIT 1 ),0) AS addition,CASE WHEN a.`level` >= :maxLevel THEN :maxNum ELSE ifnull(b.value,1) END AS nextNum FROM `wxdk_userbook` a LEFT JOIN wxdk_uplevel b ON a.`level` + 1 = b.`level` AND b.`name` = 'book' WHERE a.userId = :userId and a.type = :type ", paramMap);
    }

    public List<Map<String, Object>> getUserBookListByUserId(int userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("maxLevel", dataConfiguration.getMaxBookLevel());
        paramMap.put("maxNum", dataConfiguration.getMaxBookNum());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT :userId AS userId,ifnull(a.image,'') as image,a.`type`,IFNULL(b.`level`,0) AS `level`,IFNULL(b.`num`,0) AS num,");
        sql.append("IFNULL((SELECT IFNULL(secondValue,0) FROM wxdk_uplevel WHERE IFNULL(`level`,0) = b.`level` AND `name` = 'book' LIMIT 1 ),0) AS addition,CASE WHEN b.`level` >= :maxLevel THEN :maxNum ELSE ifnull(c.value,1) END AS nextNum  ");
        sql.append("FROM wxdk_book a ");
        sql.append("LEFT JOIN wxdk_userbook b ON a.type = b.`type` AND b.userId = :userId ");
        sql.append("LEFT JOIN wxdk_uplevel c ON b.`level` + 1 = c.`level` AND c.`name` = 'book' ORDER BY a.sortId ASC ");
        return jdbcOperations.queryForList(sql.toString(),paramMap);
    }

    public List<Map<String, Object>> getUserBookListByUserIdSub(int userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        /*type case when 给前端抽奖用*/
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.userId,a.`type` as realType,b.`remark`,b.`image`,a.num , ");
        sql.append("CASE WHEN a.`type` = 2 THEN 7 WHEN a.`type` = 3 THEN 8 WHEN a.`type` = 4 THEN 9 WHEN a.`type` = 5 THEN 10 WHEN a.`type` = 6 THEN 11 WHEN a.`type` = 7 THEN 12 ELSE 0 END AS type ");
        sql.append("FROM `wxdk_userbook` a ");
        sql.append("LEFT JOIN wxdk_book b ON a.`type` = b.`type`");
        sql.append("WHERE a.userId = :userId AND a.num > 0 ORDER BY b.`sortId`; ");
        return jdbcOperations.queryForList(sql.toString(), paramMap);
    }

    public boolean isExistsByUserId(int userId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userbook` WHERE userId = :userId ",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean isExistsByUserIdSub(int userId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userbook` WHERE userId = :userId and num > 0",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean isExistsByUserId(int userId,int type) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("type",type);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userbook` WHERE userId = :userId AND `type` = :type ",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    public int getBookUpLevelCountByUserId(int userId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("maxBookLevel",dataConfiguration.getMaxBookLevel());
        return jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userbook` a WHERE a.userId = :userId AND EXISTS(SELECT 1 FROM wxdk_uplevel b WHERE IFNULL(a.`level`,0) + 1 = b.level AND a.`num` >= b.`value` AND b.`name` = 'book' AND b.`level` <= :maxBookLevel) ",paramMap,Integer.class);
    }

    //判断用户书的等级是否都满级
    public boolean isAllFullGradeByUserId(int userId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("maxLevel",dataConfiguration.getMaxBookLevel());
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_userbook WHERE userId = :userId AND `level` = :maxLevel ",paramMap,Integer.class);
        if(count >= 6){
            return true;
        }
        return false;
    }

}
