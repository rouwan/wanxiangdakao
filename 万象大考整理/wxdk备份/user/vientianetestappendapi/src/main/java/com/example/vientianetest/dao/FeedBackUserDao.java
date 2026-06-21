package com.example.vientianetest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class FeedBackUserDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int feedBackUserId,int userId , String types ){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_feedbackuser` (`feedBackUserId`,`userId`,`types`,`modifyTime`,`createTime`) VALUES('%s','%s','%s',NOW(),NOW()) ; "
                                            ,feedBackUserId,userId,types),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }
}
