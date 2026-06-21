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
public class UserEmailDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId,int emailId,int isRead,int isReceive){
        Map<String,Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_useremail` (`userId`,`emailId`,`isRead`,`isReceive`,`modifyTime`,`createTime`) VALUES('%s','%s','%s','%s',NOW(),NOW()) ;" ,
                userId,emailId,isRead,isReceive),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void updateStatus(int userId,int emailId,int isRead,int isReceive){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("emailId",emailId);
        paramMap.put("isRead",isRead);
        paramMap.put("isReceive",isReceive);
        StringBuilder sql = new StringBuilder();
        sql.append("update `wxdk_useremail` ");
        sql.append("SET `isRead` = CASE WHEN :isRead >= 0 THEN :isRead ELSE isRead END , ");
        sql.append("`isReceive` = CASE WHEN :isReceive >= 0 THEN :isReceive ELSE isReceive END ,");
        sql.append("`modifyTime` = NOW() ");
        sql.append("WHERE `userId` = :userId AND emailId = :emailId ;");
        jdbcOperations.update(sql.toString(),paramMap);
    }

    public List<Map<String,Object>> getEmailListByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.`id`,a.`title`,CASE WHEN a.isGroup = 1 THEN a.`content` ELSE IFNULL(b.description,a.content) END AS content,a.`gift`,DATE_FORMAT(a.`createTime`,'%Y-%m-%d %H:%i') AS createTime ,IFNULL(b.`isRead`,0) AS isRead,IFNULL(b.`isReceive`,0) AS isReceive ");
        sql.append("FROM `wxdk_email` a ");
        sql.append("LEFT JOIN wxdk_useremail b ON a.id = b.`emailId` AND b.`userId` = :userId ");
        sql.append("WHERE ((TIMESTAMPDIFF(DAY,DATE_FORMAT(a.createTime,'%Y-%m-%d'),NOW()) <= 7 AND a.`isGroup` = 1) OR (a.`isGroup` = 0 AND b.`userId`= :userId)) AND a.`status` = 1 AND a.`flag` < 3  ");
        sql.append("ORDER BY a.id DESC ");
        return jdbcOperations.queryForList(sql.toString(),paramMap);
    }

    public Map<String,Object> getNoReadNumByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        return jdbcOperations.queryForMap("SELECT IFNULL(SUM(CASE WHEN IFNULL(b.id,0) = 0 OR IFNULL(b.`isRead`,0) = 0  THEN 1 ELSE 0 END),0) AS emailNoReadNum FROM `wxdk_email` a LEFT JOIN `wxdk_useremail` b ON a.id = b.`emailId` AND b.`userId` = :userId WHERE ((TIMESTAMPDIFF(DAY,DATE_FORMAT(a.createTime,'%Y-%m-%d'),NOW()) <= 7 AND a.`isGroup` = 1) OR (a.`isGroup` = 0 AND b.`userId`= :userId)) AND a.`status` = 1 AND a.`flag` < 3",paramMap);
    }

    public Map<String,Object> getEmailById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return jdbcOperations.queryForMap("SELECT `id`,`title`,`content`,`gift` FROM `wxdk_email` WHERE id = :id",paramMap);
    }

    public boolean isExists(){
        Map<String,Object> paramMap = new HashMap<>();
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_email` WHERE flag < 3 and status = 1",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean isExistsByUserId(int userId,int emailId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("emailId",emailId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_useremail` WHERE emailId = :emailId and userId = :userId",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    //是够已经领取
    public boolean isExistsReceiveById(int emailId , int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("emailId",emailId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_useremail` WHERE emailId = :emailId and userId = :userId and ifnull(isReceive,0) = 1",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }


}
