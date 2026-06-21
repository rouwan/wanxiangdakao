package com.example.common.session;

import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SessionListener implements HttpSessionListener {

    public static Map userMap = new HashMap<>();
    private   MySessionContext myc=MySessionContext.getInstance();

    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        //System.out.println("start...");
        myc.AddSession(httpSessionEvent.getSession());
    }

    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        //System.out.println("end...");
        HttpSession session = httpSessionEvent.getSession();
        myc.DelSession(session);
    }
}