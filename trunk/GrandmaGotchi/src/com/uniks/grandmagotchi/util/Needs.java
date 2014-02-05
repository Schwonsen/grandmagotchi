package com.uniks.grandmagotchi.util;

/**
 * Created by Robin on 04.02.14.
 */
public enum Needs {
    DRINK(1), FOOD(2), MEDICINE(3), SLEEP(4), DRESS(5), WASH(6), BUY(7), CLEAN(8), DISHES(9), WALK(10);
    private int value;
    private Needs(int value) {
        this.value = value;
    }
    public static boolean compare(Needs a, Needs b){
        return a.value == b.value;
    }


}
