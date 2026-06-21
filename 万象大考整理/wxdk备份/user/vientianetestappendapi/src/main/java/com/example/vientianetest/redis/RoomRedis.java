package com.example.vientianetest.redis;

import com.alibaba.fastjson.JSONObject;
import com.example.vientianetest.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoomRedis {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    private String KEY_PACKAGE = "server:rank:roomList";

    public void add(Room room){
        redisTemplate.opsForHash().put(KEY_PACKAGE,String.valueOf(room.getId()),JSONObject.toJSONString(room));
    }

    public void updateRoomStatus(String id,int status){
        boolean hasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE,String.valueOf(id));
        if(hasKey){
            JSONObject jsonObject = JSONObject.parseObject(redisTemplate.opsForHash().get(KEY_PACKAGE,String.valueOf(id)).toString());
            Room room = new Room().getRoom(jsonObject);
            room.setStatus(status);
            redisTemplate.opsForHash().put(KEY_PACKAGE,String.valueOf(room.getId()),JSONObject.toJSONString(room));
        }
    }

    public Room getRoomById(String roomId){
        boolean hasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE,roomId);
        Room resultRoom = null;
        if(hasKey){
            JSONObject jsonObject = JSONObject.parseObject(redisTemplate.opsForHash().get(KEY_PACKAGE,roomId).toString());
            resultRoom = new Room().getRoom(jsonObject);
        }
        return resultRoom;
    }

    public List<Object> getPageList() {
        boolean hasKey = redisTemplate.hasKey(KEY_PACKAGE);
        if(!hasKey){
            return null;
        }
        Cursor<Map.Entry<Object, Object>> curosr = redisTemplate.opsForHash().scan(KEY_PACKAGE, ScanOptions.scanOptions().build().NONE);
        List<Object> roomList = new ArrayList<>();
        while(curosr.hasNext()){
            Map.Entry<Object, Object> entry = curosr.next();
            roomList.add(JSONObject.parse(entry.getValue().toString()));
        }
        return roomList;
    }

    //房间加人数加一
    public void addUserToRoom(String id) {
        boolean hasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE,String.valueOf(id));
        if(hasKey){
            JSONObject jsonObject = JSONObject.parseObject(redisTemplate.opsForHash().get(KEY_PACKAGE,id).toString());
            Room room = new Room().getRoom(jsonObject);
            room.setUserNum(room.getUserNum() + 1);
            redisTemplate.opsForHash().put(KEY_PACKAGE,String.valueOf(room.getId()),JSONObject.toJSONString(room));
        }
    }

    //踢人
    public void kickUser(String roomId){
        boolean hasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE,String.valueOf(roomId));
        if(hasKey){
            JSONObject jsonObject = JSONObject.parseObject(redisTemplate.opsForHash().get(KEY_PACKAGE,roomId).toString());
            Room room = new Room().getRoom(jsonObject);
            if(room.getUserNum() <= 1){
                redisTemplate.opsForHash().delete(KEY_PACKAGE,String.valueOf(roomId));
                return;
            }
            room.setUserNum(room.getUserNum() - 1);
            redisTemplate.opsForHash().put(KEY_PACKAGE,String.valueOf(room.getId()),JSONObject.toJSONString(room));
        }
    }

    //删除
    public void remove(String roomId){
        boolean hasKey = redisTemplate.opsForHash().hasKey(KEY_PACKAGE,String.valueOf(roomId));
        if(hasKey){
            redisTemplate.opsForHash().delete(KEY_PACKAGE,String.valueOf(roomId));
        }
    }
}
