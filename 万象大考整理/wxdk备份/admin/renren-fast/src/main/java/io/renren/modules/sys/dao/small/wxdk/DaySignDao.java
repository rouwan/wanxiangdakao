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
public class DaySignDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int day,int goodType,int num , int isLookNum,String description){
        String sql = String.format("INSERT INTO `wxdk_daysign` (`day`,`goodType`,`num`,`isLookNum`,`description`,`modifyTime`,`createTime`,`flag`) VALUES('%s','%s','%s','%s','%s',NOW(),NOW(),1) ;",
                day,goodType,num,isLookNum,description);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void update(int id,int day,int goodType,int num , int isLookNum,String description){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("day",day);
        paramMap.put("goodType",goodType);
        paramMap.put("num",num);
        paramMap.put("isLookNum",isLookNum);
        paramMap.put("description",description);
        jdbcOperations.update("UPDATE `wxdk_daysign` SET `day`=:day,`goodType` = :goodType,`num` = :num,`isLookNum` = :isLookNum,`description`=:description,`modifyTime` = NOW(),`flag` = 2 WHERE `id` = :id ;",paramMap);
    }

    public void delete(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        jdbcOperations.update("UPDATE wxdk_daysign SET flag =3 ,`modifyTime` = NOW() WHERE id = :id ",paramMap);
    }

    public Map<String,Object> getDaySignById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return jdbcOperations.queryForMap("SELECT a.`id`,a.day,a.`goodType`,a.`num`,a.`isLookNum`,a.`modifyTime`,a.`createTime`,a.`flag`,b.name,b.`image`,a.description FROM `wxdk_daysign` a LEFT JOIN wxdk_goods b ON a.`goodType` = b.`type` where a.id = :id",paramMap);
    }

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM wxdk_daysign "+innerStr+" "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr , String innerStr , String whereStr , String orderByStr, int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM wxdk_daysign "+innerStr+" "+whereStr+" "+orderByStr+" LIMIT "+pageIndex+", "+pageSize;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }

    public boolean isExistsByDay(int id,int day) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("day",day);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_daysign WHERE flag<3 and day=:day and id != :id",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }

    public boolean isExistsByDay(int day) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("day",day);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_daysign WHERE flag<3 and day=:day",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }
}
