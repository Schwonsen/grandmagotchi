package com.uniks.grandmagotchi;

import com.uniks.grandmagotchi.data.DatabaseAdapter;
import com.uniks.grandmagotchi.data.Attributes;
import com.uniks.grandmagotchi.util.Message;
import com.uniks.grandmagotchi.util.Root;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends Activity {
	
	public Attributes grannyAttributes = new Attributes();

	private EditText fieldPassword;
	private Spinner fieldName;
	private DatabaseAdapter databaseHandler;
	private String[] names;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Root.getUniqueRootInstance();
        
        databaseHandler = new DatabaseAdapter(this);
        
        names = databaseHandler.getNamesArray();
        fieldName = (Spinner) findViewById(R.id.spinnerNames);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, names);
        fieldName.setAdapter(adapter);
        fieldName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id)
			{
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
			
		});
        
    	fieldPassword = (EditText) findViewById(R.id.passwordValue);
    	
//		prefs = getSharedPreferences(PREF_USER_NAME, MODE_PRIVATE );
//		String userName = prefs.getString("userName","");
//		fieldName.setText(userName);
    }
    
    //Speichert UserNamen des letzten Loggins
	@Override
	public void onPause(){    
	  super.onPause();
//	  SharedPreferences.Editor editor = prefs.edit();
//	  editor.putString("userName", fieldName.getText().toString());
//	    editor.commit();
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    public void btnOnClickLogin(View view)
    {
    	
//    	String name = fieldName.getText().toString().trim();
    	String password = fieldPassword.getText().toString().trim();
    	
    	fieldName = (Spinner) findViewById(R.id.spinnerNames);
    	if(names.length > 0 && !password.equals(""))
    	{
    		String name = fieldName.getSelectedItem().toString();
    		String id = databaseHandler.getID(name, password);
    		
    		if(!id.equals(""))
    		{
    			if(Root.DEBUG) Message.message(this, id);

    			id = id.trim();
    			String[] data = databaseHandler.getDataByID(id);
    			
    			Root.getAttributes().setId(data[0]);
    			Root.getAttributes().setName(data[1]);
    			Root.getAttributes().setDifficultyLevel(data[2]);
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