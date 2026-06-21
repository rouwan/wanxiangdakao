package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.SubjectDao;
import io.renren.modules.sys.dao.small.wxdk.SubjectOptionDao;
import io.renren.modules.sys.dao.small.wxdk.WeekSubjectDao;
import io.renren.modules.sys.dao.small.wxdk.WeekTitleDao;
import io.renren.modules.sys.domain.BasicResultObject;
import io.renren.modules.sys.domain.BasicTagState;
import io.renren.modules.sys.domain.small.wxdk.Subject;
import io.renren.modules.sys.domain.small.wxdk.SubjectOption;
import io.renren.modules.sys.domain.small.wxdk.WeekSubject;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeekSubjectService {
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private WeekSubjectDao weekSubjectDao;
    @Autowired
    private SubjectOptionDao subjectOptionDao;
    @Autowired
    private SubjectRatioService subjectRatioService;

    public String getPageList(int pageIndex , int pageSize , String keyWords,int typeId,int type , int status, int modeType){
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr = keyWords.length() > 0 ? " (a.promptWord like '%"+keyWords+"%' OR a.answer like '%"+keyWords+"%') " : " 1=1 ";
        String tempWhereStr2 = typeId > 0 ? " b.parentType="+typeId  : " 1=1 ";
        String tempWhereStr3 = type > 0 ? " a.type="+type  : " 1=1 ";
        String tempWhereStr4 = status >= 0 ? " a.status="+status  : " 1=1 ";
        String tempWhereStr5 = modeType > 0 ? " a.modeType="+modeType  : " 1=1 ";
        String columnsStr = "a.`id`,a.`image`,a.`audio`,a.`promptWord`, a.`answer`,a.`description`,a.`optionSortId`,a.`typeId`,b.`name` AS typeName,b.parentType,a.`type`,c.`sortId`,a.`status`,a.`modeType`,a.`createUserName`,c.id as weekSubjectId ";
        columnsStr += ",IFNULL((SELECT FORMAT(rightNum/totalNum,2) FROM `wxdk_subjectratio` WHERE subjectId = a.id ),0.00) AS rightRatio,d.smallName ";
        String whereStr = " a.flag < 3 and " + tempWhereStr+" and "+tempWhereStr2+" and "+tempWhereStr3+" and "+tempWhereStr4+" and "+tempWhereStr5;
        String innerStr = " INNER JOIN `wxdk_weeksubject` c ON a.id = c.`subjectId` INNER JOIN `wxdk_weektitle` d ON c.`titleId`=d.`id` LEFT JOIN `wxdk_subjecttype` b ON a.`typeId` = b.`id`";
        String innerLimitStr = " INNER JOIN ( SELECT a.id FROM  wxdk_subject a INNER JOIN `wxdk_weeksubject` c ON a.id = c.`subjectId` INNER JOIN `wxdk_weektitle` d ON c.`titleId`=d.`id` LEFT JOIN `wxdk_subjecttype` b ON a.`typeId` = b.`id` WHERE  a.flag < 3 AND "+whereStr+"  ORDER BY a.id  LIMIT "+(pageIndex-1) * pageSize+","+pageSize+") f ON  a.id =f.id";
        String orderByStr = "";
        obj.Result = subjectDao.getPageList(columnsStr,innerLimitStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = subjectDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }

    public String add(int answer,String image,String audioName,String promptWord,String optionStr,int typeId,int type,int modeType, int sortId,String description,String createUserName,int titleId,int isLevelType,int levelType){
        BasicResultObject obj = new BasicResultObject();
        if(subjectDao.isExistsByTitle(promptWord) && modeType != 1){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该题目已存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        String answerStr = optionStr.split("%#@")[answer-1].split("@#")[1];
        String[] optionArr = optionStr.split("%#@");
        int maxSortId = subjectDao.getMaxSortId()+1;
        Subject subject = new Subject(0,image,audioName,promptWord,answerStr,description,answer,typeId,type,maxSortId,0,modeType,createUserName,null);
        //插入题目
        int subjectId = subjectDao.insert(subject);
        //循环选项插入
        for (int i = 0; i < optionArr.length; i++) {
            String[] optionContentArr = optionArr[i].split("@#");
            int optionId = Integer.parseInt(optionContentArr[0]);
            String optionName = optionContentArr[1];
            subjectOptionDao.insert(new SubjectOption(optionId,subjectId,optionName,i+1));
        }
        int maxSortId2 = weekSubjectDao.getMaxSortId()+1;
        weekSubjectDao.insert(new WeekSubject(0,subjectId,titleId,maxSortId2));
        /*
         *修改初始化的正确率
         * 困难 1:0%~32% 24/30
         * 一般 2:33%~66% 15/30
         * 简单 3:67%~100% 6/30
         * */
        if(isLevelType > 0){
            int rightNum = 24;
            int totalNum = 30;
            int errorNUm = 6;
            if(levelType == 2){
                rightNum = 15;
                errorNUm = 15;
            }else if(levelType == 3){
                rightNum = 18;
                errorNUm = 72;
                totalNum = 90;
            }
            subjectRatioService.update(subjectId,totalNum,rightNum,errorNUm);
        }
        return JSONObject.toJSONString(obj);
    }

    public String update(int id , int answer,String image,String audioName,String promptWord,String optionStr,int typeId,int type,int modeType, int sortId,String description,String createUserName,int titleId,int isLevelType,int levelType){
        BasicResultObject obj = new BasicResultObject();
        if(subjectDao.isExistsByTitle(id,promptWord) && modeType != 1){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该题目已存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        String answerStr = optionStr.split("%#@")[answer-1].split("@#")[1];
        String[] optionArr = optionStr.split("%#@");
        Subject subject = new Subject(id,image,audioName,promptWord,answerStr,description,answer,typeId,type,0,0,modeType,createUserName,null);
        subjectDao.update(subject);
        subjectOptionDao.delete(id); //先删除
        //循环选项插入
        for (int i = 0; i < optionArr.length; i++) {
            String[] optionContentArr = optionArr[i].split("@#");
            int optionId = Integer.parseInt(optionContentArr[0]);
            String optionName = optionContentArr[1];
            subjectOptionDao.insert(new SubjectOption(optionId,id,optionName,i+1));
        }
        weekSubjectDao.update(new WeekSubject(0,id,titleId,0));
        /*
         *修改初始化的正确率
         * 困难 1:0%~32% 24/30
         * 一般 2:33%~66% 15/30
         * 简单 3:67%~100% 6/30
         * */
        if(isLevelType > 0){
            int rightNum = 24;
            int totalNum = 30;
            int errorNUm = 6;
            if(levelType == 2){
                rightNum = 15;
                errorNUm = 15;
            }else if(levelType == 3){
                rightNum = 18;
                errorNUm = 72;
                totalNum = 90;
            }
            subjectRatioService.update(id,totalNum,rightNum,errorNUm);
        }
        return JSONObject.toJSONString(obj);
    }

    public String getSubjectById(int id){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = weekSubjectDao.getWeekSubjectBySubjectId(id);
        obj.Result2 = subjectOptionDao.getSubjectOptionBySubjectId(id);
        return JSONObject.toJSONString(obj);
    }

    public String delete(String ids){
        String[] arrId = ids.split(";");
        for(int i = 0;i<arrId.length;i++){
            int id = Integer.parseInt(arrId[i].split(",")[0]);
            int weekSubjectId = Integer.parseInt(arrId[i].split(",")[1]);
            subjectDao.delete(id);
            subjectDao.updateAfterSortIdById(id); //排序向上推
            weekSubjectDao.updateAfterSortIdById(weekSubjectId);
            weekSubjectDao.delete(weekSubjectId);
        }
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String delete2(String ids){
        String[] arrId = ids.split(",");
        for(int i = 0;i<arrId.length;i++){
            int id = Integer.parseInt(arrId[i]);
            weekSubjectDao.updateAfterSortIdById(id); //排序向上推
            weekSubjectDao.delete(id);
        }
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String updateStatus(String ids,int status){
        String[] idArr = ids.split(",");
        for(int i =0 ;i<idArr.length;i++){
            subjectDao.updateStatus(Integer.parseInt(idArr[i]),status);
        }
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String saveSort(String ids , int pageIndex,int pageSize){
        int len = ids.split(";").length;
        int startSortId = (pageIndex-1) * pageSize;
        for(int i = 0 ;i< len; i++){
            int id = Integer.parseInt(ids.split(";")[i].toString());
            subjectDao.updateSortIdById(id,startSortId+i+1);
        }
        return JSONObject.toJSONString(new BasicResultObject());
    }
}
