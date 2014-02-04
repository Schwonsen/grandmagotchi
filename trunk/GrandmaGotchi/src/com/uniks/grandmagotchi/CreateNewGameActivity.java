package com.uniks.grandmagotchi;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.uniks.grandmagotchi.data.DatabaseAdapter;
import com.uniks.grandmagotchi.util.Message;
import com.uniks.grandmagotchi.util.Root;
import com.uniks.grandmagotchi.util.timer.services.FoodTimer;
import com.uniks.grandmagotchi.util.timer.receiver.DrinkReceiver;
import com.uniks.grandmagotchi.util.timer.receiver.FoodReceiver;
import com.uniks.grandmagotchi.util.timer.services.DrinkTimer;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.Activity;
import android.content.Intent;


public class CreateNewGameActivity extends Activity
{
	
	private DatabaseAdapter databaseHandler;
	private EditText userName, userPassword;
	private Spinner difficultyLevels;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_game);
		
		Root.getUniqueRootInstance();
		
		difficultyLevels = (Spinner) findViewById(R.id.spinnerDifficultyLevels);
		difficultyLevels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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
		
		databaseHandler = new DatabaseAdapter(this);;
		
		userName = (EditText) findViewById(R.id.createNewGameUserName);
		userPassword = (EditText) findViewById(R.id.createNewGamePassword);
		
	}

	public void btnOnClickStartGame(View view)
	{
		
		String name = userName.getText().toString().trim();
		String password = userPassword.getText().toString().trim();
		
		String existingNames = databaseHandler.getNames();
		
		if(!existingNames.contains(name))
		{
			if(!name.equals("") && !password.equals(""))
			{
				difficultyLevels = (Spinner) findViewById(R.id.spinnerDifficultyLevels);
				long id = databaseHandler.insertData(name, password, difficultyLevels.getSelectedItem().toString());
				
				if(id < 0)
				{
					if(Root.DEBUG) Message.message(CreateNewGameActivity.this, "Unsuccessful written in Database");
				}
				else
				{
					String databaseId = databaseHandler.getID(name, password);
					Root.getAttributes().setDifficultyLevel(difficultyLevels.getSelectedItem().toString());
					Root.getAttributes().setName(name);
					Root.getAttributes().setId(databaseId);
					if(Root.DEBUG) Message.message(CreateNewGameActivity.this, "Successfully written in Database");
				}
				startActivity(new Intent(CreateNewGameActivity.this, RoomActivity.class));
				setstartsetup();
				CreateNewGameActivity.this.finish();
			}
			else
			{
				Message.message(this, "name and password field must not be empty");
			}
		}
		else
		{
			Message.message(this, "User " + name + " already exists, choose another name");
		}
		

		
	}
    void createTimer(int countdown, Class target){
        Intent mServiceIntent = new Intent(getApplicationContext(), target);
        mServiceIntent.putExtra("countdown", countdown);
        startService(mServiceIntent);
    }
	public void setstartsetup(){	
        createTimer(5000, FoodTimer.class);
        createTimer(5000, DrinkTimer.class);
	}

}