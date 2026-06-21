package com.example.vientianetest.global;

import com.example.vientianetest.domain.User;

import java.util.HashMap;

public class MyUserContext {
    private static MyUserContext instance;
    private HashMap mymap;

    private MyUserContext() {
        mymap = new HashMap();
    }

    public static MyUserContext getInstance() {
        if (instance == null) {
            instance = new MyUserContext();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public synchronized void AddUser(String sessionId , User user) {
        if (sessionId != null) {
            mymap.put(sessionId, user);
        }
    }

    public synchronized void DelUser(String sessionId) {
        if (sessionId != null) {
            mymap.remove(sessionId);
        }
    }

    public synchronized User getUser(String sessionId) {
        if (sessionId == null)
            return null;
        return (User) mymap.get(sessionId);
    }
}
