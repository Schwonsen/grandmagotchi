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
public class FragmentKitchen extends Fragment
{

	public FragmentKitchen()
	{
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_fragment_kitchen, container,
				false);
	}

}
