package com.example.vientianetest.config;

import com.example.vientianetest.dao.DataConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataConfiguration {

    @Autowired
    private DataConfigDao dataConfigDao;

    private Map<String,Object> gradeMap = new HashMap<>();
    private Map<String,Object> dataMap = new HashMap<>();
    private Map<String,Object> prizeMap = new HashMap<>();
    private List<Map<String,Object>> daySignList = new ArrayList<>();   //每日签到
    private List<Map<String,Object>> luckNumberList = new ArrayList<>();//幸运数字
    private Map<String,Object> paragraphMap = new HashMap<>(); //段位列表
    private Map<String, Map<String, Map<String, Object>>> subjectWeightMap = new HashMap<>();//题目权重值分配

    public void initData(Map<String,Object> map){
        this.dataMap = map;
    }

    public void initGradeData(Map<String,Object> map){
        this.gradeMap = map;
    }

    public void initPrizeRatioData(Map<String,Object> map){
        this.prizeMap = map;
    }

    public void initSubjectWeightData(Map<String, Map<String, Map<String, Object>>> mapList){
        this.subjectWeightMap = mapList;
    }

    public void initDaySignData(List<Map<String,Object>> mapList){
        this.daySignList = mapList;
    }

    public void initLuckNumberData(List<Map<String,Object>> mapList){
        this.luckNumberList = mapList;
    }

    public void initParagraphData(Map<String,Object> map){
        this.paragraphMap = map;
    }

    public Map<String,Object> getGradeLevel(){
        return gradeMap;
    }

    public Map<String,Object> getPrizeRatio(){
        return prizeMap;
    }

    public Map<String,Object> getParagraph(){
        return paragraphMap;
    }

    public Map<String, Map<String, Map<String, Object>>>  getSubjectWeight(){
        return subjectWeightMap;
    }

    public List<Map<String,Object>> getDaySignList(){
        return daySignList;
    }

    public List<Map<String,Object>> getLuckNumberList(){
        return luckNumberList;
    }

    public int getMaxGrade(){
        return Integer.parseInt(dataMap.get("maxGrade").toString());
    }

    public int getMaxParagraph(){
        return Integer.parseInt(dataMap.get("maxParagraph").toString());
    }

    public int getMaxExpValue(){
        return Integer.parseInt(dataMap.get("maxExpValue").toString());
    }

    public int getMaxStar(){
        return Integer.parseInt(dataMap.get("maxStar").toString());
    }

    public int getAddExp(){
        return Integer.parseInt(dataMap.get("addExp").toString());
    }

    public String getMainSubject(){
        return dataMap.get("mainSubject").toString();
    }

    public int getMaxBookLevel() {
        return Integer.parseInt(dataMap.get("maxBookLevel").toString());
    }

    public int getMaxBookNum() {
        return Integer.parseInt(dataMap.get("maxBookNum").toString());
    }

    public int getMaxSubjectNum() {
        return Integer.parseInt(dataMap.get("maxSubjectNum").toString());
    }

    public int getDayTaskGood() {
        return Integer.parseInt(dataMap.get("dayTaskGood").toString());
    }




    //是否开启每日挑战的开关
    public boolean isDayChallenge(){
        int count = Integer.parseInt(dataMap.get("isDayChallenge").toString());
        if(count > 0){
            return true;
        }
        return false;
    }

}
