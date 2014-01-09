package com.uniks.grandmagotchi.util;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by Robin on 08.01.14.
 */
public class TimerService extends IntentService {
    public static final String TYPE = "Timer";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public TimerService(String name) {
        super(name);
    }
    public TimerService(){
        super("TimerService");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        int type = workIntent.getIntExtra("type", -1);
        int time = workIntent.getIntExtra("countdown", 500);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        intent.setAction(TimerReceiver.BROADCAST_ACTION);
        intent.putExtra("type", type);
        sendBroadcast(intent);
    }
}