package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.SubjectRatioDao;
import io.renren.modules.sys.domain.BasicResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectRatioService {
    @Autowired
    private SubjectRatioDao subjectRatioDao;

    public void update(int subjectId,int totalNum,int rightNum,int errorNum){
        if(!subjectRatioDao.isExistsBySubjectId(subjectId)){
            subjectRatioDao.insert(subjectId,totalNum,rightNum,errorNum);
        }else{
            subjectRatioDao.update(subjectId,totalNum,rightNum,errorNum);
        }
    }

    public String getSubjectLevelTypeRatio(){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = subjectRatioDao.getSubjectLevelTypeRatio();
        return JSONObject.toJSONString(obj);
    }
}
