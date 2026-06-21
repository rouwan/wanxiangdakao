package io.renren.modules.sys.dao.small;

import io.renren.modules.sys.domain.BasicResultObject;
import io.renren.modules.sys.domain.BasicTagState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PYGXWebDao {

    @Autowired
    NamedParameterJdbcOperations jdbcOperations;

    //region getContentList
    public BasicResultObject getContentList(int pageIndex, int pageSize,String keyWords){
        BasicResultObject obj = new BasicResultObject();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("pageIndex",(pageIndex-1) * pageSize);
        paramMap.put("pageSize",pageSize);
        paramMap.put("keyWords",keyWords);

        StringBuilder countSb =new StringBuilder();
        countSb.append("select count(1) from pygx_content a ");
        countSb.append("WHERE flag < 3 ");
        if(keyWords.length() >0 ){
            countSb.append("and a.relation like '%"+keyWords+"%' ");
        }

        int totalCount = jdbcOperations.queryForObject(countSb.toString(),paramMap,Integer.class);
        obj.TotalCount = totalCount;
        if(totalCount<=0){
            return obj;
        }

        StringBuilder listSb = new StringBuilder();
        listSb.append("SELECT a.id,a.relation, a.type,a.image,a.writing,a.status ");
        listSb.append("FROM pygx_content a ");
        listSb.append("WHERE a.flag < 3 ");
        if(keyWords.length() >0 ){
            listSb.append("and a.relation like '%"+keyWords+"%' ");
        }

        listSb.append("order by a.createTime desc ");
        listSb.append("limit :pageIndex,:pageSize ");
        obj.Result = jdbcOperations.queryForList(listSb.toString(),paramMap);
        return obj;
    }
    //endregion

    //region addContent

    public BasicResultObject addContent(String relation,int type,String image,String writing){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("relation",relation);
        paramMap.put("type",type);
        paramMap.put("image",image);
        paramMap.put("writing",writing);
        BasicResultObject result = new BasicResultObject();
        int readCount = jdbcOperations.queryForObject(" SELECT COUNT(0) FROM `pygx_content` a where a.relation = :relation AND a.flag < 3 ", paramMap, Integer.class);
        if(readCount>0){
            result.Tag = BasicTagState.TagFailure;
            result.Message = "该种关系已经存在，请重新填写！";
            return result;
        }
        jdbcOperations.update("INSERT INTO `pygx_content` (`relation`,`type`,`image`,`writing`,`modifyTime`,`createTime`,`flag`,`status`) VALUES(:relation,:type,:image,:writing,NOW(),NOW(),1,0) ",paramMap);

        return result;
    }
    //endregion

    //region updateContent
    public BasicResultObject updateContent(int id,String relation,int type,String image,String writing){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("relation",relation);
        paramMap.put("type",type);
        paramMap.put("image",image);
        paramMap.put("writing",writing);
        BasicResultObject result = new BasicResultObject();
        int readCount = jdbcOperations.queryForObject("SELECT COUNT(0) FROM `pygx_content` a where a.relation = :relation AND a.id != :id AND a.flag < 3 ", paramMap, Integer.class);
        if(readCount>0){
            result.Tag = BasicTagState.TagFailure;
            result.Message = "该种关系已经存在，请重新填写！";
            return result;
        }
        jdbcOperations.update("UPDATE `pygx_content` SET `relation` = :relation,`type` = :type,`image` = :image,`writing` = :writing,`modifyTime` = NOW(),`flag` = 2 WHERE `id` = :id ;",paramMap);
        return result;
    }
    //endregion

    //region deleteContent
    public BasicResultObject deleteContent(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        BasicResultObject result = new BasicResultObject();
        int readCount = jdbcOperations.queryForObject("SELECT COUNT(0) FROM `pygx_content` a where  a.id = :id AND a.status = 1 and a.flag < 3 ", paramMap, Integer.class);
        if(readCount>0){
            result.Tag = BasicTagState.TagFailure;
            result.Message = "已发布的不能删除！";
            return result;
        }
        jdbcOperations.update("update pygx_content set flag =3 ,modifyTime = now() where id = :id",paramMap);
        return result;
    }
    //endregion

    //region updateContentStatus
    public BasicResultObject updateContentStatus(String ids,int status){
        for(String id:ids.split(",")){
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("id",id);
            paramMap.put("status",status);

            jdbcOperations.update("update pygx_content set flag = 2 ,modifyTime = now() , status = :status where id = :id",paramMap);
        }
        return new BasicResultObject();
    }
    //endregion

    //region getContentById
    public BasicResultObject getContentById(int id){
        BasicResultObject obj =new BasicResultObject();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        obj.Result = jdbcOperations.queryForMap("SELECT `id`,`relation`,CONVERT(`type`,CHAR(10)) as type ,`image`,`writing`,`status`,`modifyTime`,`createTime`,`flag` FROM `pygx_content` WHERE id = :id",paramMap);
        return obj;
    }
    //endregion
}
