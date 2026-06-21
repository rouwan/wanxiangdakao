package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.SubjectDao;
import io.renren.modules.sys.dao.small.wxdk.SubjectOptionDao;
import io.renren.modules.sys.dao.small.wxdk.UserSubjectDao;
import io.renren.modules.sys.domain.BasicResultObject;
import io.renren.modules.sys.domain.BasicTagState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserSubjectService {
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private UserSubjectDao userSubjectDao;
    @Autowired
    private SubjectOptionDao subjectOptionDao;

    public void insert(int userId,int subjectId) {
        if(!userSubjectDao.isExistsBySubjectId(subjectId)){
            userSubjectDao.insert(userId,subjectId);
        }
    }

    public String getPageList(String userName,int pageIndex , int pageSize , String keyWords,int typeId,int status, int diffType) {
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr = keyWords.length() > 0 ? " (a.promptWord like '%" + keyWords + "%' OR a.answer like '%" + keyWords + "%' OR e.nickname like '%" + keyWords + "%') " : " 1=1 ";
        String tempWhereStr2 = typeId > 0 ? " b.parentType=" + typeId : " 1=1 ";
        String tempWhereStr3 = status >= 0 ? " a.status=" + status : " 1=1 ";
        /*题目审核人员做个限制*/
        if (userName.equals("subjectUser")) {
            tempWhereStr3 = " a.status in (0,2,3) ";
            if (status != 1 && status >= 0) {
                tempWhereStr3 = " a.status=" + status;
            }
        }
        String tempWhereStr4 = " 1=1 ";
        if (diffType == 1) {
            tempWhereStr4 = " IFNULL((c.`rightNum`/c.totalNum)*100,0)>=0 AND IFNULL((c.`rightNum`/c.totalNum)*100,0)<33 ";
        } else if (diffType == 2) {
            tempWhereStr4 = " IFNULL((c.`rightNum`/c.totalNum)*100,0)>=33 AND IFNULL((c.`rightNum`/c.totalNum)*100,0)<=66 ";
        } else if (diffType == 3) {
            tempWhereStr4 = " IFNULL((c.`rightNum`/c.totalNum)*100,0)>66 ";
        }
        String columnsStr = "d.id , a.`id` AS subjectId,a.`promptWord`, a.`answer`,a.`description`,a.`optionSortId`,a.`typeId`,b.`name` AS typeName,b.parentType,a.`type`,a.`sortId`,a.`status`,a.`modeType`,e.nickName AS createUserName , e.`headImgUrl` AS createUserUrl ,DATE_FORMAT(d.createTime,'%Y-%m-%d %H:%i:%S') as createTime,d.rejectRemark,";
        columnsStr += "CASE WHEN IFNULL((`rightNum`/totalNum)*100,0)>=0 AND IFNULL((`rightNum`/totalNum)*100,0)<33 THEN 1 WHEN IFNULL((`rightNum`/totalNum)*100,0)>=33 AND IFNULL((`rightNum`/totalNum)*100,0)<=66 THEN 2 WHEN IFNULL((`rightNum`/totalNum)*100,0)>66 THEN 3 END AS diffType ";
        String whereStr = " a.flag < 3 and " + tempWhereStr + " and " + tempWhereStr2 + " and " + tempWhereStr3 + " and " + tempWhereStr4;
        String innerStr = " INNER JOIN `wxdk_subject` a ON d.subjectId = a.id LEFT JOIN `wxdk_subjecttype` b ON a.`typeId` = b.`id` LEFT JOIN `wxdk_subjectratio` c ON a.id = c.`subjectId` LEFT JOIN wxdk_user e ON d.userId = e.id ";
        String innerLimitStr = " INNER JOIN ( SELECT a.id FROM wxdk_usersubject d INNER JOIN wxdk_subject a ON d.subjectId = a.id INNER JOIN wxdk_user e on d.userId = e.id LEFT JOIN `wxdk_subjecttype` b ON a.`typeId` = b.`id` LEFT JOIN `wxdk_subjectratio` c ON a.id = c.`subjectId` WHERE  a.flag < 3 AND " + whereStr + "  ORDER BY d.id  LIMIT " + (pageIndex - 1) * pageSize + "," + pageSize + ") f ON  d.subjectId =f.id";
        String orderByStr = "";
        obj.Result = userSubjectDao.getPageList(columnsStr, innerLimitStr, innerStr, whereStr, orderByStr, (pageIndex - 1) * pageSize, pageSize);
        obj.TotalCount = userSubjectDao.getPageTotalCount(innerStr, whereStr);
        return JSONObject.toJSONString(obj);
    }

    public String getSubjectById(int id){
        BasicResultObject obj = new BasicResultObject();
        Map<String,Object> map = userSubjectDao.getSubjectById(id);
        obj.Result = map;
        obj.Result2 = subjectOptionDao.getSubjectOptionBySubjectId(Integer.parseInt(map.get("id").toString()));
        return JSONObject.toJSONString(obj);
    }

    public String getNextSubjectById(int id){
        BasicResultObject obj = new BasicResultObject();
        List<Map<String,Object>> mapList = userSubjectDao.getNextSubjectById(id);
        if(mapList.size() > 0){
            int subjectId = Integer.parseInt(mapList.get(0).get("id").toString());
            obj.Result = mapList.get(0);
            obj.Result2 = subjectOptionDao.getSubjectOptionBySubjectId(subjectId);
        }else{
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该序号不存在！";
        }
        return JSONObject.toJSONString(obj);
    }

    public String getPreSubjectById(int id){
        BasicResultObject obj = new BasicResultObject();
        List<Map<String,Object>> mapList = userSubjectDao.getPreSubjectById(id);
        if(mapList.size() > 0){
            int subjectId = Integer.parseInt(mapList.get(0).get("id").toString());
            obj.Result = mapList.get(0);
            obj.Result2 = subjectOptionDao.getSubjectOptionBySubjectId(subjectId);
        }else{
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该序号不存在！";
        }
        return JSONObject.toJSONString(obj);
    }

    public String updateRejectRemark(String ids,String rejectRemark){
        String[] idArr = ids.split(";");
        for(int i=0;i<idArr.length;i++){
            int id = Integer.parseInt(idArr[i].split(",")[0]);
            int subjectId = Integer.parseInt(idArr[i].split(",")[1]);
            userSubjectDao.updateRejectRemark(id,rejectRemark);
            subjectDao.updateStatus(subjectId,2);
        }
        return JSONObject.toJSONString(new BasicResultObject());
    }
}
