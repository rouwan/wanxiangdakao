package io.renren.modules.sys.dao.small.wxdk;

import org.springframework.beans.factory.annotation.Autowired;
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

    public int insert(int userId,int paragraph,int star,int mmr){
        String sql = String.format("INSERT INTO `wxdk_robotparagraph` (`userId`,`paragraph`,`star`,`mmr`,`modifyTime`,`createTime`) VALUES('%s','%s','%s','%s',NOW(),NOW()) ;",
                                     userId,paragraph,star,mmr);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void update(int userId,int paragraph,int star){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("paragraph",paragraph);
        paramMap.put("star",star);
        jdbcOperations.update("UPDATE `wxdk_robotparagraph` SET `paragraph` = :paragraph,`star` = :star,`modifyTime` = NOW() WHERE `userId` = :userId ;",paramMap);
    }

    public void delete(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        jdbcOperations.update("delete from wxdk_robotparagraph WHERE userId = :userId ",paramMap);
    }

    public Boolean isExistsByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_robotparagraph WHERE userId = :userId",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }
}
