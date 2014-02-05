package com.uniks.grandmagotchi.data;

public class Attributes {
	
	//data attributes
	private String name;
	private String id;
	private boolean isSleeping;
	
	private int currentFragmentPosition;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public boolean isSleeping()
	{
		return isSleeping;
	}

	public void setSleeping(boolean isSleeping)
	{
		this.isSleeping = isSleeping;
	}

	public int getCurrentFragmentPosition()
	{
		return currentFragmentPosition;
	}

	public void setCurrentFragmentPosition(int currentFragmentPosition)
	{
		this.currentFragmentPosition = currentFragmentPosition;
	}
}
