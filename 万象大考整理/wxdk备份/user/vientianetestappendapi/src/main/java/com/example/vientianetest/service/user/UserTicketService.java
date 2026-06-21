package com.example.vientianetest.service.user;

import com.example.vientianetest.dao.user.UserTicketDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserTicketService {
    @Autowired
    private UserTicketDao userTicketDao;

    public int getTicketNumByUserId(int userId, int type) {
        int num = 0;
        if(userTicketDao.isExistsByUserId(userId,type)){
            num = Integer.parseInt(userTicketDao.getTicketByUserId(userId, type).get("num").toString());
        }
        return num;
    }
}
