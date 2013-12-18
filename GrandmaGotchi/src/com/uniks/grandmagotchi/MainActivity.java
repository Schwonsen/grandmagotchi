package com.uniks.grandmagotchi;

import com.uniks.grandmagotchi.data.DatabaseAdapter;
import com.uniks.grandmagotchi.data.Attributes;
import com.uniks.grandmagotchi.util.Message;
import com.uniks.grandmagotchi.util.Root;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	public Attributes grannyAttributes = new Attributes();

	Button btnStartGame;
	EditText editName, fieldName, fieldPassword;
	DatabaseAdapter databaseHandler;
	private SharedPreferences prefs;
	private String PREF_USER_NAME = "LastUser";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHandler = new DatabaseAdapter(this);
        
        fieldName = (EditText) findViewById(R.id.userNameValue);
    	fieldPassword = (EditText) findViewById(R.id.passwordValue);
    	
		prefs = getSharedPreferences(PREF_USER_NAME, MODE_PRIVATE );
		String userName = prefs.getString("userName","");
		fieldName.setText(userName);
    }
    
    //Speichert UserNamen des letzten Loggins
	@Override
	public void onPause(){    
	  super.onPause();
	  SharedPreferences.Editor editor = prefs.edit();
	  editor.putString("userName", fieldName.getText().toString());
	    editor.commit();
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

    	if(!name.equals("") && !password.equals(""))
		{
    		String id = databaseHandler.getData(name, password);
    		
    		if(!id.equals(""))
    		{
    			if(Root.DEBUG) Message.message(this, id);
    		
    			Root.getAttributes().setName(name);
    			Root.getAttributes().setId(id);
    			Root.getAttributes().setSleeping(false);
    			
    			startActivity(new Intent(MainActivity.this, RoomActivity.class));
    			MainActivity.this.finish();	
    		}
    		else
    		{
    			Message.message(this, "Wrong name or password");
    		}
		}
		else
		{
			Message.message(this, "name and password field must not be empty");
		}
    	
		
    }
    
    public void btnOnClickCreateNewGame(View view)
    {
    	startActivity(new Intent(MainActivity.this, CreateNewGameActivity.class));
    	MainActivity.this.finish();
    }

    
}