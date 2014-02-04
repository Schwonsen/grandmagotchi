package com.uniks.grandmagotchi.util.timer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.uniks.grandmagotchi.RoomActivity;
import com.uniks.grandmagotchi.util.Message;
import com.uniks.grandmagotchi.util.Needs;
import com.uniks.grandmagotchi.util.Root;
import com.uniks.grandmagotchi.util.timer.services.MedDeathTimer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Robin on 04.02.14.
 */
public class OverallTimings extends AsyncTask {
    private Activity act;
    public OverallTimings (Activity act){
        this.act = act;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        while(true){
        String timeStamp = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
        int hour = Integer.valueOf(timeStamp);
        if(hour == 7 && Root.getUniqueRootInstance().isSleeping()){
            Root.getUniqueRootInstance().setSleeping(false);
        }
        if(hour == 7 && !Root.getUniqueRootInstance().isDressed() && !Root.getUniqueRootInstance().isMed()){
            createMessage("Grandma is awake!", "Dress her and give her medicine!");
            startTimer(MedDeathTimer.class);
        }
        else{
            if(hour == 7 && Root.getUniqueRootInstance().isDressed() && !Root.getUniqueRootInstance().isMed()){
                createMessage("Grandma is awake!", "Give her medicine!");
                startTimer(MedDeathTimer.class);
            }

            if(hour == 7 && !Root.getUniqueRootInstance().isDressed() && Root.getUniqueRootInstance().isMed()){
                createMessage("Grandma is awake!", "Dress her!");
            }
        }
        if(hour == 14 && Root.getUniqueRootInstance().isMed()){
            Root.getUniqueRootInstance().setMed(false);
        }

        if(hour > 12 && hour < 15 && Root.getUniqueRootInstance().isUnhealthyFood()){
            createMessage("Grandma ate a lot", "She needs Medicine!");
            startTimer(MedDeathTimer.class);
        }
        if(hour == 20 && !Root.getUniqueRootInstance().isMed() && !Root.getUniqueRootInstance().containsNeed(Needs.MEDICINE)){
            createMessage("It's late ..", "Grandma needs medicine");
            startTimer(MedDeathTimer.class);
            Root.getUniqueRootInstance().addNeed(Needs.MEDICINE);
        }

        if(hour == 22 && !Root.getUniqueRootInstance().isSleeping()){
            createMessage("Grandma is tired", "Put her to sleep.");
        }
        if(hour == 5 && Root.getUniqueRootInstance().isMed()){
            Root.getUniqueRootInstance().setMed(false);
        }

        Log.d("grandmaRunner", "ruuuuun");

        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){
           e.printStackTrace();
            return null;
        }
        }
    }

    private void createMessage(final String title, final String text){
        if(Root.getUniqueRootInstance().isInForeground()){
            act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Message.message(act.getApplicationContext(), text);
            }
        });
        }
        else
            Message.notification(act.getApplicationContext(), title, text, RoomActivity.class);
    }

    private void startTimer(Class cl){
        Intent mServiceIntent = new Intent(act.getApplicationContext(), cl);
        mServiceIntent.putExtra("countdown", 10000);
        act.startService(mServiceIntent);
    }

}
