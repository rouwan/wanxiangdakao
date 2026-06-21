package com.example.vientianetest.dao.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserToXcxStatusDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId, String name, int num) {
        Map<String, Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_usertoxcxstatus` (`userId`,`name`,`num`,`modifyTime`,`createTime`) VALUES('%s','%s','%s',NOW(),NOW()) ;",
                userId, name, num), null, keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void updateStatus(int userId, String name, int num) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("name", name);
        paramMap.put("num", num);
        jdbcOperations.update("UPDATE wxdk_usertoxcxstatus SET num = num + :num,modifyTime = NOW() WHERE userId=:userId AND name = :name", paramMap);
    }

    //判断是否存在
    public boolean isExistsByName(int userId, String name) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("name", name);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_usertoxcxstatus WHERE userId = :userId AND name=:name", paramMap, Integer.class);
        if (count > 0) {
            return true;
        }
        return false;
    }

    //判断今天有没有领取
    public boolean isExistsTodayByName(int userId, String name) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("name", name);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_usertoxcxstatus WHERE userId = :userId AND name=:name AND DATEDIFF(DATE_FORMAT(NOW(),'%Y-%m-%d'),DATE_FORMAT(createTime,'%Y-%m-%d')) = 0", paramMap, Integer.class);
        if (count > 0) {
            return true;
        }
        return false;
    }

}
