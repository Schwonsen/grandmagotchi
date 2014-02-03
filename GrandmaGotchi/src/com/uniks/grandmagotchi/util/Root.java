package com.uniks.grandmagotchi.util;

import java.util.LinkedList;

import android.support.v4.app.Fragment;

import com.uniks.grandmagotchi.data.Attributes;
import com.uniks.grandmagotchi.data.FoodAttributes;

public class Root
{
	
	public static boolean DEBUG = true;

	
	private static Root uniqueRootInstance;
	private static Attributes attributes = new Attributes();
	private static LinkedList<Fragment> roomList = new LinkedList<Fragment>();
	private static LinkedList<FoodAttributes> foodList = new LinkedList<FoodAttributes>();

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

}
