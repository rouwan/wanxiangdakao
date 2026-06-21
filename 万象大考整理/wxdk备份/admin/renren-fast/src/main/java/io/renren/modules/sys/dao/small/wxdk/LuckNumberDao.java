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
public class LuckNumberDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(int number,int goodType,int num , int isLookNum,String description,int sortId){
        String sql = String.format("INSERT INTO `wxdk_lucknumber` (`number`,`goodType`,`num`,`isLookNum`,`description`,`sortId`,`modifyTime`,`createTime`,`flag`) VALUES('%s','%s','%s','%s','%s','%s',NOW(),NOW(),1) ;",
                number,goodType,num,isLookNum,description,sortId);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void update(int id,int number,int goodType,int num , int isLookNum,String description,int sortId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("number",number);
        paramMap.put("goodType",goodType);
        paramMap.put("num",num);
        paramMap.put("isLookNum",isLookNum);
        paramMap.put("description",description);
        paramMap.put("sortId",sortId);
        jdbcOperations.update("UPDATE  `wxdk_lucknumber` SET `number` = :number,`goodType` = :goodType,`num` = :num,`isLookNum` = :isLookNum,`description` = :description,`sortId`= :sortId,`modifyTime` = NOW(),`flag` = 2 WHERE `id` = :id ;",paramMap);
    }

    public void delete(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        jdbcOperations.update("UPDATE wxdk_lucknumber SET flag =3 ,`modifyTime` = NOW() WHERE id = :id ",paramMap);
    }

    public Map<String,Object> getLuckNumberById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return jdbcOperations.queryForMap("SELECT a.`id`,a.`number`,a.`goodType`,a.`num`,a.`isLookNum`,a.`description`,a.`modifyTime`,a.`createTime`,a.`flag` ,IFNULL(b.`image`,'') AS image,b.name,a.sortId FROM `wxdk_lucknumber` a LEFT JOIN `wxdk_goods` b ON a.goodType = b.`type` where a.id = :id",paramMap);
    }

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM wxdk_lucknumber "+innerStr+" "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr , String innerStr , String whereStr , String orderByStr, int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM wxdk_lucknumber "+innerStr+" "+whereStr+" "+orderByStr+" LIMIT "+pageIndex+", "+pageSize;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }

    public boolean isExistsByNumber(int id,int number) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("number",number);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_lucknumber WHERE flag<3 and number=:number and id != :id",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }

    public boolean isExistsByNumber(int number) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("number",number);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_lucknumber WHERE flag<3 and number=:number",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }

    public int getMaxSortId () {
        return jdbcOperations.queryForObject("SELECT IFNULL(MAX(sortId),0) FROM wxdk_lucknumber WHERE flag < 3 ",new HashMap<>(),Integer.class);
    }
}
