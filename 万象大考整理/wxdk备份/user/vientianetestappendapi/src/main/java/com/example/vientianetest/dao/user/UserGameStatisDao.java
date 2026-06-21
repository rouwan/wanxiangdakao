package com.example.vientianetest.dao.user;

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
public class UserGameStatisDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId,int totalNum,int winNum,int loseNum,int flatNum,int exitNum){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int continueWinNum = winNum > 0 ? 1 : 0;
        int continueLoseNum = winNum > 0 ? 0 : 1;
        jdbcOperations.update(String.format("INSERT INTO `wxdk_usergamestatis` (`userId`,`totalNum`,`winNum`,`loseNum`,`flatNum`,`exitNum`,`continueWinNum`,`continueLoseNum`,`modifyTime`,`createTime`) "+
                                            " VALUES('%s','%s','%s','%s','%s','%s','%s','%s',NOW(),NOW());",
                                            userId,totalNum,winNum,loseNum,flatNum,exitNum,continueWinNum,continueLoseNum),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void appendNumByUserId(int userId,int totalNum,int winNum,int loseNum,int flatNum,int exitNum){
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
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE `wxdk_usergamestatis` SET  `totalNum` = totalNum + :totalNum,");
        sb.append("`winNum` = winNum+:winNum,");
        sb.append("`loseNum` = loseNum+:loseNum,");
        sb.append("`flatNum` = flatNum+:flatNum,");
        sb.append("`exitNum` = exitNum + :exitNum,");
        sb.append("`continueWinNum` = case when :continueWinNum >0 then continueWinNum + :continueWinNum else 0 end ,");
        sb.append("`continueLoseNum` = case when :continueLoseNum >0 then continueLoseNum + :continueLoseNum else 0 end ,");
        sb.append("`modifyTime`=NOW() WHERE `userId` = :userId ;");
        jdbcOperations.update(sb.toString(),paramMap);
    }

    public User getWinNumByUserId(User user){
        if(this.isExistsByUserId(user.getUser().getUserId()) && !user.getBoot()){
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("userId",user.getUser().getUserId());
            Map<String,Object> resultMap = jdbcOperations.queryForMap("SELECT totalNum,winNum,ifnull(CONVERT(winNum/totalNum,DECIMAL(10,2))*100,0) AS winRatio,continueWinNum,continueLoseNum FROM  `wxdk_usergamestatis` WHERE userId = :userId",paramMap);
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
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_usergamestatis WHERE userId = :userId",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }
}
