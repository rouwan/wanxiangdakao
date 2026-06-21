package com.example.vientianetest.dao.specialSubject;

import com.example.vientianetest.dao.subject.SubjectOptionDao;
import com.example.vientianetest.domain.SubjectOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserSpecialSubjectDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private SubjectOptionDao subjectOptionDao;
    @Autowired
    private WordLibraryDao wordLibraryDao;

    public int insert(int userId,int cardNumber,int type){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("cardNumber",cardNumber);
        paramMap.put("type",type);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `wxdk_userspecialsubject` (`userId`,`cardNumber`,`type`,`modifyTime`,`createTime`) VALUES('%s','%s','%s',NOW(),NOW()) ;" ,
                userId,cardNumber,type),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }


    public void appendCardNumber(int userId, int cardNumber,int type) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("cardNumber",cardNumber);
        paramMap.put("type",type);
        StringBuilder sql = new StringBuilder();
        sql.append("update `wxdk_userspecialsubject` ");
        sql.append("SET `cardNumber` = cardNumber + :cardNumber, ");
        sql.append("`modifyTime` = now() ");
        sql.append("WHERE `userId` = :userId and type =:type ;");
        jdbcOperations.update(sql.toString(),paramMap);
    }

    public Map<String,Object> getSubjectByUserId(int userId,int cardNumber, int type) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("cardNumber",cardNumber);
        paramMap.put("type",type);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.id,a.image,a.audio,a.answer,a.description,a.modeType,a.type,a.typeId,a.optionSortId,a.promptWord AS title,a.createUserName ,a.createUserUrl,b.cardNumber ");
        sql.append("FROM wxdk_specialsubject b ");
        sql.append("INNER JOIN `wxdk_subject` a ON b.subjectId = a.id ");
        sql.append("WHERE a.flag < 3 AND b.cardNumber = :cardNumber and b.type=:type; ");
        Map<String,Object> subjectMap = jdbcOperations.queryForMap(sql.toString(), paramMap);
        int subjectId = Integer.parseInt(subjectMap.get("id").toString());
        List<SubjectOption> subjectOptionList = subjectOptionDao.getSubjectOptionBySubjectId(subjectId);
        subjectMap.put("optionList",subjectOptionList);
        subjectMap.put("randomStr", wordLibraryDao.getRandomWordLib(12));
        return subjectMap;
    }

    public int getCardNumberByUserId(int userId,int type) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("type", type);
        int cardNumber = 0;
        int count = jdbcOperations.queryForObject("select count(1) from wxdk_userspecialsubject where userId = :userId and type = :type", paramMap, Integer.class);
        if (count > 0) {
            cardNumber = Integer.parseInt(jdbcOperations.queryForMap("select * from wxdk_userspecialsubject where userId = :userId and type = :type limit 1 ",paramMap).get("cardNumber").toString());
            return cardNumber;
        }
        return cardNumber;
    }

    public boolean isExistsByUserId(int userId, int type) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("type", type);
        int count = jdbcOperations.queryForObject("select count(1) from wxdk_userspecialsubject where userId = :userId and type=:type", paramMap, Integer.class);
        if (count > 0) {
            return true;
        }
        return false;
    }

    public Boolean isRightAnswerByIdAndAnswer(int id,String answer){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("answer",answer);
        int answerCount = jdbcOperations.queryForObject("SELECT COUNT(1) FROM wxdk_subject WHERE id = :id  AND answer = :answer ",paramMap,Integer.class);
        if(answerCount > 0){
            return true;
        }
        return false;
    }
}
