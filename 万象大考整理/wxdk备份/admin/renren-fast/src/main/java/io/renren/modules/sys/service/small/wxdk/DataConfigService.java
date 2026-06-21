package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.DataConfigDao;
import io.renren.modules.sys.domain.BasicResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("wxdk_DataConfigService")
public class DataConfigService {
    @Autowired
    private DataConfigDao dataConfigDao;

    public String add(String name ,String value,String description,int type ){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = dataConfigDao.insert(name,value,description,type);
        return JSONObject.toJSONString(obj);
    }

    public String update(int id , String name ,String value,String description,int type){
        dataConfigDao.update(id,name,value,description,type);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String delete(String ids){
        String[] arr = ids.split(",");
        for(int i =0;i<arr.length; i ++){
            int id = Integer.parseInt(arr[i]);
            dataConfigDao.delete(id);
        }
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String getDataConfigById(int id){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = dataConfigDao.getDataConfigById(id);
        return JSONObject.toJSONString(obj);
    }

    public String getPageList(int pageIndex , int pageSize , String keyWords){
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr = keyWords.length()>0 ? " name like '%"+keyWords+"%' " : " 1=1 ";
        String columnsStr = "`id`,`name`,`value`,`description`,`type`,DATE_FORMAT(modifyTime, '%Y-%m-%d %H:%i:%s') as modifyTime";
        String innerStr = "";
        String whereStr = "where flag < 3 and "+tempWhereStr;
        String orderByStr = "order by id";
        obj.Result = dataConfigDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = dataConfigDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }
}
