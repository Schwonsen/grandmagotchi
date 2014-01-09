package com.uniks.grandmagotchi.util;

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

/**
 * Created by Robin on 09.01.14.
 */
public class TimerReceiver extends BroadcastReceiver
{
    public static final String BROADCAST_ACTION = "com.uniks.grandmagotchi.intent.boardcast";
    // Prevents instantiation
    public TimerReceiver() {
    }
    // Called when the BroadcastReceiver gets an Intent it's registered to receive

    public void onReceive(Context context, Intent intent) {
        int type = intent.getIntExtra("type", 0);
        Log.d("grandmaService", "response reveived:" + type);


        createNotification(context, type);
    }
    private void createNotification(Context context, int type){
        String title = "";
        String text = "";
        switch (type){
            case RoomActivity.LIVINGROOM_POS : title = "Grandma is boring"; text = "Let her watch T.V.!"; break;
            case RoomActivity.KITCHEN_POS : title = "Grandma is hungry"; text = "Give her something to eat."; break;
           // case RoomActivity.DRESSINGROOM_POS : title = "Grandma is dirty"; text = ">ou need to clean her."; break;
            case RoomActivity.WASHINGROOM_POS : title = "Grandma is dirty"; text = "You need to clean her."; break;
            case RoomActivity.BEDROOM_POS : title = "Grandma is tired"; text = "Put her to sleep."; break;
            case RoomActivity.DRUGSTORE_POS : title = "Grandma is ill"; text = "Give her medicine (or someting else wooohoo!)."; break;
            default: title = "Grandma wants something"; text = "Give her a look!"; break;
        }
        //TODO: Change class depending on case
        Message.message(context, title);
        Message.notification(context, title, text, MainActivity.class);
    }

}