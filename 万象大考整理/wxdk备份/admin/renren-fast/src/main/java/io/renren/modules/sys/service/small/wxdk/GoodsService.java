package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.GoodsDao;
import io.renren.modules.sys.domain.BasicResultObject;
import io.renren.modules.sys.domain.BasicTagState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    public String add(int type ,String image,String name){
        BasicResultObject obj = new BasicResultObject();
        if(goodsDao.isExistsByType(type)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该类型已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        int sortId = goodsDao.getMaxSortId() + 1;
        goodsDao.insert(type,image,name,sortId);
        return JSONObject.toJSONString(obj);
    }

    public String update(int id ,int type ,String image,String name){
        BasicResultObject obj = new BasicResultObject();
        if(goodsDao.isExistsByType(id,type)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该类型已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        goodsDao.update(id,type,image,name);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String delete(int id){
        goodsDao.delete(id);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String getGoodsById(int id){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = goodsDao.getGoodsById(id);
        return JSONObject.toJSONString(obj);
    }

    public String getPageList(int pageIndex , int pageSize , String keyWords){
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr = " 1=1 ";
        String columnsStr = "`id`,`type`,`image`,`name`,`sortId`,`flag`,`modifyTime`,`createTime` ";
        String innerStr = "";
        String whereStr = "where flag < 3 and "+tempWhereStr;
        String orderByStr = "order by sortId ";
        obj.Result = goodsDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = goodsDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }
}
