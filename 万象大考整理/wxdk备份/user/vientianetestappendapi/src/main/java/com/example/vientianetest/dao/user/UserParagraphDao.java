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
public class UserParagraphDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private DataConfiguration dataConfiguration;

    public int insert(int userId,double paragraph,int star,double mmr){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userparagraph` (`userId`, `paragraph`,`star`,`mmr`) VALUES('%s','%s','%s','%s') ;",
                userId,paragraph,star,mmr),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void update(int userId,double paragraph,int star,double mmr,int totalNum, int winNum,String play, int isReceive, int isRefine,int receiveStar){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("paragraph",paragraph);
        paramMap.put("star",star);
        paramMap.put("mmr",mmr);
        paramMap.put("totalNum",totalNum);
        paramMap.put("winNum",winNum);
        paramMap.put("play",play);
        paramMap.put("isReceive",isReceive);
        paramMap.put("isRefine",isRefine);
        paramMap.put("receiveStar",receiveStar);
        paramMap.put("maxParagraph",dataConfiguration.getMaxParagraph());
        paramMap.put("maxStar",dataConfiguration.getMaxStar());
        jdbcOperations.update("UPDATE `wxdk_userparagraph` SET `paragraph` = case when :paragraph>:maxParagraph then :maxParagraph else :paragraph end ,`star` = case when :star > :maxStar and :paragraph != :maxParagraph then :maxStar else :star end,`mmr` = :mmr,totalNum=:totalNum,winNum=:winNum,play=:play,isReceive=:isReceive,isRefine=:isRefine,receiveStar=:receiveStar,`modifyTime` = NOW() WHERE `userId` = :userId ;",paramMap);
    }

    //mmr追加
    public void update2(int userId,double paragraph,int star,double mmr){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("paragraph",paragraph);
        paramMap.put("star",star);
        paramMap.put("mmr",mmr);
        paramMap.put("maxParagraph",dataConfiguration.getMaxParagraph());
        paramMap.put("maxStar",dataConfiguration.getMaxStar());
        jdbcOperations.update("UPDATE `wxdk_userparagraph` SET `paragraph` = case when :paragraph>:maxParagraph then :maxParagraph else :paragraph end ,`star` = case when :star > :maxStar and :paragraph != :maxParagraph then :maxStar else :star end,`mmr` = mmr+:mmr,`modifyTime` = NOW() WHERE `userId` = :userId ;",paramMap);
    }

    public void updateOverTimePlayStar(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE wxdk_userparagraph ");
        sql.append("SET paragraph = CASE WHEN star <= 0 AND `paragraph` > 1 THEN paragraph - 1 ELSE paragraph END , ");
        sql.append("star = CASE WHEN star <= 0 AND `paragraph` > 1 THEN 3 WHEN star = 0 AND paragraph = 1 THEN star ELSE star - 1 END,");
        sql.append("modifyTime = NOW() ");
        sql.append("WHERE userId = :userId;");
        jdbcOperations.update(sql.toString(),paramMap);
    }

    public void appendValueByUserId(int userId,double paragraph,int star,double mmr){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("paragraph",paragraph);
        paramMap.put("star",star);
        paramMap.put("mmr",mmr);
        paramMap.put("maxParagraph",dataConfiguration.getMaxParagraph());
        paramMap.put("maxStar",dataConfiguration.getMaxStar());
        jdbcOperations.update("UPDATE `wxdk_userparagraph` SET `paragraph` = case when paragraph+:paragraph > :maxParagraph then :maxParagraph else paragraph+:paragraph end ,`star` = case when star + :star > :maxStar then 0 else star + :star end, `mmr` = mmr + :mmr ,`modifyTime` = NOW() WHERE `userId` = :userId ;",paramMap);
    }

    public List<Map<String,Object>> getSeasonParagraphByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sql = new StringBuilder();
        //新版排位
        sql.append("SELECT a.id AS userId,c.name AS paragraphName,c.image AS paragraphImage,c.level, ");
        sql.append("CASE WHEN c.level < b.paragraph THEN c.star WHEN c.level > b.paragraph THEN 0 ELSE IFNULL(b.`star`,0) END AS star, ");
        sql.append("IFNULL(b.`totalNum`,0) AS totalNum,IFNULL(b.`winNum`,0) AS winNum, ");
        sql.append("CASE WHEN c.level != b.paragraph THEN '' ELSE IFNULL(b.play,'') END AS play, ");
        sql.append("CASE WHEN c.level < b.paragraph THEN 2  WHEN c.level > b.paragraph THEN 0 ELSE IFNULL(b.isReceive,0) END AS isReceive, ");
        sql.append("CASE WHEN c.level != b.paragraph THEN 0 ELSE IFNULL(b.`isRefine`,0) END AS isRefine, ");
        sql.append("c.`star` AS maxStar,c.`goodType`,c.`goodNum`,c.`totalNum` AS maxTotalNum,c.`winNum` AS maxWinNum,b.receiveStar ");
        sql.append("FROM wxdk_user a ");
        sql.append("LEFT JOIN wxdk_userparagraph b ON a.id = b.userId ");
        sql.append(",wxdk_paragraph c  ");
        sql.append("WHERE a.`id` = :userId AND c.flag < 3 ");
        sql.append("ORDER BY c.level; ");
        return jdbcOperations.queryForList(sql.toString(),paramMap);
    }

    public Map<String,Object> getInfoByUserId(int userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("maxParagraph", dataConfiguration.getMaxParagraph());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.id,a.`userId`,a.`mmr`, ");
        sql.append("IFNULL(c.level,b.level) AS `level`,IFNULL(c.level,b.level) AS `paragraph`,IFNULL(c.name,b.name) AS `name`,IFNULL(c.image,b.image) AS `image`,IFNULL(c.star,a.`star`) AS star, ");
        sql.append("a.paragraph AS realParagraph,b.name AS realName,b.image AS realImage,a.star AS realStar, ");
        sql.append("UNIX_TIMESTAMP(a.`createTime`) AS createTime,a.`totalNum`,a.`winNum`,IFNULL(a.`play`,'') AS play,a.isReceive,receiveStar,a.isRefine  ");
        sql.append("FROM `wxdk_userparagraph` a ");
        sql.append("LEFT JOIN `wxdk_paragraph` b ON a.`paragraph` = b.level ");
        sql.append("LEFT JOIN wxdk_paragraph c ON a.`paragraph`-1 = c.level AND a.isRefine = 1 AND a.paragraph <= :maxParagraph AND a.star <=0 ");//试炼状态
        sql.append("WHERE userId = :userId LIMIT 1 ");
        return jdbcOperations.queryForMap(sql.toString(), paramMap);
        //return jdbcOperations.queryForMap("SELECT a.id,a.`userId`,a.`mmr`,a.paragraph AS `level`,a.`paragraph`,a.`star`,IFNULL(b.name,'') AS `name`,IFNULL(b.`image`,'') AS image,UNIX_TIMESTAMP(a.`createTime`) AS createTime,a.`totalNum`,a.`winNum`,IFNULL(a.`play`,'') AS play,a.isReceive,receiveStar,a.isRefine FROM `wxdk_userparagraph` a LEFT JOIN `wxdk_paragraph` b ON a.`paragraph` = b.level WHERE userId = :userId LIMIT 1",paramMap);
    }

    public Map<String,Object> getParagraphInfoByLevel(int level){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("level",level);
        return jdbcOperations.queryForMap("SELECT * FROM `wxdk_paragraph`  WHERE level = :level LIMIT 1",paramMap);
    }

    public  List<Map<String,Object>> getBigParagraphList(){
        return jdbcOperations.queryForList("SELECT a.name AS paragraphName,image AS paragraphImage FROM `wxdk_paragraph` a WHERE a.isRefine = 0 ORDER BY `level` DESC",new HashMap<>());
    }

    public List<Map<String,Object>> getRankListByUserIds(String userIds){
        System.out.println("userParagraphDao.getRankListByUserIds"+userIds);
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("maxParagraph", dataConfiguration.getMaxParagraph());
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.`userId`,b.nickName ,b.`headImgUrl`,IFNULL(d.name,c.name) AS `paragraphName`,IFNULL(d.image,c.image) AS `paragraphImage`,IFNULL(d.level,c.level) AS `level`,IFNULL(d.star,a.`star`) AS star,IFNULL(b.province,'') AS province,IFNULL(b.city,'') AS city ");
        sb.append("FROM `wxdk_userparagraph` a  ");
        sb.append("LEFT JOIN wxdk_user b ON a.`userId` = b.id ");
        sb.append("LEFT JOIN `wxdk_paragraph` c ON c.level = a.`paragraph` ");
        sb.append("LEFT JOIN wxdk_paragraph d ON a.`paragraph`-1 = d.level AND a.isRefine = 1 AND a.paragraph <= :maxParagraph AND a.star <=0 ");
        sb.append("WHERE a.userId in ("+userIds+") ORDER BY FIELD(a.userId,"+userIds+");");
        System.out.println("排行榜追踪+sb.tostring"+sb.toString());
        return jdbcOperations.queryForList(sb.toString(),paramMap);
    }

    public Map<String,Object> getRankListByUserIdSub(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("maxParagraph",dataConfiguration.getMaxParagraph());
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.`userId`,b.nickName ,b.`headImgUrl`, IFNULL(d.name,c.name) AS `paragraphName`,IFNULL(d.image,c.image) AS `paragraphImage`,IFNULL(d.level,c.level) AS `level`, IFNULL(d.star,a.`star`) AS star,IFNULL(b.province,'') AS province,IFNULL(b.city,'') AS city ");
        sb.append("FROM wxdk_user b ");
        sb.append("LEFT JOIN `wxdk_userparagraph` a ON a.`userId` = b.id ");
        sb.append("LEFT JOIN `wxdk_paragraph` c ON c.level = ifnull(a.`paragraph`,1) ");
        sb.append("LEFT JOIN wxdk_paragraph d ON a.`paragraph`-1 = d.level AND a.isRefine = 1 AND a.paragraph <= :maxParagraph AND a.star <=0 ");
        sb.append("WHERE a.userId = :userId LIMIT 1");
        return jdbcOperations.queryForMap(sb.toString(),paramMap);
    }

    public List<Map<String,Object>> getRankList(int num){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("num",num);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.`userId`,b.nickname ,b.`headImgUrl`, c.name AS paragraphName,c.image as paragraphImage,c.`level`,a.star,a.mmr ");
        sb.append("FROM `wxdk_userparagraph` a  ");
        sb.append("LEFT JOIN wxdk_user b ON a.`userId` = b.id ");
        sb.append("LEFT JOIN `wxdk_paragraph` c ON c.level = a.`paragraph` ");
        sb.append("WHERE LENGTH(nickname) > 0 ");
        sb.append("ORDER BY a.`mmr` DESC,a.id ASC LIMIT :num ");
        System.out.println("查询语句为"+sb.toString());
        System.out.println("查询的数据结果为："+jdbcOperations.queryForList(sb.toString(),paramMap));
        return jdbcOperations.queryForList(sb.toString(),paramMap);
    }

    public Map<String,Object> getRankListByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.`userId`,b.nickname ,b.`headImgUrl`, c.name AS paragraphName,c.image as paragraphImage,c.`level`, a.star, ");
        sb.append("IFNULL((SELECT a.index FROM (SELECT (@i:=@i+1) `index`,a.userId  FROM (SELECT a.userId FROM wxdk_userparagraph a LEFT JOIN wxdk_paragraph b ON b.level = a.`paragraph` LEFT JOIN wxdk_user c ON a.`userId` = c.`id` WHERE LENGTH(c.nickname) > 0 ORDER BY a.`paragraph`DESC,a.star DESC,a.modifyTime ASC LIMIT 1000) a,(SELECT @i:=0)  c) a WHERE a.userId = :userId LIMIT 1),-1) AS `index` ");
        sb.append("FROM `wxdk_userparagraph` a ");
        sb.append("LEFT JOIN wxdk_user b ON a.`userId` = b.id  ");
        sb.append("LEFT JOIN `wxdk_paragraph` c ON c.level = a.`paragraph`  ");
        sb.append("WHERE a.userId = :userId LIMIT 1");
        System.out.println("查询语句为"+sb.toString());
        System.out.println("查询的数据结果为："+jdbcOperations.queryForList(sb.toString(),paramMap));
        return jdbcOperations.queryForMap(sb.toString(),paramMap);
    }

    //赛季清空
    public void seasonTimeClear(){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE wxdk_userparagraph a ,wxdk_rankseasontime b ");
        sb.append("SET a.star = 0,");
        sb.append("a.paragraph = 1,");
        sb.append("a.`modifyTime` = NOW() ");
        sb.append("WHERE a.modifyTime < b.beginTime AND a.modifyTime < b.endTime");
        jdbcOperations.update(sb.toString(),new HashMap<>());
    }

    public boolean isExistsByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userparagraph` WHERE userId = :userId",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean isOverTimePlayByDay(int userId , int day){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("day",day);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userparagraph` WHERE userId = :userId AND DATEDIFF(NOW(),modifyTime) > :day ",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean isExistsNoReceive(int userId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userparagraph` WHERE userId = :userId AND isReceive = 1",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }
}
