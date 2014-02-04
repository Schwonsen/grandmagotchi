package com.uniks.grandmagotchi.util.timer.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.uniks.grandmagotchi.util.timer.receiver.DrinkReceiver;

/**
 * Created by Robin on 08.01.14.
 */
public class DrinkDeathTimer extends DeathTimer {
    public DrinkDeathTimer(){
        BROADCAST_ACTION = DrinkReceiver.BROADCAST_ACTION;
    }
}