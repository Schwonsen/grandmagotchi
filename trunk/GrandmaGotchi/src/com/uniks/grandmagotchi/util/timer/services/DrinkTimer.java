package com.uniks.grandmagotchi.util.timer.services;

import com.uniks.grandmagotchi.data.DatabaseAdapter;
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
        Root.getUniqueRootInstance().setThirsty(false);
        Root.getUniqueRootInstance().removeNeed(Needs.DRINK);
        BROADCAST_ACTION = DrinkReceiver.BROADCAST_ACTION;
        
        
        DatabaseAdapter databaseHandler = getDatabaseHandler();
        
        if(Root.isCalledFromExistingAccount())
        {
        	if(databaseHandler.getTimer(Root.getId(), "DrinkTimer"))
            {
        		String date = String.valueOf(System.currentTimeMillis());
            	databaseHandler.updateTimerData(Root.getId(), "DrinkTimer" , date, String.valueOf(time));
            }
        	else
        	{
        		String date = String.valueOf(System.currentTimeMillis());
        		databaseHandler.insertTimerData(Root.getId(), "DrinkTimer", date, String.valueOf(time));
        	}
        }
        else
        {
        	String[] countNames = databaseHandler.getNamesArray();
 		   	String idCode = String.valueOf(countNames.length);
 		   	String date = String.valueOf(System.currentTimeMillis());
 		   	databaseHandler.insertTimerData(idCode, "DrinkTimer", date, String.valueOf(time));
        }
//TODO TEst
//        Root.getUniqueRootInstance().setDrinkTimerRunning(true);

    }
}