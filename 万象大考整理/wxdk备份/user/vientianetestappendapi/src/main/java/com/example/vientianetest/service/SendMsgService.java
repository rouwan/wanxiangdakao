package com.example.vientianetest.service;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;


@Component
public class SendMsgService {
    private static final Logger logger = LoggerFactory.getLogger(SendMsgService.class);

    //@Autowired
    //private WebSocketRankService webSocketRankService;

    //推送消息
    public void sendMsgToClient(WebSocketSession session, TextMessage message) {
        synchronized(session){
            try {
                session.sendMessage(message);
            } catch (Exception e) {
                logger.debug("推送消息出错 = {}", e.getMessage());
                //e.printStackTrace();
            }
        }
    }

    //推送消息
    public void sendMsgToClient(WebSocketSession session, BasicResultObject result) {
        //String sid = webSocketRankService.getSid(session);
        if (!session.isOpen()) {
            logger.debug("ws已关闭，消息发送失败, sid = {}, msg = {}", session.getId(), JSONObject.toJSONString(result));
            return;
        }
        String text = JSONObject.toJSONString(result);
        TextMessage textMessage = new TextMessage(text);
        this.sendMsgToClient(session, textMessage);
    }
}
