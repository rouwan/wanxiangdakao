package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.ClearDataTimeDao;
import io.renren.modules.sys.domain.BasicResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClearDataTimeService {
    @Autowired
    private ClearDataTimeDao clearDataTimeDao;

    public String add(String clearTime,int type ){
        BasicResultObject obj = new BasicResultObject();
        if(clearDataTimeDao.isExistsByType(type)) {
            obj.Tag = -1;
            obj.Message = "你选择的类型已经再列表，不能重复添加！";
            return JSONObject.toJSONString(obj);
        }
        obj.Result = clearDataTimeDao.insert(clearTime,type);
        return JSONObject.toJSONString(obj);
    }

    public String update(int id , String clearTime,int type){
        BasicResultObject obj = new BasicResultObject();
        if(clearDataTimeDao.isExistsByType(id,type)) {
            obj.Tag = -1;
            obj.Message = "你选择的类型已经再列表，不能重复添加！";
            return JSONObject.toJSONString(obj);
        }
        clearDataTimeDao.update(id,clearTime,type);
        return JSONObject.toJSONString(obj);
    }

    public String delete(String ids){
        String[] arr = ids.split(",");
        for(int i =0;i<arr.length; i ++){
            int id = Integer.parseInt(arr[i]);
            clearDataTimeDao.delete(id);
        }
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String getClearDataTimeById(int id){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = clearDataTimeDao.getClearDataTimeById(id);
        return JSONObject.toJSONString(obj);
    }

    public String getPageList(int pageIndex , int pageSize , String keyWords){
        BasicResultObject obj = new BasicResultObject();
        String columnsStr = "`id`,`type`,date_format(clearTime,'%Y-%m-%d') as clearTime ";
        String innerStr = "";
        String whereStr = "";
        String orderByStr = "order by id";
        obj.Result = clearDataTimeDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = clearDataTimeDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }
}

