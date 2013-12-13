
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
	EditText editName, fieldName, fieldPassword;
	DatabaseAdapter databaseHandler;
	DebugClass dMode;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        dMode = new DebugClass();
        databaseHandler = new DatabaseAdapter(this);
        
        fieldName = (EditText) findViewById(R.id.userNameValue);
    	fieldPassword = (EditText) findViewById(R.id.passwordValue);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    public void btnOnClickLogin(View view)
    {
    	
    	String name = fieldName.getText().toString().trim();
    	String password = fieldPassword.getText().toString().trim();

		String id = databaseHandler.getData(name, password);

		if(!id.equals(""))
		{
			if(dMode.getDebugMode()) Message.message(this, id);
			
			startActivity(new Intent(MainActivity.this, RoomActivity.class));
			MainActivity.this.finish();	
		}
		else
		{
			Message.message(this, "Wrong name or password");
		}
		
    }
    
    public void btnOnClickCreateNewGame(View view)
    {
    	startActivity(new Intent(MainActivity.this, CreateNewGameActivity.class));
    	MainActivity.this.finish();
    }

    
}