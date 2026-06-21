package com.example.vientianetest.dao.subject;

import com.example.vientianetest.dao.user.UserSubjectLikeDao;
import com.example.vientianetest.domain.Subject;
import com.example.vientianetest.domain.SubjectOption;
import com.example.vientianetest.domain.User;
import com.example.vientianetest.redis.subject.SubjectRedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SubjectDao {
    private static final Logger logger = LoggerFactory.getLogger(SubjectDao.class);
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private SubjectOptionDao subjectOptionDao;
    @Autowired
    private SubjectRedis subjectRedis;
    @Autowired
    private UserSubjectLikeDao userSubjectLikeDao;

    public List<Subject> getRandSubjectList(int randNum,int modeType){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("randNum",randNum);
        paramMap.put("modeType",modeType);
        List<Subject> subjectList = jdbcOperations.query("SELECT a.id,a.image,a.audio,a.answer,a.description,a.modeType,a.type,a.typeId,a.optionSortId,a.promptWord AS title,IFNULL(b.`parentType`,0) as parentType,a.createUserName ,a.createUserUrl,IFNULL((SELECT FORMAT(rightNum/totalNum*100,2) FROM `wxdk_subjectratio` WHERE subjectId = a.id LIMIT 1),0.00) AS rightRatio FROM `wxdk_subject` a LEFT JOIN `wxdk_subjecttype` b ON a.`typeId` = b.id WHERE a.flag < 3 AND a.status= 1 AND a.modeType = :modeType ORDER BY RAND() LIMIT :randNum ",
                paramMap,new BeanPropertyRowMapper<>(Subject.class));
        for(int i = 0 ;i<subjectList.size();i++){
            List<SubjectOption> subjectOptionList = subjectOptionDao.getSubjectOptionBySubjectId(subjectList.get(i).getId());
            subjectList.get(i).setOptionList(subjectOptionList);
        }
        return subjectList;
    }

    public List<Subject> getRandSubjectList(List<User> userList,int randNum,String modeType) {
        //region 旧流程
        /*Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("randNum", randNum);
        List<Subject> subjectList = jdbcOperations.query("SELECT a.id,a.image,a.audio,a.answer,a.description,a.modeType,a.type,a.typeId,a.optionSortId,a.promptWord AS title,IFNULL(b.`parentType`,0) as parentType,a.createUserName ,a.createUserUrl,IFNULL((SELECT FORMAT(rightNum/totalNum*100,2) FROM `wxdk_subjectratio` WHERE subjectId = a.id LIMIT 1),0.00) AS rightRatio FROM `wxdk_subject` a LEFT JOIN `wxdk_subjecttype` b ON a.`typeId` = b.id WHERE a.flag < 3 AND a.status= 1 AND a.modeType in (" + modeType + ") ORDER BY RAND() LIMIT :randNum ",
                paramMap, new BeanPropertyRowMapper<>(Subject.class));
        for (int i = 0; i < subjectList.size(); i++) {
            subjectList.get(i).setOptionList(subjectOptionDao.getSubjectOptionBySubjectId(subjectList.get(i).getId()));
        }*/
        //endregion 旧流程

        //region 读redis
        List<Subject> subjectList = subjectRedis.getRandSubjectList(userList,modeType, randNum);
        if (subjectList == null || subjectList.size() < randNum) {
            logger.info("getRandSubjectList 题目数目不够，走原来的流程！！ randNum :{} modeType:{}", randNum, modeType);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("randNum", randNum);
            subjectList = jdbcOperations.query("SELECT a.id,a.image,a.audio,a.answer,a.description,a.modeType,a.type,a.typeId,a.optionSortId,a.promptWord AS title,IFNULL(b.`parentType`,0) as parentType,a.createUserName ,a.createUserUrl,IFNULL((SELECT FORMAT(rightNum/totalNum*100,2) FROM `wxdk_subjectratio` WHERE subjectId = a.id LIMIT 1),0.00) AS rightRatio FROM `wxdk_subject` a LEFT JOIN `wxdk_subjecttype` b ON a.`typeId` = b.id WHERE a.flag < 3 AND a.status= 1 AND a.modeType in (" + modeType + ") ORDER BY RAND() LIMIT :randNum ",
                    paramMap, new BeanPropertyRowMapper<>(Subject.class));
            for (int i = 0; i < subjectList.size(); i++) {
                subjectList.get(i).setOptionList(subjectOptionDao.getSubjectOptionBySubjectId(subjectList.get(i).getId()));
                //查找用户的点赞数
                if (userList != null) {
                    List<Object> likeList = new ArrayList<>();
                    for (int j = 0; j < userList.size(); j++) {
                        int userId = userList.get(j).getUser().getUserId();
                        if (userId > 0) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("userId", userId);
                            map.put("likeStatus", userSubjectLikeDao.getUserLikeBySubjectId(userId, subjectList.get(i).getId()));
                            likeList.add(map);
                        }
                    }
                    subjectList.get(i).setLikeStatusList(likeList);
                }
            }
        }
        //endregion

        return subjectList;
    }

    public List<Subject> getRandSubjectListByUserId(int userId,int randNum){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("randNum",randNum);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.id,a.image,a.audio,a.answer,a.description,a.modeType,a.type,a.typeId,a.optionSortId,a.promptWord AS title,IFNULL(b.`parentType`,0) AS parentType,a.createUserName ,a.createUserUrl ");
        sb.append("FROM `wxdk_subject` a  ");
        sb.append("LEFT JOIN `wxdk_subjecttype` b ON a.`typeId` = b.id  ");
        sb.append(",(SELECT `historySubjectId` FROM wxdk_userdaychallenge WHERE userId = :userId LIMIT 1) c ");
        sb.append("WHERE a.flag < 3 AND LOCATE(CONCAT(',',a.id,','),c.historySubjectId) = 0 AND a.status= 1 AND a.modeType = 3 ORDER BY RAND() LIMIT :randNum ");
        List<Subject> subjectList = jdbcOperations.query(sb.toString(),paramMap,new BeanPropertyRowMapper<>(Subject.class));
        for(int i = 0 ;i<subjectList.size();i++){
            List<SubjectOption> subjectOptionList = subjectOptionDao.getSubjectOptionBySubjectId(subjectList.get(i).getId());
            subjectList.get(i).setOptionList(subjectOptionList);
        }
        return subjectList;
    }

    public List<Subject> getWeekRandSubjectListByUserId(int userId,int titleId,int randNum){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("titleId",titleId);
        paramMap.put("randNum",randNum);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT b.id,b.image,b.audio,b.answer,b.description,b.modeType,b.type,b.typeId,b.optionSortId,b.promptWord AS title,IFNULL(c.`parentType`,0) AS parentType,b.createUserName ,b.createUserUrl   ");
        sb.append("FROM `wxdk_weeksubject` a  ");
        sb.append("INNER JOIN `wxdk_subject` b ON a.subjectId = b.`id` ");
        sb.append("LEFT JOIN `wxdk_subjecttype` c ON b.`typeId` = c.id ");
        sb.append(",(SELECT `historySubjectId` FROM wxdk_userweekchallenge WHERE userId = :userId AND titleId = :titleId LIMIT 1) d ");
        sb.append("WHERE b.flag < 3 AND a.titleId = :titleId AND LOCATE(CONCAT(',',b.id,','),d.historySubjectId) = 0 AND b.status = 1 AND b.modeType = 3 ORDER BY RAND() LIMIT :randNum ");
        List<Subject> subjectList = jdbcOperations.query(sb.toString(),paramMap,new BeanPropertyRowMapper<>(Subject.class));
        for(int i = 0 ;i<subjectList.size();i++){
            List<SubjectOption> subjectOptionList = subjectOptionDao.getSubjectOptionBySubjectId(subjectList.get(i).getId());
            subjectList.get(i).setOptionList(subjectOptionList);
        }
        return subjectList;
    }

    public Map<String,Object> getInfoById(int id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return jdbcOperations.queryForMap("SELECT * FROM wxdk_subject WHERE id =:id",paramMap);
    }

    public int getTotalCount(){
        return jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_subject WHERE flag < 3 AND status= 1 ",new HashMap<>(),Integer.class);
    }

    //根据正确率随机一条
    public List<Subject> getRandSubjectByRightType(List<User> userList,int rightType, String noExistsId, int pageSize, boolean isMain, String mainStr, int noExistsModeType) {
        //region 旧流程
        /*Map<String, Object> paramMap = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.id,a.image,a.audio,a.answer,a.description,a.modeType,a.type,a.typeId,a.optionSortId,a.promptWord AS title,IFNULL(b.`parentType`,0) AS parentType,a.createUserName ,a.createUserUrl,");
        sql.append("IFNULL((SELECT FORMAT(rightNum/totalNum*100,2) FROM `wxdk_subjectratio` WHERE subjectId = a.id LIMIT 1),0.00) AS rightRatio ");
        sql.append("FROM `wxdk_subject` a ");
        sql.append("LEFT JOIN `wxdk_subjecttype` b ON a.`typeId` = b.id ");
        sql.append("LEFT JOIN wxdk_subjectratio c ON a.id = c.`subjectId` ");
        sql.append("WHERE a.flag < 3 AND a.status= 1 AND "); // ORDER BY RAND() LIMIT 5
        if (isMain) {
            sql.append("a.modeType in (" + mainStr + ") AND ");
        } else {
            if (noExistsModeType > 0) {
                sql.append("a.modeType not in (" + mainStr + "," + noExistsModeType + ") AND ");
            } else {
                sql.append("a.modeType not in (" + mainStr + ") AND ");
            }
        }
        if (rightType == 1) { //简单
            sql.append("c.rightNum/c.totalNum*100 > 66 ");
        } else if (rightType == 2) {//一般
            sql.append("c.rightNum/c.totalNum*100 >= 33 AND c.rightNum/c.totalNum*100 <= 66 ");
        } else if (rightType == 3) {//困难
            sql.append("c.rightNum/c.totalNum*100 >= 0 AND c.rightNum/c.totalNum*100 < 33 ");
        } else {
            sql.append(" 1=1 ");
        }
        if (noExistsId.length() > 0) {//避免重复
            sql.append("AND LOCATE(CONCAT(',',a.id,','),'" + noExistsId + "') = 0 ");
        }
        List<Subject> subjectList = jdbcOperations.query(sql.toString() + " ORDER BY RAND() LIMIT " + pageSize + " ", paramMap, new BeanPropertyRowMapper<>(Subject.class));
        if (subjectList.size() < pageSize) {
            List<Subject> newSubjectList = new ArrayList<>();
            if (subjectList.size() > 0) {
                newSubjectList.addAll(subjectList);
            }
            int newPageSize = pageSize - subjectList.size();
            newSubjectList.addAll(this.getRandSubjectByRightType(-1, noExistsId, newPageSize, isMain, mainStr, noExistsModeType));
            for (int i = 0; i < newSubjectList.size(); i++) {
                newSubjectList.get(i).setOptionList(subjectOptionDao.getSubjectOptionBySubjectId(newSubjectList.get(i).getId()));
            }
            return newSubjectList;
        } else {
            for (int i = 0; i < subjectList.size(); i++) {
                subjectList.get(i).setOptionList(subjectOptionDao.getSubjectOptionBySubjectId(subjectList.get(i).getId()));
            }
        }*/
        //endregion 旧流程

        //region 读redis
        List<Subject> subjectList = subjectRedis.getRandSubjectByRightType(userList, rightType, noExistsId, pageSize, isMain, mainStr, noExistsModeType);
        if (subjectList == null || subjectList.size() < pageSize) {
            logger.info("getRandSubjectByRightType 题目数目不够，走原来的流程！！ rightType :{} noExistsId:{} pageSize:{} isMain:{} mainStr:{} noExistsModeType:{}", rightType, noExistsId, pageSize, isMain, mainStr, noExistsModeType);
            //判断有没有这个类型的条数
            Map<String, Object> paramMap = new HashMap<>();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT a.id,a.image,a.audio,a.answer,a.description,a.modeType,a.type,a.typeId,a.optionSortId,a.promptWord AS title,IFNULL(b.`parentType`,0) AS parentType,a.createUserName ,a.createUserUrl,");
            sql.append("IFNULL((SELECT FORMAT(rightNum/totalNum*100,2) FROM `wxdk_subjectratio` WHERE subjectId = a.id LIMIT 1),0.00) AS rightRatio ");
            sql.append("FROM `wxdk_subject` a ");
            sql.append("LEFT JOIN `wxdk_subjecttype` b ON a.`typeId` = b.id ");
            sql.append("LEFT JOIN wxdk_subjectratio c ON a.id = c.`subjectId` ");
            sql.append("WHERE a.flag < 3 AND a.status= 1 AND "); // ORDER BY RAND() LIMIT 5
            if (isMain) {
                sql.append("a.modeType in (" + mainStr + ") AND ");
            } else {
                if (noExistsModeType > 0) {
                    sql.append("a.modeType not in (" + mainStr + "," + noExistsModeType + ") AND ");
                } else {
                    sql.append("a.modeType not in (" + mainStr + ") AND ");
                }
            }
            if (rightType == 1) { //简单
                sql.append("c.rightNum/c.totalNum*100 > 66 ");
            } else if (rightType == 2) {//一般
                sql.append("c.rightNum/c.totalNum*100 >= 33 AND c.rightNum/c.totalNum*100 <= 66 ");
            } else if (rightType == 3) {//困难
                sql.append("c.rightNum/c.totalNum*100 >= 0 AND c.rightNum/c.totalNum*100 < 33 ");
            } else {
                sql.append(" 1=1 ");
            }
            if (noExistsId.length() > 0) {//避免重复
                sql.append("AND LOCATE(CONCAT(',',a.id,','),'" + noExistsId + "') = 0 ");
            }
            subjectList = jdbcOperations.query(sql.toString() + " ORDER BY RAND() LIMIT " + pageSize + " ", paramMap, new BeanPropertyRowMapper<>(Subject.class));
            if (subjectList.size() < pageSize) {
                List<Subject> newSubjectList = new ArrayList<>();
                if (subjectList.size() > 0) {
                    newSubjectList.addAll(subjectList);
                }
                int newPageSize = pageSize - subjectList.size();
                newSubjectList.addAll(this.getRandSubjectByRightType(userList, -1, noExistsId, newPageSize, isMain, mainStr, noExistsModeType));
                for (int i = 0; i < newSubjectList.size(); i++) {
                    Map<String,Object> likeTotalMap = userSubjectLikeDao.getTotalLikeBySubjectId(newSubjectList.get(i).getId());
                    int likeNum = Integer.parseInt(likeTotalMap.get("likeNum").toString());
                    int noLikeNum = Integer.parseInt(likeTotalMap.get("noLikeNum").toString());
                    newSubjectList.get(i).setLikeNum(likeNum);
                    newSubjectList.get(i).setNoLikeNum(noLikeNum);
                    newSubjectList.get(i).setOptionList(subjectOptionDao.getSubjectOptionBySubjectId(newSubjectList.get(i).getId()));
                    //查找用户的点赞数
                    if (userList != null) {
                        List<Object> likeList = new ArrayList<>();
                        for (int j = 0; j < userList.size(); j++) {
                            int userId = userList.get(j).getUser().getUserId();
                            if (userId > 0) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("userId", userId);
                                map.put("likeStatus", userSubjectLikeDao.getUserLikeBySubjectId(userId, subjectList.get(i).getId()));
                                likeList.add(map);
                            }
                        }
                        subjectList.get(i).setLikeStatusList(likeList);
                    }
                    likeTotalMap = null;
                }
                return newSubjectList;
            } else {
                for (int i = 0; i < subjectList.size(); i++) {
                    Map<String,Object> likeTotalMap = userSubjectLikeDao.getTotalLikeBySubjectId(subjectList.get(i).getId());
                    int likeNum = Integer.parseInt(likeTotalMap.get("likeNum").toString());
                    int noLikeNum = Integer.parseInt(likeTotalMap.get("noLikeNum").toString());
                    subjectList.get(i).setLikeNum(likeNum);
                    subjectList.get(i).setNoLikeNum(noLikeNum);
                    subjectList.get(i).setOptionList(subjectOptionDao.getSubjectOptionBySubjectId(subjectList.get(i).getId()));
                    //查找用户的点赞数
                    if (userList != null) {
                        List<Object> likeList = new ArrayList<>();
                        for (int j = 0; j < userList.size(); j++) {
                            int userId = userList.get(j).getUser().getUserId();
                            if (userId > 0) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("userId", userId);
                                map.put("likeStatus", userSubjectLikeDao.getUserLikeBySubjectId(userId, subjectList.get(i).getId()));
                                likeList.add(map);
                            }
                        }
                        subjectList.get(i).setLikeStatusList(likeList);
                    }
                    likeTotalMap = null;
                }
            }
        }
        //endregion 读redis后期再开启

        return subjectList;
    }
}
