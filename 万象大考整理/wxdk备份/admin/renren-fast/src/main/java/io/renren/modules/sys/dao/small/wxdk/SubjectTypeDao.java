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
public class SubjectTypeDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int parentType,String name,int sortId){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_subjecttype` (`parentType`,`name`,`sortId`,`modifyTime`,`createTime`,`flag`) VALUES('%s','%s','%s',NOW(),NOW(),1) ;",parentType,name,sortId),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

     public void delete(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        jdbcOperations.update("UPDATE wxdk_subjecttype SET flag =3 , modifyTime = NOW() WHERE id = :id",paramMap);
     }

     public void update(int id ,int parentType, String name,int sortId){
         Map<String,Object> paramMap = new HashMap<>();
         paramMap.put("id",id);
         paramMap.put("parentType",parentType);
         paramMap.put("name",name);
         paramMap.put("sortId",sortId);
         jdbcOperations.update("UPDATE wxdk_subjecttype SET parentType=:parentType,name=:name,sortId=:sortId,flag =2 , modifyTime = NOW() WHERE id = :id",paramMap);

     }

    public Map<String,Object> getSubjectTypeById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return jdbcOperations.queryForMap("SELECT `id`,`parentType`,`name`,`sortId`,DATE_FORMAT(modifyTime, '%Y-%m-%d %H:%i:%s') as modifyTime FROM wxdk_subjecttype WHERE id = :id",paramMap);
    }

    public List<Map<String,Object>> getSubjectTypeByTypeId(int parentType){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("parentType",parentType);
        return jdbcOperations.queryForList("SELECT `id`,`parentType`,`name`,`sortId`,DATE_FORMAT(modifyTime, '%Y-%m-%d %H:%i:%s') as modifyTime FROM wxdk_subjecttype WHERE parentType = :parentType",paramMap);
    }

    public Map<String,Object> getSubjectTypeByName(String name){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        return jdbcOperations.queryForMap("SELECT `id`,`parentType`,`name`,`sortId`,DATE_FORMAT(modifyTime, '%Y-%m-%d %H:%i:%s') as modifyTime FROM wxdk_subjecttype WHERE name = :name limit 1",paramMap);
    }

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM wxdk_subjecttype "+innerStr+" "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr , String innerStr , String whereStr , String orderByStr, int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM `wxdk_subjecttype` "+innerStr+" "+whereStr+" "+orderByStr+" LIMIT "+pageIndex+", "+pageSize;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }

    public int getMaxSortIdByType(int parentType){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("parentType",parentType);
        return jdbcOperations.queryForObject("SELECT ifnull(MAX(sortId),0) FROM wxdk_subjectType WHERE parentType = :parentType ",paramMap,Integer.class);
    }

    public Boolean isExistsByName(String name){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        String sql = "SELECT count(1) FROM wxdk_subjecttype where name = :name and flag<3 ";
        int count = jdbcOperations.queryForObject(sql,paramMap,Integer.class);
        if(count > 0 ){
            return true;
        }
        return false;
    }


}
