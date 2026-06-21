package io.renren.modules.sys.dao.small;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.domain.BasicResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SQCSWebDao {

    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    //region titleList
    public BasicResultObject titleList(String keyWords, int pageIndex, int pageSize ){
        BasicResultObject obj = new BasicResultObject();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("keyWords",keyWords);
        paramMap.put("pageIndex",(pageIndex-1) * pageSize);
        paramMap.put("pageSize",pageSize);

        StringBuilder countSb =new StringBuilder();
        countSb.append("select count(1) from test_title a ");
        countSb.append("inner join test_titletype b on a.id = b.titleId ");
        countSb.append("where a.flag<3 ");
        if(keyWords.length() >0 ){
            countSb.append("and a.name like '%"+keyWords+"%' ");
        }

        int totalCount = jdbcOperations.queryForObject(countSb.toString(),paramMap,Integer.class);
        obj.TotalCount = totalCount;
        if(totalCount<=0){
            return obj;
        }

        StringBuilder listSb = new StringBuilder();
        listSb.append("select a.id ,a.name ,b.type , a.description,ifnull(a.image,\"\") as image,a.sortId, ifnull(a.initRead,0) as initRead  ");
        listSb.append("from test_title a ");
        listSb.append("inner join test_titletype b on a.id = b.titleId ");
        listSb.append("where a.flag<3  ");
        if(keyWords.length() >0 ){
            listSb.append("and a.name like '%"+keyWords+"%' ");
        }
        listSb.append("order by a.sortId ");
        listSb.append("limit :pageIndex,:pageSize ");
        obj.Result = jdbcOperations.queryForList(listSb.toString(),paramMap);
        return obj;
    }
    //endregion

    //region createTitle
    public BasicResultObject createTitle(String type,
                                         String titleName,
                                         String titleDescription,
                                         String titleImageUrl,
                                         String subjectStr,
                                         String answerStr,
                                         String optionStr,
                                         String titleInitRead){
        BasicResultObject obj = new BasicResultObject();

        try{
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("titleName",titleName);
            paramMap.put("titleDescription",titleDescription);
            paramMap.put("titleImageUrl",titleImageUrl);
            int titleExists = jdbcOperations.queryForObject("select count(1) from test_title where name=:titleName and flag<3",paramMap,Integer.class);
            if(titleExists>0){
                obj.Tag = -1;
                obj.Message= "该标题名称已经存在，请重新填写！";
                return obj;
            }
            //region insertTitle

            int titleMaxSortId = jdbcOperations.queryForObject("select ifnull(max(sortId),0)+1 from test_title where flag<3",paramMap,Integer.class);
            paramMap.put("titleMaxSortId",titleMaxSortId);

            Object titleInsertObj = new Object();
            KeyHolder titleKeyHolder = new GeneratedKeyHolder();
            jdbcOperations.update(String.format("INSERT INTO test_title(name,image,description,initRead,sortId,flag,modifyTime,createTime) VALUES('%s','%s','%s','%s','%s',1,now(),now());",
                    titleName,titleImageUrl,titleDescription,titleInitRead,titleMaxSortId), new BeanPropertySqlParameterSource(titleInsertObj), titleKeyHolder);
            int titleMaxId =Integer.parseInt(titleKeyHolder.getKey().toString());
            // jdbcOperations.update("INSERT INTO test_title(name,image,description,sortId,flag,modifyTime,createTime) VALUES(:titleName,:titleImageUrl,:titleDescription,:titleMaxSortId,1,now(),now());",paramMap);
            // int titleMaxId = jdbcOperations.queryForObject("select max(id) from test_title where flag<3",paramMap,Integer.class);

            jdbcOperations.update(String.format("INSERT INTO test_titletype(titleId,type,modifyTime,createTime) " +
                    "VALUES('%s','%s',now(),now());",titleMaxId,type),paramMap);
            //endregion

            //region insertAnser
            String [] answerArr = answerStr.split("%#@");
            for(int i=0;i<answerArr.length;i++){
                String[] answerContentArr = answerArr[i].split("@#");
                String answerId = answerContentArr[0];
                String answerSmallTitle = answerContentArr[1];
                String answerName = answerContentArr[2];
                String answerSortId = answerContentArr[3];
                /*Map<String,Object> answerMap = new HashMap<>();
                answerMap.put("titleMaxId",titleMaxId);
                answerMap.put("answerSmallTitle",answerSmallTitle);
                answerMap.put("answerName",answerName);
                answerMap.put("answerSortId",answerSortId);*/
                jdbcOperations.update(String.format("INSERT INTO test_titleanswer(titleId,smallTitle,answer,sortId,modifyTime,createTime) " +
                                "VALUES('%s','%s','%s','%s',now(),now());",
                        titleMaxId,answerSmallTitle,answerName,answerSortId),paramMap);
            }
            //endregion

            // region insertSubject
            int optionSortId = 1;
            String[] subjectArr = subjectStr.split("%#@");
            for(int i =0 ;i<subjectArr.length;i++){
                String[] subjectContentArr = subjectArr[i].split("@#");
                String id = subjectContentArr[0];
                String subjectName = subjectContentArr[1];
                String subjectSortId = subjectContentArr[2];
                /*Map<String,Object> subMap = new HashMap<>();
                subMap.put("titleMaxId",titleMaxId);
                subMap.put("subjectName",subjectName);
                subMap.put("subjectSortId",subjectSortId);*/
                jdbcOperations.update(String.format("INSERT INTO test_subject(titleId,name,sortId,modifyTime,createTime) " +
                                "VALUES('%s','%s','%s',now(),now())",
                        titleMaxId,subjectName,subjectSortId),paramMap);

                String[] optionArr = optionStr.split("%#@");

                for(int j=0;j<optionArr.length;j++){
                    String[] optionContentArr = optionArr[j].split("@#");
                    String optionId = optionContentArr[0];
                    String optionName = optionContentArr[1];
                    String subjectSortId2 = optionContentArr[2];
                    String nextSubjectSortId = optionContentArr[3];
                    String answerSortId = optionContentArr[4];
                    if(subjectSortId2.equals(subjectSortId)){
                        /*Map<String,Object> subOptionMap = new HashMap<>();
                        subOptionMap.put("titleMaxId",titleMaxId);
                        subOptionMap.put("optionName",optionName);
                        subOptionMap.put("subjectSortId2",subjectSortId2);
                        subOptionMap.put("nextSubjectSortId",nextSubjectSortId);
                        subOptionMap.put("answerSortId",answerSortId);
                        subOptionMap.put("optionSortId",optionSortId);*/
                        jdbcOperations.update(String.format("INSERT INTO test_subjectoption (titleId,subjectSortId,nextSubjectSortId,answerSortId,name,sortId,modifyTime,createTime) " +
                                        "VALUES('%s','%s','%s','%s','%s','%s',now(),now())",
                                titleMaxId,subjectSortId2,nextSubjectSortId,answerSortId,optionName,optionSortId),paramMap);
                        optionSortId ++;
                    }
                }
            }
            //endregion

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return obj;
    }
    //endregion

    //region updateTitleById
    public BasicResultObject updateTitleById(
                                        int id,
                                        String type,
                                        String titleName,
                                        String titleDescription,
                                        String titleImageUrl,
                                        String subjectStr,
                                        String answerStr,
                                        String optionStr,
                                        String sortId,
                                        String titleInitRead){
        BasicResultObject obj = new BasicResultObject();

        try{
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("id",id);
            paramMap.put("type",type);
            paramMap.put("titleName",titleName);
            paramMap.put("titleDescription",titleDescription);
            paramMap.put("titleImageUrl",titleImageUrl);
            paramMap.put("sortId",sortId);
            paramMap.put("titleInitRead",titleInitRead);
            int titleExists = jdbcOperations.queryForObject("select count(1) from test_title where name=:titleName and id != :id and flag<3",paramMap,Integer.class);
            if(titleExists>0){
                obj.Tag = -1;
                obj.Message= "该标题名称已经存在，请重新填写！";
                return obj;
            }
            //region updateTitle
            jdbcOperations.update("UPDATE " +
                                    "`test_title` " +
                                    "SET " +
                                    "`name` = :titleName, " +
                                    "`image` = :titleImageUrl, " +
                                    "`description` = :titleDescription, " +
                                    "`sortId` = :sortId, " +
                                    "`initRead` = :titleInitRead, " +
                                    "`flag` = 2, " +
                                    "`modifyTime` = now() " +
                                    "WHERE `id` = :id ;",paramMap);

             jdbcOperations.update("UPDATE " +
                                    "`test_titletype` " +
                                    "SET " +
                                    "`type` = :type, " +
                                    "`modifyTime` = now() " +
                                    "WHERE `id` = :id ; ",paramMap );
            //endregion

            //region updateAnser

            String [] answerArr = answerStr.split("%#@");
            for(int i=0;i<answerArr.length;i++){
                String[] answerContentArr = answerArr[i].split("@#");
                String answerId = answerContentArr[0];
                String answerSmallTitle = answerContentArr[1];
                String answerName = answerContentArr[2];
                String answerSortId = answerContentArr[3];
                int readAnswerId = 0;
                try{
                    readAnswerId = jdbcOperations.queryForObject(String.format(" SELECT ifnull(id,0) FROM `test_titleanswer` WHERE titleId ='%s' AND sortid ='%s' ",id,answerSortId), paramMap, Integer.class);
                }
                catch (Exception ex){
                    readAnswerId = 0;
                }

                if(readAnswerId>0){
                    jdbcOperations.update(String.format("UPDATE " +
                                                        "`test_titleanswer` " +
                                                        "SET " +
                                                        "`smallTitle` = '%s', " +
                                                        "`answer` = '%s', " +
                                                        "`modifyTime` = now() " +
                                                        "WHERE `id` = '%s' ; ",answerSmallTitle,answerName,readAnswerId ),paramMap);
                }else {
                    jdbcOperations.update(String.format("INSERT INTO test_titleanswer(titleId,smallTitle,answer,sortId,modifyTime,createTime) " +
                            "VALUES('%s','%s','%s','%s',now(),now());", id, answerSmallTitle, answerName, answerSortId), paramMap);
                }
            }
            jdbcOperations.update(String.format("delete from test_titleanswer where titleId ='%s' and sortId > '%s'",id,answerArr.length),paramMap);
            //endregion

            // region udpateSubject
            int optionSortId = 1;
            String[] subjectArr = subjectStr.split("%#@");
            for(int i =0 ;i<subjectArr.length;i++){
                String[] subjectContentArr = subjectArr[i].split("@#");
                String subjectId = subjectContentArr[0];
                String subjectName = subjectContentArr[1];
                String subjectSortId = subjectContentArr[2];
                int readSubjectId = 0;
                try{
                    readSubjectId = jdbcOperations.queryForObject(String.format(" SELECT ifnull(id,0) FROM `test_subject` WHERE titleId ='%s' AND sortid ='%s' ",id,subjectSortId), paramMap, Integer.class);
                }
                catch (Exception ex){
                    readSubjectId = 0;
                }

                if(readSubjectId>0){
                    jdbcOperations.update(String.format("UPDATE " +
                                                        "`test_subject` " +
                                                        "SET " +
                                                        "`name` = '%s', " +
                                                        "`modifyTime` = now() " +
                                                        "WHERE `id` = '%s' ;",subjectName,readSubjectId),paramMap);
                }else{
                    jdbcOperations.update(String.format("INSERT INTO test_subject(titleId,name,sortId,modifyTime,createTime) " +
                                                        "VALUES('%s','%s','%s',now(),now())",id,subjectName,subjectSortId),paramMap);
                }

                String[] optionArr = optionStr.split("%#@");

                for(int j=0;j<optionArr.length;j++){
                    String[] optionContentArr = optionArr[j].split("@#");
                    String optionId = optionContentArr[0];
                    String optionName = optionContentArr[1];
                    String subjectSortId2 = optionContentArr[2];
                    String nextSubjectSortId = optionContentArr[3];
                    String answerSortId = optionContentArr[4];
                    if(subjectSortId2.equals(subjectSortId)){
                        int readOptionId = 0;
                        try{
                            readOptionId = jdbcOperations.queryForObject(String.format(" SELECT ifnull(id,0) FROM `test_subjectoption` WHERE titleId ='%s' AND sortid ='%s' ",id,optionSortId), paramMap, Integer.class);
                        }
                        catch (Exception ex){
                            readOptionId = 0;
                        }

                        if(readOptionId>0){
                            jdbcOperations.update(String.format("UPDATE " +
                                    "`test_subjectoption` " +
                                    "SET " +
                                    "`nextSubjectSortId` = '%s'," +
                                    "`answerSortId` = '%s'," +
                                    "`name` = '%s'," +
                                    "`modifyTime` = now() " +
                                    "WHERE `id` = '%s' ; ",nextSubjectSortId,answerSortId,optionName,readOptionId),paramMap);
                        }else {
                            jdbcOperations.update(String.format("INSERT INTO test_subjectoption (titleId,subjectSortId,nextSubjectSortId,answerSortId,name,sortId,modifyTime,createTime) " +
                                                                "VALUES('%s','%s','%s','%s','%s','%s',now(),now())", id, subjectSortId2, nextSubjectSortId, answerSortId, optionName, optionSortId), paramMap);
                        }
                        optionSortId ++;
                    }
                }

            }

            jdbcOperations.update(String.format("delete from test_subject where titleId ='%s' and sortId > '%s'",id,subjectArr.length),paramMap);
            jdbcOperations.update(String.format("delete from test_subjectoption where titleId ='%s' and sortId > '%s'",id,(optionSortId-1)),paramMap);
            //endregion

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return obj;
    }
    //endregion

    //region delTitleById
    public BasicResultObject delTitleById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        jdbcOperations.update("update test_title set flag =3 ,modifyTime=now() where id = :id",paramMap);
        return new BasicResultObject();
    }
    //endregion

    //region getTitleById
    public BasicResultObject getTitleById(int id){

        BasicResultObject obj = new BasicResultObject();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("titleId",id);

        List<Map<String,Object>> titleListMap = jdbcOperations.queryForList("select a.id,a.name,a.description,a.image,a.sortId,b.type,a.initRead from test_title a inner join `test_titletype` b on a.id = b.titleId where a.id = :titleId order by a.sortId;",paramMap);
        List<Map<String,Object>> subjectListMap = jdbcOperations.queryForList("select a.id ,a.name ,a.sortId from test_subject a where a.titleId = :titleId order by a.sortId ",paramMap);
        List<Map<String,Object>> subjectoptionListMap = jdbcOperations.queryForList("select a.id ,a.name,a.subjectSortId,a.nextSubjectSortId,a.sortId, a.answerSortId from test_subjectoption a where a.titleId = :titleId order by a.sortId ",paramMap);
        List<Map<String,Object>> answerListMap = jdbcOperations.queryForList("select a.Id ,a.smallTitle,a.answer,a.sortId from test_titleanswer a where a.titleId = :titleId order by a.sortId ",paramMap);
        //region StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append("{\"title\":{");
        for(Map<String,Object> titleMap :titleListMap) {
            sb.append("\"id\":"+titleMap.get("id")+",");
            sb.append("\"name\":\""+titleMap.get("name").toString().replaceAll("\"","“")+"\",");
            sb.append("\"description\":\""+titleMap.get("description").toString().replaceAll("\"","“")+"\",");
            sb.append("\"image\":\""+titleMap.get("image")+"\",");
            sb.append("\"sortId\":\""+titleMap.get("sortId")+"\",");
            sb.append("\"type\":\""+titleMap.get("type")+"\",");
            sb.append("\"initRead\":"+titleMap.get("initRead")+",");
            sb.append("\"answer\":[");
            for(int i =0;i<answerListMap.size();i++) {
                sb.append("{\"id\":"+answerListMap.get(i).get("id")+",");
                sb.append("\"smallTitle\":\""+answerListMap.get(i).get("smallTitle").toString().replaceAll("\"","“")+"\",");
                sb.append("\"name\":\""+answerListMap.get(i).get("answer").toString().replaceAll("\"","“")+"\",");
                if((answerListMap.size()-1) != i)
                    sb.append("\"sortId\":"+answerListMap.get(i).get("sortId")+"},");
                else
                    sb.append("\"sortId\":"+answerListMap.get(i).get("sortId")+"}");

            }
            sb.append("]");
        }
        sb.append("},");
        sb.append("\"subject\":[");
        for(int i =0;i<subjectListMap.size();i++ ) {
            if (i == 0 )
                sb.append("{\"id\":"+subjectListMap.get(i).get("id")+",");
            else
                sb.append(",{\"id\":"+subjectListMap.get(i).get("id")+",");
            sb.append("\"name\":\""+subjectListMap.get(i).get("name").toString().replaceAll("\"","“")+"\",");
            sb.append("\"sortId\":"+subjectListMap.get(i).get("sortId")+",");
            sb.append("\"option\":[");
            String subjectSortId = subjectListMap.get(i).get("sortId").toString();

            for(int j =0;j<subjectoptionListMap.size();j++) {

                String subjectSortId2 = subjectoptionListMap.get(j).get("subjectSortId").toString();
                if(subjectSortId.equals(subjectSortId2)){ /**/
                    sb.append("{\"id\":"+subjectoptionListMap.get(j).get("id")+",");
                    sb.append("\"name\":\""+subjectoptionListMap.get(j).get("name").toString().replaceAll("\"","“")+"\",");
                    sb.append("\"subjectSortId\":\""+subjectoptionListMap.get(j).get("subjectSortId")+"\",");
                    sb.append("\"nextSubjectSortId\":\""+subjectoptionListMap.get(j).get("nextSubjectSortId")+"\",");
                    sb.append("\"answerSortId\":\""+subjectoptionListMap.get(j).get("answerSortId")+"\",");
                    sb.append("\"sortId\":"+subjectoptionListMap.get(j).get("sortId")+"},");
                }

            }
            String str = sb.substring(0,sb.length()-1);
            sb.setLength(0);
            sb.append(str+"]}");

        }
        sb.append("]}");
        //endregion
        obj.Result = JSONObject.parseObject(sb.toString());
        return obj;
    }
    //endregion
}
