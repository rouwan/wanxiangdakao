package com.example.vientianetest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WeekTitleDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public List<Map<String,Object>> getWeekTitleList(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.id,a.bigName,a.smallName,a.image,a.sortId ,IFNULL(b.`isWeekFinish`,0) AS isWeekFinish,IFNULL(b.`weekCardNumber`,0) AS weekCardNumber ");
        sql.append("FROM `wxdk_weektitle` a ");
        sql.append("LEFT JOIN wxdk_userweekchallenge b ON a.`id` = b.`titleId` AND b.`userId` = :userId ");
        sql.append("WHERE a.STATUS = 1 AND a.flag < 3 ORDER BY a.sortId ");
        return jdbcOperations.queryForList(sql.toString(),paramMap);
    }
}
