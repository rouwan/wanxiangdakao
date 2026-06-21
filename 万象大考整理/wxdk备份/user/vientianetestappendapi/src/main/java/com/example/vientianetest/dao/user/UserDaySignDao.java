package com.example.vientianetest.dao.user;

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
public class UserDaySignDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId,int day) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userdaysign` (`userId`,`day`,`modifyTime`,`createTime`) VALUES('%s','%s',NOW(),NOW()) ;",
                userId,day),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void update(int userId,int day) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("day", day);
        jdbcOperations.update("UPDATE wxdk_userdaysign SET day= :day,modifyTime = now() WHERE userId = :userId ",paramMap);
    }

    public List<Map<String,Object>> getDaySignList(){
        Map<String,Object> paramMap = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.id,a.day,a.goodType as type,a.num,a.isLookNum,a.description,IFNULL(b.image,'') AS image,b.name ");
        sql.append("FROM wxdk_daysign a ");
        sql.append("LEFT JOIN `wxdk_goods` b ON a.goodType = b.`type` ");
        sql.append("WHERE a.flag < 3 ORDER BY a.`day` ");
        List<Map<String,Object>> listMap = jdbcOperations.queryForList(sql.toString(),paramMap);
        return listMap;
    }

    public int getSignDayByUserId(int userId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        Map<String,Object> resultMap = jdbcOperations.queryForMap("SELECT userId,CASE WHEN DATEDIFF(DATE_FORMAT(NOW(),'%Y-%m-%d'),DATE_FORMAT(modifyTime,'%Y-%m-%d'))=0  OR (DATEDIFF(DATE_FORMAT(NOW(),'%Y-%m-%d'),DATE_FORMAT(modifyTime,'%Y-%m-%d'))=1 AND DAY < 7) THEN `day` ELSE 0 END AS `day` FROM wxdk_userdaysign WHERE userId = :userId ",paramMap);
        return Integer.parseInt(resultMap.get("day").toString());
    }

    //判断用户今天是否有打卡过
    public boolean isSignedByUserId(int userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_userdaysign WHERE userId = :userId AND DATEDIFF(DATE_FORMAT(NOW(),'%Y-%m-%d'),DATE_FORMAT(modifyTime,'%Y-%m-%d'))=0", paramMap, Integer.class);
        if (count > 0) {
            return true;
        }
        return false;
    }

    public boolean isExistsByUserId(int userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_userdaysign WHERE userId = :userId ", paramMap, Integer.class);
        if (count > 0) {
            return true;
        }
        return false;
    }
}
