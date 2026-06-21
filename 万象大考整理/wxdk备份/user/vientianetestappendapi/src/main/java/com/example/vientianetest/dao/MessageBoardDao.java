package com.example.vientianetest.dao;

import com.example.vientianetest.config.DataConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageBoardDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private DataConfiguration dataConfiguration;

    public int insert(int userId,String message, int type){
        Map<String,Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_usermessageboard` (`userId`,`message`,`type`,`modifyTime`,`createTime`) VALUES('%s','%s','%s',NOW(),NOW()) ;",userId,message,type),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public int getPageTotal() {
        Map<String, Object> paramMap = new HashMap<>();
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_usermessageboard ", paramMap, Integer.class);
        return count;
    }

    public List<Map<String,Object>> getMessageBoardPageList(int pageIndex,int pageSize) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageIndex", pageIndex * pageSize);
        paramMap.put("pageSize", pageSize);
        paramMap.put("maxParagraph", dataConfiguration.getMaxParagraph());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.id, a.`userId`,b.`nickname`,d.`name` AS paragraphName,a.`message`,b.headImgUrl,d.image as paragraphImage,a.type, ");
        sql.append("CASE WHEN a.type = 1 THEN (SELECT `image` FROM wxdk_paragraph WHERE LEVEL = :maxParagraph LIMIT 1) ELSE '' END highParagraphImage ");
        sql.append("FROM `wxdk_usermessageboard` a ");
        sql.append("LEFT JOIN `wxdk_user` b ON a.`userId` = b.id ");
        sql.append("LEFT JOIN `wxdk_userparagraph` c ON a.`userId` = c.`userId` ");
        sql.append("LEFT JOIN `wxdk_paragraph` d ON c.`paragraph` = d.`level` ");
        sql.append("ORDER BY a.id DESC LIMIT :pageIndex,:pageSize; ");
        return jdbcOperations.queryForList(sql.toString(), paramMap);
    }

}
