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
public class UserCombatDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId, int combat){
        Map<String,Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_usercombat` (`userId`,`combat`) VALUES('%s','%s') ;",
                userId,combat),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void appendCeByUserId(int userId, int combat) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("combat",combat);
        jdbcOperations.update("UPDATE wxdk_usercombat SET combat = combat + :combat,modifyTime = NOW() WHERE userId = :userId ",paramMap);
    }

    public Map<String,Object> getUserCombatByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        return  jdbcOperations.queryForMap("SELECT IFNULL(combat,0) AS combat FROM wxdk_usercombat WHERE userId = :userId",paramMap);
    }

    public List<Map<String,Object>> getUserCombatRankList(int num) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("num",num);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.`userId`,IFNULL(b.`nickname`,'') AS nickName , IFNULL(b.`headImgUrl`,'') AS headImgUrl,IFNULL(a.`combat`,0) AS combat ");
        sb.append("FROM `wxdk_usercombat` a ");
        sb.append("INNER JOIN wxdk_user b ON a.`userId` = b.`id` ");
        sb.append("ORDER BY a.combat DESC LIMIT :num ");
        return jdbcOperations.queryForList(sb.toString(),paramMap);
    }

    public List<Map<String,Object>> initUserCombatRankList(int num) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("num",num);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT b.`id` as userId,IFNULL(b.`nickname`,'') AS nickName , IFNULL(b.`headImgUrl`,'') AS headImgUrl,IFNULL(a.`combat`,0) AS combat ");
        sb.append("FROM wxdk_user b ");
        sb.append("LEFT JOIN `wxdk_usercombat` a ON a.`userId` = b.`id` ");
        sb.append("ORDER BY b.createTime ASC LIMIT :num ");
        return jdbcOperations.queryForList(sb.toString(),paramMap);
    }

    public List<Map<String,Object>> getUserCombatListByUserIds(String userIds){
        Map<String,Object> paramMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.`userId`,IFNULL(b.`nickname`,'') AS nickName , IFNULL(b.`headImgUrl`,'') AS headImgUrl,IFNULL(a.`combat`,0) AS combat,IFNULL(b.province,'') AS province,IFNULL(b.city,'') AS city ");
        sb.append("FROM `wxdk_usercombat` a   ");
        sb.append("LEFT JOIN wxdk_user b ON a.userId = b.`id` ");
        sb.append("WHERE a.userId in ("+userIds+") ORDER BY FIELD(a.userId,"+userIds+");");
        return jdbcOperations.queryForList(sb.toString(),paramMap);
    }

    public Map<String,Object> getUserCombatByUserIdSub(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.`userId`,IFNULL(b.`nickname`,'') AS nickName , IFNULL(b.`headImgUrl`,'') AS headImgUrl,IFNULL(a.`combat`,0) AS combat ");
        sb.append("FROM wxdk_user b ");
        sb.append("LEFT JOIN `wxdk_usercombat` a ON a.userId = b.`id` ");
        sb.append("WHERE a.userId = :userId;");
        return jdbcOperations.queryForMap(sb.toString(),paramMap);
    }

    public boolean isExistsByUserId(int userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_usercombat WHERE userId = :userId ",paramMap,Integer.class);
        if(count > 0) {
            return true;
        }
        return false;
    }
//    public Map<String,Object> getUserCombatByUserId(int userId){
//        Map<String,Object> paramMap = new HashMap<>();
//        paramMap.put("userId",userId);
//        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT * FROM ( ");
//        sql.append("SELECT CASE WHEN id = 0 THEN -1 ELSE (@i:=@i+1) END AS `index`,a.* FROM ( ");
//        sql.append("SELECT IFNULL(a.id,0) AS id,b.id AS `userId`,IFNULL(b.`nickname`,'') AS nickName , IFNULL(b.`headImgUrl`,'') AS headImgUrl,IFNULL(a.`combat`,0) AS combat,IFNULL(b.province,'') AS province,IFNULL(b.city,'') AS city ");
//        sql.append("FROM wxdk_user b ");
//        sql.append("LEFT JOIN `wxdk_usercombat` a ON a.`userId` = b.`id` ");
//        sql.append("ORDER BY a.combat DESC,a.id ASC ");
//        sql.append(") a,(SELECT @i:=0) t ");
//        sql.append(") a ");
//        sql.append("WHERE a.userId = :userId limit 1");
//        return  jdbcOperations.queryForMap(sql.toString(),paramMap);
}
