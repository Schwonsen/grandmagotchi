package com.uniks.grandmagotchi.util.timer.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.uniks.grandmagotchi.RoomActivity;
import com.uniks.grandmagotchi.util.Message;

/**
 * Created by Robin on 03.02.14.
 */
public abstract class NotificationReceiver extends BroadcastReceiver {
    public static String BROADCAST_ACTION;
    Activity act;
    String title;
    String text;
    int timerBodyID;

    public NotificationReceiver(){

    }

    public NotificationReceiver(Activity act) {
        this.act = act;
    }
    public void onReceive(Context context, Intent intent) {
        int perc = intent.getIntExtra("percent", 0);
        Log.d("grandmaService", "response received: " + perc);
        createNotification(context, perc);
    }
    private void createNotification(Context context, final int perc){
        if(perc == 101 && isStillInNeed()){
            String title = "Grandma died";
            String text = "You shouldn't work in a retirement home ..";
            Message.message(context, title);
            Message.notification(context, title, text, RoomActivity.class);
            //TODO: implement death
        }
        if(perc == 100){
            Message.message(context, title);
            Message.notification(context, title, text, RoomActivity.class);
            createNeed();
            startTimer();
        }
        else{

            act.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    ImageView testBody = (ImageView) act.findViewById(timerBodyID);
                    ViewGroup.LayoutParams lp = testBody.getLayoutParams();
                    final float scale = act.getResources().getDisplayMetrics().density;
                    int pixels = (int) (perc * scale + 0.5f);
                    lp.height = pixels;
                    testBody.setLayoutParams(lp);

                }
            });
        }
    }

    protected abstract boolean isStillInNeed();
    protected abstract void createNeed();
    protected abstract void startTimer();


}
