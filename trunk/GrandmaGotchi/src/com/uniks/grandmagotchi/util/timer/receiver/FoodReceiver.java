package com.uniks.grandmagotchi.util.timer.receiver;

import android.app.Activity;
import android.content.Intent;
import com.uniks.grandmagotchi.R;
import com.uniks.grandmagotchi.data.RoomAttributes;
import com.uniks.grandmagotchi.util.timer.services.FoodDeathTimer;

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
        return RoomAttributes.getInstance().isHungry();
    }
    @Override
    protected void createNeed(){
        RoomAttributes.getInstance().setHungry(true);
    }
    @Override
    protected void startTimer(){
        Intent mServiceIntent = new Intent(act.getApplicationContext(), FoodDeathTimer.class);
        act.startService(mServiceIntent);
    }

       /* switch (type){
            case RoomActivity.LIVINGROOM_POS : title = "Grandma is boring"; currentRoom = "LR"; text = "Let her watch T.V.!"; break;
            case RoomActivity.KITCHEN_POS :
                if(add < 4){
                    title = "Grandma is hungry"; currentRoom = "K"; text = "Give her something to eat.";
                }
                else{
                    title = "Grandma is thirsty"; currentRoom = "K"; text = "Give her something to drink.";
                }
                break;
           // case RoomActivity.DRESSINGROOM_POS : title = "Grandma is dirty"; text = ">ou need to clean her."; break;
            case RoomActivity.WASHINGROOM_POS : title = "Grandma is dirty"; currentRoom = "WR"; text = "You need to clean her."; break;
            case RoomActivity.BEDROOM_POS : title = "Grandma is tired"; currentRoom = "BR"; text = "Put her to sleep."; break;
            case RoomActivity.DRUGSTORE_POS : title = "Grandma is ill"; currentRoom = "DS"; text = "Give her medicine (or someting else wooohoo!)."; break;
            default: title = "Grandma wants something"; text = "Give her a look!"; break;

        }     */


}