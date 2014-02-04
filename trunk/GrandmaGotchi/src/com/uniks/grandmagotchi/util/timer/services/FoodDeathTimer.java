package com.uniks.grandmagotchi.util.timer.services;

import com.uniks.grandmagotchi.util.timer.receiver.FoodReceiver;

/**
 * Created by Robin on 08.01.14.
 */
public class FoodDeathTimer extends DeathTimer {
    public FoodDeathTimer(){
        BROADCAST_ACTION = FoodReceiver.BROADCAST_ACTION;
    }
}