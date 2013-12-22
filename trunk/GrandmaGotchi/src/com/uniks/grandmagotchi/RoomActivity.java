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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RoomActivity extends FragmentActivity implements TabListener, SensorEventListener
{

	// positions for different Rooms 
	// (always write a constant for a new Room for better legibility of code)
	private static final int LIVINGROOM_POS = 0;
	private static final int KITCHEN_POS = 1;
	private static final int DRESSINGROOM_POS = 2;
	private static final int WASHINGROOM_POS = 3;
	private static final int BEDROOM_POS = 4;
	private static final int DRUGSTORE_POS = 5;
	
	// by adding or removing a room update the new number of rooms
	private static final int NUMBER_OF_ROOMS = 6;
	
	// the threshold for how hard you've to shake the device to react
	private static final int SHAKE_THRESHOLD = 6;
	
	private Fragment livingRoomFragment;
	private Fragment kitchenFragment;
	private Fragment dressingRoomFragment;
	private Fragment washingRoomFragment;
	private Fragment bedRoomFragment;
	private Fragment drugstoreFragment;
	
	private ViewPager viewPager;
	private ActionBar actionBar;
	
	private ImageButton btnWakeUp;
	
	// values for shake gesture
	// acceleration apart from gravity
	private float mAccel;
	// current acceleration including gravity
	private float mAccelCurrent;
	// last acceleration including gravity
	private float mAccelLast;
	
	private SensorManager sensorManager;
	private Sensor proxSensor;
	private Sensor accelSensor;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room);	
		
		Root.getUniqueRootInstance();
	
		this.setTitle(Root.getAttributes().getName() + " - Difficulty: " + Root.getAttributes().getDifficultyLevel());
		
		Root.getAttributes().setCurrentFragmentPosition(LIVINGROOM_POS);
		
		// initialization and registration of the sensor
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		sensorManager.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
		
		accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
		
		mAccel = 0.00f;
	    mAccelCurrent = SensorManager.GRAVITY_EARTH;
	    mAccelLast = SensorManager.GRAVITY_EARTH;
		
		// adding rooms to a static list to reuse them
		livingRoomFragment = new FragmentLivingRoom();
		Root.getRoomList().add(livingRoomFragment);
		kitchenFragment = new FragmentKitchen();
		Root.getRoomList().add(kitchenFragment);
		dressingRoomFragment = new FragmentDressingRoom();
		Root.getRoomList().add(dressingRoomFragment);
		washingRoomFragment = new FragmentWashingRoom();
		Root.getRoomList().add(washingRoomFragment);
		bedRoomFragment = new FragmentBedroom();
		Root.getRoomList().add(bedRoomFragment);
		drugstoreFragment = new FragmentDrugstore();
		Root.getRoomList().add(drugstoreFragment);

		// initialization and registration of the viewpager for swiping the fragments
		viewPager = (ViewPager) findViewById(R.id.pager);
		//since we extends FragmentActivity we can call FragAdapter with the Support Fragment Manager
		viewPager.setAdapter(new FragAdapter(getSupportFragmentManager()));
		
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			
			@Override
			public void onPageSelected(int position)
			{	
				Root.getAttributes().setCurrentFragmentPosition(position);
				
				// concatenates the actionBar and the viewPager to work together
				// if you change the fragment by viewPager the actionBar calls the position and change its status	
				// for the opposite site look at the onTabSelected Method down below
				actionBar.setSelectedNavigationItem(Root.getAttributes().getCurrentFragmentPosition());
				
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
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.living_room, menu);
        return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings_roomactivity_change_account:
            	startActivity(new Intent(RoomActivity.this, MainActivity.class));
    			RoomActivity.this.finish();	
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
	@Override
	public void onBackPressed()
	{
		
		   AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setMessage("Are you sure you want to exit?")
		           .setCancelable(false)
		           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		            	   
		            	   // gets the current time and date
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
		}

		@Override
		public Fragment getItem(int position)
		{
			
			//returns the fragment by its position
			
			Fragment fragment = null;
			
			if(position == LIVINGROOM_POS)
			{
				fragment = Root.getRoomList().get(LIVINGROOM_POS);
			}
			else if(position == KITCHEN_POS)
			{
				fragment = Root.getRoomList().get(KITCHEN_POS);
			}
			else if(position == DRESSINGROOM_POS)
			{
				fragment = Root.getRoomList().get(DRESSINGROOM_POS);
			}
			else if(position == WASHINGROOM_POS)
			{
				fragment = Root.getRoomList().get(WASHINGROOM_POS);
			}
			else if(position == BEDROOM_POS)
			{
				fragment = Root.getRoomList().get(BEDROOM_POS);
			}
			else if(position == DRUGSTORE_POS)
			{
				fragment = Root.getRoomList().get(DRUGSTORE_POS);
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
	
	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////// ACTIONBAR-TABS METHODS ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft)
	{
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{
		// slides to the fragment (room) you selected on the actionbar tabs
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft)
	{
	}

	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////// SENSOR METHODS ////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		// if grandma is awake, the sensor (light/proximity) changes and you are in the bedroom put her to sleep

		if(Root.getAttributes().getCurrentFragmentPosition() == BEDROOM_POS)
		{
			if(event.sensor.getType() == Sensor.TYPE_PROXIMITY && event.values[0] == 0.0f && 
					!Root.getAttributes().isSleeping())
			{
				btnWakeUp = (ImageButton) findViewById(R.id.btn_bedroom_wake_up);
				Message.message(this, "You turned the light off, Grandma sleeps now");
				btnWakeUp.setVisibility(View.VISIBLE);
			}
		}
		if(Root.getAttributes().getCurrentFragmentPosition() == WASHINGROOM_POS)
		{
			if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			{
				 float x = event.values[0];
			     float y = event.values[1];
			     float z = event.values[2];
			     mAccelLast = mAccelCurrent;
			     mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
			     float delta = mAccelCurrent - mAccelLast;
			     mAccel = mAccel * 0.9f + delta; // perform low-cut filter
			     
			     if(mAccel > SHAKE_THRESHOLD)
			     {
			    	 Message.message(this, "Grandma washed cloth " + mAccel);
			     }
			     
		
			}
		}	
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////BUTTON CLICK METHODS/////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	
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
		Message.message(this,"Grandma is not hungry anymore");
	}
	
	public void btnOnClickDrink(View view)
	{
		Message.message(this,"Grandma is not thirsty anymore");
	}
	
	public void btnOnClickBedroomHelp(View view)
	{
		Message.message(this,"Put Grandma to sleep by putting your hand over proximity Sensor");
	}
	
	public void btnOnClickWashingroomHelp(View view)
	{
		Message.message(this,"Shake Device to clean laundry");
	}

	public void btnOnClickWakeUp(View view)
	{
		Message.message(this, "Grandma is awake");
		Root.getAttributes().setSleeping(false);
		
		btnWakeUp = (ImageButton) findViewById(R.id.btn_bedroom_wake_up);
		btnWakeUp.setVisibility(View.INVISIBLE);
	}
}

