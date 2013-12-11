package com.uniks.grandmagotchi;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RoomActivity extends FragmentActivity implements TabListener
{

	ViewPager viewPager;
	ActionBar actionBar;
	
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
				Log.d("VIVZ", "onPageSelected at position " + position);
				
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
			{
				Log.d("VIVZ", "onPageScrolled at position " + position + " from position " + positionOffset +
						" with number of pixels = " + positionOffsetPixels);
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state)
			{
				if(state == ViewPager.SCROLL_STATE_IDLE)
				{
					Log.d("VIVZ", "onPageScrollStateChanged IDLE");
				}
				else if(state == ViewPager.SCROLL_STATE_DRAGGING)
				{
					Log.d("VIVZ", "onPageScrollStateChanged DRAGGING");
				}
				else if(state == ViewPager.SCROLL_STATE_SETTLING)
				{
					Log.d("VIVZ", "onPageScrollStateChanged SETTLING");
				}
				
			}
		});
		actionBar = getActionBar();
		// using tabs in action bar
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
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
		
		actionBar.addTab(tabLivingRoom);
		actionBar.addTab(tabKitchen);
		actionBar.addTab(tabDressingRoom);
		actionBar.addTab(tabWashingRoom);

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{
		
		viewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub
		
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
			
			if(position == 0)
			{
				fragment = new FragmentLivingRoom();
			}
			else if(position == 1)
			{
				fragment = new FragmentKitchen();
			}
			else if(position == 2)
			{
				fragment = new FragmentDressingRoom();
			}
			else if(position == 3)
			{
				fragment = new FragmentWashingRoom();
			}
			
			
			return fragment;
		}

		@Override
		public int getCount()
		{
			// returns the number of pages
			return 4;
		}
		
	}
	
}
