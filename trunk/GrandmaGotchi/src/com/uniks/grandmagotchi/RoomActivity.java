package com.uniks.grandmagotchi;


import java.util.Calendar;

import android.content.IntentFilter;
import android.net.Uri;

import android.view.*;
import com.uniks.grandmagotchi.data.FoodAttributes;
import com.uniks.grandmagotchi.data.RoomAttributes;
import com.uniks.grandmagotchi.rooms.FragmentBedroom;
import com.uniks.grandmagotchi.rooms.FragmentDressingRoom;
import com.uniks.grandmagotchi.rooms.FragmentDrugstore;
import com.uniks.grandmagotchi.rooms.FragmentKitchen;
import com.uniks.grandmagotchi.rooms.FragmentLivingRoom;
import com.uniks.grandmagotchi.rooms.FragmentOutside;
import com.uniks.grandmagotchi.rooms.FragmentSupermarket;
import com.uniks.grandmagotchi.rooms.FragmentWashingRoom;
import com.uniks.grandmagotchi.util.*;

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
import android.widget.ImageButton;
import com.uniks.grandmagotchi.util.timer.receiver.DrinkReceiver;
import com.uniks.grandmagotchi.util.timer.receiver.FoodReceiver;
import com.uniks.grandmagotchi.util.timer.services.DrinkTimer;
import com.uniks.grandmagotchi.util.timer.services.FoodTimer;
import android.widget.ImageView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RoomActivity extends FragmentActivity implements TabListener, SensorEventListener
{

	// positions for different Rooms 
	// (always write a constant for a new Room for better legibility of code)
	public static final int LIVINGROOM_POS = 0;
    public static final int KITCHEN_POS = 1;
    public static final int DRESSINGROOM_POS = 2;
    public static final int WASHINGROOM_POS = 3;
    public static final int BEDROOM_POS = 4;
    public static final int DRUGSTORE_POS = 5;
    public static final int SUPERMARKET_POS = 6;
    public static final int OUTSIDE_POS = 7;
	
	// by adding or removing a room update the new number of rooms
	private static final int NUMBER_OF_ROOMS = 8;
	
	// the threshold for how hard you've to shake the device to react
	private static final int SHAKE_THRESHOLD = 6;
	
	
	// entries for the foodlist
//	private static final String YOGURT   = "yogurt";
//	private static final String FLAKES   = "flakes";
//	private static final String EGGS     = "eggs";
//	private static final String FISH     = "fish";
//	private static final String PIZZA    = "pizza";
//	private static final String SALAT    = "salat";
//	private static final String PASTA    = "pasta";
//	private static final String POTATOS  = "potatos";
//	private static final String SANDWICH = "sandwich";
//	private static final String WATER    = "water";
	
	private static FoodAttributes YOGURT = new FoodAttributes();
	private static FoodAttributes FLAKES = new FoodAttributes();
	private static FoodAttributes EGGS = new FoodAttributes();
	private static FoodAttributes FISH = new FoodAttributes();
	private static FoodAttributes PIZZA = new FoodAttributes();
	private static FoodAttributes SALAT = new FoodAttributes();
	private static FoodAttributes PASTA = new FoodAttributes();
	private static FoodAttributes POTATOS = new FoodAttributes();
	private static FoodAttributes SANDWICH = new FoodAttributes();
	private static FoodAttributes WATER = new FoodAttributes();
	
	private Fragment livingRoomFragment;
	private Fragment kitchenFragment;
	private Fragment dressingRoomFragment;
	private Fragment washingRoomFragment;
	private Fragment bedRoomFragment;
	private Fragment drugstoreFragment;
	private Fragment supermarketFragment;
	private Fragment outsideFragment;
	
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
    private FoodReceiver mFoodReceiver;
    private DrinkReceiver mDrinkReceiver;

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room);	
		
		Root.getUniqueRootInstance();
	
		this.setTitle(Root.getAttributes().getName() + " - Difficulty: " + Root.getAttributes().getDifficultyLevel());
		
		init();
		
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
		supermarketFragment = new FragmentSupermarket();
		Root.getRoomList().add(supermarketFragment);
		outsideFragment = new FragmentOutside();
		Root.getRoomList().add(outsideFragment);

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
		
		ActionBar.Tab tabSupermarket = actionBar.newTab();
		tabSupermarket.setText("Supermarket");
		tabSupermarket.setTabListener(this);
		
		ActionBar.Tab tabOutside = actionBar.newTab();
		tabOutside.setText("Outside");
		tabOutside.setTabListener(this);
		
		actionBar.addTab(tabLivingRoom);
		actionBar.addTab(tabKitchen);
		actionBar.addTab(tabDressingRoom);
		actionBar.addTab(tabWashingRoom);
		actionBar.addTab(tabBedroom);
		actionBar.addTab(tabDrugstore);
		actionBar.addTab(tabSupermarket);
		actionBar.addTab(tabOutside);

        //Timer Listener
        IntentFilter mStatusIntentFilter = new IntentFilter(FoodReceiver.BROADCAST_ACTION);
        mFoodReceiver =  new FoodReceiver(this);
        registerReceiver(mFoodReceiver, mStatusIntentFilter);
        IntentFilter drinkStatusIntentFilter = new IntentFilter(DrinkReceiver.BROADCAST_ACTION);
        mDrinkReceiver =  new DrinkReceiver(this);
        registerReceiver(mDrinkReceiver, drinkStatusIntentFilter);

	}

	private void init()
	{		
		YOGURT.setName("Yogurt");
		YOGURT.setCount(3);
		FLAKES.setName("Flakes");
		FLAKES.setCount(1);
		EGGS.setName("Eggs");
		EGGS.setCount(1);
		FISH.setName("Fish");
		FISH.setCount(1);
		PIZZA.setName("Pizza");
		PIZZA.setCount(1);
		SALAT.setName("Salat");
		SALAT.setCount(1);
		PASTA.setName("Pasta");
		PASTA.setCount(1);
		POTATOS.setName("Potatos");
		POTATOS.setCount(1);
		SANDWICH.setName("Sandwich");
		SANDWICH.setCount(1);
		WATER.setName("Water");
		WATER.setCount(3);
		
		Root.getFoodList().add(YOGURT);
		Root.getFoodList().add(FLAKES);
		Root.getFoodList().add(EGGS);
		Root.getFoodList().add(FISH);
		Root.getFoodList().add(PIZZA);
		Root.getFoodList().add(SALAT);
		Root.getFoodList().add(PASTA);
		Root.getFoodList().add(POTATOS);
		Root.getFoodList().add(SANDWICH);
		Root.getFoodList().add(WATER);
		
	}

    @Override
    public void onPause(){
        super.onPause();
        unregisterReceiver(mDrinkReceiver);
        unregisterReceiver(mFoodReceiver);
    }
    @Override
    public void onResume(){
        super.onResume();
        IntentFilter mStatusIntentFilter = new IntentFilter(FoodReceiver.BROADCAST_ACTION);
        registerReceiver(mFoodReceiver, mStatusIntentFilter);
        IntentFilter drinkStatusIntentFilter = new IntentFilter(DrinkReceiver.BROADCAST_ACTION);
        registerReceiver(mDrinkReceiver, drinkStatusIntentFilter);
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
			else if(position == SUPERMARKET_POS)
			{
				fragment = Root.getRoomList().get(SUPERMARKET_POS);
			}
			else if(position == OUTSIDE_POS)
			{
				fragment = Root.getRoomList().get(OUTSIDE_POS);
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
				
				ImageView grannyBedroom = (ImageView) findViewById(R.id.imageGrandma);
				grannyBedroom.setImageResource(R.drawable.image_sleeping_grandma);
				
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
        createTimer(5000, FoodTimer.class);

    }
	
	public void btnOnClickGame(View view)
	{
		startActivity(new Intent(RoomActivity.this, TicTacToeGameActivity.class));
//		RoomActivity.this.finish();
	}
	
	public void btnOnClickMusic(View view)
	{
		String videoID = "raluLqdSrJ4";
		// TODO: Sanitize input to prevent code injection
		startActivity(new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://m.youtube.com/watch?v=" + videoID)));
	}
	
	public void btnOnClickSoap(View view) 
	{
		Message.message(this, "The Grandma is clean again!");
        createTimer(5000, FoodTimer.class);

    }
	
	public void btnOnClickDrugs(View view)
	{
		Message.message(this, "Grandma is fit again!");
        createTimer(5000, FoodTimer.class);

    }
	
	public void btnOnClickWash(View view)
	{
		Message.message(this,"All clothes are clean again!");
	}
	
	public void btnOnClickBrush(View view)
	{
		Message.message(this,"The house is clean again");
        createTimer(5000, FoodTimer.class);

    }
	
	public void btnOnClickEat(View view)
	{
        if(RoomAttributes.getInstance().isHungry()){
		    startActivity(new Intent(RoomActivity.this, MealActivity.class));
            createTimer(5000, FoodTimer.class);
        }
        else
            Message.message(this, "Grandma is not hungry at the moment.");
	}
	
	public void btnOnClickShopcart(View view)
	{
		startActivity(new Intent(RoomActivity.this, ShopActivity.class));
	}
	
	public void btnOnClickDrink(View view)
	{
		for(FoodAttributes foodAttribute : Root.getFoodList())
		{
			if(foodAttribute.getName().equals("Water"))
			{
				if(foodAttribute.getCount() > 0)
				{
					foodAttribute.setCount(foodAttribute.getCount() - 1);
					Message.message(this,"Grandma is not thirsty anymore");
			        //every 8 hours
			        createTimer(10000, DrinkTimer.class);
                    RoomAttributes.getInstance().setThirsty(false);
				}
				else
				{
					Message.message(this, "No Items, buy new Water");
				}
			}
		}
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
		
		ImageView grannyBedroom = (ImageView) findViewById(R.id.imageGrandma);
		grannyBedroom.setImageResource(R.drawable.image_grandma_confused);
		
		btnWakeUp = (ImageButton) findViewById(R.id.btn_bedroom_wake_up);
		btnWakeUp.setVisibility(View.INVISIBLE);
	} 
    private void createTimer(int countdown, Class target){
        Intent mServiceIntent = new Intent(getApplicationContext(), target);
        mServiceIntent.putExtra("countdown", countdown);
        startService(mServiceIntent);
    }

}

