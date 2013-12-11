package com.uniks.grandmagotchi.rooms;

import com.uniks.grandmagotchi.R;
import com.uniks.grandmagotchi.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class FragmentWashingRoom extends Fragment
{

	public FragmentWashingRoom()
	{
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_washing_room,
				container, false);
	}

}
