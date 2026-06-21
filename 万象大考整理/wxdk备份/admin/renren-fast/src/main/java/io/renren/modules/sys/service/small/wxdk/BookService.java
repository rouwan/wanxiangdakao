package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.BookDao;
import io.renren.modules.sys.domain.BasicResultObject;
import io.renren.modules.sys.domain.BasicTagState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookService {
    @Autowired
    private BookDao bookDao;

    public String add(int type ,String image,String remark,int sortId){
        BasicResultObject obj = new BasicResultObject();
        if(bookDao.isExistsByType(type)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该类型已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        bookDao.insert(type,image,remark,sortId);
        return JSONObject.toJSONString(obj);
    }

    public String update(int id ,int type ,String image,String remark,int sortId){
        BasicResultObject obj = new BasicResultObject();
        if(bookDao.isExistsByType(id,type)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该类型已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        bookDao.update(id,type,image,remark,sortId);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String delete(int id){
        bookDao.delete(id);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String getBookById(int id){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = bookDao.getBookById(id);
        return JSONObject.toJSONString(obj);
    }

    public String getPageList(int pageIndex , int pageSize , String keyWords){
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr = " 1=1 ";
        String columnsStr = "`id`,`type`,`remark`,`image`,`sortId`,`modifyTime`,`createTime` ";
        String innerStr = "";
        String whereStr = "where "+tempWhereStr;
        String orderByStr = "order by sortId ";
        obj.Result = bookDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = bookDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }
}
