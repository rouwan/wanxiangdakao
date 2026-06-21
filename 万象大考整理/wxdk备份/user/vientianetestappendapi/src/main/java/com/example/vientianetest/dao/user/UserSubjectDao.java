package com.example.vientianetest.dao.user;

import com.example.vientianetest.config.DataConfiguration;
import com.example.vientianetest.dao.subject.SubjectOptionDao;
import com.example.vientianetest.domain.Param;
import com.example.vientianetest.domain.Subject;
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
public class UserSubjectDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private SubjectOptionDao subjectOptionDao;
    @Autowired
    private DataConfiguration dataConfiguration;

    public int insert(int userId,int subjectId){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_usersubject` (`userId`,`subjectId`,`modifyTime`,`createTime`) VALUES('%s','%s',NOW(),NOW()) ;",userId,subjectId),new BeanPropertySqlParameterSource(new Object()),keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    public int insert(Subject subject){
        String sql = "INSERT INTO `wxdk_subject` (`image`,`audio`,`promptWord`,`answer`,`description`,`optionSortId`,`typeId`,`type`,`sortId`,`status`,`modeType`,`createUserName`,`createUserUrl`) VALUES(:image,:audio,:title,:answer,:description,:optionSortId,:typeId,:type,:sortId,0,:modeType,:createUserName,:createUserUrl);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(subject),keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void update(Subject subject){
        String sql = "UPDATE `wxdk_subject` SET `image` = :image,`audio` = :audio,`promptWord` = :title,`answer` = :answer,`description` = :description,`optionSortId` = :optionSortId,`typeId` = case when :typeId <= 0 then typeId else :typeId end,`type` = :type,`modeType` = :modeType,`modiftTime` = NOW(),`flag` = 2 WHERE `id` = :id;";
        jdbcOperations.update(sql,new BeanPropertySqlParameterSource(subject));
    }

    //更改领取状态
    public void updateStatus(int userId,int subjectId, int status){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("subjectId",subjectId);
        paramMap.put("status",status);
        String sql = "UPDATE wxdk_usersubject SET `status` = :status,`modifyTime` = NOW() WHERE userId = :userId AND subjectId = :subjectId;";
        jdbcOperations.update(sql,paramMap);
    }

    public Map<String,Object> getSubjectById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        Map<String,Object> map = jdbcOperations.queryForMap("SELECT a.id,a.answer,a.description,a.modeType,a.type,a.typeId,a.optionSortId,a.promptWord AS title,IFNULL(b.`parentType`,0) as parentType,a.createUserName ,a.createUserUrl,IFNULL((SELECT FORMAT(rightNum/totalNum*100,2) FROM `wxdk_subjectratio` WHERE subjectId = a.id LIMIT 1),0.00) AS rightRatio,case when a.status = 3 then 2 else a.status end as status,ifnull(c.rejectRemark,'') as rejectRemark FROM `wxdk_subject` a LEFT JOIN `wxdk_subjecttype` b ON a.`typeId` = b.id LEFT JOIN `wxdk_usersubject` c ON a.id = c.subjectId WHERE a.id = :id ", paramMap);
        map.put("optionList",subjectOptionDao.getNormalSubjectOptionBySubjectId(Integer.parseInt(map.get("id").toString())));
        return map;
    }

    public Map<String,Object> getSubjectNumByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("maxSubjectNum",dataConfiguration.getMaxSubjectNum());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT totalNum,passNum,IFNULL(CONVERT(passNum/totalNum,DECIMAL(10,2))*100,0) AS passRatio,effectNum, ");
        sql.append("CASE WHEN passNum>=:maxSubjectNum THEN :maxSubjectNum ELSE (SELECT `value` FROM `wxdk_uplevel` WHERE NAME = 'subject' AND `value` > passNum ORDER BY sortId LIMIT 1) END AS  nextNum, ");//下一盘升级数目
        sql.append("(SELECT `description` FROM `wxdk_uplevel` WHERE `name` = 'subject' AND `value` <= passNum ORDER BY sortId DESC LIMIT 1) AS  `name`, "); //当前职称
        sql.append("(SELECT `level` FROM `wxdk_uplevel` WHERE `name` = 'subject' AND `value` <= passNum ORDER BY sortId DESC LIMIT 1) AS `level` "); //等级
        sql.append("FROM ( ");
        sql.append("SELECT COUNT(1) AS totalNum,IFNULL(SUM(CASE WHEN b.`status` = 1 THEN 1 ELSE 0 END ),0) AS passNum , IFNULL(SUM(c.`realTotalNum`),0) AS effectNum ");
        sql.append("FROM `wxdk_usersubject` a ");
        sql.append("INNER JOIN wxdk_subject b ON a.`subjectId` = b.`id` ");
        sql.append("LEFT  JOIN `wxdk_subjectratio` c ON a.`subjectId` = c.`subjectId` ");
        sql.append("WHERE a.userId = :userId AND b.`flag` < 3 ) a");
        return jdbcOperations.queryForMap(sql.toString(),paramMap);
    }

    public int getPageTotalCount(String innerStr , String whereStr ){
        String sql = "SELECT count(1) FROM wxdk_usersubject "+innerStr+" "+whereStr;
        return jdbcOperations.queryForObject(sql,new BeanPropertySqlParameterSource(new Object()),Integer.class);
    }

    public List<Map<String,Object>> getPageList(String columnsStr , String innerStr , String whereStr , String orderByStr, int pageIndex , int pageSize ){
        String sql = "SELECT "+columnsStr+" FROM `wxdk_usersubject` "+innerStr+" "+whereStr+" "+orderByStr+" LIMIT "+pageIndex+", "+pageSize;
        return jdbcOperations.queryForList(sql,new HashMap<>());
    }

    public int getMaxSortId(){
        return jdbcOperations.queryForObject("SELECT IFNULL(MAX(sortId),0) FROM wxdk_subject WHERE flag < 3;",new HashMap<>(),Integer.class);
    }

    public boolean isExistsByTitle(int subjectId,int userId,String title){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("subjectId",subjectId);
        paramMap.put("userId",userId);
        paramMap.put("title",title);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_usersubject a inner join `wxdk_subject` b on a.subjectId = b.id WHERE a.subjectId <> :subjectId and a.userId = :userId and b.promptWord = :title  and b.flag < 3",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean isExistsByTitle(int userId,String title){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("title",title);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_usersubject a inner join `wxdk_subject` b on a.subjectId = b.id WHERE a.userId = :userId and b.promptWord = :title  and b.flag < 3",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean isTodayOverByUserId(int userId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_usersubject` a WHERE a.`userId` = :userId AND TIMESTAMPDIFF(DAY,DATE_FORMAT(a.createTime,'%Y-%m-%d'),NOW()) = 0 ",paramMap,Integer.class);
        if(count >= Param.GLOBAL_MAX_ADD_SUBJECTNUM){
            return true;
        }
        return false;
    }

    public boolean isExistsByStatus(int userId,int status){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("status",status);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_usersubject a inner join `wxdk_subject` b on a.subjectId = b.id WHERE a.userId = :userId and case when b.status = 3 then 2 else b.status end = :status  and b.flag < 3",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }

    //审核通过
    public boolean isExistBySubjectId(int userId, int subjectId,int status) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("subjectId",subjectId);
        paramMap.put("status",status);
        int count = jdbcOperations.queryForObject("SELECT COUNT(1) FROM `wxdk_usersubject` a INNER JOIN wxdk_subject b ON a.`subjectId` = b.id AND b.`status` = 1 WHERE a.userId = :userId AND a.subjectId = :subjectId AND a.`status` = :status AND b.flag < 3 ",paramMap,Integer.class);
        if(count > 0){
            return true;
        }
        return false;
    }
}
