package com.example.vientianetest.web;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicTagState;
import com.example.common.domain.BasicUserInfo;
import com.example.common.utils.DesUtils;
import com.example.vientianetest.domain.*;
import com.example.vientianetest.redis.SessionRedis;
import com.example.vientianetest.service.SendMsgService;
import com.example.vientianetest.service.WebSocketRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class WebSocketRoomHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketRoomHandler.class);

    DesUtils desUtils = new DesUtils("1234567890");
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private SendMsgService sendMsgService;
    @Autowired
    private WebSocketRoomService webSocketRoomService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (session.getAttributes().get("code") == null) {
            logger.error("房间_没有传code, sessionId:{}", session.getId());
            sendMsgService.sendMsgToClient(session, new TextMessage(JSONObject.toJSONString(new BasicResultObject(BasicTagState.TagFailure, 0, "", "sid为空", 0))));
            session.close();
            return;
        }
        int type = webSocketRoomService.getType(session);
        String code = desUtils.decrypt(webSocketRoomService.getSid(session));
        if (!sessionRedis.isLogin(code)) {
            logger.error("房间_用户未登录 code={}", code);
            sendMsgService.sendMsgToClient(session, new BasicResultObject(BasicTagState.TagSignOut, 0, "", "登录失败", 0));
            return;
        }
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(code);
        User user = new User();
        user.setBoot(false);
        user.setSession(session);
        user.setUser(basicUserInfo);
        //1:四人混战,2:2v2对战(预留接口)
        if (type == 1) {
            //1.自己创建房间,邀请好友
            //webSocketRoomService.getHallList(user, session);
            webSocketRoomService.addUser(user,session);
        } else if (type == 2) {
            webSocketRoomService.reconnect(user, session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("web测试");
        String userId = webSocketRoomService.getUserId(session);
        User user = webSocketRoomService.getUser(Integer.parseInt(userId));
        if(user == null){ return;}
        try {
            String jsonMessage = message.getPayload();
            JSONObject jsonObj = JSONObject.parseObject(jsonMessage);
            if(jsonObj.get("type") == null || jsonObj.get("data") == null){
                logger.error("消息格式错误, message:{}", jsonMessage);
                return;
            }

            int type = Integer.parseInt(jsonObj.get("type").toString());
            if(type != Protocol.TYPE_KEEPALIVE && type != Protocol.TYPE_CHAT && type != Protocol.TYPE_ROOM_HALL){
                logger.info("请求消息,sessionId:{} user:{} message:{}",session.getId(), user.getUser().getUserId(), jsonMessage);
            }
            /*if(type == Protocol.TYPE_KEEPALIVE){
                System.out.println(formatter.format(new Date())+" 心跳请求,sessionId:"+user.getSession().getId()+" user:"+user.getUser().getUserId()+" message: 0 ");
            }*/
            //String roomId = user.getRoomId();
            switch(type){
                case Protocol.TYPE_KEEPALIVE:
                    user.setKeepTime(new Date().getTime());
                    sendMsgService.sendMsgToClient(user.getSession(), new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_KEEPALIVE,"","",0));
                    //System.out.println(formatter.format(new Date())+" 心跳回应,sessionId:"+user.getSession().getId()+" user:"+user.getUser().getUserId()+" message: 0 ");
                    break;
                case Protocol.TYPE_ROOM_HALL:
                    //{"type":12,"data":1}
                    //user.setSession(session);
                    //webSocketRoomService.getHallList(user,session);
                    break;
                case Protocol.TYPE_ROOM_CREATE:
                    //{"type":13,"data":1,"modeType":1}//1.四人混战,2:3v3
                    int modeType = Integer.parseInt(jsonObj.get("modeType").toString());
                    webSocketRoomService.createModeTypeRoom(user,modeType, session);
                    break;
                case Protocol.TYPE_ROOM_QUICKSTART:
                    //{"type":14,"data":1}
                    webSocketRoomService.quickStartGame(user,session);
                    break;
                case Protocol.TYPE_ROOM_ENTER:
                    //{"type":16,"data":1}
                    String roomId = jsonObj.get("data").toString();
                    webSocketRoomService.enterGame(user,roomId,session);
                    break;
                case Protocol.TYPE_RECONNECT:
                    //{"type":2,"data":1}
                    webSocketRoomService.reconnect(user,session);
                    break;
                case Protocol.TYPE_ROOM_CHANGESTATUS://准备或取消准备
                    //{"type":10,"data":1}
                    //int kickUserId = Integer.parseInt(jsonObj.get("data").toString());
                    webSocketRoomService.changeStatus(user);
                    break;
                case Protocol.TYPE_ROOM_KICK://被踢
                    //{"type":8,"data":1}
                    int kickUserId = Integer.parseInt(jsonObj.get("data").toString());
                    webSocketRoomService.kickPlayer(user,kickUserId);
                    break;
                case Protocol.TYPE_STATUS_MATCH:
                    webSocketRoomService.matchStatus(user,session);
                    break;
                case Protocol.TYPE_MATCH:
                    //{"type":1,"data":1}
                    webSocketRoomService.quickStartGame(user,session);
                    break;
                case Protocol.TYPE_START:
                    //{"type":3,"data":3}
                    //webSocketRoomService.start(user.getPlayRoomId());
                    break;
                case Protocol.TYPE_RESTART:
                    //{"type":9,"data":1}
                    break;
                case Protocol.TYPE_ANSWER:
                    //{"type":4,"data":{"answer": 2,"roomId": "","index": 0,"seconds": 0,"timeout": false }}
                    JSONObject answerMap = (JSONObject)jsonObj.get("data");
                    answerMap.put("roomId",answerMap.get("roomId").toString());
                    answerMap.put("uid",user.getUser().getUserId());
                    webSocketRoomService.onAnswer(user, new Answer().getAnswer2(answerMap));
                    break;
                case Protocol.TYPE_NEXT:
                    //{"type":6,"data":3}
                    webSocketRoomService.next(user.getPlayRoomId(),user.getUser().getUserId());
                    break;
                case Protocol.TYPE_EXIT:
                    //{"type":5,"data":3}
                    user.setStatus(0);
                    webSocketRoomService.userExit(user.getRoomId(),user.getPlayRoomId(), user.getUser().getUserId());
                    break;
                case Protocol.TYPE_CHAT:
                    //{"type":15,"data":3}
                    // String content = jsonObj.get("data").toString();
                    //webSocketRoomService.chat(user.getRoomId(),content);
                    break;
                case Protocol.TYPE_MINISTATUS:
                    //{"type":17,"data":3}
                    //webSocketRoomService.miniStatus(user.getUser().getUserId());
                    break;
                default:
                    logger.error("房间_未知消息类型, message:{}", jsonMessage);
                    break;
            }

        }
        catch (Exception ex){
            logger.error("房间_用户发送消息失败 userId={},Exception={}",userId,ex);
            ex.printStackTrace();
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        //删除全局
        try{
            String userId = webSocketRoomService.getUserId(session);
            User user = webSocketRoomService.getUser(Integer.parseInt(userId));
            if(session.isOpen()) {
                session.close();
            }
            if(user != null ){
                String roomId = user.getRoomId();
                if(roomId == null || roomId.length() <= 0){//在大厅的话关闭，直接删除用户
                    logger.info("房间_用户已关闭webSocket,sessionId:{} user:{} ", session.getId(), user.getUser().getUserId());
                    //user.setStatus(0);
                    webSocketRoomService.removeUser(Integer.parseInt(userId));
                }
            }
            /*user user = webSocketRoomService.getUser(Integer.parseInt(userId));
            //不是最小化就默认逃跑
            if(user != null && user.getStatus() != 2){
                logger.info("房间_用户已关闭webSocket不是最小化就默认逃跑,sessionId:{} user:{} ", session.getId(), user.getUser().getUserId());
                webSocketRoomService.userExit(user.getRoomId(),user.getUser().getUserId());
            }*/
        }
        catch (Exception ex){
            ex.printStackTrace();
            logger.debug("房间_用户关闭socket id:{} 报错异常:{}",session.getId(),ex.getMessage());
        }finally {
            //logger.debug("用户退出");
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        try{
            if(session.isOpen()){
                session.close();
            }
            exception.printStackTrace();
            logger.debug("房间_handleTransportError id:{} 报错异常:{}",session.getId(),exception.getMessage());
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.debug("房间_handleTransportError id:{} 报错异常:{}",session.getId(),ex.getMessage());
        }
    }
}
