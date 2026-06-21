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
public class GoodsDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int type ,String image,String name,int sortId ){
        String sql = String.format("INSERT INTO `wxdk_goods` (`type`,`image`,`name`,`sortId`,`flag`,`modifyTime`,`createTime`) VALUES('%s','%s','%s','%s',1,NOW(),NOW()) ;",
                type,image,name,sortId);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void update(int id ,int type ,String image,String name ){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("type",type);
        paramMap.put("image",image);
        paramMap.put("name",name);
        jdbcOperations.update("UPDATE `wxdk_goods` SET `type` = :type,`image` = :image,`name` = :name,`flag` = 2,`modifyTime` = NOW() WHERE `id` = :id ;",paramMap);
    }

    public void delete(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        jdbcOperations.update("UPDATE wxdk_goods SET flag =3 ,`modifyTime` = NOW() WHERE id = :id ",paramMap);
    }

    public Map<String,Object> getGoodsById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return jdbcOperations.queryForMap("SELECT `id`,`type`,`image`,`name`,`sortId`,`flag`,`modifyTime`,`createTime`  FROM wxdk_goods WHERE id = :id",paramMap);
    }

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM wxdk_goods "+innerStr+" "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr , String innerStr , String whereStr , String orderByStr, int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM wxdk_goods "+innerStr+" "+whereStr+" "+orderByStr+" LIMIT "+pageIndex+", "+pageSize;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }

    public int getMaxSortId () {
        return jdbcOperations.queryForObject("SELECT IFNULL(MAX(sortId),0) FROM wxdk_goods WHERE flag < 3 ",new HashMap<>(),Integer.class);
    }

    public boolean isExistsByType(int id,int type) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("type",type);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_goods WHERE flag<3 and type=:type and id != :id",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }

    public boolean isExistsByType(int type) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("type",type);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_goods WHERE flag<3 and type=:type",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }
}
