package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.SubjectDao;
import io.renren.modules.sys.dao.small.wxdk.SubjectOptionDao;
import io.renren.modules.sys.dao.small.wxdk.WeekSubjectDao;
import io.renren.modules.sys.domain.BasicResultObject;
import io.renren.modules.sys.domain.BasicTagState;
import io.renren.modules.sys.domain.small.wxdk.Subject;
import io.renren.modules.sys.domain.small.wxdk.SubjectOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class SubjectService {
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private WeekSubjectDao weekSubjectDao;
    @Autowired
    private SubjectOptionDao subjectOptionDao;
    @Autowired
    private SubjectRatioService subjectRatioService;


    public String getPageList(int pageIndex , int pageSize , String keyWords,int typeId,int type , int status, int modeType ,int diffType){
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr = keyWords.length() > 0 ? " (a.promptWord like '%"+keyWords+"%' OR a.answer like '%"+keyWords+"%' OR a.createUserName like '%"+keyWords+"%') " : " 1=1 ";
        String tempWhereStr2 = typeId > 0 ? " b.parentType="+typeId  : " 1=1 ";
        String tempWhereStr3 = type > 0 ? " a.type="+type  : " 1=1 ";
        String tempWhereStr4 = status >= 0 ? " a.status="+status  : " 1=1 ";
        String tempWhereStr5 = modeType > 0 ? " a.modeType="+modeType  : " 1=1 ";
        String tempWhereStr6 = " 1=1 ";
        //0-20%、20-40%、40-60%、60-80%、80-100%
        if(diffType == 1){
            tempWhereStr6 = " IFNULL((c.`realRightNum`/c.realTotalNum)*100,0)>=0 AND IFNULL((c.`realRightNum`/c.realTotalNum)*100,0)<20 ";
        }else if(diffType == 2){
            tempWhereStr6 = " IFNULL((c.`realRightNum`/c.realTotalNum)*100,0)>=20 AND IFNULL((c.`realRightNum`/c.realTotalNum)*100,0)<40 ";
        }else if(diffType == 3){
            tempWhereStr6 = " IFNULL((c.`realRightNum`/c.realTotalNum)*100,0)>=40 AND IFNULL((c.`realRightNum`/c.realTotalNum)*100,0)<60 ";
        }else if(diffType == 4){
            tempWhereStr6 = " IFNULL((c.`realRightNum`/c.realTotalNum)*100,0)>=60 AND IFNULL((c.`realRightNum`/c.realTotalNum)*100,0)<80 ";
        }else if(diffType == 5){
            tempWhereStr6 = " IFNULL((c.`realRightNum`/c.realTotalNum)*100,0)>=80 AND IFNULL((c.`realRightNum`/c.realTotalNum)*100,0)<=100 ";
        }
        String columnsStr = "a.`id`,a.`image`,a.`audio`,a.`promptWord`, a.`answer`,a.`description`,a.`optionSortId`,a.`typeId`,b.`name` AS typeName,b.parentType,a.`type`,a.`sortId`,a.`status`,a.`modeType`,a.`createUserName`,realTotalNum ";
               columnsStr += ",IFNULL(FORMAT((`realRightNum`/realTotalNum)*100,0),0) AS diffType ";
               columnsStr += ",CASE WHEN IFNULL(d.id,0) = 0 THEN 0 ELSE 1 END isUser ";
        String whereStr = " a.flag < 3 and "+tempWhereStr+" and "+tempWhereStr2+" and "+tempWhereStr3+" and "+tempWhereStr4+" and "+tempWhereStr5 + " and "+tempWhereStr6;
        String innerStr = " LEFT JOIN `wxdk_subjecttype` b ON a.`typeId` = b.`id` LEFT JOIN `wxdk_subjectratio` c ON a.id = c.`subjectId` LEFT JOIN `wxdk_usersubject` d ON a.id = d.subjectId LEFT JOIN wxdk_user e ON d.userId = e.id ";
        String innerLimitStr = " INNER JOIN (SELECT a.id FROM wxdk_subject a LEFT JOIN `wxdk_subjecttype` b ON a.`typeId` = b.`id` LEFT JOIN `wxdk_subjectratio` c ON a.id = c.`subjectId` WHERE  a.flag < 3 AND "+whereStr+"  ORDER BY a.id  LIMIT "+(pageIndex-1) * pageSize+","+pageSize+") f ON  a.id =f.id";
        String innerWhereStr = "";
        if(typeId > 0) {
            innerWhereStr += " LEFT JOIN `wxdk_subjecttype` b ON a.`typeId` = b.`id` ";
        }
        if(diffType > 0) {
            innerWhereStr += " LEFT JOIN `wxdk_subjectratio` c ON a.id = c.`subjectId` ";
        }
        // String orderByStr = "order by sortId";
        String orderByStr = "";
        obj.Result = subjectDao.getPageList(columnsStr,innerLimitStr ,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = subjectDao.getPageTotalCount(innerWhereStr,whereStr);
        return JSONObject.toJSONString(obj);
    }

    public String add(int answer,String image,String audioName,String promptWord,String optionStr,int typeId,int type,int modeType, int sortId,String description,String createUserName,int isLevelType,int levelType){
        BasicResultObject obj = new BasicResultObject();
        if(subjectDao.isExistsByTitle(promptWord) && modeType != 1){ // 图片很多会重复题目
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
        /*
         *修改初始化的正确率
         * 困难 1:0%~32% 24/30
         * 一般 2:33%~66% 15/30
         * 简单 3:67%~100% 6/30
         * */
        if(isLevelType > 0){
            int rightNum = 240;
            int totalNum = 300;
            int errorNUm = 60;
            if(levelType == 2){
                rightNum = 150;
                errorNUm = 150;
            }else if(levelType == 3){
                rightNum = 0;
                errorNUm = 300;
                totalNum = 300;
            }
            subjectRatioService.update(subjectId,totalNum,rightNum,errorNUm);
        }
        return JSONObject.toJSONString(obj);
     }

    public String updateSubjectById(int id , int answer,String image,String audioName,String promptWord,String optionStr,int typeId,int type,int modeType, int sortId,String description,String createUserName,int isLevelType,int levelType){
        BasicResultObject obj = new BasicResultObject();
        if(subjectDao.isExistsByTitle(id,promptWord) && modeType != 1){ // 图片很多会重复题目
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
        /*
        *修改初始化的正确率
        * 困难 1:0%~32% 24/30
        * 一般 2:33%~66% 15/30
        * 简单 3:67%~100% 6/30
        * */
        if(isLevelType > 0){
            int rightNum = 240;
            int totalNum = 300;
            int errorNUm = 60;
            if(levelType == 2){
                rightNum = 150;
                errorNUm = 150;
            }else if(levelType == 3){
                rightNum = 0;
                errorNUm = 300;
                totalNum = 300;
            }
            subjectRatioService.update(id,totalNum,rightNum,errorNUm);
        }
        return JSONObject.toJSONString(obj);
    }

    public String updateSubjectTypeById(String ids,int typeId,int isLevelType,int levelType){
        String[] arrId = ids.split(";");
        for(int i = 0;i<arrId.length;i++){
            int id = Integer.parseInt(arrId[i]);
            subjectDao.updateTypeId(id,typeId);
            /*
             *修改初始化的正确率
             * 困难 1:0%~32% 24/30
             * 一般 2:33%~66% 15/30
             * 简单 3:67%~100% 6/30
             * */
            if(isLevelType > 0){
                int rightNum = 240;
                int totalNum = 300;
                int errorNUm = 60;
                if(isLevelType == 2){
                    rightNum = 150;
                    errorNUm = 150;
                }else if(isLevelType == 3){
                    rightNum = 0;
                    errorNUm = 300;
                    totalNum = 300;
                }
                subjectRatioService.update(id,totalNum,rightNum,errorNUm);
            }
        }
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String getSubjectById(int id){
         BasicResultObject obj = new BasicResultObject();
         obj.Result = subjectDao.getSubjectById(id);
         obj.Result2 = subjectOptionDao.getSubjectOptionBySubjectId(id);
         return JSONObject.toJSONString(obj);
    }

    public String getSubjectBySortId(int sortId){
        BasicResultObject obj = new BasicResultObject();
        if(subjectDao.isExistsBySortId(sortId)){
            Map<String,Object> map = subjectDao.getSubjectBySortId(sortId);
            obj.Result = map;
            obj.Result2 = subjectOptionDao.getSubjectOptionBySubjectId(Integer.parseInt(map.get("id").toString()));
        }else{
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该序号不存在！";
        }

        return JSONObject.toJSONString(obj);
    }

    public String delete(String ids){
        String[] arrId = ids.split(";");
        for(int i = 0;i<arrId.length;i++){
            int id = Integer.parseInt(arrId[i]);
            subjectDao.delete(id);
            //subjectDao.updateAfterSortIdById(id); //排序向上推
            //if(weekSubjectDao.isExistsBySubjectId(id)){
                //int weekSubjectId = Integer.parseInt(weekSubjectDao.getWeekSubjectBySubjectId(id).get("weekSubjectId").toString());
                //weekSubjectDao.updateAfterSortIdById(weekSubjectId);
                //weekSubjectDao.delete(weekSubjectId);
            //}
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

    public String getModeTypeNumList(){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = subjectDao.getModeTypeNumList();
        return JSONObject.toJSONString(obj);
    }
    public String batchUpdateStatus(int modeType, int num){
        subjectDao.batchUpdateStatus(modeType,num);
        return JSONObject.toJSONString(new BasicResultObject());
    }

}
