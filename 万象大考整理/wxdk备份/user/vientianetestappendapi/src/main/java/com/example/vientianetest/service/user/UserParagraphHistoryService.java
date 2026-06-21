package com.example.vientianetest.service.user;

import com.example.vientianetest.dao.user.UserParagraphDao;
import com.example.vientianetest.dao.user.UserParagraphHistoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserParagraphHistoryService {
    @Autowired
    private UserParagraphDao userParagraphDao;
    @Autowired
    private UserParagraphHistoryDao userParagraphHistoryDao;

    public List<Map<String,Object>> getUserParagraphHistoryByUserId(int userId) {
        List<Map<String,Object>> mapList = null;
        if(userParagraphHistoryDao.isExistsByUserId(userId)) {
            Map<String,Object> historyMap = userParagraphHistoryDao.getUserParagraphHistoryByUserId(userId);
            String[] historyStr = historyMap.get("historyStr").toString().split(";");
            mapList = new ArrayList<>();
            for(String str : historyStr){
                String seasonTime = str.split(",")[0];
                int paragraph = Integer.parseInt(str.split(",")[1]);
                int star = Integer.parseInt(str.split(",")[2]);
                Map<String,Object> paragraphMap = userParagraphDao.getParagraphInfoByLevel(paragraph);
                mapList.add(new HashMap(){{ put("star",star);put("seasonTime",seasonTime);put("paragraph",paragraph);put("paragraphName",paragraphMap.get("name"));put("paragraphImage",paragraphMap.get("image"));}});
            }
        }
        return mapList;
    }

    public Map<String,Object> getNoReadRecordByUserId(int userId){
        Map<String,Object> resultMap = new HashMap<>();
        if(userParagraphHistoryDao.isExistsNoReadByUserId(userId)) {
            Map<String,Object> historyMap = userParagraphHistoryDao.getUserParagraphHistoryNoReadByUserId(userId);
            userParagraphHistoryDao.updateStatusByUserId(userId,1);
            String[] historyStr = historyMap.get("historyStr").toString().split(";");
            String lastHistoryStr = historyStr[historyStr.length - 1];//最后一个
            String seasonTime = lastHistoryStr.split(",")[0];
            int paragraph = Integer.parseInt(lastHistoryStr.split(",")[1]);
            int star = Integer.parseInt(lastHistoryStr.split(",")[2]);
            Map<String,Object> paragraphMap = userParagraphDao.getParagraphInfoByLevel(paragraph);
            resultMap.put("seasonTime",seasonTime);
            resultMap.put("paragraph",paragraph);
            resultMap.put("paragraphName",paragraphMap.get("name"));
            resultMap.put("paragraphImage",paragraphMap.get("image"));
            resultMap.put("star",star);
        }
        return resultMap;
    }
}
