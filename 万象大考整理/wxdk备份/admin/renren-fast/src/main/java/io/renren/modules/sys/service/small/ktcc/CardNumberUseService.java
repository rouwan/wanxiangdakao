package io.renren.modules.sys.service.small.ktcc;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.ktcc.LastSubjectDao;
import io.renren.modules.sys.domain.BasicResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardNumberUseService {
    @Autowired
    private LastSubjectDao lastSubjectDao;

    public String getSubjectUse(){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = lastSubjectDao.getSubjectUse();
        return JSONObject.toJSONString(obj);
    }

}
