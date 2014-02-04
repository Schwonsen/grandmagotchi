package com.uniks.grandmagotchi.util.timer.receiver;

import android.app.Activity;
import android.content.Intent;
import com.uniks.grandmagotchi.R;
import com.uniks.grandmagotchi.util.Root;
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
        return Root.getUniqueRootInstance().isThirsty();
    }
    @Override
    protected void createNeed(){
        Root.getUniqueRootInstance().setThirsty(true);
    }
    @Override
    protected void startTimer(){
        Intent mServiceIntent = new Intent(act.getApplicationContext(), DrinkDeathTimer.class);
        act.startService(mServiceIntent);
    }
    @Override
    protected boolean restartTimer(){
        return Root.getUniqueRootInstance().isSleeping();
    }

}