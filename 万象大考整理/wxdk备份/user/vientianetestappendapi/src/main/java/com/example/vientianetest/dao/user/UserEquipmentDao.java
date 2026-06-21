package com.example.vientianetest.dao.user;

import com.example.vientianetest.config.DataConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class UserEquipmentDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private DataConfiguration dataConfiguration;

    public int insert(int userId,int type,int num){
        Map<String,Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userequipment` (`userId`,`type`,`num`,`modifyTime`,`createTime`) VALUES('%s','%s','%s',NOW(),NOW()) ;" ,
                userId,type,num),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void appendNumByUserId(int userId,int type , int num){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("type",type);
        paramMap.put("num",num);
        //type = 1小体力瓶999其他99
        jdbcOperations.update("UPDATE wxdk_userequipment SET num = case when num + :num > 99 and type != 1 then 99 when num + :num > 999 and type = 1 then 999 else num + :num end ,modifyTime=now() where userId = :userId and type = :type",paramMap);
    }

    public List<Map<String,Object>> getEquipmentByUserId(int userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        List<Map<String, Object>> resultListMap = jdbcOperations.queryForList("SELECT userId,`type`,num FROM wxdk_userequipment WHERE userId = :userId ", paramMap);
        for (int i = 0; i < resultListMap.size(); i++) {
            Map<String, Object> map = resultListMap.get(i);
            int currentType = Integer.parseInt(map.get("type").toString());
            Iterator<Map.Entry<String, Object>> entries = dataConfiguration.getPrizeRatio().entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, Object> entry = entries.next();
                Map<String, Object> valueMap = (Map<String, Object>) entry.getValue();
                int type = Integer.parseInt(valueMap.get("type").toString());
                if (type == currentType) {
                    int maxNum = 99;
                    if (currentType == 1) {
                        maxNum = 999;
                    }
                    map.put("remark", valueMap.get("remark").toString());
                    map.put("description", valueMap.get("description").toString());
                    map.put("image", valueMap.get("image").toString());
                    map.put("maxNum", maxNum);
                    break;
                }
            }
        }
        return resultListMap;
    }

    public boolean isHashNumByUserId(int userId , int type,int num){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("type",type);
        paramMap.put("num",num);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userequipment` WHERE userId = :userId and type = :type and num>=:num",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean isExistsByUserId(int userId , int type){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("type",type);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_userequipment` WHERE userId = :userId and type = :type ",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }
}
