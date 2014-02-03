package com.uniks.grandmagotchi.util.timer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.uniks.grandmagotchi.RoomActivity;
import com.uniks.grandmagotchi.util.Message;

/**
 * Created by Robin on 09.01.14.
 */
public class DrinkReceiver extends BroadcastReceiver
{
    public static final String BROADCAST_ACTION = "com.uniks.grandmagotchi.intent.broadcast.drink";
    // Prevents instantiation
    public DrinkReceiver() {
    }
    // Called when the BroadcastReceiver gets an Intent it's registered to receive

    public void onReceive(Context context, Intent intent) {
        int perc = intent.getIntExtra("percentage", 0);
        Log.d("grandmaService", "response reveived: Drink");


        createNotification(context, perc);
    }
    private void createNotification(Context context, int perc){

        if(perc == 99){
        String title = "Grandma is thirsty";
        String text = "Give her something to drink.";

        //TODO: Change class depending on case
        Message.message(context, title);
        Message.notification(context, title, text, RoomActivity.class);
        }
    }

}