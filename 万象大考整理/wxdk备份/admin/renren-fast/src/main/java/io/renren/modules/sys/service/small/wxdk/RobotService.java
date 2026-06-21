package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.RobotDao;
import io.renren.modules.sys.dao.small.wxdk.RobotParagraphDao;
import io.renren.modules.sys.domain.BasicResultObject;
import io.renren.modules.sys.domain.BasicTagState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RobotService {
    @Autowired
    private RobotDao robotDao;
    @Autowired
    private RobotParagraphDao robotParagraphDao;

    public String add(String nickName,String sexDesc,String headImgUrl,int type,int status,String country,String province,String city,float simpleRatio,float commonRatio,float hardRatio,String simpleTime,String commonTime,String hardTime,int paragraph,int star){
        BasicResultObject obj = new BasicResultObject();
        if(robotDao.isExistsByName(nickName)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "改昵称已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        int userId = robotDao.insert(nickName,sexDesc,headImgUrl,type,status,country,province,city,simpleRatio,commonRatio,hardRatio,simpleTime,commonTime,hardTime);
        if(!robotParagraphDao.isExistsByUserId(-userId)){
            robotParagraphDao.insert(-userId,paragraph,star,0);
        }else{
            robotParagraphDao.update(-userId, paragraph, star);
        }

        return JSONObject.toJSONString(obj);
    }

    public String update(int id,String nickName,String sexDesc,String headImgUrl,int type,int status,String country,String province,String city,float simpleRatio,float commonRatio,float hardRatio,String simpleTime,String commonTime,String hardTime,int paragraph,int star){
        BasicResultObject obj = new BasicResultObject();
        if(robotDao.isExistsByName(id,nickName)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "改昵称已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        robotDao.update(id,nickName,sexDesc,headImgUrl,type,status,country,province,city,simpleRatio,commonRatio,hardRatio,simpleTime,commonTime,hardTime);
        if(!robotParagraphDao.isExistsByUserId(-id)){
            robotParagraphDao.insert(-id,paragraph,star,0);
        }else{
            robotParagraphDao.update(-id, paragraph, star);
        }
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String delete(int id){
        robotDao.delete(id);
        robotParagraphDao.delete(-id);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String getRobotById(int id){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = robotDao.getRobotById(id);
        return JSONObject.toJSONString(obj);
    }

    public String getPageList(int pageIndex , int pageSize ,int type, String keyWords){
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr = keyWords.length()>0 ? " a.nickname like '%"+keyWords+"%' " : " 1=1 ";
        String tempWhereStr2 = type>=0 ? " a.type="+type : " 1=1 ";
        String columnsStr = "a.`id`,a.province,a.city,a.`nickname`,a.`sexDesc`,a.`headImgUrl`,a.`type`,a.`status`,a.simpleRatio,a.commonRatio,a.hardRatio,a.simpleTime,a.commonTime,a.hardTime,ifnull(b.`paragraph`,1) as paragraph,ifnull(b.`star`,0) as star,c.name as paragraphName ";
        String innerStr = " a LEFT JOIN `wxdk_robotparagraph` b ON a.`id` = -b.`userId` LEFT JOIN wxdk_paragraph c ON ifnull(b.paragraph,1) = c.level ";
        String whereStr = "where a.flag < 3 and "+tempWhereStr + " and "+ tempWhereStr2;
        String orderByStr = "order by a.id";
        obj.Result = robotDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = robotDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }
}
