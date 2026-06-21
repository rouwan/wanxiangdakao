package com.example.vientianetest.dao.user;

import com.example.vientianetest.config.DataConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserFriendDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private DataConfiguration dataConfiguration;

    public int insert(int userId,int withUserId,int isNew){
        Map<String,Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userfriend` (`userId`,`withUserId`,`isNew`,`modifyTime`,`createTime`) VALUES('%s','%s','%s',NOW(),NOW()) ;" ,
                userId,withUserId,isNew),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void updateStatus(int userId,int withUserId,int goodType,int goodNum,int status) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("withUserId",withUserId);
        paramMap.put("goodType",goodType);
        paramMap.put("goodNum",goodNum);
        paramMap.put("status",status);
        StringBuilder sql = new StringBuilder();
        sql.append("update `wxdk_userfriend` ");
        sql.append("SET `goodType` = :goodType , ");
        sql.append("`goodNum` = :goodNum,");
        sql.append("`status` = :status,");
        sql.append("`modifyTime` = NOW() ");
        sql.append("WHERE `userId` = :userId AND withUserId = :withUserId ;");
        jdbcOperations.update(sql.toString(),paramMap);
    }

    public Map<String,Object> getRankListByUserId(String userIds , int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userIds",userIds);
        paramMap.put("userId",userId);
        paramMap.put("maxParagraph", dataConfiguration.getMaxParagraph());
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ( ");
        sb.append("SELECT (@i:=@i+1) AS `index` ,a.* FROM ( ");
        sb.append("SELECT b.id AS userId,b.nickName ,b.`headImgUrl`, IFNULL(d.name,c.name) AS `paragraphName`,IFNULL(d.image,c.image) AS `paragraphImage`,IFNULL(d.level,c.level) AS `level`,IFNULL(d.star,a.`star`) AS star,IFNULL(b.province,'') AS province,IFNULL(b.city,'') AS city ");
        sb.append("FROM wxdk_user b ");
        sb.append("LEFT JOIN wxdk_userparagraph a ON a.`userId` = b.id ");
        sb.append("LEFT JOIN `wxdk_paragraph` c ON c.level = IFNULL(a.`paragraph`,1) ");
        sb.append("LEFT JOIN wxdk_paragraph d ON a.`paragraph`-1 = d.level AND a.isRefine = 1 AND a.paragraph <= :maxParagraph AND a.star <=0 ");//试炼状态
        sb.append("WHERE b.id in ("+userIds+") ORDER BY a.mmr DESC ");
        sb.append(") a,(SELECT @i:=0) t ) a ");
        sb.append("WHERE a.userId = "+userId+"; ");
        return jdbcOperations.queryForMap(sb.toString(),paramMap);
    }

    //排位
    public List<Map<String,Object>> getRankListByUserIds(String userIds){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userIds",userIds);
        paramMap.put("maxParagraph", dataConfiguration.getMaxParagraph());
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT b.id AS userId,b.nickName ,b.`headImgUrl`, IFNULL(d.name,c.name) AS `paragraphName`,IFNULL(d.image,c.image) AS `paragraphImage`,IFNULL(d.level,c.level) AS `level`,IFNULL(d.star,a.`star`) AS star,IFNULL(b.province,'') AS province,IFNULL(b.city,'') AS city  ");
        sb.append("FROM wxdk_user b ");
        sb.append("LEFT JOIN wxdk_userparagraph a ON a.`userId` = b.id ");
        sb.append("LEFT JOIN `wxdk_paragraph` c ON c.level = IFNULL(a.`paragraph`,1) ");
        sb.append("LEFT JOIN wxdk_paragraph d ON a.`paragraph`-1 = d.level AND a.isRefine = 1 AND a.paragraph <= :maxParagraph AND a.star <=0 ");//试炼状态
        sb.append("WHERE b.id in ("+userIds+") ORDER BY a.mmr DESC LIMIT 100; ");
        return jdbcOperations.queryForList(sb.toString(),paramMap);
    }

    //获取全部人的好友列表初始化redis
    public List<Map<String,Object>> getFriendList() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT userId,GROUP_CONCAT(friendList) AS friendList FROM ( ");
        sql.append("SELECT a.userId,");
        sql.append("(SELECT GROUP_CONCAT(CASE WHEN b.`userId` = a.`userId` THEN b.withUserId ELSE b.userId END) AS friend FROM wxdk_userfriend b WHERE b.`userId` = a.`userId`) AS friendList ");
        sql.append("FROM wxdk_userfriend a GROUP BY a.userId ");
        sql.append("UNION ALL ");
        sql.append("SELECT a.withUserId, ");
        sql.append("(SELECT GROUP_CONCAT(CASE WHEN b.`withUserId` = a.`withUserId` THEN b.userId ELSE b.withUserId END) AS friend FROM wxdk_userfriend b WHERE b.`withUserId` = a.`withUserId` ) AS friendList ");
        sql.append("FROM wxdk_userfriend a GROUP BY a.withUserId ");
        sql.append(") a ");
        sql.append("GROUP BY a.userId ");
        return jdbcOperations.queryForList(sql.toString(), new HashMap<>());
    }

    public List<Map<String,Object>> getNewFriendList(int userId , int num) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("num",num);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("a.id,a.withUserId as userId,b.`nickname`,b.`headImgUrl`,a.`status` ");
        sql.append("FROM `wxdk_userfriend` a ");
        sql.append("LEFT JOIN wxdk_user b ON a.`withUserId` = b.`id` ");
        sql.append("WHERE a.userId = :userId  AND a.isNew = 1 ORDER BY a.id LIMIT :num ");
        return jdbcOperations.queryForList(sql.toString(), paramMap);
    }

    public boolean isExistsFriendByUserId(int userId,int withUserId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("withUserId",withUserId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userfriend` WHERE (`userId`=:userId AND `withUserId` = :withUserId) OR (`userId`=:withUserId AND `withUserId` = :userId); ",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean isStatusNewFriendByUserId(int userId, int withUserId, int status){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("withUserId",withUserId);
        paramMap.put("status",status);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userfriend` WHERE userId=:userId and withUserId=:withUserId  and status = :status and isNew = 1; ",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }
}
