package com.uniks.grandmagotchi.util;

import com.uniks.grandmagotchi.data.Attributes;

public class Root
{
	
	public static boolean DEBUG = true;

	
	private static Root uniqueRootInstance;
	private static Attributes attributes = new Attributes();

	public static Attributes getAttributes()
	{
		return attributes;
	}
	public static void setAttributes(Attributes attributes)
	{
		Root.attributes = attributes;
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

}
