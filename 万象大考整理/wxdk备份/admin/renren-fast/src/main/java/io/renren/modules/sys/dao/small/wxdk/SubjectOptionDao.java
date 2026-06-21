package io.renren.modules.sys.dao.small.wxdk;

import io.renren.modules.sys.domain.small.wxdk.SubjectOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SubjectOptionDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(SubjectOption subjectOption){
        String sql = "INSERT INTO `wxdk_subjectoption` (`subjectId`,`option`,`sortId`) VALUES(:subjectId,:option,:sortId) ;";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(subjectOption),keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void delete(int subjectId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("subjectId",subjectId);
        jdbcOperations.update("DELETE FROM wxdk_subjectoption WHERE subjectId = :subjectId",paramMap);
    }

    public List<Map<String,Object>> getSubjectOptionBySubjectId(int subjectId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("subjectId",subjectId);
        return jdbcOperations.queryForList("SELECT * FROM wxdk_subjectoption WHERE subjectId = :subjectId",paramMap);
    }

    public void updateBySubjectIdAndSortId(SubjectOption subjectOption){
        String sql = "UPDATE `wxdk_subjectoption` SET `option` = :option,`sortId` = :sortId,`modifyTime` = NOW(),`flag` = 2 WHERE subjectId = :subjectId AND sortId = :sortId ;";
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(subjectOption));
    }



}
