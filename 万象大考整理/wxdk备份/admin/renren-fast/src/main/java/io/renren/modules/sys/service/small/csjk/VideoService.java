package io.renren.modules.sys.service.small.csjk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.csjk.VideoDao;
import io.renren.modules.sys.domain.BasicResultObject;
import io.renren.modules.sys.domain.BasicTagState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VideoService {
    @Autowired
    private VideoDao videoDao;

    public String add(String video,String title,int seconds,int status,String image,int frame){
        BasicResultObject obj = new BasicResultObject();
        if(videoDao.isExistsByTitle(title)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该标题已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        int maxSortId = videoDao.getMaxSortId()+1;
        videoDao.insert(video,title,seconds,maxSortId,status,image,frame);
        return JSONObject.toJSONString(obj);
    }

    public String update(int id ,String video,String title,int seconds,int sortId,int status,String image,int frame){
        BasicResultObject obj = new BasicResultObject();
        if(videoDao.isExistsByTitle(id,title)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该标题已经存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        videoDao.update(id,video,title,seconds,sortId,status,image,frame);
        return JSONObject.toJSONString(obj);
    }

    public String delete(int id){
        videoDao.delete(id);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String getVideoById(int id){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = videoDao.getVideoById(id);
        return JSONObject.toJSONString(obj);
    }

    public String getPageList(int pageIndex , int pageSize , String keyWords){
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr = keyWords.length()>0 ? " title like '%"+keyWords+"%' " : " 1=1 ";
        String columnsStr = "`id`,`video`,`title`,`seconds`,`sortId`,`status` ";
        String innerStr = "";
        String whereStr = "where flag < 3 and "+tempWhereStr;
        String orderByStr = "order by id";
        obj.Result = videoDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = videoDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }
}
