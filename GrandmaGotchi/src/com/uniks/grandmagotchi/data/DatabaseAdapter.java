package com.uniks.grandmagotchi.data;

import com.uniks.grandmagotchi.util.DebugClass;
import com.uniks.grandmagotchi.util.Message;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
	
	public long insertData(String name, String password)
	{
		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(databaseHandler.NAME, name);
		contentValues.put(databaseHandler.PASSWORD, password);
		long id = db.insert(databaseHandler.SAVEGAME_TABLE, null, contentValues);
		return id;
	}
	
	public String getAllData()
	{
		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		String[] columns = {databaseHandler.SAVEGAME_ID, databaseHandler.NAME, databaseHandler.PASSWORD};
		Cursor cursor = db.query(databaseHandler.SAVEGAME_TABLE, columns, null, null, null, null, null);
		StringBuffer buffer = new StringBuffer();
		while(cursor.moveToNext())
		{
			int index1 = cursor.getColumnIndex(databaseHandler.SAVEGAME_ID);
			int cid = cursor.getInt(index1);
			int index2 = cursor.getColumnIndex(databaseHandler.NAME);
			String name = cursor.getString(index2);
			int index3 = cursor.getColumnIndex(databaseHandler.PASSWORD);
			String password = cursor.getString(index3);
			buffer.append(cid+" "+name+" "+password+"\n");
		}
		return buffer.toString();
	}
	
	public String getNames()
	{
		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		String[] columns = {databaseHandler.SAVEGAME_ID, databaseHandler.NAME, databaseHandler.PASSWORD};
		Cursor cursor = db.query(databaseHandler.SAVEGAME_TABLE, columns, null, null, null, null, null);
		StringBuffer buffer = new StringBuffer();
		while(cursor.moveToNext())
		{
			int index1 = cursor.getColumnIndex(databaseHandler.NAME);
			String name = cursor.getString(index1);
			buffer.append(name+" ");
		}
		return buffer.toString().trim();
	}
	
	public String getData(String name, String password)
	{
		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		String[] columns = {databaseHandler.SAVEGAME_ID};
		String[] selectionArgs = {name, password};
		Cursor cursor = db.query(databaseHandler.SAVEGAME_TABLE, columns, 
				databaseHandler.NAME + " =? AND " + databaseHandler.PASSWORD + " =? ", 
				selectionArgs, null, null, null);
		StringBuffer buffer = new StringBuffer();
		while(cursor.moveToNext())
		{
			int index1 = cursor.getColumnIndex(databaseHandler.SAVEGAME_ID);
			int cid = cursor.getInt(index1);
			buffer.append(cid+"\n");
		}
		return buffer.toString();
	}
	
	
	static class DatabaseHandler extends SQLiteOpenHelper
	{
		private static final String DATABASE_NAME = "savegame.db";
		private static final int DATABASE_VERSION = 2;
		
		private static final String SAVEGAME_TABLE = "savegame";
		
		private static final String SAVEGAME_ID = "_id";
		private static final String NAME = "name";
		private static final String PASSWORD = "password";
		private static final String QUERY = "CREATE TABLE " + SAVEGAME_TABLE + " (" +
				SAVEGAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255), " +
				PASSWORD + " VARCHAR(255));";
		private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + SAVEGAME_TABLE;
		
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
			
			try
			{
				db.execSQL(QUERY);
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
				db.execSQL(DROP_TABLE);
				onCreate(db);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

}