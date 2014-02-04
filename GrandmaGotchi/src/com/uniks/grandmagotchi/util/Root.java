package com.uniks.grandmagotchi.util;

import java.util.LinkedList;

import android.support.v4.app.Fragment;

import com.uniks.grandmagotchi.data.Attributes;
import com.uniks.grandmagotchi.data.ClotheAttributes;
import com.uniks.grandmagotchi.data.FoodAttributes;
import com.uniks.grandmagotchi.data.MedicAttributes;

public class Root
{
	
	public static boolean DEBUG = true;

	
	private static Root uniqueRootInstance;
	private static Attributes attributes = new Attributes();
	private static LinkedList<Fragment> roomList = new LinkedList<Fragment>();
	private static LinkedList<FoodAttributes> foodList = new LinkedList<FoodAttributes>();
	private static LinkedList<ClotheAttributes> clotheList = new LinkedList<ClotheAttributes>();
	private static LinkedList<MedicAttributes> medicList = new LinkedList<MedicAttributes>();

    private int foodTime = 0;


    private boolean isThirsty = false;
    private boolean isHungry = true;
    private boolean inForeground = true;
    private boolean isSleeping = false;
    private boolean isDressed = false;
    private boolean med = false;
    private boolean unhealthyFood = false;
    private LinkedList<Needs> needs= new LinkedList<Needs>();

    public void addNeed(Needs need){
        for(Needs exNeed : needs){
            if(Needs.compare(need, exNeed))
                return;
        }
        needs.add(need);
    }
    public void removeNeed(Needs need){
        for(Needs exNeed : needs){
            if(Needs.compare(need, exNeed)){
                needs.remove(exNeed);
                break;
            }
        }
    }
    public boolean containsNeed(Needs need){
        for(Needs exNeed : needs){
            if(Needs.compare(exNeed, need))
                return true;
        }
        return false;
    }
    public LinkedList<Needs> getAllNeeds(){
        return needs;
    }

    public boolean isUnhealthyFood() {
        return unhealthyFood;
    }

    public void setUnhealthyFood(boolean unhealthyFood) {
        this.unhealthyFood = unhealthyFood;
    }

    public boolean isDressed() {
        return isDressed;
    }

    public void setDressed(boolean isDressed) {
        this.isDressed = isDressed;
    }

    public boolean isMed() {
        return med;
    }

    public void setMed(boolean morningMed) {
        this.med = morningMed;
    }

    public boolean isSleeping() {
        return isSleeping;
    }

    public void setSleeping(boolean isSleeping) {
        this.isSleeping = isSleeping;
    }

    public boolean isInForeground() {
        return inForeground;
    }

    public void setInForeground(boolean inForeground) {
        this.inForeground = inForeground;
    }
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



    public static Attributes getAttributes()
	{
		return attributes;
	}
	public static void setAttributes(Attributes attributes)
	{
		Root.attributes = attributes;
	}

	public static LinkedList<Fragment> getRoomList()
	{
		return roomList;
	}
	public static void setRoomList(LinkedList<Fragment> roomList)
	{
		Root.roomList = roomList;
	}
	
	// Method to get unique Root object (Singleton)
	public static Root getUniqueRootInstance()
	{
		if(uniqueRootInstance == null)
		{
			uniqueRootInstance = new Root();
		}
		
		return uniqueRootInstance;
	}
	
	public static LinkedList<FoodAttributes> getFoodList()
	{
		return foodList;
	}
	public static void setFoodList(LinkedList<FoodAttributes> foodList)
	{
		Root.foodList = foodList;
	}
	
	public static LinkedList<ClotheAttributes> getClotheList() {
		// TODO Auto-generated method stub
		return clotheList;
	}
	public static void setClotheList(LinkedList<ClotheAttributes> clotheList)
	{
		Root.clotheList = clotheList;
	}
	
	public static LinkedList<MedicAttributes> getMedicList() {
		// TODO Auto-generated method stub
		return medicList;
	}
	public static void setMedicList(LinkedList<MedicAttributes> clotheList)
	{
		Root.medicList = medicList;
	}

}
