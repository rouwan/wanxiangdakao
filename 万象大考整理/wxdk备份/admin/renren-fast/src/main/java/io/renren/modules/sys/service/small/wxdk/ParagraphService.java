package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.ParagraphDao;
import io.renren.modules.sys.domain.BasicResultObject;
import io.renren.modules.sys.domain.BasicTagState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParagraphService {
    @Autowired
    private ParagraphDao paragraphDao;

    public String add(String name,String image,int level,int status){
        BasicResultObject obj = new BasicResultObject();
        if(paragraphDao.isExistsByName(name)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该段位名称已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        if(paragraphDao.isExistsByLevel(level)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该段位级别已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        paragraphDao.insert(name,image,level,status);
        return JSONObject.toJSONString(obj);
    }

    public String update(int id,String name,String image,int level,int status){
        BasicResultObject obj = new BasicResultObject();
        if(paragraphDao.isExistsByName(id,name)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该段位名称已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        if(paragraphDao.isExistsByLevel(id,level)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该段位级别已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        paragraphDao.update(id,name,image,level,status);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String delete(int id){
        paragraphDao.delete(id);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String getParagraphById(int id){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = paragraphDao.getParagraphById(id);
        return JSONObject.toJSONString(obj);
    }

    public String getPageList(int pageIndex , int pageSize , String keyWords){
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr = keyWords.length()>0 ? " name like '%"+keyWords+"%' " : " 1=1 ";
        String columnsStr = "`id`,`name`,`image`,`status`,`level` ";
        String innerStr = "";
        String whereStr = "where flag < 3 and "+tempWhereStr;
        String orderByStr = "order by id";
        obj.Result = paragraphDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = paragraphDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }
}
