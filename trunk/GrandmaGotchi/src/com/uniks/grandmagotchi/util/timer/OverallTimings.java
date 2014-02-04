package com.uniks.grandmagotchi.util.timer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.uniks.grandmagotchi.R;
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
        if(hour == 7 && !Root.getUniqueRootInstance().isDressed() && !Root.getUniqueRootInstance().isMed()
                &&  !Root.getUniqueRootInstance().containsNeed(Needs.MEDICINE)
                &&  !Root.getUniqueRootInstance().containsNeed(Needs.DRESS)){
            createMessage("Grandma is awake!", "Dress her and give her medicine!");
            startTimer(MedDeathTimer.class);
            Root.getUniqueRootInstance().addNeed(Needs.MEDICINE);
            Root.getUniqueRootInstance().addNeed(Needs.DRESS);
        }
        else{
            if(hour == 7 && Root.getUniqueRootInstance().isDressed() && !Root.getUniqueRootInstance().isMed()
                    &&  !Root.getUniqueRootInstance().containsNeed(Needs.MEDICINE)){
                createMessage("Grandma is awake!", "Give her medicine!");
                Root.getUniqueRootInstance().addNeed(Needs.MEDICINE);
                startTimer(MedDeathTimer.class);
            }

            if(hour == 7 && !Root.getUniqueRootInstance().isDressed() && Root.getUniqueRootInstance().isMed()
                    &&  !Root.getUniqueRootInstance().containsNeed(Needs.DRESS) ){
                createMessage("Grandma is awake!", "Dress her!");
                Root.getUniqueRootInstance().addNeed(Needs.DRESS);
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
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout needs = (LinearLayout) act.findViewById(R.id.needs_view);
                needs.removeAllViews();
                for(Needs need : Root.getUniqueRootInstance().getAllNeeds()){
                    ImageView iv = new ImageView(act.getApplicationContext());
                    //TODO: switch to dps
                    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(20, 20);
                    iv.setLayoutParams(lp);
                    switch (need){
                        case FOOD: iv.setImageResource(R.id.btn_food); break;
                        case DRINK: iv.setImageResource(R.id.btn_drink); break;
                        case MEDICINE: iv.setImageResource(R.id.btn_drugs); break;
                        case BUY: iv.setImageResource(R.id.btn_shopcart); break;
                        case WASH: iv.setImageResource(R.id.btn_washCloth); break;
                        case SLEEP: iv.setImageResource(R.id.btn_bedroom_help); break;
                        case DRESS: iv.setImageResource(R.id.btn_washCloth); break;
                        case CLEAN: iv.setImageResource(R.id.btn_brush); break;
                    }
                    needs.addView(iv);
                }
            }
        });



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
