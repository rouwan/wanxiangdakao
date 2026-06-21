package io.renren.modules.sys.dao.small.ktcc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TestUserDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public Map<String,Object> getUserInfoByName(String name){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.id,a.nickname,a.`headImgUrl`,b.cardNumber,c.`strengthCount` ");
        sb.append("FROM ktcc_user a ");
        sb.append("INNER JOIN `ktcc_lastsubject` b ON a.id = b.userId ");
        sb.append("INNER JOIN `ktcc_userability` c ON a.`id` = c.userId ");
        sb.append("WHERE a.nickname = :name LIMIT 1");
        return jdbcOperations.queryForMap(sb.toString(),paramMap);
    }

    public void update(int userId , int strengthCount , int cardNumber){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("strengthCount",strengthCount);
        paramMap.put("cardNumber",cardNumber);
        jdbcOperations.update("UPDATE ktcc_user SET isTest = 1 WHERE id = :userId",paramMap);
        jdbcOperations.update("UPDATE ktcc_lastsubject SET cardNumber = :cardNumber , modifyTime = NOW() WHERE userId = :userId",paramMap);
        jdbcOperations.update("UPDATE `ktcc_userability` SET`strengthCount` = :strengthCount,`intervalTimer` = 0,`modifyTime` = NOW()  WHERE userId = :userId",paramMap);
    }

    public int delete(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        return jdbcOperations.update("UPDATE ktcc_user SET isTest = 0 WHERE id = :userId",paramMap);
    }

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM ktcc_user "+innerStr+" "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr , String innerStr , String whereStr , String orderByStr,int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM ktcc_user "+innerStr+" "+whereStr+" "+orderByStr+" LIMIT "+pageIndex+", "+pageSize;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }

    public Boolean isExistsByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("select count(1) from ktcc_user where id = :userId and isTest = 1 ",paramMap,Integer.class);
        return count>0 ? true : false;
    }
}