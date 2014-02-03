package com.uniks.grandmagotchi.util.timer.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.uniks.grandmagotchi.util.timer.receiver.DrinkReceiver;
import com.uniks.grandmagotchi.util.timer.receiver.FoodReceiver;

/**
 * Created by Robin on 08.01.14.
 */
public class DrinkTimer extends IntentService {
    public static final String TYPE = "Timer";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DrinkTimer(String name) {
        super(name);
    }
    public DrinkTimer(){
        super("DrinkTimer");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        int time = workIntent.getIntExtra("countdown", 500);

        Log.d("grandmaService", "Drinking started");
        for(int i = 0; i < 100; i++){
            try {
                Thread.sleep(time/100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent();
            intent.setAction(DrinkReceiver.BROADCAST_ACTION);
            intent.putExtra("percent", i);
            sendBroadcast(intent);
        }

        Log.d("grandmaService", "Drinking ended");


    }
}