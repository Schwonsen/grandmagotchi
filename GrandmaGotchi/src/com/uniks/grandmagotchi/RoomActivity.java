package com.uniks.grandmagotchi;


import java.util.Calendar;
import java.util.Random;

import android.app.NotificationManager;


import android.content.IntentFilter;
import android.net.Uri;
import android.view.*;

import com.uniks.grandmagotchi.data.DatabaseAdapter;

import android.os.AsyncTask;
import android.view.View.OnClickListener;





import com.uniks.grandmagotchi.data.ClotheAttributes;
import com.uniks.grandmagotchi.data.FoodAttributes;
import com.uniks.grandmagotchi.rooms.FragmentBedroom;
import com.uniks.grandmagotchi.rooms.FragmentDressingRoom;
import com.uniks.grandmagotchi.rooms.FragmentDrugstore;
import com.uniks.grandmagotchi.rooms.FragmentKitchen;
import com.uniks.grandmagotchi.rooms.FragmentLivingRoom;
import com.uniks.grandmagotchi.rooms.FragmentOutside;
import com.uniks.grandmagotchi.rooms.FragmentSupermarket;
import com.uniks.grandmagotchi.rooms.FragmentTest;
import com.uniks.grandmagotchi.rooms.FragmentWashingRoom;
import com.uniks.grandmagotchi.util.*;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.PopupWindow;

import com.uniks.grandmagotchi.util.timer.OverallTimings;
import com.uniks.grandmagotchi.util.timer.receiver.DrinkReceiver;
import com.uniks.grandmagotchi.util.timer.receiver.FoodReceiver;
import com.uniks.grandmagotchi.util.timer.receiver.MedReceiver;
import com.uniks.grandmagotchi.util.timer.services.DrinkDeathTimer;
import com.uniks.grandmagotchi.util.timer.services.DrinkTimer;
import com.uniks.grandmagotchi.util.timer.services.FoodDeathTimer;
import com.uniks.grandmagotchi.util.timer.services.FoodTimer;

import android.widget.ImageView;



