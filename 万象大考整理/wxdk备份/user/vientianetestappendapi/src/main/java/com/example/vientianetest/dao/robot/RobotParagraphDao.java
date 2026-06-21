package com.example.vientianetest.dao.robot;

import com.example.vientianetest.config.DataConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RobotParagraphDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private DataConfiguration dataConfiguration;

    public int insert(int userId,double paragraph,int star,double mmr){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_robotparagraph` (`userId`, `paragraph`,`star`,`mmr`) VALUES('%s','%s','%s','%s') ;",
                userId,paragraph,star,mmr),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void update(int userId,double paragraph,int star,double mmr){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("paragraph",paragraph);
        paramMap.put("star",star);
        paramMap.put("mmr",mmr);
        paramMap.put("maxParagraph",dataConfiguration.getMaxParagraph());
        paramMap.put("maxStar",dataConfiguration.getMaxStar());
        jdbcOperations.update("UPDATE `wxdk_robotparagraph` SET `paragraph` = case when :paragraph>:maxParagraph then :maxParagraph else :paragraph end ,`star` = case when :star > :maxStar and :paragraph != :maxParagraph then :maxStar else :star end,`mmr` = :mmr,`modifyTime` = NOW() WHERE `userId` = :userId ;",paramMap);
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
        jdbcOperations.update("UPDATE `wxdk_robotparagraph` SET `paragraph` = case when :paragraph>:maxParagraph then :maxParagraph else :paragraph end ,`star` = case when :star > :maxStar and :paragraph != :maxParagraph then :maxStar else :star end,`mmr` = mmr+:mmr,`modifyTime` = NOW() WHERE `userId` = :userId ;",paramMap);
    }

    public void updateOverTimePlayStar(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE wxdk_robotparagraph ");
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
        jdbcOperations.update("UPDATE `wxdk_robotparagraph` SET `paragraph` = case when paragraph+:paragraph > :maxParagraph then :maxParagraph else paragraph+:paragraph end ,`star` = case when star + :star > :maxStar then 0 else star + :star end, `mmr` = mmr + :mmr ,`modifyTime` = NOW() WHERE `userId` = :userId ;",paramMap);
    }

    public Map<String,Object> getInfoByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        return jdbcOperations.queryForMap("SELECT a.id,a.`userId`,a.`mmr`,a.paragraph as level,a.`paragraph`,a.`star`,ifnull(b.name,'') as name,ifnull(b.`image`,'') as image FROM `wxdk_robotparagraph` a LEFT JOIN `wxdk_paragraph` b ON a.`paragraph` = b.level WHERE userId = :userId LIMIT 1",paramMap);
    }

    public Map<String,Object> getParagraphInfoByLevel(int level){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("level",level);
        return jdbcOperations.queryForMap("SELECT * FROM `wxdk_paragraph`  WHERE level = :level LIMIT 1",paramMap);
    }


    public boolean isExistsByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_robotparagraph` WHERE userId = :userId",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }
}
