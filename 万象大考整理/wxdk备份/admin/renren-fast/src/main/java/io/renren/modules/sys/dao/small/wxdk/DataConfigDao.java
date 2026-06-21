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

@Component("wxdk_dataconfig")
public class DataConfigDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;


    public int insert(String name ,String value,String description,int type ){

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_dataconfig` (`name`,`value`,`description`,`type`,`modifyTime`,`createTime`,`flag`) VALUES('%s','%s','%s','%s',NOW(),NOW(),'1') ;",name,value,description,type),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void update(int id , String name ,String value,String description,int type ){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("id",id);
        paramMap.put("name",name);
        paramMap.put("value",value);
        paramMap.put("description",description);
        paramMap.put("type",type);
        jdbcOperations.update("UPDATE `wxdk_dataconfig` SET `name` =:name,`value` =:value,`description` =:description,`type` =:type,`modifyTime` = NOW(),`flag`= 2 WHERE `id` = :id ",paramMap);
    }

    public void delete(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        jdbcOperations.update("UPDATE wxdk_dataconfig SET modifyTime = now(),flag = 3 WHERE id = :id",paramMap);
    }

    public Map<String,Object> getDataConfigById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return jdbcOperations.queryForMap("SELECT `id`,`name`,`value`,`description`,`type`,DATE_FORMAT(modifyTime, '%Y-%m-%d %H:%i:%s') as modifyTime FROM wxdk_dataconfig WHERE id = :id",paramMap);
    }

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM wxdk_dataconfig "+innerStr+" "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr , String innerStr , String whereStr , String orderByStr, int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM wxdk_dataconfig "+innerStr+" "+whereStr+" "+orderByStr+" LIMIT "+pageIndex+", "+pageSize;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }
}
