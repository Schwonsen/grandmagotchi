package com.uniks.grandmagotchi;

import com.uniks.grandmagotchi.data.DatabaseAdapter;
import com.uniks.grandmagotchi.util.DebugClass;
import com.uniks.grandmagotchi.util.Message;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;

public class CreateNewGameActivity extends Activity
{
	
	DatabaseAdapter databaseHandler;
	DebugClass dMode;
	EditText userName, userPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_game);
		
		databaseHandler = new DatabaseAdapter(this);
		dMode = new DebugClass();
		
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
				long id = databaseHandler.insertData(name, password);
				
				if(id < 0)
				{
					if(dMode.getDebugMode()) Message.message(CreateNewGameActivity.this, "Unsuccessful written in Database");
				}
				else
				{
					if(dMode.getDebugMode()) Message.message(CreateNewGameActivity.this, "Successfully written in Database");
				}
				startActivity(new Intent(CreateNewGameActivity.this, RoomActivity.class));
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
	

}