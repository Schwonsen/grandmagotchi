package com.uniks.grandmagotchi.util.timer.services;

import com.uniks.grandmagotchi.util.Needs;
import com.uniks.grandmagotchi.util.Root;
import com.uniks.grandmagotchi.util.timer.receiver.FoodReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Robin on 08.01.14.
 */
public class FoodTimer extends Timer {

    @Override
    protected long getTime(long time) {
        String timeStamp = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
        int hour = Integer.valueOf(timeStamp);
        //Timerange: Breakfest: 6-9 Lunch: 12-14 Dinner: 18-21
        if(hour > 6 && hour < 10 && (14-hour)*60*60 < time/1000)
            time = (14-hour)*60*60*1000;
        if(hour > 12 && hour < 15 && (21-hour)*60*60 < time/1000)
            time = (21-hour)*60*60*1000;
        if(hour > 18 && hour < 22 && (9+(24-hour))*60*60 < time/1000)
            time = (14-hour)*60*60*1000;
        return time;
    }

    @Override
    protected void setNeed() {
        Root.getUniqueRootInstance().setHungry(false);
        Root.getUniqueRootInstance().removeNeed(Needs.FOOD);
        Root.getUniqueRootInstance().addNeed(Needs.DISHES);
        BROADCAST_ACTION = FoodReceiver.BROADCAST_ACTION;
    }
}