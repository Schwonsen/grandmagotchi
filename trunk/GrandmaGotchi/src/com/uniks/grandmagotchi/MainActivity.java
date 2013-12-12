package com.uniks.grandmagotchi;

import com.uniks.grandmagotchi.data.DatabaseAdapter;
import com.uniks.grandmagotchi.data.GrannyAttributes;
import com.uniks.grandmagotchi.util.DebugClass;
import com.uniks.grandmagotchi.util.Message;

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
	DatabaseAdapter databaseHandler;
	DebugClass dMode;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        dMode = new DebugClass();
        databaseHandler = new DatabaseAdapter(this);
        
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
    
    
    public void btnClickStartGame(View view)
    {
    	EditText fieldGrannyName = (EditText)findViewById(R.id.tfGrannysName);
    	String grannyName = fieldGrannyName.toString();
    	long id = databaseHandler.insertData(grannyName);
		
		if(id < 0)
		{
			if(dMode.getDebugMode()) Message.message(MainActivity.this, "Unsuccessful written in Database");
		}
		else
		{
			if(dMode.getDebugMode()) Message.message(MainActivity.this, "Successfully written in Database");
		}
		startActivity(new Intent(MainActivity.this, RoomActivity.class));
		MainActivity.this.finish();
    }

    
}
