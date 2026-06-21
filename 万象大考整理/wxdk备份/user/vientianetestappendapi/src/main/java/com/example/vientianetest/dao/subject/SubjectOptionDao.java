package com.example.vientianetest.dao.subject;

import com.example.vientianetest.domain.SubjectOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
        String sql = "INSERT INTO `wxdk_subjectoption` (`subjectId`,`option`,`sortId`) VALUES(:subjectId,:name,:sortId) ;";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(subjectOption),keyHolder);
        return keyHolder.getKey().intValue();
    }

    public List<SubjectOption> getSubjectOptionBySubjectId(int subjectId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("subjectId",subjectId);
        List<SubjectOption> subjectOptionList = jdbcOperations.query("SELECT a.id,a.subjectId,a.`option` AS name,a.sortId  FROM `wxdk_subjectoption` a INNER JOIN wxdk_subject b ON a.`subjectId` = b.`id`WHERE a.`subjectId` = :subjectId ORDER BY CASE WHEN SUBSTRING(a.`option`,1,2) = '以上' THEN -1 ELSE RAND() END DESC ",paramMap,new BeanPropertyRowMapper<>(SubjectOption.class));
        return subjectOptionList;
    }

    public List<SubjectOption> getNormalSubjectOptionBySubjectId(int subjectId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("subjectId",subjectId);
        List<SubjectOption> subjectOptionList = jdbcOperations.query("SELECT a.id,a.subjectId,a.`option` AS name,a.sortId  FROM `wxdk_subjectoption` a INNER JOIN wxdk_subject b ON a.`subjectId` = b.`id`WHERE a.`subjectId` = :subjectId ORDER BY a.sortId asc ",paramMap,new BeanPropertyRowMapper<>(SubjectOption.class));
        return subjectOptionList;
    }

    public void delete(int subjectId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("subjectId",subjectId);
        jdbcOperations.update("DELETE FROM wxdk_subjectoption WHERE subjectId = :subjectId",paramMap);
    }
}
