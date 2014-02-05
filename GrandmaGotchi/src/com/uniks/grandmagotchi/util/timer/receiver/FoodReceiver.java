package com.uniks.grandmagotchi.util.timer.receiver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.uniks.grandmagotchi.MealActivity;
import com.uniks.grandmagotchi.R;
import com.uniks.grandmagotchi.data.FoodAttributes;
import com.uniks.grandmagotchi.util.Message;
import com.uniks.grandmagotchi.util.Needs;
import com.uniks.grandmagotchi.util.Root;
import com.uniks.grandmagotchi.util.timer.services.FoodDeathTimer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Robin on 09.01.14.
 */
public class FoodReceiver extends NotificationReceiver
{
    public static String BROADCAST_ACTION = "com.uniks.grandmagotchi.intent.broadcast.food";

    public FoodReceiver(Activity act) {
        super(act);
        title = "Grandma is hungry";
        text = "Give her something to eat.";
        timerBodyID = R.id.food_body;
    }

    @Override
    protected boolean isStillInNeed() {
        return Root.getUniqueRootInstance().isHungry();
    }
    @Override
    protected void createNeed(){
        Root.getUniqueRootInstance().setFoodTimerRunning(false);
        Root.getUniqueRootInstance().setHungry(true);
        setRandomFood(act);
        Root.getUniqueRootInstance().addNeed(Needs.FOOD);

    }

    public static void setRandomFood(Context ctx) {
        String timeStamp = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
        int hour = Integer.valueOf(timeStamp);
        String meal = "dinner";
        //Timerange: Breakfest: 6-9 Lunch: 12-14 Dinner: 18-21
        if(hour > 6 && hour < 10 )
            meal = "breakfest";
        if(hour > 12 && hour < 15)
            meal = "lunch";
        if(hour > 18 && hour < 22) {
            meal="dinner";
        }


        try {

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder docBuilder = null;

            docBuilder = docBuilderFactory.newDocumentBuilder();

        Document doc = docBuilder.parse(ctx.getAssets().open("mealdata.xml"));

        final ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        doc.getDocumentElement().normalize(); // getting DOM element

        NodeList mList = doc.getElementsByTagName(MealActivity.KEY_MEALDATA);
        String[] possible = new String[3];
            int j = 0;
            for(int i = 0; i < mList.getLength(); i++){
                Element mealEl = (Element) mList.item(i);
                String curMeal = mealEl.getElementsByTagName("time").item(0).getChildNodes().item(0).getNodeValue();
                if(meal.equals(curMeal)){
                    Log.d("grandmaMeals", mealEl.getElementsByTagName("meal").item(0).getChildNodes().item(0).getNodeValue());
                    possible[j] = mealEl.getElementsByTagName("meal").item(0).getChildNodes().item(0).getNodeValue();
                    j++;
                }
            }

            Random random = new Random();
            random.setSeed(System.currentTimeMillis());
            int myRand = random.nextInt(2);
            Root.getUniqueRootInstance().setFood(possible[myRand]);
            Message.message(ctx, "Grandma wants to eat " + possible[myRand]);
            if(possible[myRand].equals("Pizza"))
                Root.getUniqueRootInstance().setUnhealthyFood(true);
            for(FoodAttributes fa : Root.getFoodList()){
                if(fa.getName().equals(possible[myRand])){
                    if(fa.getCount() == 0){
                        Message.message(ctx, "You need to buy " + fa.getName());
                        Root.getUniqueRootInstance().addNeed(Needs.BUY);
                        Root.getUniqueRootInstance().addBuys(fa.getName());
                    }
                }
            }



        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void startTimer(){
        Intent mServiceIntent = new Intent(act.getApplicationContext(), FoodDeathTimer.class);
        act.startService(mServiceIntent);
    }
    @Override
    protected boolean restartTimer(){
        return false;
    }



}