package io.renren.modules.sys.dao.small.ktcc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("ktccSubjectDao")
public class SubjectDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int getTotalCountByStatus(int status){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("status",status);
        String sql = "SELECT count(1) FROM ktcc_subject where flag < 3 and status = :status";
        return jdbcOperations.queryForObject(sql,paramMap,Integer.class);
    }
}
