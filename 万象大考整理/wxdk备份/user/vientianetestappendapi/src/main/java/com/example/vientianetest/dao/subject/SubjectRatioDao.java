package com.example.vientianetest.dao.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SubjectRatioDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int subjectId,int totalNum,int rightNum,int errorNum){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_subjectratio` (`subjectId`,`totalNum`,`rightNum`,`errorNum`,`realRightNum`,`realTotalNum`,`modifyTime`,`createTime`) VALUES('%s','%s','%s','%s','%s','%s',NOW(),NOW()) ;",
                            subjectId,totalNum,rightNum,errorNum,0,0),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void update(int subjectId,int totalNum,int rightNum,int errorNum){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("subjectId",subjectId);
        paramMap.put("totalNum",totalNum);
        paramMap.put("rightNum",rightNum);
        paramMap.put("errorNum",errorNum);
        jdbcOperations.update("UPDATE `wxdk_subjectratio` SET `totalNum` = :totalNum,`rightNum` = :rightNum,`errorNum` = :errorNum,`modifyTime` = NOW() WHERE `subjectId` = :subjectId;",paramMap);
    }

    public void appendValueByUserId(int subjectId,int totalNum,int rightNum,int errorNum){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("subjectId",subjectId);
        paramMap.put("totalNum",totalNum);
        paramMap.put("rightNum",rightNum);
        paramMap.put("errorNum",errorNum);
        jdbcOperations.update("UPDATE `wxdk_subjectratio` SET `totalNum` = totalNum+:totalNum,`rightNum` = rightNum+:rightNum,`errorNum` = errorNum+:errorNum,`realRightNum` = realRightNum + :rightNum,`realTotalNum` = realTotalNum + :totalNum ,`modifyTime` = NOW() WHERE `subjectId` = :subjectId;",paramMap);
    }

    public boolean isExistsBySubjectId(int subjectId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("subjectId",subjectId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_subjectratio` WHERE subjectId = :subjectId",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }
}
