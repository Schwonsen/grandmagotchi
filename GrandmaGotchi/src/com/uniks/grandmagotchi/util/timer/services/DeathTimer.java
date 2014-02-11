package com.uniks.grandmagotchi.util.timer.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.uniks.grandmagotchi.util.timer.receiver.DrinkReceiver;

/**
 * Created by Robin on 04.02.14.
 */
public abstract class DeathTimer extends IntentService{
    protected String BROADCAST_ACTION;
    public DeathTimer(String name) {
        super(name);
    }
    public DeathTimer(){
        super("DrinkDeathTimer");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        long time = workIntent.getLongExtra("countdown", 3600000l);
        Log.d("grandmaService", "Death Timer started");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION);

        intent.putExtra("percent", 101);
        sendBroadcast(intent);

        Log.d("grandmaService", "Death. Done.");


    }
}
