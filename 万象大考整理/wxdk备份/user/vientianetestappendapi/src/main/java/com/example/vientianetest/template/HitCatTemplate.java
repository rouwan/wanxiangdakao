package com.example.vientianetest.template;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.alibaba.fastjson.JSONObject;
import com.example.common.utils.HTTPUtils;
import com.example.vientianetest.dao.user.UserFormIdDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HitCatTemplate {
    @Autowired
    private WxMaService wxService;
    @Autowired
    private UserFormIdDao userFormIdDao;

    public void sendTemplate() {
        try {
            String accessToken = this.wxService.getAccessToken();
            List<Map<String, Object>> mapList = userFormIdDao.getFormIdList();
            for (int i = 0; i < mapList.size(); i++) {
                int userId = Integer.parseInt(mapList.get(i).get("userId").toString());
                String openId = mapList.get(i).get("openId").toString();
                String[] formIdArr = mapList.get(i).get("formId").toString().split(",");
                String formId = formIdArr[0];
                SortedMap<String, Object> dataMap = new TreeMap<String, Object>();
                dataMap.put("keyword1", new HashMap<String, Object>() {{
                    put("value", "撸猫时间开始啦！");
                    put("color", "#9b9b9b");
                }});
                dataMap.put("keyword2", new HashMap<String, Object>() {{
                    put("value", "11:00-14:00和17:00-22:00记得撸猫哦！");
                    put("color", "#9b9b9b");
                }});
                dataMap.put("keyword3", new HashMap<String, Object>() {{
                    put("value", "撸猫可以获得更多金币哦！！");
                    put("color", "#9b9b9b");
                }});
                SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();
                //parameterMap.put("access_token", accessToken);
                parameterMap.put("touser", openId);
                parameterMap.put("template_id", "e4cE7uUzpqJDdTs2Obeo4VgcguxLA28jvPPeuX-JuNo");
                parameterMap.put("page", "/pages/entry/index?cardType=cat");
                parameterMap.put("form_id", formId);
                parameterMap.put("data", dataMap);
                parameterMap.put("emphasis_keyword", "");
                HTTPUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + accessToken, "POST", JSONObject.toJSONString(parameterMap));
                //把用过的删除，更新下一个formId
                if(formIdArr.length > 1){
                    String nextFormId = formIdArr[1];
                    userFormIdDao.updateFormId(userId,nextFormId);
                }else{//直接删除
                    userFormIdDao.delete(userId);
                }
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }
}
