package com.uniks.grandmagotchi.util.timer.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import com.uniks.grandmagotchi.MainActivity;
import com.uniks.grandmagotchi.R;
import com.uniks.grandmagotchi.RoomActivity;
import com.uniks.grandmagotchi.util.Message;

/**
 * Created by Robin on 09.01.14.
 */
public class FoodReceiver extends BroadcastReceiver
{
    public static final String BROADCAST_ACTION = "com.uniks.grandmagotchi.intent.broadcast.food";
    // Prevents instantiation
    public FoodReceiver() {
    }
    // Called when the BroadcastReceiver gets an Intent it's registered to receive

    public void onReceive(Context context, Intent intent) {
        int type = intent.getIntExtra("type", 0);
        int add = intent.getIntExtra("add", -1);
        Log.d("grandmaService", "response reveived:" + type);


        createNotification(context, type, add);
    }
    private void createNotification(Context context, int type, int add){
        String title = "";
        String text = "";
        String currentRoom = "";
        switch (type){
            case RoomActivity.LIVINGROOM_POS : title = "Grandma is boring"; currentRoom = "LR"; text = "Let her watch T.V.!"; break;
            case RoomActivity.KITCHEN_POS :
                if(add < 4){
                    title = "Grandma is hungry"; currentRoom = "K"; text = "Give her something to eat.";
                }
                else{
                    title = "Grandma is thirsty"; currentRoom = "K"; text = "Give her something to drink.";
                }
                break;
           // case RoomActivity.DRESSINGROOM_POS : title = "Grandma is dirty"; text = ">ou need to clean her."; break;
            case RoomActivity.WASHINGROOM_POS : title = "Grandma is dirty"; currentRoom = "WR"; text = "You need to clean her."; break;
            case RoomActivity.BEDROOM_POS : title = "Grandma is tired"; currentRoom = "BR"; text = "Put her to sleep."; break;
            case RoomActivity.DRUGSTORE_POS : title = "Grandma is ill"; currentRoom = "DS"; text = "Give her medicine (or someting else wooohoo!)."; break;
            default: title = "Grandma wants something"; text = "Give her a look!"; break;
        }
        //TODO: Change class depending on case
        Message.message(context, title);
        Message.notification(context, title, text, RoomActivity.class);
    }

}