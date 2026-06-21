package com.example.vientianetest.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicUserInfo;
import com.example.common.utils.Rand;
import com.example.vientianetest.dao.user.UserParagraphDao;
import com.example.vientianetest.dao.user.UserSubjectDao;
import com.example.vientianetest.dao.subject.SubjectDao;
import com.example.vientianetest.dao.subject.SubjectOptionDao;
import com.example.vientianetest.dao.subject.SubjectTypeDao;
import com.example.vientianetest.domain.Param;
import com.example.vientianetest.domain.Subject;
import com.example.vientianetest.domain.SubjectOption;
import com.example.vientianetest.redis.SessionRedis;
import com.example.vientianetest.service.SubjectRatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserSubjectService {
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private UserSubjectDao userSubjectDao;
    @Autowired
    private SubjectTypeDao subjectTypeDao;
    @Autowired
    private SubjectOptionDao subjectOptionDao;
    @Autowired
    private UserParagraphDao userParagraphDao;
    @Autowired
    private UserDayTaskService userDayTaskService;
    @Autowired
    private SubjectRatioService subjectRatioService;
    @Autowired
    private UserBookService userBookService;

    public String getSubjectNum(HttpServletRequest request){
        HttpSession session = request.getSession();
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        BasicResultObject obj = new BasicResultObject();
        obj.Result = userSubjectDao.getSubjectNumByUserId(userId);
        return JSONObject.toJSONString(obj);
    }

    public String getSubjectById(HttpServletRequest request,int subjectId){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = userSubjectDao.getSubjectById(subjectId);
        return JSONObject.toJSONString(obj);
    }

    public String getSubjectList(HttpServletRequest request,int pageIndex,int pageSize){
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        BasicResultObject obj = new BasicResultObject();
        String columnsStr = "b.id,b.`promptWord` AS title, IFNULL(c.`parentType`,0) AS parentType,CASE WHEN b.`status` = 3 THEN 2 ELSE b.`status` END AS `status`,a.rejectRemark ";
        String innerStr = "a INNER JOIN wxdk_subject b ON a.`subjectId` = b.`id` LEFT JOIN `wxdk_subjecttype` c ON b.`typeId` = c.`id` ";
        String whereStr = "where a.userId = "+userId+" AND b.flag < 3 ";
        String orderByStr = "order by a.id";
        obj.Result = userSubjectDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,pageIndex * pageSize,pageSize);
        obj.TotalCount = userSubjectDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }

    public String getSubjectListByStatus(HttpServletRequest request,int status,int pageIndex,int pageSize){
        HttpSession session = request.getSession();
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        BasicResultObject obj = new BasicResultObject();
        String columnsStr = "b.id,b.`promptWord` AS title, IFNULL(c.`parentType`,0) AS parentType,CASE WHEN b.`status` = 3 THEN 2 ELSE b.`status` END AS `status`,a.rejectRemark, ";
               columnsStr += "IFNULL(FORMAT(d.realRightNum/d.realTotalNum*100,0),0) AS rightRatio,d.realTotalNum AS replyNum,a.status as isReceive ";
        String innerStr = "a INNER JOIN wxdk_subject b ON a.`subjectId` = b.`id` LEFT JOIN `wxdk_subjecttype` c ON b.`typeId` = c.`id` ";
               innerStr += "LEFT JOIN `wxdk_subjectratio` d ON a.subjectId = d.subjectId ";
        String whereStr = "WHERE a.userId = "+userId+" AND CASE WHEN b.`status` = 3 THEN 2 ELSE b.`status` END = "+status+" AND b.flag < 3 ";
        String orderByStr = "ORDER BY a.id DESC ";
        obj.Result = userSubjectDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,pageIndex * pageSize,pageSize);
        obj.TotalCount = userSubjectDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }

    public String addSubject(HttpServletRequest request,
                             int parentType,
                             int answer,
                             String title,
                             String option,
                             String description){
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        BasicResultObject obj = new BasicResultObject();
        if(userSubjectDao.isTodayOverByUserId(userId)){
            obj.Tag = -2;
            obj.Message = "您今天你已经达到了出题上限！备注:一天最多出"+Param.GLOBAL_MAX_ADD_SUBJECTNUM +"题";
            return JSONObject.toJSONString(obj);
        }

        if(userSubjectDao.isExistsByTitle(userId,title)){
            obj.Tag = -1;
            obj.Message = "该题目已存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }

        /*if(Integer.parseInt(userParagraphDao.getInfoByUserId(userId).get("paragraph").toString()) != 41){
            obj.Tag = -3;
            obj.Message = "该用户没有权限，不能添加！";
            return JSONObject.toJSONString(obj);
        }*/
        //父类类型(1:德,2:商,3:文,4:理,5:艺,6:体,7:综)
        int typeId = 0;
        if(parentType == 2){//商
            typeId = Integer.parseInt(subjectTypeDao.getSubjectTypeByName(2,"管理").get("id").toString());
        } else if(parentType == 3){//文
            typeId = Integer.parseInt(subjectTypeDao.getSubjectTypeByName(3,"文学").get("id").toString());
        }else if(parentType == 4){//理
            typeId = Integer.parseInt(subjectTypeDao.getSubjectTypeByName(4,"数学").get("id").toString());
        }else if(parentType == 5){//艺
            typeId = Integer.parseInt(subjectTypeDao.getSubjectTypeByName(5,"综合艺术").get("id").toString());
        }else if(parentType == 6){//体
            typeId = Integer.parseInt(subjectTypeDao.getSubjectTypeByName(6,"健康").get("id").toString());
        }else if(parentType == 7){//综
            typeId = Integer.parseInt(subjectTypeDao.getSubjectTypeByName(7,"待定").get("id").toString());
        }
        String answerStr = option.split("%#@")[answer-1];
        String[] optionArr = option.split("%#@");
        int maxSortId = userSubjectDao.getMaxSortId()+1;
        Subject subject = new Subject(0,title,answerStr,description,3,answer,1,typeId,parentType,maxSortId,basicUserInfo.getNickName(),basicUserInfo.getAvatarUrl());
        //插入题目
        int subjectId = userSubjectDao.insert(subject);
        //循环选项插入
        for (int i = 0; i < optionArr.length; i++) {
            String optionName = optionArr[i];
            subjectOptionDao.insert(new SubjectOption(0,subjectId,optionName,i+1));
        }
        userSubjectDao.insert(userId,subjectId);
        /*
         *修改初始化的正确率
         * 困难 1:0%~32% 24/30
         * 一般 2:33%~66% 15/30
         * 简单 3:67%~100% 6/30
         * */
        int levelType = 3;//默认困难44

        int totalNum = 300;
        int rightNum = 240;
        int errorNUm = 60;
        if(levelType == 2){
            rightNum = 150;
            errorNUm = 150;
        }else if(levelType == 3){
            rightNum = 0;
            errorNUm = 300;
        }
        subjectRatioService.update(subjectId,totalNum,rightNum,errorNUm);
        userDayTaskService.insert(userId, 4, 1, 1);//每日任务
        return JSONObject.toJSONString(obj);
    }

    public String updateSubject(HttpServletRequest request,
                             int subjectId,
                             int parentType,
                             int answer,
                             String title,
                             String option,
                             String description){
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        BasicResultObject obj = new BasicResultObject();
        if(userSubjectDao.isExistsByTitle(subjectId,userId,title)){
            obj.Tag = -1;
            obj.Message = "该题目已存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }

        /*if(!userSubjectDao.isExistsByStatus(userId,2)){
            obj.Tag = -2;
            obj.Message = "该题目不是未通过状态，不能修改！";
            return JSONObject.toJSONString(obj);
        }*/

        String answerStr = option.split("%#@")[answer-1];
        String[] optionArr = option.split("%#@");
        int maxSortId = userSubjectDao.getMaxSortId()+1;
        Subject subject = new Subject(subjectId,title,answerStr,description,3,answer,1,0,parentType,maxSortId,basicUserInfo.getNickName(),basicUserInfo.getAvatarUrl());
        //插入题目
        userSubjectDao.update(subject);
        subjectOptionDao.delete(subjectId); //先删除
        //循环选项插入
        for (int i = 0; i < optionArr.length; i++) {
            String optionName = optionArr[i];
            subjectOptionDao.insert(new SubjectOption(0,subjectId,optionName,i+1));
        }

        return JSONObject.toJSONString(obj);
    }

    //领取奖励
    public String receiveGood(HttpServletRequest request,int subjectId){
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(request.getCookies());
        int userId = basicUserInfo.getUserId();
        BasicResultObject obj = new BasicResultObject();
        Map<String, Integer> userBookMap = new HashMap<>();
        if(userSubjectDao.isExistBySubjectId(userId,subjectId,0)) {
            List<Integer> bookTypeList = new ArrayList<Integer>(){{add(2);add(3);add(4);add(5);add(6);add(7);}};
            int bookType = bookTypeList.get(Rand.nextInt(bookTypeList.size()));
            userBookMap.put(String.valueOf(bookType), 1);
            userBookService.insert(userId,bookType,1);
            userSubjectDao.updateStatus(userId,subjectId,1);
        }
        obj.Result = userBookMap;
        return JSONObject.toJSONString(obj);
    }
}
