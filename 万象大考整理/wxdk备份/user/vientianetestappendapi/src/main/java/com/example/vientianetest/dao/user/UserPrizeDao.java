package com.example.vientianetest.dao.user;

import org.apache.commons.collections4.map.LinkedMap;
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
public class UserPrizeDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId,int type ){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userprize` (`userId`,`type`,`modifyTime`,`createTime`) VALUES('%s','%s',NOW(),NOW()) ;",
                                userId,type),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public LinkedMap<String,Object> getPrize() {
        LinkedMap<String, Object> resultMap = new LinkedMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.`goodType` as type,a.`minValue`,a.`maxValue`,a.`num`,IFNULL(b.`image`,'') AS image,b.name AS `remark`,IFNULL(a.`description`,'') AS description,a.sortId  ");
        sql.append("FROM `wxdk_prizeratio` a ");
        sql.append("LEFT JOIN `wxdk_goods` b ON a.goodType = b.`type` ");
        sql.append("WHERE a.flag < 3  ORDER BY a.sortId");
        List<Map<String, Object>> listMap = jdbcOperations.queryForList(sql.toString(), new HashMap<>());
        for (int i = 0; i < listMap.size(); i++) {
            Map<String, Object> map = listMap.get(i);
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("type", map.get("type").toString());
            objectMap.put("minValue", map.get("minValue").toString());
            objectMap.put("maxValue", map.get("maxValue").toString());
            objectMap.put("num", map.get("num").toString());
            objectMap.put("remark", map.get("remark").toString());
            objectMap.put("image", map.get("image").toString());
            objectMap.put("description", map.get("description").toString());
            resultMap.put(map.get("sortId").toString(), objectMap);
        }
        return resultMap;
    }
}
