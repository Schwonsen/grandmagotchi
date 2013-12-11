package com.uniks.grandmagotchi;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	public GrannyAttributes grannyAttributes = new GrannyAttributes();

	Button btnStartGame;
	EditText editName;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnStartGame = (Button) findViewById(R.id.btnStartGame);
        btnStartGame.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(MainActivity.this, RoomActivity.class));
		    	EditText fieldGrannyName = (EditText)findViewById(R.id.tfGrannysName);
		    	grannyAttributes.setName(fieldGrannyName.getText().toString());
			}
		});
        
        editName = (EditText) findViewById(R.id.tfGrannysName);
        editName.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				editName.setHint("");
			}
		});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
//    public void btnClickStartGame(View view) {
//    	
//    	Button btnStartGame =(Button) findViewById(R.id.btnStartGame);
//    	btnStartGame.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				startActivity(new Intent(MainActivity.this, RoomActivity.class));
//		    	EditText fieldGrannyName = (EditText)findViewById(R.id.tfGrannysName);
//		    	grannyAttributes.setName(fieldGrannyName.getText().toString());
//			}
//		});    	
//    }
    
}
