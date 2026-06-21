package com.example.vientianetest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class FeedBackSubjectDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int subjectId,int feedBackUserId , String types ,String remark){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_feedbacksubject` (`subjectId`,`feedBackUserId`,`types`,`remark`,`modifyTime`,`createTime`) " +
                                            "VALUES ('%s','%s','%s','%s',NOW(),NOW()) ;",subjectId,feedBackUserId,types,remark),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

}
