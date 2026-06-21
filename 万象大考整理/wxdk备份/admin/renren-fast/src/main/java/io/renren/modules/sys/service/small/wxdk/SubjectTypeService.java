package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.SubjectDao;
import io.renren.modules.sys.dao.small.wxdk.SubjectTypeDao;
import io.renren.modules.sys.domain.BasicResultObject;
import io.renren.modules.sys.domain.BasicTagState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectTypeService {
    @Autowired
    private SubjectTypeDao subjectTypeDao;
    @Autowired
    private SubjectDao subjectDao;

    public String add(int parentType,String name){
        int sortId = subjectTypeDao.getMaxSortIdByType(parentType);
        subjectTypeDao.insert(parentType,name,sortId+1);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String delete(int id){
        BasicResultObject obj = new BasicResultObject();
        if(subjectDao.isExistsByTypeId(id)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "该类型已经有题目绑定，不能删除！(备注：请先删除题目)";
            return JSONObject.toJSONString(obj);
        }
        subjectTypeDao.delete(id);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String update(int id ,int parentType, String name,int sortId){
        subjectTypeDao.update(id,parentType,name,sortId);
        return JSONObject.toJSONString(new BasicResultObject());
    }

    public String getSubjectTypeById(int id){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = subjectTypeDao.getSubjectTypeById(id);
        return JSONObject.toJSONString(obj);
    }

    public String getPageList(int pageIndex , int pageSize , String keyWords,int parentType){
        BasicResultObject obj = new BasicResultObject();
        String tempWhereStr = keyWords.length() > 0 ? " name like '%"+keyWords+"%' " : " 1=1 ";
        String tempWhereStr2 = parentType > 0 ? " parentType="+parentType  : " 1=1 ";
        String columnsStr = "id,parentType,name ,sortId,DATE_FORMAT(modifyTime, '%Y-%m-%d %H:%i:%s') as modifyTime";
        String innerStr = "";
        String whereStr = "where flag < 3 and "+tempWhereStr+" and "+tempWhereStr2;
        String orderByStr = "order by parentType , sortId";
        obj.Result = subjectTypeDao.getPageList(columnsStr,innerStr,whereStr,orderByStr,(pageIndex-1) * pageSize,pageSize);
        obj.TotalCount = subjectTypeDao.getPageTotalCount(innerStr,whereStr);
        return JSONObject.toJSONString(obj);
    }

    public String getSubjectTypeByTypeId(int parentType){
        BasicResultObject obj = new BasicResultObject();
        obj.Result = subjectTypeDao.getSubjectTypeByTypeId(parentType);
        return JSONObject.toJSONString(obj);
    }
}
