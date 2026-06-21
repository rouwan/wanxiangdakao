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
public class UserLuckNumberDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId, int number,int status,int isWin) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userlucknumber` (`userId`,`number`,`num`,`status`,`isWin`,`modifyTime`,`createTime`) VALUES('%s','%s','%s','%s','%s',NOW(),NOW()) ;",
                userId,number,1,status,isWin),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void appendNumByUserId(int userId, int number, int status, int isWin) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("number", number);
        paramMap.put("status", status);
        paramMap.put("isWin", isWin);
        jdbcOperations.update("UPDATE wxdk_userlucknumber SET status= :status,num = num + 1,isWin=:isWin,modifyTime = now() WHERE userId = :userId and number = :number ",paramMap);
    }

    public void updateStatus(int userId, int number, int status) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("number", number);
        paramMap.put("status", status);
        jdbcOperations.update("UPDATE wxdk_userlucknumber SET status= :status,modifyTime = now() WHERE userId = :userId and number = :number ",paramMap);
    }

    public List<Map<String,Object>> getLuckNumberList(){
        Map<String,Object> paramMap = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.id,a.number,a.goodType as type,a.num,a.isLookNum,a.description,IFNULL(b.image,'') AS image,b.name ");
        sql.append("FROM wxdk_lucknumber a ");
        sql.append("LEFT JOIN `wxdk_goods` b ON a.goodType = b.`type` ");
        sql.append("WHERE a.flag < 3 ORDER BY a.`sortId` ");
        List<Map<String,Object>> listMap = jdbcOperations.queryForList(sql.toString(),paramMap);
        return listMap;
    }

    public List<Map<String,Object>> getLuckNumberList(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.id,a.number,a.goodType AS `type`,a.num,a.isLookNum,a.description,IFNULL(b.image,'') AS image,b.name,IFNULL(c.status,-1) AS `status`,IFNULL(c.isWin,0) AS `isWin` ");
        sql.append("FROM wxdk_lucknumber a ");
        sql.append("LEFT JOIN `wxdk_goods` b ON a.goodType = b.`type` ");
        sql.append("LEFT JOIN wxdk_userlucknumber c ON a.`number` = c.`number` AND c.userId = :userId ");
        sql.append("WHERE a.flag < 3 ORDER BY a.`sortId`; ");
        List<Map<String,Object>> listMap = jdbcOperations.queryForList(sql.toString(),paramMap);
        return listMap;
    }

    public Map<String, Object> getUserLuckNumberByNumber(int userId,int number){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("number",number);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.id,a.`userId`,b.`goodType`,b.`num`,b.`isLookNum`,b.`description`,a.`status`,a.`number`,a.`isWin` ");
        sql.append("FROM wxdk_userlucknumber a ");
        sql.append("LEFT JOIN wxdk_lucknumber b ON a.number = b.`number` ");
        sql.append("WHERE a.`userId` = :userId AND a.`number` = :number LIMIT 1 ");
        return jdbcOperations.queryForMap(sql.toString(),paramMap);
    }

    public Map<String, Object> getLuckNumberByNumber(int number){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("number",number);
        return jdbcOperations.queryForMap("SELECT * FROM wxdk_lucknumber WHERE number = :number AND flag < 3 limit 1",paramMap);
    }

    public boolean isExistsByUserId(int userId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userlucknumber` WHERE userId = :userId ",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean isExistsByNumber(int userId,int number) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("number",number);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userlucknumber` WHERE userId = :userId and number = :number",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean isLuckNumberNoReceiveByUserId(int userId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_lucknumber a INNER JOIN wxdk_userlucknumber b ON a.`number` = b.`number` WHERE  b.userId = :userId AND a.flag < 3 AND b.`status` = 0",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }
}
