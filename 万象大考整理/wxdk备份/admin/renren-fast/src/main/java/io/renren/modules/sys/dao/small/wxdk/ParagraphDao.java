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
public class ParagraphDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(String name,String image,int level,int status){
        String sql = String.format("INSERT INTO `wxdk_paragraph` (`name`,`image`,`status`,`level`,`modifyTime`,`createTime`,`flag`) VALUES('%s','%s','%s','%s',NOW(),NOW(),1) ;",
                                    name,image,status,level);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void update(int id ,String name,String image,int level,int status){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("name",name);
        paramMap.put("image",image);
        paramMap.put("level",level);
        paramMap.put("status",status);
        jdbcOperations.update("UPDATE `wxdk_paragraph` SET`name` = :name,`image` = :image,`status` = :status,`level`=:level,`modifyTime` = NOW(),`flag` = 2  WHERE `id` = :id ;",paramMap);
    }

    public void delete(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        jdbcOperations.update("UPDATE wxdk_paragraph SET flag =3 ,`modifyTime` = NOW() WHERE id = :id ",paramMap);
    }

    public Map<String,Object> getParagraphById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return jdbcOperations.queryForMap("SELECT * FROM wxdk_paragraph WHERE id = :id",paramMap);
    }

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM wxdk_paragraph "+innerStr+" "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr , String innerStr , String whereStr , String orderByStr, int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM wxdk_paragraph "+innerStr+" "+whereStr+" "+orderByStr+" LIMIT "+pageIndex+", "+pageSize;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }

    public Boolean isExistsByName(String name){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_paragraph WHERE flag<3 and name=:name",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }

    public Boolean isExistsByName(int id,String name){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("name",name);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_paragraph WHERE flag<3 and name=:name and id != :id",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }

    public Boolean isExistsByLevel(int level){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("level",level);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_paragraph WHERE flag<3 and level=:level",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }

    public Boolean isExistsByLevel(int id,int level){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("level",level);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_paragraph WHERE flag<3 and level=:level and id != :id",paramMap,Integer.class);
        if(count>0){
            return true;
        }
        return false;
    }
}
