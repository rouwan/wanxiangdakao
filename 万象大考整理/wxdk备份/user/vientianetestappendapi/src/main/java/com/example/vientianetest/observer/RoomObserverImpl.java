package com.example.vientianetest.observer;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicTagState;
import com.example.vientianetest.domain.Protocol;
import com.example.vientianetest.domain.RoomGame;
import com.example.vientianetest.domain.User;
import com.example.vientianetest.redis.RoomRedis;
import com.example.vientianetest.service.SendMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Component
public class RoomObserverImpl implements RoomObserver {
    private static final Logger logger = LoggerFactory.getLogger(RoomObserverImpl.class);
    @Autowired
    private RoomRedis roomRedis;
    @Override
    public void onEnter(RoomGame game, User user) {
        List<Object> list = roomRedis.getPageList();
        this.send(user.getSession(),new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_ROOM_HALL,list == null ? "" : list,"",0));
    }

    @Override
    public void onLeave(RoomGame game, User user) {

    }

    private void send(WebSocketSession session,BasicResultObject result) {
        try {
            if (!session.isOpen()) {
                logger.debug("观察者_ws已关闭，消息发送失败, sid = {}, msg = {}", session.getId(), JSONObject.toJSONString(result));
                return;
            }
            String text = JSONObject.toJSONString(result);
            TextMessage textMessage = new TextMessage(text);
            session.sendMessage(textMessage);
        }
        catch (Exception e){
            logger.debug("观察者_推送消息出错 = {}", e.getMessage());
        }
    }
}
