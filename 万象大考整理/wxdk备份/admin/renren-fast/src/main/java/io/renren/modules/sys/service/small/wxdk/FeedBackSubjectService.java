package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.FeedBackSubjectDao;
import io.renren.modules.sys.domain.BasicResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeedBackSubjectService {
    @Autowired
    private FeedBackSubjectDao feedBackSubjectDao;

    public String updateStatus(String ids){
        BasicResultObject obj = new BasicResultObject();
        String[] idArr = ids.split(",");
        for(int i = 0;i < idArr.length;i++){
            int id = Integer.parseInt(idArr[i]);
            feedBackSubjectDao.updateStatus(id);
        }
        return JSONObject.toJSONString(obj);
    }

    public String getPageList(int pageIndex , int pageSize , String keyWords,int typeId){
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr1 = keyWords.length()>0 ? " a.remark like '%"+keyWords+"%' " : " 1=1 ";
        String tempWhereStr2 = typeId >0 ? " a.types like '%"+typeId+"%' " : " 1=1 ";
        String columnsStr = "a.`id`,a.`subjectId`,d.parentType,a.`feedBackUserId`,b.`nickname`,b.`headImgUrl`,c.`promptWord`,a.`types`,a.`remark`,DATE_FORMAT(a.createTime,'%Y-%m-%d %H:%i:%s') AS createTime,ifnull(a.status,0) AS status  ";
        String innerStr = "a LEFT JOIN wxdk_user b ON a.`feedBackUserId` = b.`id` LEFT JOIN wxdk_subject c ON a.`subjectId` = c.id LEFT JOIN wxdk_subjectType d ON c.typeId = d.id";
        String whereStr = "where "+tempWhereStr1+" and "+tempWhereStr2;
        String orderByStr = "order by a.id desc";
        obj.Result = feedBackSubjectDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = feedBackSubjectDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }
}
