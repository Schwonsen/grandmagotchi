package com.uniks.grandmagotchi.util.timer.receiver;

import android.app.Activity;
import com.uniks.grandmagotchi.R;
import com.uniks.grandmagotchi.util.Root;

/**
 * Created by Robin on 04.02.14.
 */
public class MedReceiver extends NotificationReceiver {
    public static String BROADCAST_ACTION = "com.uniks.grandmagotchi.intent.broadcast.med";
    public MedReceiver(Activity act) {
        super(act);
        title = "Grandma needs Med";
        text = "Give her Med.";
        timerBodyID = 0;
    }
    @Override
    protected boolean isStillInNeed() {
        return !Root.getUniqueRootInstance().isMed();
    }

    @Override
    protected void createNeed() {

    }

    @Override
    protected void startTimer() {

    }

    @Override
    protected boolean restartTimer() {
        return false;
    }
}
