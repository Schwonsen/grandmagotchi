package com.uniks.grandmagotchi;


import java.util.Calendar;

import com.uniks.grandmagotchi.rooms.FragmentBedroom;
import com.uniks.grandmagotchi.rooms.FragmentDressingRoom;
import com.uniks.grandmagotchi.rooms.FragmentDrugstore;
import com.uniks.grandmagotchi.rooms.FragmentKitchen;
import com.uniks.grandmagotchi.rooms.FragmentLivingRoom;
import com.uniks.grandmagotchi.rooms.FragmentWashingRoom;
import com.uniks.grandmagotchi.util.Message;
import com.uniks.grandmagotchi.util.Root;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RoomActivity extends FragmentActivity implements TabListener
{

	// positions for different Rooms 
	// (always write a constant for a new Room for better legibility of code)
	private static final int LIVINGROOM_POS = 0;
	private static final int KITCHEN_POS = 1;
	private static final int DRESSINGROOM_POS = 2;
	private static final int WASHINGROOM_POS = 3;
	private static final int BEDROOM_POS = 4;
	private static final int DRUGSTORE_POS = 5;
	
	private static final int NUMBER_OF_ROOMS = 6;

	private ImageButton btnWakeUp;
	
	private ViewPager viewPager;
	private ActionBar actionBar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room);	


		viewPager = (ViewPager) findViewById(R.id.pager);
		//since we extends FragmentActivity we can call FragAdapter with the Support Fragment Manager
		viewPager.setAdapter(new FragAdapter(getSupportFragmentManager()));
		
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			
			@Override
			public void onPageSelected(int position)
			{
			// concatenates the actionBar and the viewPager to work together
			// if you change the fragment by viewPager the actionBar calls the position and change its status	
			// for the opposite site look at the onTabSelected Method down below
				actionBar.setSelectedNavigationItem(position);
				if(Root.DEBUG) Log.d("GrandmaGotchi", "onPageSelected at position " + position);
				
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
			{
				if(Root.DEBUG) Log.d("GrandmaGotchi", "onPageScrolled at position " + position + " from position " + positionOffset +
						" with number of pixels = " + positionOffsetPixels);
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state)
			{
				if(Root.DEBUG) 
				{
					if(state == ViewPager.SCROLL_STATE_IDLE)
					{
						Log.d("GrandmaGotchi", "onPageScrollStateChanged IDLE");
					}
					else if(state == ViewPager.SCROLL_STATE_DRAGGING)
					{
						Log.d("GrandmaGotchi", "onPageScrollStateChanged DRAGGING");
					}
					else if(state == ViewPager.SCROLL_STATE_SETTLING)
					{
						Log.d("GrandmaGotchi", "onPageScrollStateChanged SETTLING");
					}
				}
			}
		});
		
		actionBar = getActionBar();
		// using tabs in action bar
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// initialization and adding of rooms in tabs 
		ActionBar.Tab tabLivingRoom = actionBar.newTab();
		tabLivingRoom.setText("Living Room");
		tabLivingRoom.setTabListener(this);
		
		ActionBar.Tab tabKitchen = actionBar.newTab();
		tabKitchen.setText("Kitchen");
		tabKitchen.setTabListener(this);
		
		ActionBar.Tab tabDressingRoom = actionBar.newTab();
		tabDressingRoom.setText("Dressing Room");
		tabDressingRoom.setTabListener(this);
		
		ActionBar.Tab tabWashingRoom = actionBar.newTab();
		tabWashingRoom.setText("Washing Room");
		tabWashingRoom.setTabListener(this);
		
		ActionBar.Tab tabBedroom = actionBar.newTab();
		tabBedroom.setText("Bedroom");
		tabBedroom.setTabListener(this);
		
		ActionBar.Tab tabDrugstore = actionBar.newTab();
		tabDrugstore.setText("Drugstore");
		tabDrugstore.setTabListener(this);
		
		actionBar.addTab(tabLivingRoom);
		actionBar.addTab(tabKitchen);
		actionBar.addTab(tabDressingRoom);
		actionBar.addTab(tabWashingRoom);
		actionBar.addTab(tabBedroom);
		actionBar.addTab(tabDrugstore);
		

	}

	@Override
	public void onBackPressed()
	{
		   AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setMessage("Are you sure you want to exit?")
		           .setCancelable(false)
		           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		            	   
//		            	   SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
//		            	   String currentDateandTime = sdf.format(new Date());
		            	   
		            	   String date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		            	   
		            	   if(Root.DEBUG) Message.message(RoomActivity.this, date);
		            	   
		                   RoomActivity.this.finish();
		               }
		           })
		           .setNegativeButton("No", new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		                    dialog.cancel();
		               }
		           });
		    AlertDialog alert = builder.create();
		    alert.show();
	}
	
	class FragAdapter extends FragmentPagerAdapter
	{

		public FragAdapter(FragmentManager fm)
		{
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position)
		{
			
			//returns the fragment by its position
			
			Fragment fragment = null;
			
			if(position == LIVINGROOM_POS)
			{
				fragment = new FragmentLivingRoom();
			}
			else if(position == KITCHEN_POS)
			{
				fragment = new FragmentKitchen();
			}
			else if(position == DRESSINGROOM_POS)
			{
				fragment = new FragmentDressingRoom();
			}
			else if(position == WASHINGROOM_POS)
			{
				fragment = new FragmentWashingRoom();
			}
			else if(position == BEDROOM_POS)
			{
				fragment = new FragmentBedroom();
			}
			else if(position == DRUGSTORE_POS)
			{
				fragment = new FragmentDrugstore();
			}
			
			
			return fragment;
		}

		@Override
		public int getCount()
		{
			// returns the number of rooms
			return NUMBER_OF_ROOMS;
		}
		
	}
	
	public void btnOnClickTelevision(View view) 
	{
		Message.message(this, "Now you are watching TV!");
	}
	
	public void btnOnClickSoap(View view) 
	{
		 Message.message(this, "The Grandma is clean again!");
	}
	
	public void btnOnClickDrugs(View view)
	{
		Message.message(this, "Grandma is fit again!");
	}
	
	public void btnOnClickBrush(View view)
	{
		Message.message(this,"The house is clean again");
	}
	
	public void btnOnClickEat(View view)
	{
		Message.message(this,"Grandma is no more hungry");
	}
	
	public void btnOnClickDrink(View view)
	{
		Message.message(this,"Grandma is no more thirsty");
	}
	
	public void btnOnClickBedroomHelp(View view)
	{
		Message.message(this,"Put Grandma to sleep by putting your hand over light Sensor");
	}

	public void btnOnClickWakeUp(View view)
	{
		Message.message(this, "Grandma is awake");
		Root.getAttributes().setSleeping(false);
		
		btnWakeUp = (ImageButton) findViewById(R.id.btn_bedroom_wake_up);

		btnWakeUp.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub
		
	}
	
}

