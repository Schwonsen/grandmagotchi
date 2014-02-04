package com.uniks.grandmagotchi;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

public class Wash extends Activity{
	View view1;
	WebView webviewActionView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.door);
	    opendoorfunction(view1);

	}
	
	public void opendoorfunction(View rootView) {
		setContentView(R.layout.washing);
	    InputStream stream = null;
	    try {
	        stream = getAssets().open("a.gif");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	          GifWebView view = new GifWebView(this, "file:///android_asset/washanimation.gif");                 
        
	    setContentView(view);
	    Handler handler = new Handler(); 
	    handler.postDelayed(new Runnable() { 
	         public void run() { 
	              closewashing();
	         } 
	    }, 5100);
	}
	public void closewashing() {
		Wash.this.finish();
	}	
	
}
