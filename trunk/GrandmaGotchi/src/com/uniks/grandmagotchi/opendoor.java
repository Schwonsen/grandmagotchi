package com.uniks.grandmagotchi;

import java.io.IOException;
import java.io.InputStream;

import com.uniks.grandmagotchi.GifWebView;
import com.uniks.grandmagotchi.R;
import com.uniks.grandmagotchi.grandmawalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

public class opendoor extends Activity{
	View view1 ;
	WebView webviewActionView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.door);
	    opendoorfunction(view1);

	}
	
	public void opendoorfunction(View rootView) {
		setContentView(R.layout.dooropen);
	    InputStream stream = null;
	    try {
	        stream = getAssets().open("a.gif");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	          GifWebView view = new GifWebView(this, "file:///android_asset/tueropen.gif");                 
        
	    setContentView(view);
	    Handler handler = new Handler(); 
	    handler.postDelayed(new Runnable() { 
	         public void run() { 
	              startwalking();
	         } 
	    }, 1500);		
		Toast.makeText(getApplicationContext(), "Starte deine Reise", Toast.LENGTH_LONG).show();
	}
	public void startwalking() {
	
	Intent intent = new Intent(this, grandmawalk.class);
    startActivity(intent);
    this.finish();
	}	
	
}