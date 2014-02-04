package com.uniks.grandmagotchi.rooms;

import java.io.IOException;
import java.io.InputStream;



import com.uniks.grandmagotchi.GifWebView;
import com.uniks.grandmagotchi.R;
import com.uniks.grandmagotchi.grandmawalk;
import com.uniks.grandmagotchi.R.layout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.webkit.WebView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class FragmentOutside extends Fragment
{
	WebView webviewActionView;
	public FragmentOutside()
	{
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.door, container,
				false);
		
	}
	

	
	

	
	

}
