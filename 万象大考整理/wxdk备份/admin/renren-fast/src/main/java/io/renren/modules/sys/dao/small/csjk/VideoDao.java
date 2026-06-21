package io.renren.modules.sys.dao.small.csjk;

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
public class VideoDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(String video,String title,int seconds,int sortId,int status,String image,int frame){
        String sql = String.format("INSERT INTO `csjk_video` (`video`,`title`,`seconds`,`sortId`,`status`,`image`,`frame`,`flag`,`modifyTime`,`createTime`) VALUES('%s','%s','%s','%s','%s','%s','%s',1,NOW(),NOW());",
                                    video,title,seconds,sortId,status,image,frame);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void update(int id ,String video,String title,int seconds,int sortId,int status,String image,int frame){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("video",video);
        paramMap.put("title",title);
        paramMap.put("seconds",seconds);
        paramMap.put("sortId",sortId);
        paramMap.put("status",status);
        paramMap.put("image",image);
        paramMap.put("frame",frame);
        jdbcOperations.update("UPDATE `csjk_video` SET `video` = :video,`title` = :title,`seconds` = :seconds, `sortId` = :sortId,`status` = :status,`image`=:image,`frame`=:frame ,`flag` = 2,`modifyTime` = NOW() WHERE `id` = :id ;",paramMap);
    }

    public void delete(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        jdbcOperations.update("UPDATE csjk_video SET flag = 3 ,`modifyTime` = NOW() WHERE id = :id ",paramMap);
    }

    public Map<String,Object> getVideoById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return jdbcOperations.queryForMap("SELECT `id`,`video`,`title`,`seconds`,`sortId`,`status`,`image`,`frame`  FROM csjk_video WHERE id = :id",paramMap);
    }

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM csjk_video "+innerStr+" "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr , String innerStr , String whereStr , String orderByStr, int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM csjk_video "+innerStr+" "+whereStr+" "+orderByStr+" LIMIT "+pageIndex+", "+pageSize;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }

    public int getMaxSortId(){
        Map<String,Object> paramMap = new HashMap<>();
        return jdbcOperations.queryForObject("SELECT ifnull(MAX(sortId),0) FROM csjk_video WHERE flag < 3 ",paramMap,Integer.class);

    }

    public boolean isExistsByTitle(String title){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("title",title);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM csjk_video WHERE flag < 3 AND title = :title",paramMap,Integer.class);
        if(count > 0 ){
            return true;
        }
        return false;
    }
    public boolean isExistsByTitle(int id ,String title){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("title",title);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM csjk_video WHERE flag < 3 AND id !=:id AND title = :title",paramMap,Integer.class);
        if(count > 0 ){
            return true;
        }
        return false;
    }

}
