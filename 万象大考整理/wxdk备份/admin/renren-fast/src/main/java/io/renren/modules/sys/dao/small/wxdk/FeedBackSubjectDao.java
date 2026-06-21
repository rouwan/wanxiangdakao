package io.renren.modules.sys.dao.small.wxdk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FeedBackSubjectDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public void updateStatus(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE wxdk_subject a ");
        sql.append("INNER JOIN `wxdk_feedbacksubject` b ON b.subjectId = a.id ");
        sql.append("INNER JOIN `wxdk_user` c ON b.`feedBackUserId` = c.id ");
        sql.append("SET a.`createUserName` = c.nickName, ");
        sql.append("a.`createUserUrl` = c.`headImgUrl`, ");
        sql.append("a.modiftTime = NOW(), ");
        sql.append("b.status = 1 ");
        sql.append("WHERE b.id = :id ");
        jdbcOperations.update(sql.toString(),paramMap);
    }
    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM wxdk_feedbacksubject "+innerStr+" "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr , String innerStr , String whereStr , String orderByStr, int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM wxdk_feedbacksubject "+innerStr+" "+whereStr+" "+orderByStr+" LIMIT "+pageIndex+", "+pageSize;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }
}
