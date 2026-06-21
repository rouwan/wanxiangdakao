package com.example.vientianetest.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class RobotDayRedis {
    @Autowired
    private ValueOperations<String,Object> valueOperations;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void update(int isFinish){
        Map<String,Object> map = new HashMap<>();
        map.put("dateTime",sdf.format(new Date()));
        map.put("isFinish",isFinish);
        valueOperations.set("wxdk_robotday",map);
    }

    public boolean isTodayFinish(){
        Object obj = valueOperations.get("wxdk_robotday");
        if(obj == null ){
            return false;
        }
        Map<String,Object> map = (HashMap)obj;
        if(map.get("dateTime").toString().compareTo(sdf.format(new Date())) < 0){
            return false;
        }
        return true;

    }
}
