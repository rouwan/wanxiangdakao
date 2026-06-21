package com.example.vientianetest.dao;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicUserInfo;
import com.example.vientianetest.domain.Game;
import com.example.vientianetest.domain.SubjectOption;
import com.example.vientianetest.domain.Subject;
import com.example.vientianetest.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class WebSocketDao {

    @Autowired
    NamedParameterJdbcOperations jdbcOperations;

    //region getRandRobot
    public User getRandRobot(){
        Map<String,Object> paramMap = new HashMap<>();
        Map<String,Object> map =jdbcOperations.queryForMap("SELECT * FROM wxdk_robot ORDER BY rand() LIMIT 1",paramMap);
        User user = new User();
        BasicUserInfo basicUserInfo = new BasicUserInfo();
        basicUserInfo.setUserId(Integer.parseInt(map.get("id").toString()));
        basicUserInfo.setNickName(map.get("nickname").toString());
        basicUserInfo.setGender(map.get("sexDesc").toString());
        basicUserInfo.setAvatarUrl(map.get("headImgUrl").toString());
        basicUserInfo.setCity(map.get("city").toString());
        basicUserInfo.setProvince(map.get("province").toString());
        basicUserInfo.setCountry(map.get("country").toString());
        user.setUser(basicUserInfo);
        user.setJoinTime(new Date().getTime());
        user.setBoot(true);
        return user;
    }
    //endregion

    //region getSubjectList
    public List<Subject> getSubjectList(int randNum){
        Map<String,Object> paramMap = new HashMap<>();
        List<Map<String,Object>> subjectListMap = jdbcOperations.queryForList(String.format("SELECT id,image,answer,optionSortId,promptWord,type FROM `ktcc_subject` WHERE flag < 3 and status= 1 ORDER BY RAND() LIMIT %s ",randNum),paramMap);
        List<Subject> subjectList = new ArrayList<>();
        for(int i =0;i<subjectListMap.size();i++ ){
            Subject subject = new Subject();
            int subjectId = Integer.parseInt(subjectListMap.get(i).get("id").toString());
            subject.setId(subjectId);
            subject.setImage(subjectListMap.get(i).get("image").toString());
            subject.setOptionSortId(Integer.parseInt(subjectListMap.get(i).get("optionSortId").toString()));
            subject.setType(Integer.parseInt(subjectListMap.get(i).get("type").toString()));

            List<SubjectOption> optionList = new ArrayList<>();
            /*随机选项*/
            List<Map<String,Object>> optionListMap =jdbcOperations.queryForList(String.format("SELECT id,subjectId,`option`,sortId FROM `ktcc_subjectoption` WHERE subjectId = '%s' ORDER BY RAND() ",subjectId),paramMap);
            for(int j =0;j<optionListMap.size(); j++){
                SubjectOption option = new SubjectOption();
                option.setId(Integer.parseInt(optionListMap.get(j).get("id").toString()));
                option.setName(optionListMap.get(j).get("option").toString());
                option.setSubjectId(subjectId);
                option.setSortId(Integer.parseInt(optionListMap.get(j).get("sortId").toString()));
                optionList.add(option);
            }
            subject.setOptionList(optionList);
            subjectList.add(subject);
        }

        return subjectList;//JSONObject.parseObject(sb.toString());
    }
    /*public String getSubjectList(int randNum){
        Map<String,Object> paramMap = new HashMap<>();
        List<Map<String,Object>> subjectListMap = jdbcOperations.queryForList(String.format("SELECT id,image,answer,promptWord,type FROM `ktcc_subject` WHERE flag < 3 and status= 1 ORDER BY RAND() LIMIT %s ",randNum),paramMap);
        StringBuilder sb = new StringBuilder();
        sb.append("{\"Subject\":[");
        for(int i =0;i<subjectListMap.size();i++ ){
            int subjectId= Integer.parseInt(subjectListMap.get(i).get("id").toString());
            sb.append("{\"id\":"+subjectId+",");
            sb.append("\"image\":\""+subjectListMap.get(i).get("image")+"\",");
            sb.append("\"answer\":\""+subjectListMap.get(i).get("answer").toString().replaceAll("\"","“")+"\",");
            sb.append("\"promptWord\":\""+subjectListMap.get(i).get("promptWord").toString().replaceAll("\"","“")+"\",");
            sb.append("\"type\":"+subjectListMap.get(i).get("type")+",");
            sb.append("\"Option\":[");
            List<Map<String,Object>> optionListMap =jdbcOperations.queryForList(String.format("SELECT id,subjectId,`option`,sortId FROM `ktcc_subjectoption` WHERE subjectId = '%s' ORDER BY sortId ",subjectId),paramMap);
            for(int j =0;j<optionListMap.size(); j++){
                sb.append("{\"id\":"+optionListMap.get(j).get("id")+",");
                sb.append("\"option\":\""+optionListMap.get(j).get("option").toString().replaceAll("\"","“")+"\",");
                if((optionListMap.size()-1) != j)
                    sb.append("\"sortId\":"+optionListMap.get(j).get("sortId")+"},");
                else
                    sb.append("\"sortId\":"+optionListMap.get(j).get("sortId")+"}");
            }
            sb.append("]");
            if((subjectListMap.size()-1) != i)
                sb.append("},");
            else
                sb.append("}");

        }
        sb.append("]");
        sb.append("}");
        return sb.toString();//JSONObject.parseObject(sb.toString());
    }*/
    //endregion

    //region createGame
    public int createGame (String uidStr,int status){
        Map<String,Object> paramMap = new HashMap<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("INSERT INTO `ktcc_game` (`users`,`status`,`modifyTime`,`createTime`) " +
                                            "VALUES('%s','%s',now(),now()) ;",uidStr,status),null,keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }
    //endregion

    //region updateGame
    public void updateGame(Game game){
        Map<String,Object> paramMap = new HashMap<>();
        jdbcOperations.update(String.format("UPDATE `ktcc_game` SET detail = '%s' , status = '%s', modifyTime = NOW() WHERE id = '%s'",JSONObject.toJSONString(game),game.getStatus(),game.getId()),paramMap);
    }
    //endregion

}
