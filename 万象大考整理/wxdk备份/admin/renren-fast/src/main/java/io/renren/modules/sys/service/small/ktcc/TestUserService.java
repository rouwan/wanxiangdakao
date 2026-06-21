package io.renren.modules.sys.service.small.ktcc;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.ktcc.TestUserDao;
import io.renren.modules.sys.domain.BasicResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestUserService {
    @Autowired
    private TestUserDao testUserDao;

    public String update(int userId , int strengthCount , int cardNumber ,int type){
        BasicResultObject obj = new BasicResultObject();
        if(type == 1 && testUserDao.isExistsByUserId(userId)){
            obj.Tag = -1 ;
            obj.Message = "该用户已存在！";
            return JSONObject.toJSONString(obj);
        }
        testUserDao.update(userId,strengthCount,cardNumber);
        return JSONObject.toJSONString(obj);
    }

    public String delete(int userId){
        testUserDao.delete(userId);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String getUserInfoByName(String name){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = testUserDao.getUserInfoByName(name);
        return JSONObject.toJSONString(obj);
    }

    public String getPageList(int pageIndex , int pageSize , String keyWords){
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr = keyWords.length()>0 ? " a.nickname like '%"+keyWords+"%' " : " 1=1 ";
        String columnsStr = "a.id,a.nickname,a.`headImgUrl`,b.cardNumber,c.`strengthCount` ";
        String innerStr = "a INNER JOIN `ktcc_lastsubject` b ON a.id = b.userId INNER JOIN `ktcc_userability` c ON a.`id` = c.userId";
        String whereStr = "WHERE a.isTest = 1 and "+tempWhereStr;
        String orderByStr = "order by a.id";
        obj.Result = testUserDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = testUserDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }
}
