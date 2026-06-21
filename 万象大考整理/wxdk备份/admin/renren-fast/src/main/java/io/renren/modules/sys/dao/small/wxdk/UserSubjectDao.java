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
public class UserSubjectDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int userId,int subjectId){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_usersubject` (`userId`,`subjectId`,`modifyTime`,`createTime`) VALUES('%s','%s',NOW(),NOW()) ;",userId,subjectId),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM wxdk_usersubject d "+innerStr+" WHERE "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr ,String innerLimitStr, String innerStr , String whereStr , String orderByStr, int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM `wxdk_usersubject` d "+innerLimitStr +innerStr;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }

    public Map<String,Object> getSubjectById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT d.id AS userSubjectId,a.*,IFNULL(b.`parentType`,0) AS parentType,IFNULL(b.name,'') AS typeName, ");
        sql.append("CASE WHEN IFNULL((`rightNum`/totalNum)*100,0)>=0 AND IFNULL((`rightNum`/totalNum)*100,0)<33 THEN 3 ");
        sql.append("WHEN IFNULL((`rightNum`/totalNum)*100,0)>=33 AND IFNULL((`rightNum`/totalNum)*100,0)<=66 THEN 2 ");
        sql.append("WHEN IFNULL((`rightNum`/totalNum)*100,0)>66 AND IFNULL((`rightNum`/totalNum)*100,0)<=100 THEN 1 END AS levelType ");
        sql.append("FROM wxdk_usersubject d ");
        sql.append("INNER JOIN wxdk_subject a on d.subjectId = a.id  ");
        sql.append("LEFT JOIN `wxdk_subjecttype` b ON a.typeId = b.id ");
        sql.append("LEFT JOIN `wxdk_subjectratio` c ON a.id = c.`subjectId`");
        sql.append("WHERE d.id = :id ");
        return jdbcOperations.queryForMap(sql.toString(),paramMap);
    }


    public List<Map<String,Object>> getNextSubjectById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT d.id AS userSubjectId,a.*,IFNULL(b.`parentType`,0) AS parentType,IFNULL(b.name,'') AS typeName, ");
        sql.append("CASE WHEN IFNULL((`rightNum`/totalNum)*100,0)>=0 AND IFNULL((`rightNum`/totalNum)*100,0)<33 THEN 3 ");
        sql.append("WHEN IFNULL((`rightNum`/totalNum)*100,0)>=33 AND IFNULL((`rightNum`/totalNum)*100,0)<=66 THEN 2 ");
        sql.append("WHEN IFNULL((`rightNum`/totalNum)*100,0)>66 AND IFNULL((`rightNum`/totalNum)*100,0)<=100 THEN 1 END AS levelType ");
        sql.append("FROM wxdk_usersubject d ");
        sql.append("INNER JOIN wxdk_subject a on d.subjectId = a.id ");
        sql.append("LEFT JOIN `wxdk_subjecttype` b ON a.typeId = b.id ");
        sql.append("LEFT JOIN `wxdk_subjectratio` c ON a.id = c.`subjectId`");
        sql.append("WHERE d.id > :id and a.flag < 3 ORDER BY d.id ASC LIMIT 1 ");
        return jdbcOperations.queryForList(sql.toString(),paramMap);
    }

    public List<Map<String,Object>> getPreSubjectById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT d.id AS userSubjectId,a.*,IFNULL(b.`parentType`,0) AS parentType,IFNULL(b.name,'') AS typeName, ");
        sql.append("CASE WHEN IFNULL((`rightNum`/totalNum)*100,0)>=0 AND IFNULL((`rightNum`/totalNum)*100,0)<33 THEN 3 ");
        sql.append("WHEN IFNULL((`rightNum`/totalNum)*100,0)>=33 AND IFNULL((`rightNum`/totalNum)*100,0)<=66 THEN 2 ");
        sql.append("WHEN IFNULL((`rightNum`/totalNum)*100,0)>66 AND IFNULL((`rightNum`/totalNum)*100,0)<=100 THEN 1 END AS levelType ");
        sql.append("FROM wxdk_usersubject d ");
        sql.append("INNER JOIN wxdk_subject a on d.subjectId = a.id ");
        sql.append("LEFT JOIN `wxdk_subjecttype` b ON a.typeId = b.id ");
        sql.append("LEFT JOIN `wxdk_subjectratio` c ON a.id = c.`subjectId`");
        sql.append("WHERE d.id < :id and a.flag < 3 ORDER BY d.id DESC LIMIT 1 ");
        return jdbcOperations.queryForList(sql.toString(),paramMap);
    }

    public void updateRejectRemark(int id , String rejectRemark){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("rejectRemark",rejectRemark);
        jdbcOperations.update("UPDATE wxdk_usersubject SET rejectRemark = :rejectRemark ,modifyTime = NOW() WHERE id = :id",paramMap);
    }

    public boolean isExistsBySubjectId(int subjectId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("subjectId",subjectId);
        String sql = "SELECT count(1) FROM wxdk_usersubject where subjectId = :subjectId ";
        int count = jdbcOperations.queryForObject(sql,paramMap,Integer.class);
        if(count > 0 ){
            return true;
        }
        return false;
    }

    public boolean isExistsNextBySubjectId(int userId,int subjectId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("subjectId",subjectId);
        String sql = "SELECT count(1) FROM wxdk_usersubject where userId = :userId AND subjectId = :subjectId ";
        int count = jdbcOperations.queryForObject(sql,paramMap,Integer.class);
        if(count > 0 ){
            return true;
        }
        return false;
    }
}
