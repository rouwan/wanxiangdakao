package io.renren.modules.sys.dao.small.wxdk;

import io.renren.modules.sys.domain.small.wxdk.Subject;
import org.apache.poi.hmef.attribute.MAPIAttribute;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.omg.CORBA.OBJ_ADAPTER;
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
public class SubjectDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int insert(Subject subject){
        String sql = "INSERT INTO `wxdk_subject` (`image`,`audio`,`promptWord`,`answer`,`description`,`optionSortId`,`typeId`,`type`,`sortId`,`status`,`modeType`,`createUserUrl`,`createUserName`) VALUES(:image,:audio,:promptWord,:answer,:description,:optionSortId,:typeId,:type,:sortId,:status,:modeType,:createUserUrl,:createUserName);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(subject),keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void update(Subject subject){
        String sql = "UPDATE `wxdk_subject` SET `image` = :image,`audio` = :audio,`promptWord` = :promptWord,`answer` = :answer,`description` = :description,`optionSortId` = :optionSortId,`typeId` = :typeId,`type` = :type,`modeType` = :modeType,`status` = case when status = 2 then 3 else status end ,`createUserName`= :createUserName, `modiftTime` = NOW(),`flag` = 2 WHERE `id` = :id;";
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(subject));
    }

    public void updateTypeId(int id,int typeId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("typeId",typeId);
        String sql = "UPDATE `wxdk_subject` SET typeId = :typeId,`status` = case when status = 2 then 3 else status end ,`modiftTime` = NOW(),`flag` = 2 WHERE `id` = :id;";
        jdbcOperations.update(sql,paramMap);
    }

    public void updateAfterSortIdById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        int sortId = Integer.parseInt(this.getSubjectById(id).get("sortId").toString());
        paramMap.put("sortId",sortId);
        jdbcOperations.update("UPDATE wxdk_subject SET sortId = sortId - 1 ,modiftTime = NOW() WHERE sortId > :sortId and flag < 3",paramMap);
    }

    public void updateStatus(int id,int status){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("status",status);
        jdbcOperations.update("UPDATE `wxdk_subject` SET `status` = :status, modiftTime= NOW(), flag =2 WHERE id = :id ",paramMap);
    }

    public void batchUpdateStatus(int modeType , int num){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("modeType",modeType);
        paramMap.put("num",num);
        jdbcOperations.update("UPDATE wxdk_subject a SET STATUS=0 WHERE modeType = :modeType AND flag <3 AND NOT EXISTS(SELECT 1 FROM `wxdk_usersubject` b WHERE a.id = b.`subjectId`) ",paramMap);
        jdbcOperations.update("UPDATE `wxdk_subject` a SET `status` = 1, modiftTime= NOW(), flag =2  WHERE modeType = :modeType AND flag < 3 AND NOT EXISTS (SELECT 1 FROM `wxdk_usersubject` b WHERE a.id = b.`subjectId`) ORDER BY RAND() LIMIT :num ",paramMap);
    }

    public void updateSortIdById(int id,int sortId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("sortId",sortId);
        jdbcOperations.update("UPDATE wxdk_subject SET sortId=:sortId,modiftTime=now(),flag=2 WHERE id = :id ",paramMap);
    }

    public void delete(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        jdbcOperations.update("UPDATE wxdk_subject SET flag =3,modiftTime = NOW() WHERE id = :id",paramMap);
    }

    public int getMaxSortId(){
        return jdbcOperations.queryForObject("SELECT IFNULL(MAX(sortId),0) FROM wxdk_subject WHERE flag < 3;",new HashMap<>(),Integer.class);
    }

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM wxdk_subject a "+innerStr+" WHERE "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr ,String innerLimitStr, String innerStr , String whereStr , String orderByStr, int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM `wxdk_subject` a "+innerLimitStr +innerStr;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }

    public Map<String,Object> getSubjectById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.*,IFNULL(b.`parentType`,0) AS parentType,IFNULL(b.name,'') AS typeName, ");
        sql.append("CASE WHEN IFNULL((`rightNum`/totalNum)*100,0)>=0 AND IFNULL((`rightNum`/totalNum)*100,0)<33 THEN 3 ");
        sql.append("WHEN IFNULL((`rightNum`/totalNum)*100,0)>=33 AND IFNULL((`rightNum`/totalNum)*100,0)<=66 THEN 2 ");
        sql.append("WHEN IFNULL((`rightNum`/totalNum)*100,0)>66 AND IFNULL((`rightNum`/totalNum)*100,0)<=100 THEN 1 END AS levelType ");
        sql.append("FROM wxdk_subject a ");
        sql.append("LEFT JOIN `wxdk_subjecttype` b ON a.typeId = b.id ");
        sql.append("LEFT JOIN `wxdk_subjectratio` c ON a.id = c.`subjectId`");
        sql.append("WHERE a.id = :id ");
        return jdbcOperations.queryForMap(sql.toString(),paramMap);
    }

    public Map<String,Object> getSubjectBySortId(int sortId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("sortId",sortId);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.*,IFNULL(b.`parentType`,0) AS parentType,IFNULL(b.name,'') AS typeName, ");
        sql.append("CASE WHEN IFNULL((`rightNum`/totalNum)*100,0)>=0 AND IFNULL((`rightNum`/totalNum)*100,0)<33 THEN 3 ");
        sql.append("WHEN IFNULL((`rightNum`/totalNum)*100,0)>=33 AND IFNULL((`rightNum`/totalNum)*100,0)<=66 THEN 2 ");
        sql.append("WHEN IFNULL((`rightNum`/totalNum)*100,0)>66 AND IFNULL((`rightNum`/totalNum)*100,0)<=100 THEN 1 END AS levelType ");
        sql.append("FROM wxdk_subject a ");
        sql.append("LEFT JOIN `wxdk_subjecttype` b ON a.typeId = b.id ");
        sql.append("LEFT JOIN `wxdk_subjectratio` c ON a.id = c.`subjectId`");
        sql.append("WHERE a.sortId = :sortId and a.flag < 3 limit 1");
        return jdbcOperations.queryForMap(sql.toString(),paramMap);
    }


    //获取每种题型的总数和审核数
    public List<Map<String,Object>> getModeTypeNumList(){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT IFNULL(modeType,3) AS modeType,IFNULL(SUM(1),0)  AS totalNum,IFNULL(SUM(CASE WHEN STATUS = 1 THEN 1 ELSE 0 END),0)  AS approveNum FROM wxdk_subject a WHERE modeType = 3 AND flag < 3 AND NOT EXISTS(SELECT 1 FROM `wxdk_usersubject` b WHERE a.id = b.`subjectId`) ");
        sql.append("UNION ALL ");
        sql.append("SELECT IFNULL(modeType,1) AS modeType,IFNULL(SUM(1),0)  AS totalNum,IFNULL(SUM(CASE WHEN STATUS = 1 THEN 1 ELSE 0 END),0)  AS approveNum FROM wxdk_subject a WHERE modeType = 1 AND flag < 3 AND NOT EXISTS(SELECT 1 FROM `wxdk_usersubject` b WHERE a.id = b.`subjectId`) ");
        sql.append("UNION ALL ");
        sql.append("SELECT IFNULL(modeType,4) AS modeType,IFNULL(SUM(1),0)  AS totalNum,IFNULL(SUM(CASE WHEN STATUS = 1 THEN 1 ELSE 0 END),0)  AS approveNum FROM wxdk_subject a WHERE modeType = 4 AND flag < 3 AND NOT EXISTS(SELECT 1 FROM `wxdk_usersubject` b WHERE a.id = b.`subjectId`)");
        sql.append("UNION ALL ");
        sql.append("SELECT IFNULL(modeType,5) AS modeType,IFNULL(SUM(1),0)  AS totalNum,IFNULL(SUM(CASE WHEN STATUS = 1 THEN 1 ELSE 0 END),0)  AS approveNum FROM wxdk_subject a WHERE modeType = 5 AND flag < 3 AND NOT EXISTS(SELECT 1 FROM `wxdk_usersubject` b WHERE a.id = b.`subjectId`) ");
        sql.append("UNION ALL ");
        sql.append("SELECT IFNULL(modeType,6) AS modeType,IFNULL(SUM(1),0)  AS totalNum,IFNULL(SUM(CASE WHEN STATUS = 1 THEN 1 ELSE 0 END),0)  AS approveNum FROM wxdk_subject a WHERE modeType = 6 AND flag < 3 AND NOT EXISTS(SELECT 1 FROM `wxdk_usersubject` b WHERE a.id = b.`subjectId`)");
        sql.append("UNION ALL ");
        sql.append("SELECT IFNULL(modeType,7) AS modeType,IFNULL(SUM(1),0)  AS totalNum,IFNULL(SUM(CASE WHEN STATUS = 1 THEN 1 ELSE 0 END),0)  AS approveNum FROM wxdk_subject a WHERE modeType = 7 AND flag < 3 AND NOT EXISTS(SELECT 1 FROM `wxdk_usersubject` b WHERE a.id = b.`subjectId`)");
        sql.append("UNION ALL ");
        sql.append("SELECT IFNULL(modeType,8) AS modeType,IFNULL(SUM(1),0)  AS totalNum,IFNULL(SUM(CASE WHEN STATUS = 1 THEN 1 ELSE 0 END),0)  AS approveNum FROM wxdk_subject a WHERE modeType = 8 AND flag < 3 AND NOT EXISTS(SELECT 1 FROM `wxdk_usersubject` b WHERE a.id = b.`subjectId`)");
        sql.append("UNION ALL ");
        sql.append("SELECT IFNULL(modeType,9) AS modeType,IFNULL(SUM(1),0)  AS totalNum,IFNULL(SUM(CASE WHEN STATUS = 1 THEN 1 ELSE 0 END),0)  AS approveNum FROM wxdk_subject a WHERE modeType = 9 AND flag < 3 AND NOT EXISTS(SELECT 1 FROM `wxdk_usersubject` b WHERE a.id = b.`subjectId`)");
        sql.append("UNION ALL ");
        sql.append("SELECT IFNULL(modeType,10) AS modeType,IFNULL(SUM(1),0)  AS totalNum,IFNULL(SUM(CASE WHEN STATUS = 1 THEN 1 ELSE 0 END),0)  AS approveNum FROM wxdk_subject a WHERE modeType = 10 AND flag < 3 AND NOT EXISTS(SELECT 1 FROM `wxdk_usersubject` b WHERE a.id = b.`subjectId`)");
        return jdbcOperations.queryForList(sql.toString(),new HashMap<>());
    }



    public Boolean isExistsByTitle(String promptWord){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("promptWord",promptWord);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_subject WHERE promptWord = :promptWord and flag <3",paramMap,Integer.class);
        if(count >0 ){
            return true;
        }
        return false;
    }

    public Boolean isExistsByTitle(int id,String promptWord){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("promptWord",promptWord);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_subject WHERE id != :id and promptWord = :promptWord and flag <3",paramMap,Integer.class);
        if(count >0 ){
            return true;
        }
        return false;
    }

    public Boolean isExistsByTypeId(int typeId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("typeId",typeId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_subject WHERE typeId = :typeId and flag <3",paramMap,Integer.class);
        if(count >0 ){
            return true;
        }
        return false;
    }

    public Boolean isExistsBySortId(int sortId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("sortId",sortId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_subject WHERE sortId = :sortId and flag <3",paramMap,Integer.class);
        if(count >0 ){
            return true;
        }
        return false;
    }


}
