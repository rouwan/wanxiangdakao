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
public class RobotDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(String nickName,String sexDesc,String headImgUrl,int type,int status,String country,String province,String city,float simpleRatio,float commonRatio,float hardRatio,String simpleTime,String commonTime,String hardTime){
        String sql = String.format("INSERT INTO `wxdk_robot` (`nickname`,`sexDesc`,`headImgUrl`,`type`,`status`,country,province,city,simpleRatio,commonRatio,hardRatio,simpleTime,commonTime,hardTime,`modifyTime`,`createTime`,`flag`) VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',NOW(),NOW(),1);",
                nickName,sexDesc,headImgUrl,type,status,country,province,city,simpleRatio,commonRatio,hardRatio,simpleTime,commonTime,hardTime);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void update(int id ,String nickName,String sexDesc,String headImgUrl,int type,int status,String country,String province,String city,float simpleRatio,float commonRatio,float hardRatio,String simpleTime,String commonTime,String hardTime){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("nickName",nickName);
        paramMap.put("sexDesc",sexDesc);
        paramMap.put("headImgUrl",headImgUrl);
        paramMap.put("type",type);
        paramMap.put("status",status);
        paramMap.put("country",country);
        paramMap.put("province",province);
        paramMap.put("city",city);
        paramMap.put("simpleRatio",simpleRatio);
        paramMap.put("commonRatio",commonRatio);
        paramMap.put("hardRatio",hardRatio);
        paramMap.put("simpleTime",simpleTime);
        paramMap.put("commonTime",commonTime);
        paramMap.put("hardTime",hardTime);
        jdbcOperations.update("UPDATE `wxdk_robot` SET`nickname` = :nickName,`sexDesc` = :sexDesc,`headImgUrl` = :headImgUrl,`type` = :type,`status` = :status,country= :country,province= :province,city = :city,simpleRatio = :simpleRatio,commonRatio = :commonRatio,hardRatio = :hardRatio,simpleTime = :simpleTime,commonTime = :commonTime,hardTime = :hardTime,`modifyTime` = NOW(),flag =2 WHERE `id` = :id ;",paramMap);
    }

    public void delete(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        jdbcOperations.update("UPDATE wxdk_robot SET flag =3 ,`modifyTime` = NOW() WHERE id = :id ",paramMap);
    }

    public Map<String,Object> getRobotById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return jdbcOperations.queryForMap("SELECT a.`id`,a.`nickname`,a.`sexDesc`,a.`headImgUrl`,a.`type`,a.`status`,a.`country`,a.`province`,a.`city`,a.simpleRatio,a.commonRatio,a.hardRatio,a.simpleTime,a.commonTime,a.hardTime ,IFNULL(b.`paragraph`,1) AS paragraph,IFNULL(b.`star`,0) AS star FROM wxdk_robot a LEFT JOIN `wxdk_robotparagraph` b ON a.`id` = -b.`userId` WHERE a.id = :id",paramMap);
    }

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM wxdk_robot "+innerStr+" "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr , String innerStr , String whereStr , String orderByStr, int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM wxdk_robot "+innerStr+" "+whereStr+" "+orderByStr+" LIMIT "+pageIndex+", "+pageSize;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }

    public Boolean isExistsByName(String name){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("nickname",name);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_robot WHERE flag<3 and nickname=:nickname",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }

    public Boolean isExistsByName(int id,String name){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("nickname",name);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_robot WHERE flag<3 and nickname=:nickname and id != :id",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }
}
