package com.example.vientianetest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RankSeasonTimeDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public Map<String,Object> getSeasonTime(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("a.*,(CASE WHEN DATE_FORMAT(NOW(),'%Y-%m-%d') >= beginTime AND DATE_FORMAT(NOW(),'%Y-%m-%d') <= endTime THEN 0 ELSE 1 END) isTimeEnd ");
        sb.append("FROM ( ");
        sb.append("SELECT DATE_FORMAT(a.`beginTime`,'%Y-%m-%d') AS beginTime,DATE_FORMAT(a.`endTime`,'%Y-%m-%d') AS endTime,a.`modifyTime`,a.`createTime`,a.`flag` ");
        sb.append("FROM`wxdk_rankseasontime` a ");
        sb.append("WHERE a.flag < 3 LIMIT 1 ");
        sb.append(") a");

        return jdbcOperations.queryForMap(sb.toString(),paramMap);
    }

    public Map<String,Object> getSeasonTime() {
        return jdbcOperations.queryForMap("SELECT DATE_FORMAT(beginTime,'%Y-%m') as seasonTime FROM `wxdk_rankseasontime` WHERE flag < 3 LIMIT 1 ",new HashMap<>());
    }

    public void update(String beginTime , String endTime ){
        jdbcOperations.update("UPDATE wxdk_rankseasontime SET beginTime = :beginTime ,endTime = :endTime ,modifyTime = now(),flag =2 where flag < 3 ",new HashMap<>());
    }

    public void appendMonth(){
        jdbcOperations.update("UPDATE wxdk_rankseasontime SET endTime = DATE_SUB(DATE_ADD(beginTime, INTERVAL 2 MONTH),INTERVAL 1 DAY),beginTime = DATE_ADD(beginTime, INTERVAL 1 MONTH),modifyTime = NOW() WHERE flag < 3 ",new HashMap<>());
    }

}
