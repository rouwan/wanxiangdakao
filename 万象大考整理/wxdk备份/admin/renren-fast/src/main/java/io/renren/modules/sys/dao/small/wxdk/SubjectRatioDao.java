package io.renren.modules.sys.dao.small.wxdk;

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
public class SubjectRatioDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int subjectId,int totalNum,int rightNum,int errorNum){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_subjectratio` (`subjectId`,`totalNum`,`rightNum`,`errorNum`,`modifyTime`,`createTime`) VALUES('%s','%s', '%s','%s',NOW(),NOW()) ;",
                subjectId,totalNum,rightNum,errorNum),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public List<Map<String,Object>> getSubjectLevelTypeRatio(){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.*,");
        sql.append("FORMAT(hardNum/totalNum * 100,2) AS hardRatio,FORMAT(commonNum/totalNum * 100,2) AS commonRatio,FORMAT(simpleNum/totalNum * 100,2) AS simpleRatio ");
        sql.append("FROM ( ");
        sql.append("SELECT ");
        sql.append("(SELECT COUNT(1) FROM wxdk_subject WHERE `status` = 1 AND flag < 3) AS totalNum, ");
        sql.append("SUM(CASE WHEN IFNULL((b.`rightNum`/b.totalNum)*100,0)>=0 AND IFNULL((b.`rightNum`/b.totalNum)*100,0)<33 THEN 1 ELSE 0 END ) AS hardNum,");
        sql.append("SUM(CASE WHEN IFNULL((b.`rightNum`/b.totalNum)*100,0)>=33 AND IFNULL((b.`rightNum`/b.totalNum)*100,0)<=66 THEN 1 ELSE 0 END ) AS commonNum,");
        sql.append("SUM(CASE WHEN IFNULL((b.`rightNum`/b.totalNum)*100,0)>66 AND IFNULL((b.`rightNum`/b.totalNum)*100,0)<=100 THEN 1 ELSE 0 END ) AS simpleNum ");
        sql.append("FROM `wxdk_subject` a ");
        sql.append("LEFT JOIN wxdk_subjectratio b ON a.id = b.`subjectId` WHERE a.flag < 3 AND a.`status` = 1 ) a");
        return jdbcOperations.queryForList(sql.toString(),new HashMap<>());
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
        jdbcOperations.update("UPDATE `wxdk_subjectratio` SET `totalNum` = totalNum+:totalNum,`rightNum` = rightNum+:rightNum,`errorNum` = errorNum+:errorNum,`modifyTime` = NOW() WHERE `subjectId` = :subjectId;",paramMap);
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
