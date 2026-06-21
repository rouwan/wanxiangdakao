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
public class BookDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int type ,String image,String remark,int sortId ){
        String sql = String.format("INSERT INTO `wxdk_book` (`type`,`remark`,`image`,`sortId`,`modifyTime`,`createTime`) VALUES('%s','%s','%s','%s',NOW(),NOW()) ;",
                type,remark,image,sortId);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void update(int id ,int type ,String image,String remark,int sortId ){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("type",type);
        paramMap.put("image",image);
        paramMap.put("remark",remark);
        paramMap.put("sortId",sortId);
        jdbcOperations.update("UPDATE `wxdk_book` SET sortId= :sortId,`type` = :type,`remark` = :remark,`image` = :image,`modifyTime` = NOW() WHERE `id` = :id ;",paramMap);
    }

    public void delete(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        jdbcOperations.update("DELETE FROM wxdk_book WHERE id = :id; ",paramMap);
    }

    public Map<String,Object> getBookById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return jdbcOperations.queryForMap("SELECT `id`,`type`,`remark`,`image`,`sortId`,`modifyTime`,`createTime`  FROM wxdk_book WHERE id = :id",paramMap);
    }

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM wxdk_book "+innerStr+" "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr , String innerStr , String whereStr , String orderByStr, int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM wxdk_book "+innerStr+" "+whereStr+" "+orderByStr+" LIMIT "+pageIndex+", "+pageSize;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }

    public int getMaxSortId () {
        return jdbcOperations.queryForObject("SELECT IFNULL(MAX(sortId),0) FROM wxdk_book WHERE flag < 3 ",new HashMap<>(),Integer.class);
    }

    public boolean isExistsByType(int id,int type) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("type",type);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_book WHERE type=:type and id != :id",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }

    public boolean isExistsByType(int type) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("type",type);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_book WHERE type=:type",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }
}
