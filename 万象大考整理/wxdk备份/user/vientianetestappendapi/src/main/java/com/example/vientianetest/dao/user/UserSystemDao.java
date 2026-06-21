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
public class UserSystemDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert( int userId, String brand, String model, String pixelRatio, String screenWidth, String screenHeight, String windowWidth, String windowHeight,
                       String statusBarHeight, String language, String version, String system, String platform, String fontSizeSetting, String SDKVersion){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_usersystem` (`userId`,`brand`,`model`,`pixelRatio`,`screenWidth`,`screenHeight`,`windowWidth`,`windowHeight`,`statusBarHeight`,`language`,`version`,`system`,`platform`,`fontSizeSetting`,`SDKVersion`,`createTime`) VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',NOW());",
                userId,brand,model,pixelRatio,screenWidth,screenHeight,windowWidth,windowHeight,statusBarHeight,language,version,system,platform,fontSizeSetting,SDKVersion),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public boolean isExistsByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_usersystem` where userId = :userId ",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }
}
