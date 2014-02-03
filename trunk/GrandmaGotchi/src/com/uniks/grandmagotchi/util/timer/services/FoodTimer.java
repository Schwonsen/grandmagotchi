package com.uniks.grandmagotchi.util.timer.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.uniks.grandmagotchi.RoomActivity;
import com.uniks.grandmagotchi.util.timer.receiver.FoodReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Robin on 08.01.14.
 */
public class FoodTimer extends IntentService {
    public static final String TYPE = "Timer";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FoodTimer(String name) {
        super(name);
    }
    public FoodTimer(){
        super("FoodTimer");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        int type = workIntent.getIntExtra("type", -1);
        int time = workIntent.getIntExtra("countdown", 500);
        int add = workIntent.getIntExtra("add", -1);


        switch (type){
            case RoomActivity.LIVINGROOM_POS : ; break;
            case RoomActivity.KITCHEN_POS :
                String timeStamp = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
                int hour = Integer.valueOf(timeStamp);
                Log.d("oedlsTime", timeStamp);
                //Timerange: Breakfest: 6-9 Lunch: 12-14 Dinner: 18-21
                if(add == 1 && (14-hour)*60*60 < time/1000)
                    time = (14-hour)*60*60;
                if(add == 2 && (21-hour)*60*60 < time/1000)
                    time = (21-hour)*60*60;
                if(add == 1 && (9+(24-hour))*60*60 < time/1000)
                    time = (14-hour)*60*60;
                break;
            // case RoomActivity.DRESSINGROOM_POS : title = "Grandma is dirty"; text = ">ou need to clean her."; break;
            case RoomActivity.WASHINGROOM_POS : ; break;
            case RoomActivity.BEDROOM_POS : ; break;
            case RoomActivity.DRUGSTORE_POS : ; break;
            default: break;
        }



        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        intent.setAction(FoodReceiver.BROADCAST_ACTION);
        intent.putExtra("type", type);
        intent.putExtra("add", add);
        sendBroadcast(intent);
    }
}