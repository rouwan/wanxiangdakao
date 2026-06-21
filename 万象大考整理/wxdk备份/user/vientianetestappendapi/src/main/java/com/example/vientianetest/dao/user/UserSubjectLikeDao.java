package com.example.vientianetest.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserSubjectLikeDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId,int subjectId,int isLike){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_usersubjectlike` (`userId`,`subjectId`,`isLike`,`modifyTime`,`createTime`) VALUES('%s','%s','%s',NOW(),NOW()) ;",
                userId,subjectId,isLike),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void update(int userId,int subjectId,int isLike) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("subjectId", subjectId);
        paramMap.put("isLike", isLike);
        jdbcOperations.update("UPDATE `wxdk_usersubjectlike` SET isLike = :isLike,modifyTime = NOW() WHERE userId = :userId AND subjectId = :subjectId", paramMap);
    }

    public int getUserLikeBySubjectId(int userId, int subjectId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("subjectId", subjectId);
        Map<String, Object> resultMap = jdbcOperations.queryForMap("SELECT COUNT(1),IFNULL(isLike,-1) AS isLike FROM `wxdk_usersubjectlike` WHERE userId = :userId and subjectId = :subjectId", paramMap);
        return Integer.parseInt(resultMap.get("isLike").toString());
    }

    public Map<String,Object> getTotalLikeBySubjectId(int subjectId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("subjectId", subjectId);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("IFNULL(SUM(CASE WHEN isLike = 1 THEN 1 ELSE 0 END),0) likeNum, ");
        sql.append("IFNULL(SUM(CASE WHEN isLike = 1 THEN 0 ELSE 1 END),0) noLikeNum ");
        sql.append("FROM `wxdk_usersubjectlike` WHERE subjectId = :subjectId ");
        return jdbcOperations.queryForMap(sql.toString(), paramMap);
    }

   public  boolean isExistsByUserId(int userId, int subjectId) {
       Map<String, Object> paramMap = new HashMap<>();
       paramMap.put("userId",userId);
       paramMap.put("subjectId",subjectId);
       int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_usersubjectlike WHERE userId =:userId AND subjectId = :subjectId",paramMap,Integer.class);
       if(count > 0) {
           return true;
       }
       return false;
   }
}
