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
public class UserGameStatisRoomDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private UserParagraphDao userParagraphDao;
    @Autowired
    private UserAbilityDao userAbilityDao;

    public int insert(int userId,int totalNum,int winNum,int loseNum,int flatNum,int exitNum, int todayNum,int todayRightSubNum) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int continueWinNum = 0;
        int continueLoseNum = 0;
        jdbcOperations.update(String.format("INSERT INTO `wxdk_usergamestatisroom` (`userId`,`totalNum`,`winNum`,`loseNum`,`flatNum`,`exitNum`,`continueWinNum`,`continueLoseNum`,`todayNum`,`todayRightSubNum`,`modifyTime`,`createTime`) " +
                        " VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',NOW(),NOW());",
                userId, totalNum, winNum, loseNum, flatNum, exitNum, continueWinNum, continueLoseNum, todayNum,todayRightSubNum), new BeanPropertySqlParameterSource(new Object()), keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void appendTodayNumByUserId (int userId,int totalNum,int winNum, int todayNum,int todayRightSubNum) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("totalNum", totalNum);
        paramMap.put("winNum", winNum);
        paramMap.put("todayNum", todayNum);
        paramMap.put("todayRightSubNum", todayRightSubNum);
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE `wxdk_usergamestatisroom` SET ");
        sb.append("`totalNum` = totalNum + :totalNum, ");
        sb.append("`todayNum` = CASE WHEN TIMESTAMPDIFF(DAY,DATE_FORMAT(modifyTime,'%Y-%m-%d'),NOW()) = 0 THEN todayNum + :todayNum ELSE :todayNum END, ");
        sb.append("`todayRightSubNum` = CASE WHEN TIMESTAMPDIFF(DAY,DATE_FORMAT(modifyTime,'%Y-%m-%d'),NOW()) = 0 THEN todayRightSubNum + :todayRightSubNum ELSE :todayRightSubNum END, ");
        sb.append("`winNum` = winNum + :winNum, ");
        sb.append("`modifyTime`=NOW() WHERE `userId` = :userId ;");
        jdbcOperations.update(sb.toString(), paramMap);
    }

    public void appendNumByUserId(int userId,int totalNum,int winNum,int loseNum,int flatNum,int exitNum) {
        Map<String, Object> paramMap = new HashMap<>();
        int continueWinNum = winNum > 0 ? 1 : 0;
        int continueLoseNum = winNum > 0 ? 0 : 1;
        paramMap.put("totalNum", totalNum);
        paramMap.put("winNum", winNum);
        paramMap.put("loseNum", loseNum);
        paramMap.put("flatNum", flatNum);
        paramMap.put("exitNum", exitNum);
        paramMap.put("userId", userId);
        paramMap.put("continueWinNum", continueWinNum);
        paramMap.put("continueLoseNum", continueLoseNum);
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE `wxdk_usergamestatisroom` SET  `totalNum` = totalNum + :totalNum,");
        sb.append("`winNum` = winNum+:winNum,");
        sb.append("`loseNum` = loseNum+:loseNum,");
        sb.append("`flatNum` = flatNum+:flatNum,");
        sb.append("`exitNum` = exitNum + :exitNum,");
        sb.append("`continueWinNum` = case when :continueWinNum >0 then continueWinNum + :continueWinNum else 0 end ,");
        sb.append("`continueLoseNum` = case when :continueLoseNum >0 then continueLoseNum + :continueLoseNum else 0 end ,");
        sb.append("`modifyTime`=NOW() WHERE `userId` = :userId ;");
        jdbcOperations.update(sb.toString(), paramMap);
    }

    public Map<String,Object> getWinNumByUserId(String userIds , int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userIds",userIds);
        paramMap.put("userId",userId);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ( ");
        sb.append("SELECT (@i:=@i+1) AS `index` ,a.* FROM ( ");
        sb.append("SELECT a.`userId`,IFNULL(b.`nickname`,'') AS nickName , IFNULL(b.`headImgUrl`,'') AS headImgUrl,IFNULL(b.`city`,'') AS city,IFNULL(b.`province`,'') AS province,a.winNum ");
        sb.append("FROM wxdk_user b ");
        sb.append("LEFT JOIN wxdk_usergamestatisroom a ON a.`userId` = b.id ");
        sb.append("WHERE b.id in ("+userIds+") ORDER BY a.winNum DESC ");
        sb.append(") a,(SELECT @i:=0) t ) a ");
        sb.append("WHERE a.userId = "+userId+"; ");
        return jdbcOperations.queryForMap(sb.toString(),paramMap);
    }

    public Map<String,Object> getWinNumByUserId(int userId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("a.`userId`,IFNULL(b.`nickname`,'') AS nickName , IFNULL(b.`headImgUrl`,'') AS headImgUrl,IFNULL(b.`city`,'') AS city,IFNULL(b.`province`,'') AS province,IFNULL(a.winNum,0) AS winNum,CASE WHEN TIMESTAMPDIFF(DAY,DATE_FORMAT(a.modifyTime,'%Y-%m-%d'),NOW()) = 0 THEN IFNULL(a.todayRightSubNum,0) ELSE 0 END AS todayRightSubNum ");
        sb.append("FROM wxdk_user b ");
        sb.append("LEFT JOIN wxdk_usergamestatisroom a ON a.`userId` = b.`id` ");
        sb.append("WHERE b.id = :userId limit 1");
        return jdbcOperations.queryForMap(sb.toString(),paramMap);
    }

    public List<Map<String,Object>> getWinNumListOrderByUserIds(String userIds) {
        Map<String,Object> paramMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("a.`userId`,IFNULL(b.`nickname`,'') AS nickName , IFNULL(b.`headImgUrl`,'') AS headImgUrl,IFNULL(b.`city`,'') AS city,IFNULL(b.`province`,'') AS province,IFNULL(a.winNum,0) AS winNum ");
        sb.append("FROM wxdk_user b ");
        sb.append("LEFT JOIN wxdk_usergamestatisroom a ON a.`userId` = b.`id` ");
        sb.append("WHERE b.id in ("+userIds+") ORDER BY FIELD(b.id,"+userIds+");");
        return jdbcOperations.queryForList(sb.toString(),paramMap);
    }

    public List<Map<String,Object>> getWinNumListOrderByWiNum(String userIds) {
        Map<String,Object> paramMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("a.`userId`,IFNULL(b.`nickname`,'') AS nickName , IFNULL(b.`headImgUrl`,'') AS headImgUrl,IFNULL(b.`city`,'') AS city,IFNULL(b.`province`,'') AS province,ifnull(a.winNum,0) AS winNum ");
        sb.append("FROM wxdk_user b ");
        sb.append("LEFT JOIN wxdk_usergamestatisroom a ON a.`userId` = b.`id` ");
        sb.append("WHERE b.id in ("+userIds+") ORDER BY IFNULL(a.`winNum`,0) DESC,IFNULL(a.id,0) ASC;");
        return jdbcOperations.queryForList(sb.toString(),paramMap);
    }

    public List<Map<String,Object>> initRedisWinNum(int num){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("num",num);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("a.`userId`,ifnull(a.winNum,0) AS winNum ");
        sb.append("FROM wxdk_usergamestatisroom a ");
        sb.append("WHERE winNum >0 ORDER BY IFNULL(a.`winNum`,0) DESC,IFNULL(a.id,0) ASC LIMIT :num;");
        return jdbcOperations.queryForList(sb.toString(),paramMap);
    }

    public User getWinNumByUserId(User user) {
        if (this.isExistsByUserId(user.getUser().getUserId()) && !user.getBoot()) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", user.getUser().getUserId());
            Map<String, Object> resultMap = jdbcOperations.queryForMap("SELECT totalNum,winNum,ifnull(CONVERT(winNum/totalNum,DECIMAL(10,2))*100,0) AS winRatio,continueWinNum,continueLoseNum FROM  `wxdk_usergamestatisroom` WHERE userId = :userId", paramMap);
            user.getAbility().setTotalNum(Integer.parseInt(resultMap.get("totalNum").toString()));
            user.getAbility().setWinNum(Integer.parseInt(resultMap.get("winNum").toString()));
            user.getAbility().setContinueWinNum(Integer.parseInt(resultMap.get("continueWinNum").toString()));
            user.getAbility().setContinueLoseNum(Integer.parseInt(resultMap.get("continueLoseNum").toString()));
            user.getAbility().setWinRatio(Double.valueOf(resultMap.get("winRatio").toString()));
            user.getAbility().setLevelType(0);
        } else {
            user.getAbility().setTotalNum(0);
            user.getAbility().setWinNum(0);
            user.getAbility().setWinRatio(0);
            user.getAbility().setContinueWinNum(0);
            user.getAbility().setContinueLoseNum(0);
            user.getAbility().setWinRatio(0);
            user.getAbility().setLevelType(0);
        }
        Map<String, Object> userParagraphMap = userParagraphDao.getInfoByUserId(user.getUser().getUserId());
        if (!user.getBoot()) {
            user.getAbility().setParagraph(Integer.parseInt(userParagraphMap.get("paragraph").toString()));
            user.getAbility().setStar(Integer.parseInt(userParagraphMap.get("star").toString()));
            user.getAbility().setMmr(Double.valueOf(userParagraphMap.get("mmr").toString()));
            user.getAbility().setParagraphName(userParagraphMap.get("name").toString());
            user.getAbility().setParagraphImage(userParagraphMap.get("image").toString());
        } else {
            user.getAbility().setParagraph(1);
            user.getAbility().setStar(0);
            user.getAbility().setMmr(0);
            user.getAbility().setParagraphName("");
            user.getAbility().setParagraphImage("");
        }
        Map<String, Object> userAbilityMap = userAbilityDao.getInfoByUserId(user.getUser().getUserId());
        if (!user.getBoot()) {
            user.getAbility().setExpValue(Double.valueOf(userAbilityMap.get("expValue").toString()));
            user.getAbility().setGrade(Integer.parseInt(userAbilityMap.get("grade").toString()));
            user.getAbility().setMoney(Double.valueOf(userAbilityMap.get("money").toString()));
        }
        return user;
    }

    public Boolean isExistsByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_usergamestatisroom WHERE userId = :userId",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }

    public boolean isTodayOverCountByUserId(int userId , int num) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("num",num);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_usergamestatisroom  WHERE userId = :userId AND TIMESTAMPDIFF(DAY,DATE_FORMAT(modifyTime,'%Y-%m-%d'),NOW()) = 0 AND IFNULL(todayNum,0) >= :num ",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }
}
