package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.EmailDao;
import io.renren.modules.sys.domain.BasicResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    @Autowired
    private EmailDao emailDao;

    public String add(String title ,String content,String gift,int status ){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = emailDao.insert(title,content,gift,status);
        return JSONObject.toJSONString(obj);
    }

    public String update(int id , String title ,String content,String gift,int status){
        emailDao.update(id,title,content,gift,status);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String delete(String ids){
        String[] arr = ids.split(",");
        for(int i =0;i<arr.length; i ++){
            int id = Integer.parseInt(arr[i]);
            emailDao.delete(id);
        }
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String getEmailById(int id){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = emailDao.getEmailById(id);
        return JSONObject.toJSONString(obj);
    }

    public String getPageList(int pageIndex , int pageSize , String keyWords){
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr = keyWords.length()>0 ? " title like '%"+keyWords+"%' " : " 1=1 ";
        String columnsStr = "`id`,`title`,`content`,`gift`,`status`,DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s') AS createTime ";
        String innerStr = "";
        String whereStr = "where flag < 3 and "+tempWhereStr;
        String orderByStr = "order by id";
        obj.Result = emailDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = emailDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }
}
