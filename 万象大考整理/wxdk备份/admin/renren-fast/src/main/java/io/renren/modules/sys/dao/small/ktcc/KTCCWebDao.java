package io.renren.modules.sys.dao.small.ktcc;

import io.renren.modules.sys.domain.BasicResultObject;
import io.renren.modules.sys.domain.BasicTagState;
import io.renren.modules.sys.domain.small.ktcc.KTCCOption;
import io.renren.modules.sys.domain.small.ktcc.KTCCSubject;
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
public class KTCCWebDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;


    //region getSubjectList
    public BasicResultObject getSubjectList(int pageIndex, int pageSize,String keyWords,int type ,int status, int modeType){
        BasicResultObject obj = new BasicResultObject();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("pageIndex",(pageIndex-1) * pageSize);
        paramMap.put("pageSize",pageSize);
        paramMap.put("keyWords",keyWords);
        paramMap.put("type",type);
        paramMap.put("status",status);
        paramMap.put("modeType",modeType);

        StringBuilder countSb =new StringBuilder();
        countSb.append("select count(1) from ktcc_subject a ");
        countSb.append("WHERE flag < 3 ");
        if(modeType>0){
            countSb.append("and a.modeType =:modeType ");
        }
        if(keyWords.length() >0 ){
            countSb.append("and (a.answer like '%"+keyWords+"%' or a.promptWord like '%"+keyWords+"%') ");
        }
        if(type>=0){
            countSb.append("and a.type =:type ");
        }
        if(status>=0){
            countSb.append("and a.status = :status ");
        }

        int totalCount = jdbcOperations.queryForObject(countSb.toString(),paramMap,Integer.class);
        obj.TotalCount = totalCount;
        if(totalCount<=0){
            return obj;
        }

        StringBuilder listSb = new StringBuilder();
        listSb.append("SELECT a.id,a.image,a.audio, a.answer,a.promptWord,a.type,a.sortId,a.status,a.modeType,a.description ");
        listSb.append("FROM ktcc_subject a ");
        listSb.append("WHERE a.flag < 3 ");
        if(modeType>0){
            listSb.append("and a.modeType =:modeType ");
        }
        if(keyWords.length() >0 ){
            listSb.append("and (a.answer like '%"+keyWords+"%' or a.promptWord like '%"+keyWords+"%') ");
        }
        if(type>=0){
            listSb.append("and a.type =:type ");
        }
        if(status>=0){
            listSb.append("and a.status = :status ");
        }
        listSb.append("order by a.sortId ");
        listSb.append("limit :pageIndex,:pageSize ");
        obj.Result = jdbcOperations.queryForList(listSb.toString(),paramMap);
        return obj;
    }
    //endregion

    //region addSubject
    public BasicResultObject addSubject(String answer,String image,String audioName,String promptWord,String optionStr,String type,String status, int modeType , int sortId,String description){

        BasicResultObject obj = new BasicResultObject();
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("answer", answer);
            paramMap.put("image", image);
            paramMap.put("audioName", audioName);
            paramMap.put("promptWord", promptWord);
            paramMap.put("optionStr", optionStr);
            paramMap.put("type", type);
            paramMap.put("status", status);
            paramMap.put("modeType", modeType);
            paramMap.put("description", description);
            String tempAnswerStr = optionStr.split("%#@")[Integer.parseInt(answer)-1].split("@#")[1];
            int readCount = jdbcOperations.queryForObject(String.format(" SELECT COUNT(0) FROM `ktcc_subject` a where a.promptWord ='%s'  AND a.flag < 3 ",tempAnswerStr), paramMap, Integer.class);
            if(readCount>0){
                obj.Tag = -1;
                obj.Message = "该题目已存在，请重新填写！";
                return obj;
            }
            if(sortId > 0){ /*判断是否插入下方*/
                // 往后修改排序
                sortId = sortId + 1;
                jdbcOperations.update(String.format("update ktcc_subject set sortId = sortId+1 where flag < 3 and  sortId>='%s' ",sortId),paramMap);
            }else{
                sortId = jdbcOperations.queryForObject("select ifnull(max(sortId),0)+1 from ktcc_subject where flag<3", paramMap, Integer.class);
            }

            paramMap.put("sortId", sortId);
            KeyHolder titleKeyHolder = new GeneratedKeyHolder();
            jdbcOperations.update(String.format("INSERT INTO ktcc_subject(`image`,`audio`,`optionSortId`,`promptWord`,`answer`,`TYPE`,`sortId`,`STATUS`,`modeType`,`description`,`createTime`,`modiftTime`,`flag`) " +
                                                "VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',now(),now(),1);",image,audioName,answer,promptWord,tempAnswerStr,type,sortId,status,modeType,description), new BeanPropertySqlParameterSource(new Object()), titleKeyHolder);
            int subjectId = Integer.parseInt(titleKeyHolder.getKey().toString());


            //region insertSubjectOption
            String[] optionArr = optionStr.split("%#@");
            int index = 1;
            for (int i = 0; i < optionArr.length; i++) {
                String[] optionContentArr = optionArr[i].split("@#");
                String optionId = optionContentArr[0];
                String optionName = optionContentArr[1];
                jdbcOperations.update(String.format("INSERT INTO ktcc_subjectOption(`subjectId`,`OPTION`,`sortId`,`createTime`,`modifyTime`,`flag`)" +
                                                    "VALUES('%s','%s','%s',now(),now(),1);", subjectId, optionName, index), paramMap);

                index++;
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        //endregion
        return obj;
    }
    //endregion

    //region updateSubjectById
    public BasicResultObject updateSubjectById(int id,String answer,String image,String audioName,String promptWord,String optionStr,String type,String status,String sortId , int modeType,String description){
        BasicResultObject obj = new BasicResultObject();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("answer", answer);
        paramMap.put("image", image);
        paramMap.put("audio", audioName);
        paramMap.put("promptWord", promptWord);
        paramMap.put("optionStr", optionStr);
        paramMap.put("type", type);
        paramMap.put("status", status);
        paramMap.put("sortId", sortId);
        paramMap.put("modeType", modeType);
        paramMap.put("description", description);
        String tempAnswerStr = optionStr.split("%#@")[Integer.parseInt(answer)-1].split("@#")[1];
        paramMap.put("answerStr",tempAnswerStr);
        int readCount = jdbcOperations.queryForObject(String.format(" SELECT count(0) FROM `ktcc_subject` WHERE promptWord='%s' and id!='%s' AND flag < 3 ",tempAnswerStr,id), paramMap, Integer.class);
        if(readCount>0){
            obj.Tag = -1;
            obj.Message = "该题目已存在，请重新填写！";
            return obj;
        }
        jdbcOperations.update("UPDATE `ktcc_subject` " +
                                "SET " +
                                "`image` = :image, " +
                                "`modeType` = :modeType, "+
                                "`audio` = :audio, " +
                                "`optionSortId` = :answer, " +
                                "`answer` = :answerStr, " +
                                "`description` = :description, " +
                                "`promptWord` = :promptWord, " +
                                "`type` = :type, " +
                                "`sortId` = :sortId, " +
                                "`modiftTime` = now(), " +
                                "`status` = case when status = 2 then 3 else status end , " + // 已驳回显示已更改
                                "`flag` = 2 " +
                                "WHERE `id` = :id ;",paramMap);

        String[] optionArr = optionStr.split("%#@");
        int index = 1;
        for (int i = 0; i < optionArr.length; i++) {
            String[] optionContentArr = optionArr[i].split("@#");
            String optionId = optionContentArr[0];
            String optionName = optionContentArr[1];
            int readId = 0;
            try{
                readId = jdbcOperations.queryForObject(String.format(" SELECT ifnull(id,0) FROM `ktcc_subjectoption` WHERE subjectId ='%s' AND sortid ='%s' ",id,index), paramMap, Integer.class);
            }
            catch (Exception ex){
                readId = 0;
            }

            if(readId>0){
                jdbcOperations.update(String.format("UPDATE `ktcc_subjectoption` " +
                                                    "SET " +
                                                    "`option` = '%s', " +
                                                    "`modifyTime` = now(), " +
                                                    "`flag` = 2 " +
                                                    "WHERE `id` = '%s' ;",optionName,readId),paramMap);
            }else{
                jdbcOperations.update(String.format("INSERT INTO ktcc_subjectOption(`subjectId`,`OPTION`,`sortId`,`createTime`,`modifyTime`,`flag`)" +
                        "VALUES('%s','%s','%s',now(),now(),1);", id, optionName, index), paramMap);
            }
            index ++;
        }
        //删除选项
        jdbcOperations.update(String.format("delete from ktcc_subjectOption where subjectId='%s' and sortId > '%s'",id,(index-1)),paramMap);

        return obj;
    }
    //endregion

    //region delSubejctById
    public BasicResultObject delSubejctById(String ids){
        String[] idArr = ids.split(",");
        for(int i =0 ;i<idArr.length;i++){
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("id",idArr[i]);
            int sortId = jdbcOperations.queryForObject("select sortId from ktcc_subject where id = :id", paramMap, Integer.class);
            jdbcOperations.update("UPDATE `ktcc_subject` SET modiftTime= NOW(), flag =3 WHERE id = :id ",paramMap);
            jdbcOperations.update(String.format("update ktcc_subject set sortId = sortId-1 where flag < 3 and sortId>'%s' ",sortId),paramMap);
        }
        return new BasicResultObject();
    }
    //endregion

    //region updateSubjectStatus
    public BasicResultObject updateSubjectStatus(String ids,int status){
        String[] idArr = ids.split(",");
        for(int i =0 ;i<idArr.length;i++){
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("id",idArr[i]);
            paramMap.put("status",status);
            jdbcOperations.update("UPDATE `ktcc_subject` SET `status` = :status, modiftTime= NOW(), flag =2 WHERE id = :id ",paramMap);
        }
        return new BasicResultObject();
    }
    //endregion

    //region getSubjectById
    public BasicResultObject getSubjectById(int id){
        BasicResultObject obj =new BasicResultObject();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        obj.Result = jdbcOperations.queryForMap(String.format("SELECT a.`id`,a.`image`,a.`audio`,a.`answer`,CONVERT(a.`optionSortId`,CHAR(10)) as optionSortId,a.`promptWord`,a.`type`,a.`status`,a.`sortId`,a.`modeType`,a.`description` FROM ktcc_subject a WHERE id = %s",id),paramMap);
        obj.Result2 = jdbcOperations.queryForList(String.format("SELECT a.`id`,a.`subjectId`,a.`option`,a.`sortId` FROM ktcc_subjectOption a WHERE a.subjectId = %s",id),paramMap);
        return obj;
    }
    //endregion

    //region saveSort
    public BasicResultObject saveSort(String ids , int pageIndex,int pageSize){
        int len = ids.split(";").length;
        Map<String, Object> paramMap = new HashMap<>();
        int startSortId = (pageIndex-1) * pageSize;
        for(int i = 0 ;i< len; i++){
            int id = Integer.parseInt(ids.split(";")[i].toString());
            jdbcOperations.update(String.format("update ktcc_subject set sortId='%s',modiftTime=now() where id = '%s' ",startSortId+i+1,id),paramMap);
        }
        return new BasicResultObject();
    }
    //endregion

    //region getSubjectRatio
    public BasicResultObject getSubjectRatio(int pageIndex, int pageSize,int orderType , String keyWords){
        BasicResultObject obj = new BasicResultObject();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("pageIndex",(pageIndex-1) * pageSize);
        paramMap.put("pageSize",pageSize);
        paramMap.put("keyWords",keyWords);

        StringBuilder countSb =new StringBuilder();
        countSb.append("select count(1) from `ktcc_subject` a ");
        countSb.append("LEFT JOIN `ktcc_subjectratio` b ON b.`subjectId` = a.`id` ");
        countSb.append("WHERE a.`flag`<3 AND a.`status` = 1 ");
        if(keyWords.length() >0 ){
            countSb.append("and (a.answer like '%"+keyWords+"%' or a.promptWord like '%"+keyWords+"%') ");
        }

        int totalCount = jdbcOperations.queryForObject(countSb.toString(),paramMap,Integer.class);
        obj.TotalCount = totalCount;
        if(totalCount<=0){
            return obj;
        }

        StringBuilder listSb = new StringBuilder();
        listSb.append("SELECT * FROM ( ");
        listSb.append("SELECT a.`Id` AS subjectId,a.`sortId`,a.`promptWord`,a.`image`,a.`audio`,a.`answer`,a.`modeType`,");
        listSb.append("IFNULL(b.`replyBefNumber`,0) AS replyBefNumber,IFNULL(b.`replyAftNumber`,0) AS replyAftNumber,IFNULL(b.`unlikeNumber`,0) AS unlikeNumber, ");
        listSb.append("IFNULL(b.`rightNumber`,0) AS rightNumber,IFNULL(b.`likeNumber`,0) AS likeNumber,IFNULL(b.`exitNumber`,0) AS exitNumber,");
        listSb.append("IFNULL(CONVERT( b.`exitNumber`/b.`replyBefNumber`,DECIMAL(10,2)),0) AS exitRatio ,");
        listSb.append("IFNULL(CONVERT( (b.`rightNumber`)/b.`replyAftNumber`,DECIMAL(10,2)),0) AS rightRatio ,");
        listSb.append("IFNULL(CONVERT( (b.`replyAftNumber`- b.`rightNumber`)/b.`replyAftNumber`,DECIMAL(10,2)),0) AS errorRatio ," );
        listSb.append("IFNULL(CONVERT( b.`likeNumber`/b.`replyAftNumber`,DECIMAL(10,2)),0) AS likeRatio, ");
        listSb.append("IFNULL(CONVERT( b.`unlikeNumber`/b.`replyAftNumber`,DECIMAL(10,2)),0) AS unlikeRatio ");
        listSb.append("FROM `ktcc_subject` a ");
        listSb.append("LEFT JOIN `ktcc_subjectratio` b ON b.`subjectId` = a.`id` ");
        listSb.append("WHERE a.`flag`<3 AND a.`status` = 1 ");
        if(keyWords.length() >0 ){
            listSb.append("and (a.answer like '%"+keyWords+"%' or a.promptWord like '%"+keyWords+"%') ");
        }
        listSb.append(" ) a ");
        if(orderType == 1){ //正确率降序
            listSb.append("ORDER BY a.rightRatio DESC ");
        }else if(orderType == 2){ //正确率升序
            listSb.append("ORDER BY a.rightRatio ASC ");
        }else if(orderType == 3){ //退出率降序
            listSb.append("ORDER BY a.exitRatio DESC ");
        }else if(orderType == 4){ //退出率升序
            listSb.append("ORDER BY a.exitRatio ASC ");
        }else{
            listSb.append("ORDER BY a.`sortId` ASC ");
        }
        listSb.append("limit :pageIndex,:pageSize ");
        obj.Result = jdbcOperations.queryForList(listSb.toString(),paramMap);
        return obj;
    }
    //endregion

    //region uploadExcel
    public BasicResultObject uploadExcel(List<KTCCSubject> subjectList){
        BasicResultObject obj = new BasicResultObject();
        if(subjectList.size()%6 != 0 ){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "题目数不是6的倍数，请调整！";
        }else{
            String existsSubjectName = "";
            for(int i =0 ;i<subjectList.size();i++){

                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("promptWord",subjectList.get(i).getName());
                int readCount = jdbcOperations.queryForObject(" SELECT COUNT(0) FROM `ktcc_subject` a where a.promptWord = :promptWord  AND a.flag < 3 ", paramMap, Integer.class);
                if(readCount>0){
                    existsSubjectName += subjectList.get(i).getName()+",";
                }
            }
            if(existsSubjectName.length() > 0 ){
                obj.Tag = -1;
                obj.Message = "题目【"+existsSubjectName+"】已存在，请重新填写！";
                return obj;
            }
            for(int i =0 ;i<subjectList.size();i++){

                    Map<String, Object> paramMap = new HashMap<>();
                    int optionSortId = subjectList.get(i).getOptionSortId();
                    String promptWord = subjectList.get(i).getName();
                    String answer = subjectList.get(i).getAnswer();
                    int type = 1;
                    int STATUS = 0;
                    int modeType = 3;
                    String description = subjectList.get(i).getDescription();


                    int sortId = jdbcOperations.queryForObject("select ifnull(max(sortId),0)+1 from ktcc_subject where flag<3", paramMap, Integer.class);
                    paramMap.put("sortId",sortId);
                    KeyHolder titleKeyHolder = new GeneratedKeyHolder();

                    jdbcOperations.update(String.format("INSERT INTO ktcc_subject(`optionSortId`,`promptWord`,`answer`,`type`,`sortId`,`STATUS`,`modeType`,`description`,`createTime`,`modiftTime`,`flag`) " +
                                            "VALUES('%s','%s','%s','%s','%s','%s','%s','%s',now(),now(),1);",optionSortId,promptWord,answer,type,sortId,STATUS,modeType,description),new BeanPropertySqlParameterSource(new Object()), titleKeyHolder);

                    int subjectId = Integer.parseInt(titleKeyHolder.getKey().toString());
                    List<KTCCOption> optionList = subjectList.get(i).getOptionList();
                    for(int j =0 ;j<optionList.size();j++){
                        String optionName = optionList.get(j).getName();
                        int optionSortId2 = optionList.get(j).getSortId();
                        jdbcOperations.update(String.format("INSERT INTO ktcc_subjectOption(`subjectId`,`OPTION`,`sortId`,`createTime`,`modifyTime`,`flag`)" +
                                "VALUES('%s','%s','%s',now(),now(),1);", subjectId, optionName, optionSortId2), paramMap);
                    }
                if((i+1)%6==0){ /*额外插入4个*/
                    for(int j =0 ;j<4;j++){
                        int sortId2 = jdbcOperations.queryForObject("select ifnull(max(sortId),0)+1 from ktcc_subject where flag<3", paramMap, Integer.class);

                        KeyHolder titleKeyHolder2 = new GeneratedKeyHolder();
                        jdbcOperations.update(String.format("INSERT INTO ktcc_subject(`optionSortId`,`type`,`sortId`,`STATUS`,`modeType`,`createTime`,`modiftTime`,`flag`) " +
                                                 "VALUES('1','1','%s','0','3',now(),now(),1);",sortId2),new BeanPropertySqlParameterSource(new Object()), titleKeyHolder2);
                        int subjectId2 = Integer.parseInt(titleKeyHolder2.getKey().toString());
                        for(int d =0 ;d<4;d++){
                            jdbcOperations.update(String.format("INSERT INTO ktcc_subjectOption(`subjectId`,`sortId`,`createTime`,`modifyTime`,`flag`)" +
                                    "VALUES('%s','%s',now(),now(),1);", subjectId2, (d+1)), paramMap);
                        }
                    }
                }

            }

        }
        return obj;
    }

    //endregion
}
