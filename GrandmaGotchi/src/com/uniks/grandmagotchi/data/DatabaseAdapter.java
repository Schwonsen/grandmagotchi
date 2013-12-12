package com.uniks.grandmagotchi.data;

import com.uniks.grandmagotchi.util.DebugClass;
import com.uniks.grandmagotchi.util.Message;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAdapter
{
	
	DatabaseHandler databaseHandler;
	
	
	public DatabaseAdapter(Context context)
	{
		databaseHandler = new DatabaseHandler(context);

	}
	
	public long insertData(String name)
	{
		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHandler.NAME, name);
		long id = db.insert(DatabaseHandler.SAVEGAME_TABLE, null, contentValues);
		return id;
	}
	
	
	
	static class DatabaseHandler extends SQLiteOpenHelper
	{
		private static final String DATABASE_NAME = "savegame.db";
		private static final int DATABASE_VERSION = 1;
		
		private static final String SAVEGAME_TABLE = "savegame";
		
		private static final String SAVEGAME_ID = "_id";
		private static final String NAME = "name";
		private Context context;
		DebugClass dMode;
		

		public DatabaseHandler(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
			dMode = new DebugClass();
		}

		@Override
		public void onCreate(SQLiteDatabase db)
		{
			String createDB = "CREATE TABLE " + SAVEGAME_TABLE + " (" +
					SAVEGAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255));";
			try
			{
				db.execSQL(createDB);
				if(dMode.getDebugMode()) Message.message(context, "onCreate called");
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			try
			{
				if(dMode.getDebugMode()) Message.message(context, "onUpgrade called");
				db.execSQL("DROP TABLE IF EXISTS " + SAVEGAME_TABLE);
				onCreate(db);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

}
