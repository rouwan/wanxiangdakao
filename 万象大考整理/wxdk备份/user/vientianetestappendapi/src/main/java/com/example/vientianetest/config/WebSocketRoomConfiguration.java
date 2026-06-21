package com.example.vientianetest.config;

import com.example.vientianetest.web.WebSocketRoomHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketRoomConfiguration extends AbstractSessionWebSocketMessageBrokerConfigurer implements WebSocketConfigurer {

    @Value("${env}")
    private String env;

    @Override
    protected void configureStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/messages").withSockJS();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        WebSocketHandlerRegistration registration =registry.addHandler(myRoomHandler(), "/vientianetest/wsRoom").
        addInterceptors(new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, org.springframework.web.socket.WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
                if (serverHttpRequest instanceof ServletServerHttpRequest) {
                    ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
                    HttpServletRequest httpServletRequest = servletRequest.getServletRequest();
                    /*HttpSession httpSession = servletRequest.getServletRequest().getSession(false);
                    if(null != httpSession){
                        map.put("userInfo",httpSession);
                    }*/
                    Enumeration<String> e = httpServletRequest.getParameterNames();
                    while (e.hasMoreElements()) {
                        String name = e.nextElement();
                        map.put(name, httpServletRequest.getParameter(name));
                    }
                }
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, org.springframework.web.socket.WebSocketHandler webSocketHandler, Exception e) {

            }
        }).setAllowedOrigins("ggbsq.xingpanwang.com","*");

        //开发环境允许你跨域
        if("env".equals(env)){
            registration.setAllowedOrigins("http://192.168.2.238:8008", "http://127.0.0.1:8008", "*");
        }

    }

    @Bean
    public WebSocketRoomHandler myRoomHandler()
    {
        return new WebSocketRoomHandler();
    }
}
