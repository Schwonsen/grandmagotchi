package com.uniks.grandmagotchi.util.timer.receiver;

import android.app.Activity;
import android.content.Intent;
import com.uniks.grandmagotchi.R;
import com.uniks.grandmagotchi.data.RoomAttributes;
import com.uniks.grandmagotchi.util.timer.services.DrinkDeathTimer;

/**
 * Created by Robin on 09.01.14.
 */
public class DrinkReceiver extends NotificationReceiver
{
    public static String BROADCAST_ACTION = "com.uniks.grandmagotchi.intent.broadcast.drink";

    public DrinkReceiver(Activity act) {
        super(act);
        title = "Grandma is thirsty";
        text = "Give her something to drink";
        timerBodyID = R.id.drink_body;
    }

    @Override
    protected boolean isStillInNeed() {
        return RoomAttributes.getInstance().isThirsty();
    }
    @Override
    protected void createNeed(){
        RoomAttributes.getInstance().setThirsty(true);
    }
    @Override
    protected void startTimer(){
        Intent mServiceIntent = new Intent(act.getApplicationContext(), DrinkDeathTimer.class);
        act.startService(mServiceIntent);
    }

}