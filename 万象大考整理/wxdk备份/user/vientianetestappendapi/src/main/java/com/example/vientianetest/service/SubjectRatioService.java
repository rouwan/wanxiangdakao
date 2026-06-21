package com.example.vientianetest.service;

import com.example.vientianetest.config.DataConfiguration;
import com.example.vientianetest.dao.subject.SubjectDao;
import com.example.vientianetest.dao.subject.SubjectRatioDao;
import com.example.vientianetest.domain.Subject;
import com.example.vientianetest.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class SubjectRatioService {
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private DataConfiguration dataConfiguration;
    @Autowired
    private SubjectRatioDao subjectRatioDao;


    public void update(int subjectId,int totalNum,int rightNum,int errorNum){
        if(!subjectRatioDao.isExistsBySubjectId(subjectId)){
            subjectRatioDao.insert(subjectId,totalNum,rightNum,errorNum);
        }else{
            subjectRatioDao.appendValueByUserId(subjectId,totalNum,rightNum,errorNum);
        }
    }

    public List<Subject> getRandSubjectByRightRatio(List<User> userList,User user,String noExistsId){
        List<Subject> subjectList = new ArrayList<>();
        /*
        1345文字题
        2图文/判断/成语/诗词    
        图文3  判断1  成语1 诗词1*/
        //134都是文字题 难度随机 最后一题文字题 难度困难
        subjectList.addAll(subjectDao.getRandSubjectByRightType(userList,-1,noExistsId,2,true,"3",0));
        for(int j =0;j<subjectList.size();j++) {
            if (noExistsId.length() <= 0) {
                noExistsId += "," + subjectList.get(j).getId() + ",";
            } else {
                noExistsId += subjectList.get(j).getId() + ",";
            }
        }
        subjectList.add(1,subjectDao.getRandSubjectByRightType(userList,-1,noExistsId,1,true,this.getMainStrBySortId(2),0).get(0));
        subjectList.add(2,subjectDao.getRandSubjectByRightType(userList,-1,noExistsId,1,true,this.getMainStrBySortId(3),0).get(0));
        subjectList.addAll(subjectDao.getRandSubjectByRightType(userList,3,noExistsId,1,true,"3",0));
        return subjectList;

        //region 旧流程
        /*List<Subject> subjectList = new ArrayList<>();
        double rightRatio = user.getAbility().getWinRatio();
        int totalNum = user.getAbility().getTotalNum();
        int type = 0;
        //文字题、判断题 5题出3题，其余随机2两道不重复
        if(67<=rightRatio){//高手
            type = 3;
        }else if(34<=rightRatio && rightRatio<67){ //一般
            type = 2;
        }else {
            type = 1;
        }
        user.getAbility().setLevelType(type);
        //noExistsId = userRankSubjectRecordRedis.getUserSubjectRecord(user.getUser().getUserId());
        String mainStr = dataConfiguration.getMainSubject();
        mainStr = mainStr == null ? "3,4" : mainStr;
        List<Subject> newSubjectList = new ArrayList<>();
        switch (type){
            case 1: //新手
                newSubjectList.addAll(subjectDao.getRandSubjectByRightType(userList,1,noExistsId,2,true,mainStr,0));
                for(int j =0;j<newSubjectList.size();j++){
                    if(noExistsId.length() <= 0){
                        noExistsId += "," + newSubjectList.get(j).getId() + ",";
                    }else{
                        noExistsId += newSubjectList.get(j).getId() + ",";
                    }
                }
                break;
            case 2: //一般
                //题目难度123相当于不限制
                newSubjectList.addAll(subjectDao.getRandSubjectByRightType(userList,-1,noExistsId,2,true,mainStr,0));
                for(int j =0;j<newSubjectList.size();j++){
                    if(noExistsId.length() <= 0){
                        noExistsId += "," + newSubjectList.get(j).getId() + ",";
                    }else{
                        noExistsId += newSubjectList.get(j).getId() + ",";
                    }
                }
                break;
            case 3: //高手
                newSubjectList.addAll(subjectDao.getRandSubjectByRightType(userList,-1,noExistsId,2,true,mainStr,0));
                for(int j =0;j<newSubjectList.size();j++){
                    if(noExistsId.length() <= 0){
                        noExistsId += "," + newSubjectList.get(j).getId() + ",";
                    }else{
                        noExistsId += newSubjectList.get(j).getId() + ",";
                    }
                }
                break;
        }
        int noExistsModeType = 0;
        for(int i=1;i<=2;i++){
            if(i == 2){
                noExistsModeType = newSubjectList.get(2).getModeType();
            }
            //不控难度
            newSubjectList.addAll(subjectDao.getRandSubjectByRightType(userList,-1,noExistsId,1,false,mainStr,noExistsModeType));
        }
        Collections.shuffle(newSubjectList);//随机打乱
        subjectList.addAll(newSubjectList);
        //最后一题一定是主类型题困难题->文字题
        subjectList.addAll(subjectDao.getRandSubjectByRightType(userList,3,noExistsId,1,true,"3",0));
        return subjectList;*/
        //endregion
    }

    public String getMainStrBySortId(int subjectSortId) {
        //第二题根据权重随机
        Map<String, Map<String, Map<String, Object>>> mapList = dataConfiguration.getSubjectWeight();
        Map<String, Map<String, Object>> subjectMap = mapList.get(String.valueOf(subjectSortId));
        int randomRatio = (int) (Math.random() * 101);
        String secondMainStr = "4";
        for (Map.Entry<String, Map<String, Object>> entry : subjectMap.entrySet()) {
            String type = entry.getKey();
            int minValue = Integer.parseInt(entry.getValue().get("minRatio").toString());
            int maxValue = Integer.parseInt(entry.getValue().get("maxRatio").toString());
            if (minValue <= randomRatio && randomRatio <= maxValue) {
                secondMainStr = type;
                break;
            }
        }
        return secondMainStr;
    }
}
