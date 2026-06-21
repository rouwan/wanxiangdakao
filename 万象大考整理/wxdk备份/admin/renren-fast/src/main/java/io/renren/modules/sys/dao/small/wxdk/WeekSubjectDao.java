package io.renren.modules.sys.dao.small.wxdk;

import io.renren.modules.sys.domain.small.wxdk.WeekSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WeekSubjectDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(WeekSubject weekSubject){
        String sql = "INSERT INTO `wxdk_weeksubject` (`subjectId`,`titleId`,`sortId`,`modifyTime`,`createTime`) VALUES (:subjectId,:titleId,:sortId,NOW(),NOW()) ;";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(weekSubject),keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void delete(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        jdbcOperations.update("DELETE FROM wxdk_weeksubject WHERE id = :id",paramMap);
    }

    public Map<String,Object> getWeekSubjectBySubjectId(int subjectId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("subjectId",subjectId);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.*,IFNULL(d.`parentType`,0) AS parentType,IFNULL(d.name,'') AS typeName ,a.titleId,b.`smallName`,a.id as weekSubjectId,");
        sql.append("CASE WHEN IFNULL((`rightNum`/totalNum)*100,0)>=0 AND IFNULL((`rightNum`/totalNum)*100,0)<33 THEN 3 ");
        sql.append("WHEN IFNULL((`rightNum`/totalNum)*100,0)>=33 AND IFNULL((`rightNum`/totalNum)*100,0)<=66 THEN 2 ");
        sql.append("WHEN IFNULL((`rightNum`/totalNum)*100,0)>66 AND IFNULL((`rightNum`/totalNum)*100,0)<=100 THEN 1 END AS levelType ");

        sql.append("FROM wxdk_weeksubject a ");
        sql.append("INNER JOIN `wxdk_weektitle` b ON a.`titleId` = b.id ");
        sql.append("LEFT JOIN `wxdk_subject` c ON a.`subjectId` = c.`id` ");
        sql.append("LEFT JOIN `wxdk_subjecttype` d ON c.typeId = d.id ");
        sql.append("LEFT JOIN `wxdk_subjectratio` e ON a.subjectId = e.`subjectId`");
        sql.append("WHERE a.subjectId = :subjectId limit 1 ");
        return jdbcOperations.queryForMap(sql.toString(),paramMap);
    }

    public void update(WeekSubject weekSubject){
        String sql = "UPDATE `wxdk_weeksubject` SET `titleId` = :titleId,`modifyTime` = NOW() WHERE `subjectId` = :subjectId ;";
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(weekSubject));
    }

    public int getMaxSortId() {
        return jdbcOperations.queryForObject("SELECT IFNULL(MAX(sortId),0) FROM wxdk_weeksubject;", new HashMap<>(), Integer.class);
    }

    public Map<String,Object> getWeekSubjectId(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return jdbcOperations.queryForMap("select * from wxdk_weeksubject where id =:id limit 1",paramMap);
    }

    public boolean isExistsBySubjectId(int subjectId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("subjectId",subjectId);
        int count = jdbcOperations.queryForObject("select count(1) from wxdk_weeksubject where subjectId = :subjectId ",paramMap,Integer.class) ;
        if(count > 0){
            return true;
        }
        return false;
    }

    public void updateAfterSortIdById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        int sortId = Integer.parseInt(this.getWeekSubjectId(id).get("sortId").toString());
        paramMap.put("sortId",sortId);
        jdbcOperations.update("UPDATE wxdk_weeksubject SET sortId = sortId - 1 ,modifyTime = NOW() WHERE sortId > :sortId ",paramMap);
    }

}
