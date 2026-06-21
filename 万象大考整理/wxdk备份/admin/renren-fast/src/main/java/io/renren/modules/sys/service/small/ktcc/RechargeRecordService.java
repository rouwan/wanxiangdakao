package io.renren.modules.sys.service.small.ktcc;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.ktcc.RechargeRecordDao;
import io.renren.modules.sys.domain.BasicResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RechargeRecordService {
    @Autowired
    private RechargeRecordDao rechargeRecordDao;

    public String getPageList(int pageIndex , int pageSize , String keyWords,String date){
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr = keyWords.length()>0 ? " (b.nickname like '%"+keyWords+"%' or transactionId like '%"+keyWords+"%' or outTradeNo like '%"+keyWords+"%') " : " 1=1 ";
        tempWhereStr += date.length()>0 ? " and a.createTime >= DATE_FORMAT(DATE_SUB('"+date+"' ,INTERVAL -1 DAY),'%Y-%m-%d')":"";
        String columnsStr = "a.`id`,a.`userId`,b.nickname,a.`money`,a.`type`,a.`description`,a.`transactionId`,a.`outTradeNo`,DATE_FORMAT(a.`createTime`, '%Y-%m-%d %H:%i:%s') as createTime";
        String innerStr = "a INNER JOIN ktcc_user b ON a.userId = b.id";
        String whereStr = "where "+tempWhereStr;
        String orderByStr = date.length()>0 ? "order by id":"order by a.id desc";
        obj.Result = rechargeRecordDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = rechargeRecordDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }
}
