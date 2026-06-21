package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.PrizeRatioDao;
import io.renren.modules.sys.domain.BasicResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrizeRatioService {
    @Autowired
    private PrizeRatioDao prizeRatioDao;

    public String add(int minValue,int maxValue,int num , int goodType,String remark){
        BasicResultObject obj = new BasicResultObject();
        int sortId = prizeRatioDao.getMaxSortId() + 1;
        prizeRatioDao.insert(minValue,maxValue,num,goodType,remark,sortId);
        return JSONObject.toJSONString(obj);
    }

    public String update(int id,int minValue,int maxValue,int num , int goodType,String remark){
        BasicResultObject obj = new BasicResultObject();
        prizeRatioDao.update(id,minValue,maxValue,num,goodType,remark);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String delete(int id){
        prizeRatioDao.delete(id);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String getPrizeById(int id){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = prizeRatioDao.getPrizeById(id);
        return JSONObject.toJSONString(obj);
    }

    public String getPageList(int pageIndex , int pageSize , String keyWords){
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr = " 1=1 ";
        String columnsStr = "a.`id`,a.`minValue`,a.`maxValue`,a.`num`,IFNULL(a.`goodType`,'') AS goodType ,a.`description`,a.`sortId`,IFNULL(b.`name`,'') AS goodName ,IFNULL(b.`image`,'') AS image ";
        String innerStr = " a LEFT JOIN `wxdk_goods` b ON a.`goodType` = b.`type` ";
        String whereStr = "where a.flag < 3 and "+tempWhereStr;
        String orderByStr = "order by a.sortId ";
        obj.Result = prizeRatioDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = prizeRatioDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }
}
