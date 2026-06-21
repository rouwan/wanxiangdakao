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
public class EmailDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;


    public int insert(String title ,String content,String gift,int status ){

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_email` (`title`,`content`,`gift`,`status`,`modifyTime`,`createTime`,`flag`) VALUES('%s','%s','%s','%s',NOW(),NOW(),1) ;",
                title,content,gift,status),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public void update(int id , String title ,String content,String gift,int status ){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("title",title);
        paramMap.put("content",content);
        paramMap.put("gift",gift);
        paramMap.put("status",status);
        jdbcOperations.update("UPDATE `wxdk_email` SET `title` = :title,`content` = :content,`gift` = :gift,`status` = :status,`modifyTime` = NOW(),`flag` = 2  WHERE `id` = :id ; ",paramMap);
    }

    public void delete(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        jdbcOperations.update("UPDATE wxdk_email SET modifyTime = now(),flag = 3 WHERE id = :id",paramMap);
    }

    public Map<String,Object> getEmailById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return jdbcOperations.queryForMap("SELECT `id`,`title`,`content`,`gift`,`status`,DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s') AS createTime FROM `wxdk_email` WHERE id = :id",paramMap);
    }

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM wxdk_email "+innerStr+" "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr , String innerStr , String whereStr , String orderByStr, int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM wxdk_email "+innerStr+" "+whereStr+" "+orderByStr+" LIMIT "+pageIndex+", "+pageSize;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }

}
