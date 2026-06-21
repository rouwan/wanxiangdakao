package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.DaySignDao;
import io.renren.modules.sys.dao.small.wxdk.LuckNumberDao;
import io.renren.modules.sys.domain.BasicResultObject;
import io.renren.modules.sys.domain.BasicTagState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LuckNumberService {
    @Autowired
    private LuckNumberDao luckNumberDao;

    public String add(int number,int goodType,int num , int isLookNum,String description){
        BasicResultObject obj = new BasicResultObject();
        if(luckNumberDao.isExistsByNumber(number)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该天已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        int sortId = luckNumberDao.getMaxSortId() + 1;
        luckNumberDao.insert(number,goodType,num,isLookNum,description,sortId);
        return JSONObject.toJSONString(obj);
    }

    public String update(int id,int number,int goodType,int num , int isLookNum,String description,int sortId){
        BasicResultObject obj = new BasicResultObject();
        if(luckNumberDao.isExistsByNumber(id,number)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该天已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        luckNumberDao.update(id,number,goodType,num,isLookNum,description,sortId);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String delete(int id){
        luckNumberDao.delete(id);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String getLuckNumberById(int id){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = luckNumberDao.getLuckNumberById(id);
        return JSONObject.toJSONString(obj);
    }

    public String getPageList(int pageIndex , int pageSize , String keyWords){
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr = " 1=1 ";
        String columnsStr = " a.`id`,a.`number`,a.`goodType`,a.`num`,a.`isLookNum`,a.`description`,a.`modifyTime`,a.`createTime`,a.`flag` ,IFNULL(b.`image`,'') AS image,b.name,a.sortId ";
        String innerStr = " a LEFT JOIN wxdk_goods b ON a.`goodType` = b.`type` ";
        String whereStr = "where a.flag < 3 and "+tempWhereStr;
        String orderByStr = "order by a.sortId ";
        obj.Result = luckNumberDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = luckNumberDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }
}