@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RoomActivity extends FragmentActivity implements TabListener, SensorEventListener
{
	public static int currentgrandma = R.drawable.image_grandma_normal;
	public static int currentgrandmacloth ;
	public static int currentgrandmastatus;//0 normal /1 happy /2 ill /3confused /4 ideas
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
	public static final int TEST_POS = 8;	
	public static final long foodTimer =  18000000l;
    public static final long drinkTimer = 19000000l;

	final Context context = this;
	
	// by adding or removing a room update the new number of rooms
	private static final int NUMBER_OF_ROOMS = 9;
	
	// the threshold for how hard you've to shake the device to react
	private static final int SHAKE_THRESHOLD = 6;
	
	private DatabaseAdapter databaseHandler;
	
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
	private static ClotheAttributes PINK = new ClotheAttributes();
	private static ClotheAttributes RED = new ClotheAttributes();
	private static ClotheAttributes BLACK = new ClotheAttributes();
	private static ClotheAttributes GREEN = new ClotheAttributes();
	private static ClotheAttributes BLUE = new ClotheAttributes();
	
	private Fragment livingRoomFragment;
	private Fragment kitchenFragment;
	private Fragment dressingRoomFragment;
	private Fragment washingRoomFragment;
	private Fragment bedRoomFragment;
	private Fragment drugstoreFragment;
	private Fragment supermarketFragment;
	private Fragment outsideFragment;
	private Fragment testFragment;
	
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
	private MedReceiver mMedReceiver;
	private AsyncTask aTask;
	private ImageView grannyImage;

	private boolean youtubebuttonclicked = false;
	protected int dancecounter=0;

	private PopupWindow fadePopup;
	private boolean updatehandler=false;
	private int eyestatus = 4;
	private ImageView eyesImage;
	static boolean grandmaondrugs = false ;
	private ImageView grandmaondrugsImage;
	static int grandmaondrugscounter;


	static int[][] grandmaimagearray=new int[5][6];
	
	public void updatestatusgrandma(){
		if (Root.getUniqueRootInstance().containsNeed(Needs.MEDICINE)){
			currentgrandmastatus=2;
			
		}else if (Root.getUniqueRootInstance().containsNeed(Needs.SLEEP)){
			currentgrandmastatus=3;			
		}else if(Root.getUniqueRootInstance().containsNeed(Needs.WALK)==true){
			currentgrandmastatus=4;
		}else if(Root.getUniqueRootInstance().containsNeed(Needs.CLEAN)==true){
			currentgrandmastatus=4;
		}
		else if(Root.getUniqueRootInstance().containsNeed(Needs.WASH)==true){
			currentgrandmastatus=4;
		}else if(Root.getUniqueRootInstance().containsNeed(Needs.DISHES)==true){
			currentgrandmastatus=4;
		}else if(Root.getUniqueRootInstance().containsNeed(Needs.BUY)==true){
			currentgrandmastatus=4;
		}
		else if(Root.getUniqueRootInstance().isThirsty()==true){
			currentgrandmastatus=5;
		}else if(Root.getUniqueRootInstance().isHungry()==true){
			currentgrandmastatus=5;
		}else{currentgrandmastatus=1;}
	}
	public void updateeyestatus(){	
	switch(eyestatus){
	case 1 :
		eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);
		eyesImage.setImageResource(R.drawable.eyes1);
		break;
	case 2 :
		eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);
		eyesImage.setImageResource(R.drawable.eyes2);
		break;
	case 3 :
		eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);
		eyesImage.setImageResource(R.drawable.eyes3);
		break;
	case 4 :
		eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);
		eyesImage.setImageResource(R.drawable.eyes4);
		break;
	case 5 :
		eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);
		eyesImage.setImageResource(R.drawable.eyes4);
		break;
	case 6 :
		eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);
		eyesImage.setImageResource(R.drawable.eyes4);
		break;
	case 7 :
		eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);
		eyesImage.setImageResource(R.drawable.eyes4);
		break;
	case 8 :
		eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);
		eyesImage.setImageResource(R.drawable.eyes4);
		break;
	case 9 :
		eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);
		eyesImage.setImageResource(R.drawable.eyes4);
		break;
	case 10 :
		eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);
		eyesImage.setImageResource(R.drawable.eyes4);
		break;
	case 11 :
		eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);
		eyesImage.setImageResource(R.drawable.eyes4);
		break;
	case 12 :
		eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);
		eyesImage.setImageResource(R.drawable.eyes4);
		break;
	case 13 :
		eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);
		eyesImage.setImageResource(R.drawable.eyes4);
		break;
	case 14 :
		eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);
		eyesImage.setImageResource(R.drawable.eyes4);
		break;
	case 15 :
		eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);
		eyesImage.setImageResource(R.drawable.eyes4);
		break;
	case 16 :
		eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);
		eyesImage.setImageResource(R.drawable.eyes4);
		break;
	
	}	
	
	if(eyestatus<=16){
    eyestatus=eyestatus+1;
	}else{eyestatus=1;}
	}
	
	
	
	public void setgrandmaondrugs(){
		if(grandmaondrugscounter!=0){
		Handler handler = new Handler(); 
		handler.postDelayed(new Runnable() { 
			public void run() { 
	        	 if(grandmaondrugscounter<=10){
	        	 grandmaondrugscounter=grandmaondrugscounter+1;
	        	 setgrandmaondrugs();}else{grandmaondrugscounter=0;}
	         } 
	    }, 200);}
		
	switch(grandmaondrugscounter){
	case 1 :
		grandmaondrugsImage = (ImageView) findViewById(R.id.imageGrandma);
		grandmaondrugsImage.setImageResource(R.drawable.grandmaondrugs1);
		break;
	case 2 :
		grandmaondrugsImage = (ImageView) findViewById(R.id.imageGrandma);
		grandmaondrugsImage.setImageResource(R.drawable.grandmaondrugs2);
		break;
	case 3 :
		grandmaondrugsImage = (ImageView) findViewById(R.id.imageGrandma);
		grandmaondrugsImage.setImageResource(R.drawable.grandmaondrugs3);
		break;
	case 4 :
		grandmaondrugsImage = (ImageView) findViewById(R.id.imageGrandma);
		grandmaondrugsImage.setImageResource(R.drawable.grandmaondrugs4);
		break;
	case 5 :
		grandmaondrugsImage = (ImageView) findViewById(R.id.imageGrandma);
		grandmaondrugsImage.setImageResource(R.drawable.grandmaondrugs5);
		break;
	case 6 :
		grandmaondrugsImage = (ImageView) findViewById(R.id.imageGrandma);
		grandmaondrugsImage.setImageResource(R.drawable.grandmaondrugs6);
		break;
	case 7 :
		grandmaondrugsImage = (ImageView) findViewById(R.id.imageGrandma);
		grandmaondrugsImage.setImageResource(R.drawable.grandmaondrugs7);
		break;
	case 8 :
		grandmaondrugsImage = (ImageView) findViewById(R.id.imageGrandma);
		grandmaondrugsImage.setImageResource(R.drawable.grandmaondrugs8);
		break;
	case 9 :
		grandmaondrugsImage = (ImageView) findViewById(R.id.imageGrandma);
		grandmaondrugsImage.setImageResource(R.drawable.grandmaondrugs9);
		break;
	case 10 :
		grandmaondrugsImage = (ImageView) findViewById(R.id.imageGrandma);
		grandmaondrugsImage.setImageResource(R.drawable.grandmaondrugs10);
		break;

	}}
	public void updatecurrentgrandmacomplete(){	
		//Toast.makeText(getApplicationContext(), "update", Toast.LENGTH_SHORT).show();
	updatestatusgrandma();
	updatecurrentgrandma();
	updatecurrentgrandmaimage();
	updateeyestatus();
	if(grandmaondrugscounter!=0){
		setgrandmaondrugs();
	}
	}
	
	//updatet omabild
	public void startupdatehandler(){
		
		if(updatehandler==true){
		Handler handler = new Handler(); 
		handler.postDelayed(new Runnable() { 
			public void run() { 
				if (!Root.getAttributes().isSleeping()&&dancecounter==0&&grandmaondrugscounter==0){//im schlaf und beim tanzen soll nicht upgedated werden
				 updatecurrentgrandmacomplete();Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_SHORT).show();				 
				}else{eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
		         eyesImage.setImageResource(R.drawable.eyeszero);}
	        	 startupdatehandler();
	         } 
	    }, 200);}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room);	
		
		Root.getUniqueRootInstance();
	
		this.setTitle(Root.getAttributes().getName());
		
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
		
		updatehandler=true;
		startupdatehandler();
		updatecurrentgrandmacomplete();
		
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
		testFragment = new FragmentTest();
		Root.getRoomList().add(testFragment);

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
		
		ActionBar.Tab tabTest = actionBar.newTab();
		tabTest.setText("Test");
		tabTest.setTabListener(this);
		
		actionBar.addTab(tabLivingRoom);
		actionBar.addTab(tabKitchen);
		actionBar.addTab(tabDressingRoom);
		actionBar.addTab(tabWashingRoom);
		actionBar.addTab(tabBedroom);
		actionBar.addTab(tabDrugstore);
		actionBar.addTab(tabSupermarket);
		actionBar.addTab(tabOutside);
		actionBar.addTab(tabTest
				
				);

		//Timer Listener
		try{
			IntentFilter mStatusIntentFilter = new IntentFilter(FoodReceiver.BROADCAST_ACTION);
			mFoodReceiver =  new FoodReceiver(this);
			registerReceiver(mFoodReceiver, mStatusIntentFilter);
			IntentFilter drinkStatusIntentFilter = new IntentFilter(DrinkReceiver.BROADCAST_ACTION);
			mDrinkReceiver =  new DrinkReceiver(this);
			registerReceiver(mDrinkReceiver, drinkStatusIntentFilter);
			IntentFilter medStatusIntentFilter = new IntentFilter(MedReceiver.BROADCAST_ACTION);
			mMedReceiver = new MedReceiver(this);
			registerReceiver(mMedReceiver, medStatusIntentFilter);
		}catch(Exception e){
			Log.d("grandmaReceiver", "double creation");
		}
		if(aTask == null){
			aTask = new OverallTimings(this);
		}
        if(aTask.getStatus() != AsyncTask.Status.RUNNING){
            aTask.execute();
        }
	}
	//Deathscreen
	public static void diedPopup(final Context ctx) {
		final Dialog dialog = new Dialog(ctx);
		dialog.setContentView(R.layout.dialog_dead_granny);
		dialog.setTitle(R.string.dead);
		dialog.setCancelable(false);
		
		Button dialogButton = (Button) dialog.findViewById(R.id.btn_respwan);
		
		// if button is clicked, respawn
		dialogButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message.message(ctx, "Start a new Game with your granny!");
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	protected void onPause(Bundle savedInstanceState)
	{
		writeIntoDatabase();
		Root.setAlreadyInit(false);
		updatehandler=false;
	}
	protected void onResume(Bundle savedInstanceState)
	{
		if(!Root.isAlreadyInit())
		{
			init();
		}
		updatehandler=true;
		startupdatehandler();
	}

	private void init()
	{		
		
		Root.setAlreadyInit(true);
		
		initializegranmaimagearray();//fuer grandmaimageaustausch
		
		databaseHandler = new DatabaseAdapter(this);
		
		

		if(Root.isCalledFromExistingAccount())
		{
			
			Root.setFoodList(databaseHandler.getFoodDataById(Root.getId()));
			Root.setClotheList(databaseHandler.getClothDataById(Root.getId()));
			
			Cursor cursorMoodCloth = databaseHandler.getMoodClothById(Root.getId());
			if(cursorMoodCloth != null && cursorMoodCloth.moveToFirst())
			{
				cursorMoodCloth.moveToFirst();
				String strcurrentgrandmacloth = cursorMoodCloth.getString(0);
				int grandmacloth = Integer.valueOf(strcurrentgrandmacloth);
				String strcurrentgrandmastatus = cursorMoodCloth.getString(1);
				int grandmastatus = Integer.valueOf(strcurrentgrandmastatus);
				currentgrandmacloth = grandmacloth;
				currentgrandmastatus = grandmastatus;
				
				Log.d("INIT CLOTH NUMBER: " , "--> " + grandmacloth);
				Log.d("INIT STATUS NUMBER: " , "--> " + grandmastatus);
			}
			
			Cursor cursorFood = databaseHandler.getCurrentTimeByNameAndId(Root.getId(), "FoodTimer");
			if(cursorFood != null && cursorFood.moveToFirst())
			{
				cursorFood.moveToFirst();
				String startFoodTime = cursorFood.getString(0);
				String currentFoodTime = cursorFood.getString(1);
				
				Log.d("START FOOD TIME: " , "--> " + startFoodTime);
				Log.d("CURRENT FOOD TIME: " , "--> " + currentFoodTime);
				
				long currentFood = (Long.valueOf(currentFoodTime) + Long.valueOf(startFoodTime)) - System.currentTimeMillis();
				
				if(currentFood < 0)
				{
					
					if(currentFood < -(60*60*1000))
					{
						diedPopup(this);
					}
					else
					{
						createTimer(60*60*1000 + currentFood, FoodDeathTimer.class);
						FoodReceiver.setRandomFood(this);
						Root.getUniqueRootInstance().addNeed(Needs.FOOD);
					}
					
				}
				else
				{
					createTimer(currentFood, FoodTimer.class);
				}
				
			}
			
			Cursor cursorDrink = databaseHandler.getCurrentTimeByNameAndId(Root.getId(), "DrinkTimer");
			if(cursorDrink != null && cursorDrink.moveToFirst())
			{
				cursorDrink.moveToFirst();
				String startDrinkTime = cursorDrink.getString(0);
				String currentDrinkTime = cursorDrink.getString(1);
				
				Log.d("START DRINK TIME: " , "--> " + startDrinkTime);
				Log.d("CURRENT DRINK TIME: " , "--> " + currentDrinkTime);
				
				long currentDrink = (Long.valueOf(currentDrinkTime) + Long.valueOf(startDrinkTime)) - System.currentTimeMillis();
				
				if(currentDrink < 0)
				{
					
					if(currentDrink < -(60*60*1000))
					{
						diedPopup(this);
					}
					else
					{
						createTimer(60*60*1000 + currentDrink, DrinkDeathTimer.class);
						Root.getUniqueRootInstance().addNeed(Needs.DRINK);
					}
					
				}
				else
				{
					createTimer(currentDrink, DrinkTimer.class);
				}
				
			}
			
			
			Cursor cursorNeeds = databaseHandler.getNeedsById(Root.getId());
			if(cursorNeeds != null && cursorNeeds.moveToFirst())
			{
				cursorNeeds.moveToFirst();
				String allNeeds = cursorNeeds.getString(0);
				Log.d("CURRENT NEEDS: " , "--> " + allNeeds);
				String[] needsArray = allNeeds.split(",");
				for(String need : needsArray)
				{
					switch (Integer.valueOf(need))
					{
					case 1:
						Root.getUniqueRootInstance().addNeed(Needs.DRINK);
						break;
						
					case 2:
						Root.getUniqueRootInstance().addNeed(Needs.FOOD);
						break;
						
					case 3:
						Root.getUniqueRootInstance().addNeed(Needs.MEDICINE);
						break;
						
					case 4:
						Root.getUniqueRootInstance().addNeed(Needs.SLEEP);
						break;
						
					case 5:
						Root.getUniqueRootInstance().addNeed(Needs.DRESS);
						break;
						
					case 6:
						Root.getUniqueRootInstance().addNeed(Needs.WASH);
						break;
						
					case 7:
						Root.getUniqueRootInstance().addNeed(Needs.BUY);
						break;
						
					case 8:
						Root.getUniqueRootInstance().addNeed(Needs.CLEAN);
						break;
						
					case 9:
						Root.getUniqueRootInstance().addNeed(Needs.DISHES);
						break;
						
					case 10:
						Root.getUniqueRootInstance().addNeed(Needs.WALK);
						break;
						
					default:
						break;
					}

				}
			}
			else
			{
				Log.d("CURSOR IS NULL!!!" , " WTF?!?!?");
			}
			
		}
		else if(!Root.isCalledFromExistingAccount())
		{
			
			setstartsetup();

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
			
			//Clothes
			PINK.setName("Pinky");
			PINK.setDirty(false);
			PINK.setCurrentDress(true);
			RED.setName("Red skull");
			RED.setDirty(false);
			RED.setCurrentDress(false);
			BLACK.setName("Black Dress");
			BLACK.setDirty(false);
			BLACK.setCurrentDress(false);
			GREEN.setName("Camo");
			GREEN.setDirty(false);
			GREEN.setCurrentDress(false);
			BLUE.setName("Sunny");
			BLUE.setDirty(false);
			BLUE.setCurrentDress(false);
			
			Root.getClotheList().add(PINK);
			Root.getClotheList().add(RED);
			Root.getClotheList().add(BLACK);
			Root.getClotheList().add(GREEN);
			Root.getClotheList().add(BLUE);
			

			currentgrandmacloth = 0;
			currentgrandmastatus = 0;
		}
		
	}

	@Override
	public void onPause(){
		super.onPause();
		writeIntoDatabase();
		Root.getUniqueRootInstance().setInForeground(false);
		
	}
	@Override
	public void onResume(){
		super.onResume();
		Root.getUniqueRootInstance().setInForeground(true);
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
		nMgr.cancel(123456789);
		updatecurrentgrandmacomplete();
		if (youtubebuttonclicked==true){
		startdancing();
		}
        if(aTask.getStatus() != AsyncTask.Status.RUNNING){
            aTask.execute();
        }
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		unregisterReceiver(mMedReceiver);
		unregisterReceiver(mDrinkReceiver);
		unregisterReceiver(mFoodReceiver);
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
		            	   
		            	   writeIntoDatabase();
		            	   Root.setAlreadyInit(false);
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

	
	public void writeIntoDatabase()
	{
		updatehandler=false;

 	   long status = -1;
 	   if(Root.isCalledFromExistingAccount())
 	   {

 		   for(FoodAttributes fA : Root.getFoodList())
 		   {
 			   String foodName = fA.getName();
 			   String foodCount = String.valueOf(fA.getCount());
 			   databaseHandler.updateFoodData(Root.getId(), foodName, foodCount);
 		   }
 		   
 		   for(ClotheAttributes cA : Root.getClotheList())
 		   {
 			   String clothName = cA.getName();
 			   String clothDirtyStatus = String.valueOf(cA.isDirty());
 			   String clothCurrentDress = String.valueOf(cA.isCurrentDress());
 			   databaseHandler.updateClothdData(Root.getId(), clothName, clothDirtyStatus, clothCurrentDress);
 		   }
 		   
 		   String allNeeds = "";
 		   for(Needs need : Root.getUniqueRootInstance().getAllNeeds())
 		   {
 			   allNeeds += "," + need.getValue();
 		   }
 		   if(allNeeds.length() > 1)
 		   {
 			  allNeeds = allNeeds.substring(1);
 			  Log.d("ALL NEEDS TO DB AFTER SUB: " , "--> " + allNeeds);
 	 		  databaseHandler.updateNeedsData(Root.getId(), allNeeds);
 		   }
 		   else
 		   {
 			  allNeeds = "0";
 			  databaseHandler.updateNeedsData(Root.getId(), allNeeds); 
 		   }
 		   
 		  String currentMoodCloth = String.valueOf(currentgrandmacloth); 
 		  String statusMoodCloth = String.valueOf(currentgrandmastatus);
 		  
 		  databaseHandler.updateMoodClothData(Root.getId(), currentMoodCloth, statusMoodCloth);
 		  
 	   }
 	   else
 	   {
 		   
 		   String[] countNames = databaseHandler.getNamesArray();
 		   String idCode = String.valueOf(countNames.length);
 		   for(FoodAttributes fA : Root.getFoodList())
 		   {
 			   String foodName = fA.getName();
 			   String foodCount = String.valueOf(fA.getCount());
 			   status = databaseHandler.insertFoodData(idCode, foodName, foodCount);
 		   }
 		   
 		   for(ClotheAttributes cA : Root.getClotheList())
 		   {
 			   String clothName = cA.getName();
 			   String clothDirtyStatus = String.valueOf(cA.isDirty());
 			   String clothCurrentDress = String.valueOf(cA.isCurrentDress());
 			   databaseHandler.insertClothData(idCode, clothName, clothDirtyStatus, clothCurrentDress);
 		   }
 		   
 		   String allNeeds = "";
 		   for(Needs need : Root.getUniqueRootInstance().getAllNeeds())
 		   {
 			   allNeeds += "," + need.getValue();
 		   }
 		   if(allNeeds.length() > 1)
 		   {
 			  allNeeds = allNeeds.substring(1);
 	 		  databaseHandler.insertNeedsData(idCode, allNeeds);
 		   }
 		   else
 		   {
 			   allNeeds = "0";
 			  databaseHandler.insertNeedsData(idCode, allNeeds);
 		   }
 		   
 		   String currentMoodCloth = String.valueOf(currentgrandmacloth); 
     	   String statusMoodCloth = String.valueOf(currentgrandmastatus);
     		  
     	   databaseHandler.insertMoodClothData(idCode, currentMoodCloth, statusMoodCloth);
 	   }
 	   updatehandler=true;
 	   startupdatehandler();
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
			else if(position == TEST_POS)
			{
				fragment = Root.getRoomList().get(TEST_POS);
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
	
	public static void updatecurrentgrandma(){
      currentgrandma=grandmaimagearray[currentgrandmacloth][currentgrandmastatus];
	}

	public void updatecurrentgrandmaimage() {
		ImageView grannyBedroom = (ImageView) findViewById(R.id.imageGrandma);
		grannyBedroom.setImageResource(currentgrandma);
		handleWakeUp();
	}
	
    public void initializegranmaimagearray(){
	//normal
	grandmaimagearray[0][0]= R.drawable.image_grandma_normal;
	grandmaimagearray[0][1]= R.drawable.image_grandma_happy;
	grandmaimagearray[0][2]= R.drawable.image_grandma_ill;
	grandmaimagearray[0][3]= R.drawable.image_grandma_confused;
	grandmaimagearray[0][4]= R.drawable.image_grandma_idea;
	grandmaimagearray[0][5]= R.drawable.image_grandma_angry;
	//blue
	grandmaimagearray[4][0]= R.drawable.image_grandma_normal_blue;
	grandmaimagearray[4][1]= R.drawable.image_grandma_happy_blue;
	grandmaimagearray[4][2]= R.drawable.image_grandma_ill_blue;
	grandmaimagearray[4][3]= R.drawable.image_grandma_confused_blue;
	grandmaimagearray[4][4]= R.drawable.image_grandma_idea_blue;
	grandmaimagearray[4][5]= R.drawable.image_grandma_angry_blue;
	
	//red
	grandmaimagearray[1][0]= R.drawable.image_grandma_normal_red;
	grandmaimagearray[1][1]= R.drawable.image_grandma_happy_red;
	grandmaimagearray[1][2]= R.drawable.image_grandma_ill_red;
	grandmaimagearray[1][3]= R.drawable.image_grandma_confused_red;
	grandmaimagearray[1][4]= R.drawable.image_grandma_idea_red;
	grandmaimagearray[1][5]= R.drawable.image_grandma_angry_red;
	
	//green
	grandmaimagearray[3][0]= R.drawable.image_grandma_normal_green;
	grandmaimagearray[3][1]= R.drawable.image_grandma_happy_green;
	grandmaimagearray[3][2]= R.drawable.image_grandma_ill_green;
	grandmaimagearray[3][3]= R.drawable.image_grandma_confused_green;
	grandmaimagearray[3][4]= R.drawable.image_grandma_idea_green;
	grandmaimagearray[3][5]= R.drawable.image_grandma_angry_green;
	
	//black
	grandmaimagearray[2][0]= R.drawable.image_grandma_normal_black;
	grandmaimagearray[2][1]= R.drawable.image_grandma_happy_black;
	grandmaimagearray[2][2]= R.drawable.image_grandma_ill_black;
	grandmaimagearray[2][3]= R.drawable.image_grandma_confused_black;
	grandmaimagearray[2][4]= R.drawable.image_grandma_idea_black;
	grandmaimagearray[2][5]= R.drawable.image_grandma_angry_black;
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
//		updatecurrentgrandmaimage();
		updatecurrentgrandmacomplete();
		
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
                    ((!Root.getAttributes().isSleeping() && Root.getUniqueRootInstance().containsNeed(Needs.SLEEP)) ||
                    Root.getUniqueRootInstance().isSimMode()))
			{
				btnWakeUp = (ImageButton) findViewById(R.id.btn_bedroom_wake_up);
				Message.message(this, "You turned the light off, Grandma sleeps now");
                String date = null;
                if(Root.getUniqueRootInstance().getSleeptime() == 0){
                    date = String.valueOf(System.currentTimeMillis());
                }
                else
                date = String.valueOf(28800000);
                String id = null;
                if(Root.isCalledFromExistingAccount())
                {
                    id = Root.getId();
                    if(databaseHandler.getTimer(Root.getId(), "DrinkTimer"))
                        databaseHandler.updateTimerData(id, "SleepTimer" , date, date);
                    else
                        databaseHandler.insertTimerData(id, "SleepTimer" , date, date);
                }
                else{
                    String[] countNames = databaseHandler.getNamesArray();
                    id = String.valueOf(countNames.length);
                    databaseHandler.insertTimerData(id, "SleepTimer" , date, date);
                }

                grannyImage = (ImageView) findViewById(R.id.imageGrandma);
				grannyImage.setImageResource(R.drawable.image_sleeping_grandma);
				
				btnWakeUp.setVisibility(View.VISIBLE);
                Root.getAttributes().setSleeping(true);
                Root.getUniqueRootInstance().removeNeed(Needs.SLEEP);
			}
		}
		
		//shakes device and wash clothes
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
					 Message.message(this, "Grandma washed cloth ");
//					 Message.message(this,"All clothes are clean again!");
					 Root.getUniqueRootInstance().removeNeed(Needs.WASH);
					 
					 for(ClotheAttributes clotheItem : Root.getClotheList())
					 {
						 clotheItem.setDirty(false);
						 }
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
		//createTimer(5000, FoodTimer.class);

	}
	
	//on click starts game activity
	public void btnOnClickGame(View view)
	{
		startActivity(new Intent(RoomActivity.this, TicTacToeGameActivity.class));
//		RoomActivity.this.finish();
	}
	
	//on click go to youtube
	public void btnOnClickMusic(View view)
	{
		String videoID = "raluLqdSrJ4";
		startActivity(new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://m.youtube.com/watch?v=" + videoID)));
		youtubebuttonclicked=true;
		eyesImage = (ImageView) findViewById(R.id.imageGrandmaEyes);
		eyesImage.setImageResource(R.drawable.eyeszero);
		dancecounter=0;
		
	}
	
	//dancing granny after returning from youtube
	public void startdancing() {
		if (Root.getAttributes().getCurrentFragmentPosition() == 0
				&& dancecounter < 30) {
			int grandmadancepicture = R.drawable.grandmadance1;
			Handler handler1 = new Handler();
			grannyImage = (ImageView) findViewById(R.id.imageGrandma);
			grannyImage.setImageResource(R.drawable.grandmadancing1);
			Random rn = new Random();
			int randomnumber = rn.nextInt(2) + 1;
			switch (randomnumber) {
			case 1:
				grandmadancepicture = R.drawable.grandmadance1;
				break;
			case 2:
				grandmadancepicture = R.drawable.grandmadance2;
				break;
			case 3:
				grandmadancepicture = R.drawable.grandmadance3;
				break;
			}
			grannyImage.setImageResource(grandmadancepicture);
			handler1.postDelayed(new Runnable() {

				public void run() {
					dancecounter = dancecounter + 1;
					startdancing();
				}
			}, 170);
		} else {
			grannyImage.setImageResource(currentgrandma);
			dancecounter=0;
			
		}
		youtubebuttonclicked = false;
	}

	//on click for washing the grandma
	public void btnOnClickSoap(View view) 
	{
		Message.message(this, "The Grandma is clean again!");
		//createTimer(5000, FoodTimer.class);
        Intent intentwash = new Intent(this, Wash.class);
	    startActivity(intentwash);
	}
	
	//on click makes the granny fit
	public void btnOnClickDrugs(View view)
	{
	    startActivity(new Intent(RoomActivity.this, PainkillerActivity.class));
	}
	
	//on click for washing clothes
	public void btnOnClickWash(View view)
	{		
		Message.message(this,"All clothes are clean again!");
		Root.getUniqueRootInstance().removeNeed(Needs.WASH);
		for(ClotheAttributes clotheItem : Root.getClotheList())
		{
			 clotheItem.setDirty(false);
		}
//		diedPopup(context);
	}
	
	//on click for washing the house
	public void btnOnClickBrush(View view)
	{
        if(Root.getUniqueRootInstance().containsNeed(Needs.CLEAN) ||Root.getUniqueRootInstance().isSimMode()){
		    Message.message(this,"The house is clean again");
            Root.getUniqueRootInstance().removeNeed(Needs.CLEAN);
        }
        Message.message(this, "The house is already clean.");
		//createTimer(5000, FoodTimer.class);

	}
	
	//on click for meal
	public void btnOnClickEat(View view)
	{

		if(Root.getUniqueRootInstance().isHungry() || Root.getUniqueRootInstance().isThirsty() || Root.getUniqueRootInstance().isSimMode()){
			startActivity(new Intent(RoomActivity.this, MealActivity.class));
		}
		else
			Message.message(this, "Grandma is not hungry or thirsty at the moment.");

	}
	
	//on click for changing clothes
	public void btnOnClickChangeClothes(View view)
	{
		startActivity(new Intent(RoomActivity.this, WarderobeActivity.class));
	}
	
	//on click washing the dishes
    public void btnOnClickWashDishes(View view)
    {
        if(Root.getUniqueRootInstance().containsNeed(Needs.DISHES)||Root.getUniqueRootInstance().isSimMode()){
            Message.message(this, "Grandma washed the dishes");
            Root.getUniqueRootInstance().removeNeed(Needs.DISHES);
        }
        else{
            Message.message(this, "The dishes are clean.");
        }
    }
	
    //on click starts shop activity for shopping
	public void btnOnClickShopcart(View view)
	{
		startActivity(new Intent(RoomActivity.this, ShopActivity.class));
	}
	
	//message for proxyimity sensor instruction
	public void btnOnClickBedroomHelp(View view)
	{
		Message.message(this,"Put Grandma to sleep by putting your hand over proximity Sensor");
	}
	
	//message for shake device instruction
	public void btnOnClickWashingroomHelp(View view)
	{
		Message.message(this,"Shake Device to clean laundry");
	}

	//on click for takes the granny to beed
	public void btnOnClickWakeUp(View view)
	{
        handleWakeUp();
	}

    public void handleWakeUp(){
        if(Root.getUniqueRootInstance().isSleeping()){
        String id = null;
        if(Root.isCalledFromExistingAccount())
            id = Root.getId();
        else{
            String[] countNames = databaseHandler.getNamesArray();
            id = String.valueOf(countNames.length);
        }

        Cursor c = databaseHandler.getCurrentTimeByNameAndId(id, "SleepTimer");
        c.moveToFirst();
        String start = c.getString(1);
        long currentSleep = System.currentTimeMillis() - Long.valueOf(start);
        if(currentSleep < Root.getUniqueRootInstance().getSleeptime()){
            Root.getUniqueRootInstance().addNeed(Needs.SLEEP);
            Root.getUniqueRootInstance().setSleeptime(currentSleep);
            Message.message(this, "Grandma is awake. But she is still tired.");
        }
        else{
            Message.message(this, "Grandma is awake");
            Root.getUniqueRootInstance().setSleeptime(0);
        }
        Root.getAttributes().setSleeping(false);
        Root.getUniqueRootInstance().setSleeping(false);

        grannyImage = (ImageView) findViewById(R.id.imageGrandma);
        grannyImage.setImageResource(currentgrandma);

        btnWakeUp = (ImageButton) findViewById(R.id.btn_bedroom_wake_up);
        btnWakeUp.setVisibility(View.INVISIBLE);
        }
    }

	
	private void createTimer(long countdown, Class target){
		Intent mServiceIntent = new Intent(getApplicationContext(), target);
		mServiceIntent.putExtra("countdown", countdown);
		startService(mServiceIntent);
	}

	//starts maps
	public void opendoorfunction(View view)
	{
		Intent intent = new Intent(this, opendoor.class);
		Root.getUniqueRootInstance().removeNeed(Needs.WALK);
		startActivity(intent);
	}

	//Testmethoden
	public void essenswunsch(View view){

        if(!Root.getUniqueRootInstance().isHungry()){
            Root.getUniqueRootInstance().setHungry(true);
            Root.getUniqueRootInstance().addNeed(Needs.FOOD);
            FoodReceiver.setRandomFood(this);
            Root.getUniqueRootInstance().setFakeHunger(true);
        }
        else{
            Toast.makeText(getApplicationContext(), "is already hungry", Toast.LENGTH_LONG).show();

        }

	}
    public void gosleep(View view){
    
    if(!Root.getUniqueRootInstance().containsNeed(Needs.SLEEP)){
        Root.getUniqueRootInstance().addNeed(Needs.SLEEP);
        updatecurrentgrandmacomplete();
    }
    else{
        Toast.makeText(getApplicationContext(), "wants already to go to bed", Toast.LENGTH_LONG).show();

    }

}
	public void trinkenwunsch(View view){
        if(!Root.getUniqueRootInstance().isThirsty()){
		    Toast.makeText(getApplicationContext(), "is thirsty", Toast.LENGTH_LONG).show();
            Root.getUniqueRootInstance().setThirsty(true);
            Root.getUniqueRootInstance().addNeed(Needs.DRINK);
            Root.getUniqueRootInstance().setFakeThirst(true);

        }
        else{
            Toast.makeText(getApplicationContext(), "is already thirsty", Toast.LENGTH_LONG).show();

        }

	}
	public void medizinwunsch(View view){
        if(!Root.getUniqueRootInstance().containsNeed(Needs.MEDICINE)) {
            Toast.makeText(getApplicationContext(), "needs Med", Toast.LENGTH_LONG).show();
            Root.getUniqueRootInstance().addNeed(Needs.MEDICINE);
        }
        else
            Toast.makeText(getApplicationContext(), "needs med already", Toast.LENGTH_LONG).show();

	}
	public void spazierenwunsch(View view){
        if(!Root.getUniqueRootInstance().containsNeed(Needs.WALK)) {
            Toast.makeText(getApplicationContext(), "needs a walk", Toast.LENGTH_LONG).show();
            Root.getUniqueRootInstance().addNeed(Needs.WALK);
        }
        else
            Toast.makeText(getApplicationContext(), "already wants a walk", Toast.LENGTH_LONG).show();
	}
	public void waschenwunsch(View view){
        if(!Root.getUniqueRootInstance().containsNeed(Needs.DISHES)) {
            Toast.makeText(getApplicationContext(), "dishes dirty", Toast.LENGTH_LONG).show();
            Root.getUniqueRootInstance().addNeed(Needs.DISHES);
        }
        else
            Toast.makeText(getApplicationContext(), "already wants a walk", Toast.LENGTH_LONG).show();
	}
	public void saubermachenwunsch(View view){
        if(!Root.getUniqueRootInstance().containsNeed(Needs.CLEAN)) {
            Toast.makeText(getApplicationContext(), "wants top clean", Toast.LENGTH_LONG).show();
            Root.getUniqueRootInstance().addNeed(Needs.CLEAN);
        }
        else
            Toast.makeText(getApplicationContext(), "already wants to clean", Toast.LENGTH_LONG).show();

	}
    public void simulationsmodus(View view){
        if(!Root.getUniqueRootInstance().isSimMode())  {
            Toast.makeText(getApplicationContext(), "sim mode ON", Toast.LENGTH_LONG).show();
            Root.getUniqueRootInstance().setSimMode(true);
        }
        else{
            Toast.makeText(getApplicationContext(), "sim mode OFF", Toast.LENGTH_LONG).show();
            Root.getUniqueRootInstance().setSimMode(false);
        }
    }
    public void dressme(View view){
        if(!Root.getUniqueRootInstance().containsNeed(Needs.DRESS))  {
            Toast.makeText(getApplicationContext(), "need a dress change", Toast.LENGTH_LONG).show();
            Root.getUniqueRootInstance().addNeed(Needs.DRESS);

        }
        else{
            Toast.makeText(getApplicationContext(), "already wants to change the clothes", Toast.LENGTH_LONG).show();
        }
    }
	

	public void setstartsetup(){	
        createTimer(foodTimer, FoodTimer.class);
        createTimer(drinkTimer, DrinkTimer.class);
	}

}
