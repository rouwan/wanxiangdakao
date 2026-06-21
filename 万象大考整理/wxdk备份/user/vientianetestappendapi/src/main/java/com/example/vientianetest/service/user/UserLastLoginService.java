package com.example.vientianetest.service.user;

import com.example.vientianetest.dao.user.UserLastLoginDao;
import com.example.vientianetest.domain.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserLastLoginService {
    @Autowired
    private UserLastLoginDao userLastLoginDao;
    @Autowired
    private UserEquipmentService userEquipmentService;

    public void isTodayLoginByUserId(int userId){
        if(!userLastLoginDao.isExistsByUserId(userId)){
            userLastLoginDao.insert(userId);
            userEquipmentService.insert(userId,Param.GLOBAL_STRENGTH_MIDDLE,1 );
        }else{
            if(!userLastLoginDao.isTodayLoginByUserId(userId)){
                userLastLoginDao.updateLoginTimeByUserId(userId);
                userEquipmentService.insert(userId,Param.GLOBAL_STRENGTH_MIDDLE,1 );
            }
        }

    }
}
