package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.WeekTitleDao;
import io.renren.modules.sys.domain.BasicResultObject;
import io.renren.modules.sys.domain.BasicTagState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeekTitleService {
    @Autowired
    private WeekTitleDao weekTitleDao;

    public String add(String bigName,String smallName,String image,int status,int sortId){
        BasicResultObject obj = new BasicResultObject();
        if(weekTitleDao.isExistsByName(smallName)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该小标题已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        weekTitleDao.insert(bigName,smallName,image,status,sortId);
        return JSONObject.toJSONString(obj);
    }

    public String update(int id,String bigName,String smallName,String image,int status,int sortId){
        BasicResultObject obj = new BasicResultObject();
        if(weekTitleDao.isExistsByName(id,smallName)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该小标题已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        weekTitleDao.update(id,bigName,smallName,image,status,sortId);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String delete(int id){
        weekTitleDao.delete(id);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String getWeekTitleById(int id){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = weekTitleDao.getWeekTitleById(id);
        return JSONObject.toJSONString(obj);
    }

    public String getPageList(int pageIndex , int pageSize , String keyWords){
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr = keyWords.length()>0 ? " smallName like '%"+keyWords+"%' " : " 1=1 ";
        String columnsStr = "`id`,`bigName`,`smallName`,`image`,`status`,`sortId` ";
        String innerStr = "";
        String whereStr = "where flag < 3 and "+tempWhereStr;
        String orderByStr = "order by id";
        obj.Result = weekTitleDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = weekTitleDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }
}
