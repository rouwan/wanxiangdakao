package io.renren.modules.sys.dao.small.ktcc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RechargeRecordDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM ktcc_rechargerecord "+innerStr+" "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr , String innerStr , String whereStr , String orderByStr,int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM ktcc_rechargerecord "+innerStr+" "+whereStr+" "+orderByStr+" LIMIT "+pageIndex+", "+pageSize;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }
}
