package io.renren.modules.sys.dao.small.wxdk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("wxdk_userdao")
public class UserDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public Map<String,Object> getUserInfoById(int id){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return jdbcOperations.queryForMap("select id,openId,IFNULL(unionId,'') AS unionId,IFNULL(nickname,'') AS nickname,IFNULL(sexDesc,'') AS sexDesc,IFNULL(city,'') AS city,IFNULL(province,'') AS province,IFNULL(country,'') AS country,IFNULL(headImgUrl,'') AS headImgUrl,IFNULL(subscribeTime,'') AS subscribeTime,isTest from wxdk_user where id = :id",paramMap);
    }

}
