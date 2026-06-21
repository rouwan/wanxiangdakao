package com.example.vientianetest.dao.user;

import com.example.vientianetest.domain.User;
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
public class UserGameStatisRankDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId,int totalNum,int winNum,int loseNum,int flatNum,int exitNum,int maxSingleScore, int maxAllRightNum,int paragraph){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int continueWinNum = winNum > 0 ? 1 : 0;
        int continueLoseNum = winNum > 0 ? 0 : 1;
        int maxContinueWinNum = winNum > 0 ? 1 : 0;
        jdbcOperations.update(String.format("INSERT INTO `wxdk_usergamestatisrank` (`userId`,`totalNum`,`winNum`,`loseNum`,`flatNum`,`exitNum`,`continueWinNum`,`continueLoseNum`,`maxContinueWinNum`,`maxSingleScore`,`maxAllRightNum`,`maxParagraph`,`modifyTime`,`createTime`) "+
                        " VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',NOW(),NOW());",
                userId,totalNum,winNum,loseNum,flatNum,exitNum,continueWinNum,continueLoseNum,maxContinueWinNum,maxSingleScore,maxAllRightNum,paragraph),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public int insertSub(int userId,int totalNum,int winNum,int loseNum,int flatNum,int exitNum,int continueWinNum,int continueLoseNum,int maxContinueWinNum,int maxSingleScore, int maxAllRightNum,int paragraph){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_usergamestatisrank` (`userId`,`totalNum`,`winNum`,`loseNum`,`flatNum`,`exitNum`,`continueWinNum`,`continueLoseNum`,`maxContinueWinNum`,`maxSingleScore`,`maxAllRightNum`,`maxParagraph`,`modifyTime`,`createTime`) "+
                        " VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',NOW(),NOW());",
                userId,totalNum,winNum,loseNum,flatNum,exitNum,continueWinNum,continueLoseNum,maxContinueWinNum,maxSingleScore,maxAllRightNum,paragraph),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void appendNumByUserId(int userId,int totalNum,int winNum,int loseNum,int flatNum,int exitNum,int singleScore, int allRightNum,int paragraph,int withUserId){
        Map<String,Object> paramMap = new HashMap<>();
        int continueWinNum = winNum > 0 ? 1 : 0;
        int continueLoseNum = winNum > 0 ? 0 : 1;
        paramMap.put("totalNum",totalNum);
        paramMap.put("winNum",winNum);
        paramMap.put("loseNum",loseNum);
        paramMap.put("flatNum",flatNum);
        paramMap.put("exitNum",exitNum);
        paramMap.put("userId",userId);
        paramMap.put("continueWinNum",continueWinNum);
        paramMap.put("continueLoseNum",continueLoseNum);
        paramMap.put("singleScore",singleScore);
        paramMap.put("allRightNum",allRightNum);
        paramMap.put("paragraph",paragraph);
        paramMap.put("withUserId",withUserId);
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE `wxdk_usergamestatisrank` SET  `totalNum` = totalNum + :totalNum,");
        sb.append("`winNum` = winNum+:winNum,");
        sb.append("`loseNum` = loseNum+:loseNum,");
        sb.append("`flatNum` = flatNum+:flatNum,");
        sb.append("`exitNum` = exitNum+:exitNum,");
        sb.append("`endWinUserId` = case when :continueWinNum = 0 and continueWinNum = maxContinueWinNum and maxContinueWinNum > 0 and endWinUserId = 0 then :withUserId when :continueWinNum > 0 and continueWinNum + :continueWinNum > maxContinueWinNum then 0 else endWinUserId end , ");
        sb.append("`continueWinNum` = case when :continueWinNum >0 then continueWinNum + :continueWinNum else 0 end ,");
        sb.append("`continueLoseNum` = case when :continueLoseNum >0 then continueLoseNum + :continueLoseNum else 0 end ,");
        sb.append("`maxContinueWinNum` = case when continueWinNum >maxContinueWinNum then continueWinNum else maxContinueWinNum end ,");
        sb.append("`maxSingleScore` = case when maxSingleScore < :singleScore then :singleScore else maxSingleScore end ,");
        sb.append("`maxAllRightNum` = case when :allRightNum >0 then maxAllRightNum + :allRightNum else maxAllRightNum end ,");
        sb.append("`maxParagraph` = case when maxParagraph < :paragraph then :paragraph else maxParagraph end ,");
        sb.append("`modifyTime`=NOW() WHERE `userId` = :userId ;");
        jdbcOperations.update(sb.toString(),paramMap);
    }

    public List<Map<String,Object>> getMaxContinueNumRankList(int num) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("num",num);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("a.`userId`,IFNULL(b.`nickname`,'') AS nickName , IFNULL(b.`headImgUrl`,'') AS headImgUrl,maxContinueWinNum,endWinUserId, ");
        sb.append("CASE WHEN a.`endWinUserId` < 0 THEN c.`nickname` WHEN a.`endWinUserId` > 0 THEN d.nickName ELSE '' END AS endNickName , ");
        sb.append("CASE WHEN a.`endWinUserId` < 0 THEN c.headImgUrl WHEN a.`endWinUserId` > 0 THEN d.headImgUrl ELSE '' END AS endHeadImgUrl ");
        sb.append("FROM wxdk_usergamestatisrank a ");
        sb.append("INNER JOIN wxdk_user b ON a.`userId` = b.`id` ");
        sb.append("LEFT JOIN wxdk_robot c ON a.`endWinUserId` = -c.`id` ");
        sb.append("LEFT JOIN wxdk_user d ON a.`endWinUserId` = d.`id` ");
        sb.append("ORDER BY maxContinueWinNum DESC LIMIT :num ");
        return jdbcOperations.queryForList(sb.toString(),paramMap);
    }

    public List<Map<String,Object>> getMaxContinueNumList(String userIds) {
        Map<String,Object> paramMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("a.`userId`,IFNULL(b.`nickname`,'') AS nickName , IFNULL(b.`headImgUrl`,'') AS headImgUrl,maxContinueWinNum,endWinUserId, ");
        sb.append("CASE WHEN a.`endWinUserId` < 0 THEN c.`nickname` WHEN a.`endWinUserId` > 0 THEN d.nickName ELSE '' END AS endNickName , ");
        sb.append("CASE WHEN a.`endWinUserId` < 0 THEN c.headImgUrl WHEN a.`endWinUserId` > 0 THEN d.headImgUrl ELSE '' END AS endHeadImgUrl ");
        sb.append("FROM wxdk_usergamestatisrank a ");
        sb.append("INNER JOIN wxdk_user b ON a.`userId` = b.`id` ");
        sb.append("LEFT JOIN wxdk_robot c ON a.`endWinUserId` = -c.`id` ");
        sb.append("LEFT JOIN wxdk_user d ON a.`endWinUserId` = d.`id` ");
        sb.append("WHERE a.userId in ("+userIds+") ORDER BY FIELD(a.userId,"+userIds+");");
        return jdbcOperations.queryForList(sb.toString(),paramMap);
    }

    public Map<String,Object> getMaxContinueNumByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("a.`userId`,IFNULL(b.`nickname`,'') AS nickName , IFNULL(b.`headImgUrl`,'') AS headImgUrl,ifnull(maxContinueWinNum,0) as maxContinueWinNum,endWinUserId, ");
        sb.append("CASE WHEN a.`endWinUserId` < 0 THEN c.`nickname` WHEN a.`endWinUserId` > 0 THEN d.nickName ELSE '' END AS endNickName , ");
        sb.append("CASE WHEN a.`endWinUserId` < 0 THEN c.headImgUrl WHEN a.`endWinUserId` > 0 THEN d.headImgUrl ELSE '' END AS endHeadImgUrl ");
        sb.append("FROM wxdk_user b ");
        sb.append("LEFT JOIN wxdk_usergamestatisrank a ON a.`userId` = b.`id` ");
        sb.append("LEFT JOIN wxdk_robot c ON a.`endWinUserId` = -c.`id` ");
        sb.append("LEFT JOIN wxdk_user d ON a.`endWinUserId` = d.`id` ");
        sb.append("WHERE a.userId = :userId ;");
        return jdbcOperations.queryForMap(sb.toString(),paramMap);
    }

    public List<Map<String,Object>> getMaxSingleScoreRankList(int num) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("num",num);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("a.`userId`,IFNULL(b.`nickname`,'') AS nickName , IFNULL(b.`headImgUrl`,'') AS headImgUrl,maxSingleScore ");
        sb.append("FROM `wxdk_usergamestatisrank` a ");
        sb.append("INNER JOIN wxdk_user b ON a.`userId` = b.`id` ");
        sb.append("ORDER BY maxSingleScore DESC LIMIT :num ");
        return jdbcOperations.queryForList(sb.toString(),paramMap);
    }

    public List<Map<String,Object>> getMaxSingleScoreList(String userIds) {
        Map<String,Object> paramMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("a.`userId`,IFNULL(b.`nickname`,'') AS nickName , IFNULL(b.`headImgUrl`,'') AS headImgUrl,maxSingleScore,IFNULL(b.province,'') AS province,IFNULL(b.city,'') AS city ");
        sb.append("FROM `wxdk_usergamestatisrank` a ");
        sb.append("INNER JOIN wxdk_user b ON a.`userId` = b.`id` ");
        sb.append("WHERE a.userId in ("+userIds+") ORDER BY FIELD(a.userId,"+userIds+");");
        return jdbcOperations.queryForList(sb.toString(),paramMap);
    }

    public Map<String,Object> getMaxSingleScoreByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("a.`userId`,IFNULL(b.`nickname`,'') AS nickName , IFNULL(b.`headImgUrl`,'') AS headImgUrl,IFNULL(maxSingleScore,0) AS maxSingleScore ");
        sb.append("FROM wxdk_user b ");
        sb.append("left JOIN `wxdk_usergamestatisrank` a ON a.`userId` = b.`id` ");
        sb.append("WHERE a.userId = :userId ;");
        return jdbcOperations.queryForMap(sb.toString(),paramMap);
    }

    public Map<String,Object> getGameStatisRankByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        return jdbcOperations.queryForMap("SELECT totalNum,IFNULL(CONVERT(winNum/totalNum,DECIMAL(10,2))*100,0) AS winRatio,IFNULL(CONVERT(exitNum/totalNum,DECIMAL(10,2))*100,0) AS exitRatio, winNum,flatNum,loseNum,exitNum,maxContinueWinNum,maxSingleScore,maxAllRightNum,maxParagraph FROM `wxdk_usergamestatisrank` WHERE userId = :userId limit 1",paramMap);
    }

    public User getWinNumByUserId(User user){
        if(this.isExistsByUserId(user.getUser().getUserId()) && !user.getBoot()){
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("userId",user.getUser().getUserId());
            Map<String,Object> resultMap = jdbcOperations.queryForMap("SELECT totalNum,winNum,ifnull(CONVERT(winNum/totalNum,DECIMAL(10,2))*100,0) AS winRatio,continueWinNum,continueLoseNum  FROM  `wxdk_usergamestatisrank` WHERE userId = :userId",paramMap);
            user.getAbility().setTotalNum(Integer.parseInt(resultMap.get("totalNum").toString()));
            user.getAbility().setWinNum(Integer.parseInt(resultMap.get("winNum").toString()));
            user.getAbility().setContinueWinNum(Integer.parseInt(resultMap.get("continueWinNum").toString()));
            user.getAbility().setContinueLoseNum(Integer.parseInt(resultMap.get("continueLoseNum").toString()));
            user.getAbility().setWinRatio(Double.valueOf(resultMap.get("winRatio").toString()));
            user.getAbility().setLevelType(0);
        }else{
            user.getAbility().setTotalNum(0);
            user.getAbility().setWinNum(0);
            user.getAbility().setWinRatio(0);
            user.getAbility().setContinueWinNum(0);
            user.getAbility().setContinueLoseNum(0);
            user.getAbility().setWinRatio(0);
            user.getAbility().setLevelType(0);
        }
        return user;
    }

    public Boolean isExistsByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_usergamestatisrank WHERE userId = :userId",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }
}
