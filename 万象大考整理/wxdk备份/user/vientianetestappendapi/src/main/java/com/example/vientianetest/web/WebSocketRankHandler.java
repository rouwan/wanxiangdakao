package com.example.vientianetest.web;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicTagState;
import com.example.common.domain.BasicUserInfo;
import com.example.common.utils.DesUtils;
import com.example.vientianetest.domain.*;
import com.example.vientianetest.redis.SessionRedis;
import com.example.vientianetest.service.SendMsgService;
import com.example.vientianetest.service.WebSocketRankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Date;

/**
 * 排位/匹配 WebSocket 消息处理器。
 * 前端小程序通过 wss://.../wsRank 连接到这个 Handler，
 * 所有游戏内实时通信（匹配、答题、心跳、表情）都走这里。
 *
 * 消息类型定义在 Protocol 常量中：
 *   0  → 心跳    1  → 匹配    2  → 重连    3  → 开始
 *   4  → 答题    5  → 逃跑    6  → 下一题  7  → 游戏结束
 *   15 → 表情
 */
@Component
public class WebSocketRankHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketRankHandler.class);

    DesUtils desUtils = new DesUtils("1234567890");

    @Autowired
    private SessionRedis sessionRedis;
    @Autowired
    private SendMsgService sendMsgService;
    @Autowired
    private WebSocketRankService webSocketRankService;

    /**
     * WebSocket 连接建立成功时调用：
     * 1. 校验 URL 参数中的加密 code（携带用户 sid）
     * 2. 从 Redis 获取用户基本信息
     * 3. 创建 User 对象并注册到 WebSocketRankService 的内存池
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        if (session.getAttributes().get("code") == null) {
            logger.error("没有传code, sessionId:{}", session.getId());
            sendMsgService.sendMsgToClient(session, new TextMessage(JSONObject.toJSONString(new BasicResultObject(BasicTagState.TagFailure,0,"","sid为空",0))));
            session.close();
            return;
        }
        String sid = desUtils.decrypt(webSocketRankService.getSid(session));
        int type = Integer.parseInt(webSocketRankService.getType(session));
        BasicUserInfo basicUserInfo = sessionRedis.getAttribute(Integer.parseInt(sid));
        if(basicUserInfo == null){
            logger.error("用户未登录 code={}",sid);
            sendMsgService.sendMsgToClient(session,new BasicResultObject(BasicTagState.TagSignOut,0,"","登录失败",0));
            return;
        }
        User user = new User();
        user.setUser(basicUserInfo);
        webSocketRankService.addUser(user,type,session);
    }

    /**
     * 收到前端发来的 WebSocket 文本消息，按 type 分流处理：
     *
     * 0  → 心跳保活：更新 keepTime，返回 pong
     * 1  → 匹配请求：加入匹配队列 addMatchingUser
     * 2  → 断线重连：恢复对局 reconnect
     * 3  → 开始游戏：start
     * 4  → 提交答案：onAnswer
     * 5  → 逃跑/退出：endGame
     * 6  → 请求下一题：next
     * 15 → 发送表情：chat
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String sid = desUtils.decrypt(webSocketRankService.getSid(session));
        User user = webSocketRankService.getUser(Integer.parseInt(sid));
        if(user == null){return;}
        try {
            String jsonMessage = message.getPayload();
            JSONObject jsonObj = JSONObject.parseObject(jsonMessage);
            if(jsonObj.get("type") == null || jsonObj.get("data") == null){
                logger.error("消息格式错误, message:{}", jsonMessage);
                return;
            }

            int type = Integer.parseInt(jsonObj.get("type").toString());
            if(type != Protocol.TYPE_KEEPALIVE && type != Protocol.TYPE_CHAT){
                logger.info("请求消息,sessionId:{} user:{} message:{}",session.getId(), user.getUser().getUserId(), jsonMessage);
            }
            switch(type){
                case Protocol.TYPE_KEEPALIVE:
                    // 心跳包：前端每隔 N 秒发一次，后端更新 keepTime 用于超时踢人
                    user.setKeepTime(new Date().getTime());
                    sendMsgService.sendMsgToClient(user.getSession(), new BasicResultObject(BasicTagState.TagSuccess,Protocol.TYPE_KEEPALIVE,"","",0));
                    break;
                case Protocol.TYPE_RECONNECT:
                    // 断线重连：前端上次连接断开后重新连上，尝试恢复未完成的游戏
                    webSocketRankService.reconnect(user.getUser().getUserId(),session);
                    break;
                case Protocol.TYPE_MATCH:
                    // 开始匹配：把用户加入匹配队列 matchingUsers
                    webSocketRankService.addMatchingUser(user.getUser().getUserId());
                    break;
                case Protocol.TYPE_START:
                    // 开始游戏（备用，目前匹配成功后服务端直接下发）
                    int gameId = Integer.parseInt(jsonObj.get("data").toString());
                    webSocketRankService.start(gameId);
                    break;
                case Protocol.TYPE_ANSWER:
                    // 提交答案：data = {answer, gameId, index, seconds, timeout}
                    JSONObject answerMap = (JSONObject)jsonObj.get("data");
                    answerMap.put("uid",user.getUser().getUserId());
                    webSocketRankService.onAnswer(user, new Answer().getAnswer(answerMap));
                    break;
                case Protocol.TYPE_NEXT:
                    // 请求下一题：前端答完一题后请求切到下一题
                    gameId = Integer.parseInt(jsonObj.get("data").toString());
                    webSocketRankService.next(gameId,user);
                    break;
                case Protocol.TYPE_EXIT:
                    // 用户逃跑/主动退出对局
                    gameId = Integer.parseInt(jsonObj.get("data").toString());
                    user.setStatus(0);
                    webSocketRankService.endGame(gameId, user.getUser().getUserId());
                    break;
                case Protocol.TYPE_CHAT:
                    // 发送表情：把表情索引广播给对局的另一方
                    String content = jsonObj.get("data").toString();
                    webSocketRankService.chat(user.getGameId(),content);
                    break;
                default:
                    logger.error("未知消息类型, message:{}", jsonMessage);
                    break;

            }

        }
        catch (Exception ex){
            ex.printStackTrace();
            logger.error("用户发送消息失败 code={}",sid);
        }

    }

    /**
     * WebSocket 连接关闭时：标记用户离线（status=0），
     * 不做立即清理——等待心跳超时后由定时任务移除
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        //删除全局
        try{
            String sid = desUtils.decrypt(webSocketRankService.getSid(session));
            User user = webSocketRankService.getUser(Integer.parseInt(sid));
            if(session.isOpen()) {
                session.close();
            }
            if(user != null) {
                logger.info("用户已关闭webSocket,sessionId:{} user:{} ", session.getId(), user.getUser().getUserId());
                user.setStatus(0);//离线
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            logger.debug("用户关闭socket id:{} 报错异常:{}",session.getId(),ex.getMessage());
        }finally {
            //logger.debug("用户退出");
        }

    }

    /** WebSocket 传输异常时关闭连接 */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        try{
            if(session.isOpen()) {
                session.close();
            }
            exception.printStackTrace();
            logger.debug("handleTransportError id:{} 报错异常:{}",session.getId(),exception.getMessage());
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.debug("handleTransportError id:{} 报错异常:{}",session.getId(),ex.getMessage());
        }
    }

}
