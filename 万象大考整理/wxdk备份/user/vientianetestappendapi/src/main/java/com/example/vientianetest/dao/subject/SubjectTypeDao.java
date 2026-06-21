package com.example.vientianetest.dao.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SubjectTypeDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public Map<String,Object> getSubjectTypeByName(int parentType ,String name){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        paramMap.put("parentType",parentType);
        return jdbcOperations.queryForMap("select * from wxdk_subjectType where parentType = :parentType and name = :name and flag < 3 limit 1",paramMap);
    }

}
