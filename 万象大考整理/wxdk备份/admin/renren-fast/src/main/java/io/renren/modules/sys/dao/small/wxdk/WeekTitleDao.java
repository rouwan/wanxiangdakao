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
public class WeekTitleDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(String bigName,String smallName,String image,int status,int sortId){
        String sql = String.format("INSERT INTO `wxdk_weektitle` (`bigName`,`smallName`,`image`,`status`,`sortId`,`flag`,`modifyTime`,`createTime`) VALUES('%s','%s','%s','%s','%s',1,NOW(),NOW()) ;",
                                   bigName,smallName,image,status,sortId);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void update(int id ,String bigName,String smallName,String image,int status,int sortId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("bigName",bigName);
        paramMap.put("smallName",smallName);
        paramMap.put("image",image);
        paramMap.put("status",status);
        paramMap.put("sortId",sortId);
        jdbcOperations.update("UPDATE `wxdk_weektitle` SET `bigName` = :bigName,`smallName` = :smallName,`image` = :image,`status` = :status,`sortId`=:sortId,`flag` = 2,`modifyTime` = NOW() WHERE `id` = :id ;",paramMap);
    }

    public void delete(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        jdbcOperations.update("UPDATE wxdk_weektitle SET flag =3 ,`modifyTime` = NOW() WHERE id = :id ",paramMap);
    }

    public Map<String,Object> getWeekTitleById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return jdbcOperations.queryForMap("SELECT `id`,`bigName`,`smallName`,`image`,`status`,`sortId` FROM wxdk_weektitle WHERE id = :id",paramMap);
    }

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM wxdk_weektitle "+innerStr+" "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr , String innerStr , String whereStr , String orderByStr, int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM wxdk_weektitle "+innerStr+" "+whereStr+" "+orderByStr+" LIMIT "+pageIndex+", "+pageSize;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }

    public Map<String,Object> getInfoBySmallName(String smallName){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("smallName",smallName);
        return jdbcOperations.queryForMap("SELECT `id`,`bigName`,`smallName`,`image`,`status` FROM wxdk_weektitle WHERE smallName = :smallName limit 1",paramMap);

    }
    public Boolean isExistsByName(String smallName){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("smallName",smallName);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_weektitle WHERE flag<3 and smallName=:smallName",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }

    public Boolean isExistsByName(int id,String smallName){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("smallName",smallName);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_weektitle WHERE flag<3 and smallName=:smallName and id != :id",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }
}
