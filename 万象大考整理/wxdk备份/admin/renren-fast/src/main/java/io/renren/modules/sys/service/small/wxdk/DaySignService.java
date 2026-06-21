package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.DaySignDao;
import io.renren.modules.sys.domain.BasicResultObject;
import io.renren.modules.sys.domain.BasicTagState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DaySignService {
    @Autowired
    private DaySignDao daySignDao;

    public String add(int day,int goodType,int num , int isLookNum,String description){
        BasicResultObject obj = new BasicResultObject();
        if(daySignDao.isExistsByDay(day)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该天已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        daySignDao.insert(day,goodType,num,isLookNum,description);
        return JSONObject.toJSONString(obj);
    }

    public String update(int id,int day,int goodType,int num , int isLookNum,String description){
        BasicResultObject obj = new BasicResultObject();
        if(daySignDao.isExistsByDay(id,day)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该天已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        daySignDao.update(id,day,goodType,num,isLookNum,description);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String delete(int id){
        daySignDao.delete(id);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String getDaySignById(int id){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = daySignDao.getDaySignById(id);
        return JSONObject.toJSONString(obj);
    }

    public String getPageList(int pageIndex , int pageSize , String keyWords){
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr = " 1=1 ";
        String columnsStr = " a.`id`,a.day,a.`goodType`,a.`num`,a.`isLookNum`,a.`modifyTime`,a.`createTime`,a.`flag`,b.name,b.`image`,a.description ";
        String innerStr = " a LEFT JOIN wxdk_goods b ON a.`goodType` = b.`type` ";
        String whereStr = "where a.flag < 3 and "+tempWhereStr;
        String orderByStr = "order by a.day ";
        obj.Result = daySignDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = daySignDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }
}
