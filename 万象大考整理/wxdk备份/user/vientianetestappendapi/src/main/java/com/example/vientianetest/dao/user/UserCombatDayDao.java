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
public class UserCombatDayDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId, int combat){
        Map<String,Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_usercombatday` (`userId`,`combat`) VALUES('%s','%s') ;",
                userId,combat),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void appendCeByUserId(int userId, int combat) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("combat",combat);
        jdbcOperations.update("UPDATE wxdk_usercombatday SET combat = combat + :combat,modifyTime = NOW() WHERE userId = :userId ",paramMap);
    }

    public Map<String,Object> getUserCombatDayByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ( ");
        sql.append("SELECT CASE WHEN id = 0 THEN -1 ELSE (@i:=@i+1) END AS `index`,a.* FROM ( ");
        sql.append("SELECT IFNULL(a.id,0) AS id,b.id AS `userId`,IFNULL(b.`nickname`,'') AS nickName , IFNULL(b.`headImgUrl`,'') AS headImgUrl,IFNULL(a.`combat`,0) AS combat,IFNULL(b.province,'') AS province,IFNULL(b.city,'') AS city ");
        sql.append("FROM wxdk_user b ");
        sql.append("LEFT JOIN `wxdk_usercombatday` a ON a.`userId` = b.`id` ");
        sql.append("ORDER BY a.combat DESC,a.id ASC ");
        sql.append(") a,(SELECT @i:=0) t ");
        sql.append(") a ");
        sql.append("WHERE a.userId = :userId limit 1");
        return  jdbcOperations.queryForMap(sql.toString(),paramMap);
    }

    public List<Map<String,Object>> getUserCombatDayRankList(int num) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("num",num);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.`userId`,IFNULL(b.`nickname`,'') AS nickName , IFNULL(b.`headImgUrl`,'') AS headImgUrl,IFNULL(a.`combat`,0) AS combat,IFNULL(b.province,'') AS province,IFNULL(b.city,'') AS city ");
        sb.append("FROM `wxdk_usercombatday` a ");
        sb.append("INNER JOIN wxdk_user b ON a.`userId` = b.`id` ");
        sb.append("ORDER BY a.combat DESC,a.id ASC LIMIT :num ");
        return jdbcOperations.queryForList(sb.toString(),paramMap);
    }

    public boolean isExistsByUserId(int userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_usercombatday WHERE userId = :userId ",paramMap,Integer.class);
        if(count > 0) {
            return true;
        }
        return false;
    }


}
