package com.example.vientianetest.observer;

import com.example.vientianetest.domain.RoomGame;
import com.example.vientianetest.domain.User;

public interface RoomObserver {
    void onEnter(RoomGame game, User user);
    void onLeave(RoomGame game, User user);
}
