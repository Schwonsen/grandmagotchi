package com.uniks.grandmagotchi.data;

public class ClotheAttributes 
{
	
	private String name;
	private boolean isDirty;
	private boolean isCurrentDress;
	private int currentDress;
	private int currentDressMood;
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public boolean isDirty()
	{
		return isDirty;
	}
	public void setDirty(boolean isDirty)
	{
		this.isDirty = isDirty;
	}
	public boolean isCurrentDress()
	{
		return isCurrentDress;
	}
	public void setCurrentDress(boolean isCurrentDress)
	{
		this.isCurrentDress = isCurrentDress;
	}
	public int getCurrentDress()
	{
		return currentDress;
	}
	public void setCurrentDress(int currentDress)
	{
		this.currentDress = currentDress;
	}
	public int getCurrentDressMood()
	{
		return currentDressMood;
	}
	public void setCurrentDressMood(int currentDressMood)
	{
		this.currentDressMood = currentDressMood;
	}


}
