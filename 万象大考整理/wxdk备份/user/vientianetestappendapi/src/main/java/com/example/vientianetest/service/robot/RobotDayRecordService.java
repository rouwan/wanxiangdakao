package com.example.vientianetest.service.robot;

import com.example.vientianetest.dao.robot.RobotDayRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RobotDayRecordService {
    @Autowired
    private RobotDayRecordDao robotDayRecordDao;

    public void updateFistCounterByUserId(int userId,int counter){
        if(!robotDayRecordDao.isExistsByUserId(userId)){
            robotDayRecordDao.insert(userId,counter,0);
        }else{
            robotDayRecordDao.appendFistCounterByUserId(userId,counter);
        }
    }

    public void updateAfterCounterByUserId(int userId,int counter){
        if(!robotDayRecordDao.isExistsByUserId(userId)){
            robotDayRecordDao.insert(userId,0,counter);
        }else{
            robotDayRecordDao.appendAfterCounterByUserId(userId,counter);
        }
    }
}
