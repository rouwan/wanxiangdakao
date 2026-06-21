package com.example.vientianetest.dao.robot;

import com.example.vientianetest.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RobotGameStatisRankDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId,int totalNum,int winNum,int loseNum,int flatNum,int exitNum,int maxSingleScore, int maxAllRightNum,int paragraph){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int continueWinNum = winNum > 0 ? 1 : 0;
        int continueLoseNum = winNum > 0 ? 0 : 1;
        int maxContinueWinNum = winNum > 0 ? 1 : 0;
        jdbcOperations.update(String.format("INSERT INTO `wxdk_robotgamestatisrank` (`userId`,`totalNum`,`winNum`,`loseNum`,`flatNum`,`exitNum`,`continueWinNum`,`continueLoseNum`,`maxContinueWinNum`,`maxSingleScore`,`maxAllRightNum`,`maxParagraph`,`modifyTime`,`createTime`) "+
                        " VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',NOW(),NOW());",
                userId,totalNum,winNum,loseNum,flatNum,exitNum,continueWinNum,continueLoseNum,maxContinueWinNum,maxSingleScore,maxAllRightNum,paragraph),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void appendNumByUserId(int userId,int totalNum,int winNum,int loseNum,int flatNum,int exitNum,int singleScore, int allRightNum,int paragraph){
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
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE `wxdk_robotgamestatisrank` SET  `totalNum` = totalNum + :totalNum,");
        sb.append("`winNum` = winNum+:winNum,");
        sb.append("`loseNum` = loseNum+:loseNum,");
        sb.append("`flatNum` = flatNum+:flatNum,");
        sb.append("`exitNum` = exitNum+:exitNum,");
        sb.append("`continueWinNum` = case when :continueWinNum >0 then continueWinNum + :continueWinNum else 0 end ,");
        sb.append("`continueLoseNum` = case when :continueLoseNum >0 then continueLoseNum + :continueLoseNum else 0 end ,");
        sb.append("`maxContinueWinNum` = case when continueWinNum >maxContinueWinNum then continueWinNum else maxContinueWinNum end ,");
        sb.append("`maxSingleScore` = case when maxSingleScore < :singleScore then :singleScore else maxSingleScore end ,");
        sb.append("`maxAllRightNum` = case when :allRightNum >0 then maxAllRightNum + :allRightNum else maxAllRightNum end ,");
        sb.append("`maxParagraph` = case when maxParagraph < :paragraph then :paragraph else maxParagraph end ,");
        sb.append("`modifyTime`=NOW() WHERE `userId` = :userId ;");
        jdbcOperations.update(sb.toString(),paramMap);
    }

    public Map<String,Object> getGameStatisRankByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        return jdbcOperations.queryForMap("SELECT totalNum,IFNULL(CONVERT(winNum/totalNum,DECIMAL(10,2))*100,0) AS winRatio,IFNULL(CONVERT(exitNum/totalNum,DECIMAL(10,2))*100,0) AS exitRatio, winNum,flatNum,loseNum,exitNum,maxContinueWinNum,maxSingleScore,maxAllRightNum  FROM  `wxdk_robotgamestatisrank` WHERE userId = :userId limit 1",paramMap);
    }

    public User getWinNumByUserId(User user){
        if(this.isExistsByUserId(user.getUser().getUserId()) && !user.getBoot()){
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("userId",user.getUser().getUserId());
            Map<String,Object> resultMap = jdbcOperations.queryForMap("SELECT totalNum,winNum,ifnull(CONVERT(winNum/totalNum,DECIMAL(10,2))*100,0) AS winRatio,continueWinNum,continueLoseNum  FROM  `wxdk_robotgamestatisrank` WHERE userId = :userId",paramMap);
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
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_robotgamestatisrank WHERE userId = :userId",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }

}
