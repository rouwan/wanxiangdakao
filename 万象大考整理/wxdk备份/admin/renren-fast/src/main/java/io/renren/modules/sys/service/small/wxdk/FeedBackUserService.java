package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.FeedBackUserDao;
import io.renren.modules.sys.domain.BasicResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeedBackUserService {
    @Autowired
    private FeedBackUserDao feedBackUserDao;

    public String getPageList(int pageIndex , int pageSize , String keyWords){
        BasicResultObject obj = new BasicResultObject();
        //String tempWhereStr = keyWords.length()>0 ? " 1=1 " : " 1=1 ";
        String columnsStr = "a.`id`,b.`nickname` AS feedBackUserName,b.`headImgUrl` AS feedBackHeadImgUrl,c.`nickname` AS userName,c.`headImgUrl` AS HeadImgUrl,a.`feedBackUserId`,a.`userId`,a.`types`,DATE_FORMAT(a.createTime,'%Y-%m-%d %H:%i:%s') AS createTime   ";
        String innerStr = "a INNER JOIN wxdk_user b ON a.`feedBackUserId` = b.`id` INNER JOIN wxdk_user c ON a.`userId` = c.`id`";
        String whereStr = "";
        String orderByStr = "order by a.id desc";
        obj.Result = feedBackUserDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = feedBackUserDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }
}
