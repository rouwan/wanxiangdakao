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
public class ClearDataTimeDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(String clearTime,int type ){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_cleardatatime`(clearTime,`type`) VALUES ('%s','%s');",
                                            clearTime,type),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void update(int id , String clearTime,int type ){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("id",id);
        paramMap.put("clearTime",clearTime);
        paramMap.put("type",type);
        jdbcOperations.update("UPDATE wxdk_cleardatatime SET clearTime = :clearTime,type=:type WHERE id = :id ",paramMap);
    }

    public void delete(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        jdbcOperations.update("DELETE FROM wxdk_cleardatatime WHERE id = :id",paramMap);
    }

    public Map<String,Object> getClearDataTimeById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return jdbcOperations.queryForMap("SELECT id,type,date_format(clearTime,'%Y-%m-%d') as clearTime FROM wxdk_cleardatatime WHERE id = :id",paramMap);
    }

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM wxdk_cleardatatime "+innerStr+" "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr , String innerStr , String whereStr , String orderByStr, int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM wxdk_cleardatatime "+innerStr+" "+whereStr+" "+orderByStr+" LIMIT "+pageIndex+", "+pageSize;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }

    public boolean isExistsByType(int type) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("type",type);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_cleardatatime WHERE type = :type ",paramMap,Integer.class);
        if(count >0 ){
            return true;
        }
        return false;
    }

    public boolean isExistsByType(int id , int type) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("type",type);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_cleardatatime WHERE id != :id and type = :type ",paramMap,Integer.class);
        if(count >0 ){
            return true;
        }
        return false;
    }
}
