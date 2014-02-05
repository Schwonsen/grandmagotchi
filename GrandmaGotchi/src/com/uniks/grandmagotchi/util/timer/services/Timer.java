package com.uniks.grandmagotchi.util.timer.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.uniks.grandmagotchi.util.timer.receiver.DrinkReceiver;

/**
 * Created by Robin on 04.02.14.
 */
public abstract class Timer extends IntentService {
    protected String BROADCAST_ACTION;
    protected long time;
    public Timer(String name) {
        super(name);
    }
    public Timer(){
        super("Timer");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        time = workIntent.getIntExtra("countdown", 500);
        time = getTime(time);
        setNeed();
        Intent startIntent = new Intent();
        Log.d("grandmaString", BROADCAST_ACTION);
        startIntent.setAction(BROADCAST_ACTION);
        startIntent.putExtra("percent", 0);
        sendBroadcast(startIntent);

        Log.d("grandmaService", "Timer started");
        for(int i = 1; i <= 100; i++){
            try {
                Thread.sleep(time/100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent();
            intent.setAction(BROADCAST_ACTION);
            intent.putExtra("percent", i);
            sendBroadcast(intent);
        }
        Log.d("grandmaService", "Timer ended");
    }
    protected abstract long getTime(long time);
    protected abstract void setNeed();
}
