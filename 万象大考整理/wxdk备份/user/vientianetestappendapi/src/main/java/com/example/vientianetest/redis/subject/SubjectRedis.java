package com.example.vientianetest.redis.subject;

import com.example.common.utils.Rand;
import com.example.vientianetest.dao.subject.SubjectOptionDao;
import com.example.vientianetest.dao.user.UserSubjectLikeDao;
import com.example.vientianetest.domain.Subject;
import com.example.vientianetest.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SubjectRedis {
    private static final Logger logger = LoggerFactory.getLogger(SubjectRedis.class);
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private SubjectOptionDao subjectOptionDao;
    @Autowired
    private UserSubjectLikeDao userSubjectLikeDao;

    private String KEY_PACKAGE = "server:subject:modeType:";

    public void initSubject() {
        String[] modeTypeStr = "1,2,3,4,5,6,7,8,9,10".split(",");//题目类型
        for (int i = 0; i < modeTypeStr.length; i++) {
            int modeType = Integer.parseInt(modeTypeStr[i]);
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ratio,GROUP_CONCAT(a.id ORDER BY a.id ) AS groupId  ");
            sql.append("FROM ( ");
            sql.append("SELECT modeType,a.id,CASE WHEN IFNULL(b.rightNum/b.totalNum*100,0) > 66 THEN 1 WHEN IFNULL(b.rightNum/b.totalNum*100,0) >= 33 AND IFNULL(b.rightNum/b.totalNum*100,0) <= 66 THEN 2 WHEN IFNULL(b.rightNum/b.totalNum*100,0) >= 0 AND IFNULL(b.rightNum/b.totalNum*100,0) < 33 THEN 3 END AS ratio ");
            sql.append("FROM `wxdk_subject` a ");
            sql.append("LEFT JOIN wxdk_subjectratio b ON a.id = b.subjectId ");
            sql.append("WHERE a.flag < 3 AND a.status = 1 AND a.modeType = " + modeType);
            sql.append(" ) a ");
            sql.append("GROUP BY a.ratio ");
            sql.append("ORDER BY a.ratio ");
            List<Map<String, Object>> mapList = jdbcOperations.queryForList(sql.toString(), new HashMap<>());
            redisTemplate.delete(KEY_PACKAGE + modeType);//先删除，后增加
            for (int j = 0; j < mapList.size(); j++) {
                String key = mapList.get(j).get("ratio").toString();
                String value = mapList.get(j).get("groupId").toString();
                redisTemplate.opsForHash().put(KEY_PACKAGE + modeType, key, value);
            }
        }
    }

    public List<Subject> getRandSubjectList(List<User> userList,String modeTypeStr, int randNum) {
        try {
            List<Subject> subjectList = new ArrayList<>();
            List<Integer> subjectIdList = new ArrayList<>();
            String[] modeTypeArr = modeTypeStr.split(",");
            String modeType = String.valueOf(modeTypeArr[Rand.nextInt(modeTypeArr.length)]);
            boolean hasKey = redisTemplate.hasKey(KEY_PACKAGE + modeType);
            int safetyKey = 0;
            while (!hasKey && safetyKey < 100) {
                modeType = String.valueOf(modeTypeArr[Rand.nextInt(modeTypeArr.length)]);
                hasKey = redisTemplate.hasKey(KEY_PACKAGE + modeType);
                safetyKey++;
            }
            if (!hasKey) { logger.info("getRandSubjectList Redis key missing, returning null"); return null; }
            int len = randNum;
            int randIndex = 0;//避免死循环
            String[] ratioTypeArr = "1,2,3".split(",");//1:简单2:一般3:困难
            String ratioType = "1";
            for (int i = 0; i < len; i++) {
                ratioType = String.valueOf(ratioTypeArr[Rand.nextInt(ratioTypeArr.length)]);
                boolean ratioHasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE + modeType, ratioType);
                int safetyRatio = 0;
                while (!ratioHasKey && safetyRatio < 100) {
                    ratioType = String.valueOf(ratioTypeArr[Rand.nextInt(ratioTypeArr.length)]);
                    ratioHasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE + modeType, ratioType);
                    safetyRatio++;
                }
                if (!ratioHasKey) { continue; }
                String[] groupIdArr = redisTemplate.opsForHash().get(KEY_PACKAGE + modeType, ratioType).toString().split(",");
                if (randIndex > 3) {
                    break;
                }
                if (groupIdArr.length < randNum) {
                    len = len + 1;
                    randIndex = randIndex + 1;
                    continue;
                }
                int subjectId = Integer.parseInt(groupIdArr[Rand.nextInt(groupIdArr.length)]);
                while (subjectIdList.contains(subjectId)) {//不重复题目
                    subjectId = Integer.parseInt(groupIdArr[Rand.nextInt(groupIdArr.length)]);
                }
                subjectIdList.add(subjectId);//不重复题目
            }
            if (subjectIdList.size() >= randNum) {
                int rightType = Integer.parseInt(ratioType);
                String joinSubjectId = StringUtils.join(subjectIdList, ",");
                List<Subject> subject = jdbcOperations.query("SELECT a.id,a.image,a.audio,a.answer,a.description,a.modeType,a.type,a.typeId,a.optionSortId,a.promptWord AS title,IFNULL(b.`parentType`,0) as parentType,a.createUserName ,a.createUserUrl,case when " + rightType + " = 1 then 80 when " + rightType + " = 2 then 50 when " + rightType + " = 3 then 10 end AS rightRatio FROM `wxdk_subject` a LEFT JOIN `wxdk_subjecttype` b ON a.`typeId` = b.id WHERE a.id in (" + joinSubjectId + ") ",
                        new HashMap<>(), new BeanPropertyRowMapper<>(Subject.class));
                for (int i = 0; i < subject.size(); i++) {
                    subject.get(i).setOptionList(subjectOptionDao.getSubjectOptionBySubjectId(subject.get(i).getId()));
                    //查找用户的点赞数
                    if (userList != null) {
                        List<Object> likeList = new ArrayList<>();
                        for (int j = 0; j < userList.size(); j++) {
                            int userId = userList.get(j).getUser().getUserId();
                            if (userId > 0) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("userId", userId);
                                map.put("likeStatus", userSubjectLikeDao.getUserLikeBySubjectId(userId, subject.get(i).getId()));
                                likeList.add(map);
                            }
                        }
                        subject.get(i).setLikeStatusList(likeList);
                    }
                    subjectList.add(subject.get(i));
                }
            }

            return subjectList;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("getRandSubjectList 下发题目，异常:{}", ex.getMessage());
            return null;
        }
    }

    public List<Subject>  getRandSubjectByRightType(List<User> userList,int rightType, String noExistsId, int randNum, boolean isMain, String mainModeTypeStr, int noExistsModeType) {
        try {
            List<Subject> subjectList = new ArrayList<>();
            List<Integer> subjectIdList = new ArrayList<>();
            String[] modeTypeArr = mainModeTypeStr.split(",");
            String modeType = String.valueOf(modeTypeArr[Rand.nextInt(modeTypeArr.length)]);
            if (isMain) {
                boolean hasKey = redisTemplate.hasKey(KEY_PACKAGE + modeType);
                int safety = 0;
                while (!hasKey && safety < 100) {
                    modeType = String.valueOf(modeTypeArr[Rand.nextInt(modeTypeArr.length)]);
                    hasKey = redisTemplate.hasKey(KEY_PACKAGE + modeType);
                    safety++;
                }
                if (!hasKey) {
                    logger.info("getRandSubjectByRightType Redis key missing, modeTypeArr={} returning null", mainModeTypeStr);
                    return null;
                }
            } else {
                String noExistsModeTypeStr = noExistsModeType > 0 ? "," + mainModeTypeStr + "," + noExistsModeType + "," : "," + mainModeTypeStr + ",";
                String[] allModeType = "1,2,3,4,5,6,7,8,9,10".split(",");
                modeType = String.valueOf(allModeType[Rand.nextInt(allModeType.length)]);
                boolean hasKey = redisTemplate.hasKey(KEY_PACKAGE + modeType);
                int safety = 0;
                while ((noExistsModeTypeStr.contains(modeType) || !hasKey) && safety < 100) {
                    modeType = String.valueOf(allModeType[Rand.nextInt(allModeType.length)]);
                    hasKey = redisTemplate.hasKey(KEY_PACKAGE + modeType);
                    safety++;
                }
            }

            //控制难度
            int len = randNum;
            int randIndex = 0;//避免死循环
            String[] ratioTypeArr = rightType > 0 ? String.valueOf(rightType).split(",") : "1,2,3".split(",");//1:简单2:一般3:困难
            Map<Integer,Integer> rightTypeMap = new HashMap<>();
            for (int i = 0; i < len; i++) {
                rightType = Integer.parseInt(ratioTypeArr[Rand.nextInt(ratioTypeArr.length)]);
                boolean ratioHasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE + modeType, String.valueOf(rightType));
                while (!ratioHasKey) {
                    rightType = Integer.parseInt(ratioTypeArr[Rand.nextInt(ratioTypeArr.length)]);
                    ratioHasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE + modeType, String.valueOf(rightType));
                }
                String[] groupIdArr = redisTemplate.opsForHash().get(KEY_PACKAGE + modeType, String.valueOf(rightType)).toString().split(",");
                if (randIndex > 3) {
                    break;
                }
                if (groupIdArr.length < randNum) {
                    rightType = -1;//没有符合条数，不控难度
                    len = len + 1;
                    randIndex = randIndex + 1;
                    continue;
                }
                randIndex = 0;
                int subjectId = Integer.parseInt(groupIdArr[Rand.nextInt(groupIdArr.length)]);
                while (noExistsId.contains("," + subjectId + ",") || subjectIdList.contains(subjectId)) {
                    if (randIndex > 10) {
                        break;
                    }
                    subjectId = Integer.parseInt(groupIdArr[Rand.nextInt(groupIdArr.length)]);
                    randIndex = randIndex + 1;
                }
                if (randIndex > 10) {
                    break;
                }
                subjectIdList.add(subjectId);
                rightTypeMap.put(subjectId,rightType);
            }
            if (subjectIdList.size() >= randNum) {
                String joinSubjectId = StringUtils.join(subjectIdList, ",");
                List<Subject> subject = jdbcOperations.query("SELECT a.id,a.image,a.audio,a.answer,a.description,a.modeType,a.type,a.typeId,a.optionSortId,a.promptWord AS title,IFNULL(b.`parentType`,0) as parentType,a.createUserName ,a.createUserUrl FROM `wxdk_subject` a LEFT JOIN `wxdk_subjecttype` b ON a.`typeId` = b.id WHERE a.id IN (" + joinSubjectId + ") ",
                        new HashMap<>(), new BeanPropertyRowMapper<>(Subject.class));
                for (int i = 0; i < subject.size(); i++) {
                    int rightTypeSub = rightTypeMap.get(subject.get(i).getId());
                    if(rightTypeSub == 1){
                        subject.get(i).setRightRatio(80);
                    }else if(rightTypeSub == 2){
                        subject.get(i).setRightRatio(50);
                    }else if(rightTypeSub == 3) {
                        subject.get(i).setRightRatio(10);
                    }else {
                        subject.get(i).setRightRatio(80);
                    }
                    Map<String,Object> likeTotalMap = userSubjectLikeDao.getTotalLikeBySubjectId(subject.get(i).getId());
                    int likeNum = Integer.parseInt(likeTotalMap.get("likeNum").toString());
                    int noLikeNum = Integer.parseInt(likeTotalMap.get("noLikeNum").toString());
                    subject.get(i).setLikeNum(likeNum);
                    subject.get(i).setNoLikeNum(noLikeNum);
                    subject.get(i).setOptionList(subjectOptionDao.getSubjectOptionBySubjectId(subject.get(i).getId()));
                    subjectList.add(subject.get(i));
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
            return subjectList;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("getRandSubjectByRightType 下发题目，异常:{}", ex.getMessage());
            return null;
        }
    }
}
