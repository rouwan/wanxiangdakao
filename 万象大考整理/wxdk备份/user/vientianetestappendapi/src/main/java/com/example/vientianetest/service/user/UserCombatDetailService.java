package com.example.vientianetest.service.user;

import com.example.vientianetest.dao.user.UserCombatDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserCombatDetailService {
    @Autowired
    private UserCombatDetailDao userCombatDetailDao;

    public void update(int userId, int type, int combat){
        if(!userCombatDetailDao.isExistsByUserId(userId,type)) {
            userCombatDetailDao.insert(userId, type, combat);
        }else{
            userCombatDetailDao.appendCeByUserId(userId, type, combat);
        }
    }

    public List<Map<String,Object>> getUserCombatDetailByUserId(int userId) {
        List<Map<String,Object>> mapList = null;
        if(userCombatDetailDao.isExistsByUserId(userId)){
            mapList = userCombatDetailDao.getUserCombatDetailByUserId(userId);
        }
        return mapList;
    }
}
