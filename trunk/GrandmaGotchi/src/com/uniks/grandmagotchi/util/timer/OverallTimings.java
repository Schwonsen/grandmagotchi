package com.uniks.grandmagotchi.util.timer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.uniks.grandmagotchi.R;
import com.uniks.grandmagotchi.RoomActivity;
import com.uniks.grandmagotchi.util.Message;
import com.uniks.grandmagotchi.util.Needs;
import com.uniks.grandmagotchi.util.Root;
import com.uniks.grandmagotchi.util.timer.services.MedDeathTimer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Robin on 04.02.14.
 */
public class OverallTimings extends AsyncTask {
    protected static final Object YOGURT = "Yogurt";
	protected static final Object FLAKES = "Flakes";
	protected static final Object EGGS = "Eggs";
	protected static final Object FISH = "Fish";
	protected static final Object PIZZA = "Pizza";
	protected static final Object SALAT = "Salat";
	protected static final Object PASTA = "Pasta";
	protected static final Object POTATOS = "Potatos";
	protected static final Object SANDWICH = "Sandwich";
	private Activity act;
    private long houseClean;
    public OverallTimings (Activity act){
        this.act = act;
        houseClean = 0; //TODO: set database value
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
        if(hour == 19 && !Root.getUniqueRootInstance().isMed() && !Root.getUniqueRootInstance().containsNeed(Needs.MEDICINE)){
            createMessage("It's late ..", "Grandma needs medicine");
            startTimer(MedDeathTimer.class);
            Root.getUniqueRootInstance().addNeed(Needs.MEDICINE);
        }

        if(hour == 22 && !Root.getUniqueRootInstance().isSleeping()){
            createMessage("Grandma is tired", "Put her to sleep.");
            Root.getUniqueRootInstance().addNeed(Needs.SLEEP);
        }
        if(hour == 5 && Root.getUniqueRootInstance().isMed()){
            Root.getUniqueRootInstance().setMed(false);
        }

        Date d = new Date();
        long stamp = d.getTime();
        if((houseClean + 604800000) < stamp && !Root.getUniqueRootInstance().containsNeed(Needs.CLEAN)){
            Root.getUniqueRootInstance().addNeed(Needs.CLEAN);
            houseClean = stamp;
            //TODO: Write new stamp into db
        }

        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout needs = (LinearLayout) act.findViewById(R.id.needs_view);
                needs.removeAllViews();
                LinkedList<Needs> granniesNeeds =  Root.getUniqueRootInstance().getAllNeeds();

                for(Needs need : granniesNeeds){
                    ImageView iv = new ImageView(act.getApplicationContext());
                    final float scale = act.getResources().getDisplayMetrics().density;
                    int pixels = (int) (30 * scale + 0.5f);
                    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(pixels, pixels);
                    iv.setLayoutParams(lp);
                    String text = "";
                    String food = "";
                    switch (need){
                        case FOOD: 
                        	food = Root.getUniqueRootInstance().getFood();
                            text = "Grandma wants to eat " + food; 
//                            iv.setImageResource(R.drawable.ic_action_eat);
                            if(food.equals(YOGURT)) 
                            {
                            	iv.setImageResource(R.drawable.ic_meal_joghurt);
                            } 
                            else if(food.equals(FLAKES))
                            { 
                            	iv.setImageResource(R.drawable.ic_meal_flakes);
                            } 
                            else if(food.equals(EGGS))
                            {
                            	iv.setImageResource(R.drawable.ic_meal_egg);
                            } 
                            else if(food.equals(FISH))
                            {
                            	iv.setImageResource(R.drawable.ic_meal_fish);
                            }
                            else if(food.equals(PIZZA))
                            {
                            	iv.setImageResource(R.drawable.ic_meal_pizza);
                            } 
                            else if(food.equals(SALAT))
                            {
                            	iv.setImageResource(R.drawable.ic_meal_salat);
                            }
                            else if(food.equals(PASTA))
                            {
                            	iv.setImageResource(R.drawable.ic_meal_pasta);
                            }
                            else if(food.equals(POTATOS))
                            {
                            	iv.setImageResource(R.drawable.ic_meal_potatos);
                            } 
                            else if(food.equals(SANDWICH))
                            {
                            	iv.setImageResource(R.drawable.ic_meal_sandwich);
                            }
                            		break;
                        case DRINK: iv.setImageResource(R.drawable.ic_action_drink);
                            text ="Grandma wants to drink something"; break;
                        case MEDICINE: iv.setImageResource(R.drawable.ic_action_medication);
                            text = "Grandma needs Medication"; break;
                        case BUY: iv.setImageResource(R.drawable.ic_action_shopcart);
                            text = "Grandma needs to buy " + Root.getUniqueRootInstance().getBuysAsString(); break;
                        case WASH: iv.setImageResource(R.drawable.ic_washing_machine);
                            text = "Grandma needs to wash hr clothes"; break;
                        case SLEEP: iv.setImageResource(R.drawable.ic_action_wake_up);
                            text = "Grandma wants to go to bed"; break;
                        case DRESS: iv.setImageResource(R.drawable.ic_wardrobe);
                            text = "Grandma needs to get dressed"; break;
                        case CLEAN: iv.setImageResource(R.drawable.ic_action_brush);
                            text = "Grandma needs to clean the house"; break;
                        case DISHES: iv.setImageResource(R.drawable.ic_action_dishes);
                            text = "Grandma should clean the dishes"; break;
                        default: break;
                    }
                    final String clickText = text;
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Message.message(act, clickText);
                        }
                    });
                    needs.addView(iv);
                }
            }
        });



        try{
            Thread.sleep(2000);
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
