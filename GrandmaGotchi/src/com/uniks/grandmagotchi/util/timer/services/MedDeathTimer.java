package com.uniks.grandmagotchi.util.timer.services;

import com.uniks.grandmagotchi.util.timer.receiver.MedReceiver;

/**
 * Created by Robin on 04.02.14.
 */
public class MedDeathTimer extends DeathTimer {
    public MedDeathTimer(){
        BROADCAST_ACTION = MedReceiver.BROADCAST_ACTION;
    }
}
