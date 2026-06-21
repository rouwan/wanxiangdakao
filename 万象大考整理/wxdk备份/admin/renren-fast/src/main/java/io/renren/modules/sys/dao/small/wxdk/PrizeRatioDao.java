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
public class PrizeRatioDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int minValue,int maxValue,int num , int goodType,String description,int sortId){
        String sql = String.format("INSERT INTO `wxdk_prizeratio` (`minValue`,`maxValue`,`num`,`goodType`,`description`,`sortId`,`modifyTime`,`createTime`,`flag`) VALUES('%s','%s','%s','%s','%s','%s',NOW(),NOW(),1) ;",
                                    minValue,maxValue,num,goodType,description,sortId);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void update(int id,int minValue,int maxValue,int num ,int goodType,String description){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("minValue",minValue);
        paramMap.put("maxValue",maxValue);
        paramMap.put("num",num);
        paramMap.put("goodType",goodType);
        paramMap.put("description",description);
        jdbcOperations.update("UPDATE `wxdk_prizeratio` SET `minValue` = :minValue,`maxValue` = :maxValue,`num` = :num,`goodType` = :goodType,`description` = :description,`modifyTime` = NOW(),`flag` = 2 WHERE `id` = :id;",paramMap);
    }

    public void delete(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        jdbcOperations.update("UPDATE wxdk_prizeratio SET flag =3 ,`modifyTime` = NOW() WHERE id = :id ",paramMap);
    }

    public Map<String,Object> getPrizeById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return jdbcOperations.queryForMap("SELECT a.`id`,a.`minValue`,a.`maxValue`,a.`num`,IFNULL(a.`goodType`,'') AS goodType ,a.`description`,a.`sortId`,IFNULL(b.`name`,'') AS goodName ,IFNULL(b.`image`,'') AS image FROM wxdk_prizeratio a  LEFT JOIN `wxdk_goods` b ON a.`goodType` = b.`type` WHERE a.id = :id",paramMap);
    }

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM wxdk_prizeratio "+innerStr+" "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr , String innerStr , String whereStr , String orderByStr, int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM wxdk_prizeratio "+innerStr+" "+whereStr+" "+orderByStr+" LIMIT "+pageIndex+", "+pageSize;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }

    public int getMaxSortId () {
        return jdbcOperations.queryForObject("SELECT IFNULL(MAX(sortId),0) FROM wxdk_prizeratio WHERE flag < 3 ",new HashMap<>(),Integer.class);
    }

}
