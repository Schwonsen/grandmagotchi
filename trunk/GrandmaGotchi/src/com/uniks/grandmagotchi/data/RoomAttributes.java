package com.uniks.grandmagotchi.data;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.uniks.grandmagotchi.R;

/**
 * Created by Robin on 03.02.14.
 */
public class RoomAttributes {

    private static RoomAttributes instance;
    public static RoomAttributes getInstance(){
        if(instance == null)
            instance = new RoomAttributes();
        return instance;
    }


    private boolean isThirsty = false;
    private boolean isHungry = true;

    public boolean isThirsty() {
        return isThirsty;
    }

    public void setThirsty(boolean isThirsty) {
        this.isThirsty = isThirsty;
    }

    public boolean isHungry() {
        return isHungry;
    }

    public void setHungry(boolean isHungry) {
        this.isHungry = isHungry;
    }





}
