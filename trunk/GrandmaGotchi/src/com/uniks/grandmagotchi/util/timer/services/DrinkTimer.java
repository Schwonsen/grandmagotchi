package com.uniks.grandmagotchi.util.timer.services;

import com.uniks.grandmagotchi.util.Needs;
import com.uniks.grandmagotchi.util.Root;
import com.uniks.grandmagotchi.util.timer.receiver.DrinkReceiver;

/**
 * Created by Robin on 08.01.14.
 */
public class DrinkTimer extends Timer {
    @Override
    protected long getTime(long time) {
        return time;
    }
    @Override
    protected void setNeed() {
        BROADCAST_ACTION = DrinkReceiver.BROADCAST_ACTION;
        Root.getUniqueRootInstance().setThirsty(false);
        Root.getUniqueRootInstance().removeNeed(Needs.DRINK);
    }
}