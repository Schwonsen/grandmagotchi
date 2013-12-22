package com.uniks.grandmagotchi.data;

import com.uniks.grandmagotchi.util.Message;
import com.uniks.grandmagotchi.util.Root;

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
	
	public long insertData(String name, String password, String difficultyLevel)
	{
		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHandler.NAME, name);
		contentValues.put(DatabaseHandler.PASSWORD, password);
		contentValues.put(DatabaseHandler.DIFFICULTY_LEVEL, difficultyLevel);
		long id = db.insert(DatabaseHandler.SAVEGAME_TABLE, null, contentValues);
		return id;
	}
	
	public String getAllData()
	{
		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		String[] columns = {DatabaseHandler.SAVEGAME_ID, DatabaseHandler.NAME, DatabaseHandler.PASSWORD, DatabaseHandler.DIFFICULTY_LEVEL};
		Cursor cursor = db.query(DatabaseHandler.SAVEGAME_TABLE, columns, null, null, null, null, null);
		StringBuffer buffer = new StringBuffer();
		while(cursor.moveToNext())
		{
			int index1 = cursor.getColumnIndex(DatabaseHandler.SAVEGAME_ID);
			int cid = cursor.getInt(index1);
			int index2 = cursor.getColumnIndex(DatabaseHandler.NAME);
			String name = cursor.getString(index2);
			int index3 = cursor.getColumnIndex(DatabaseHandler.PASSWORD);
			String password = cursor.getString(index3);
			int index4 = cursor.getColumnIndex(DatabaseHandler.DIFFICULTY_LEVEL);
			String difficultyLevel = cursor.getString(index4);
			buffer.append(cid+" "+name+" "+password+" "+difficultyLevel+"\n");
		}
		return buffer.toString();
	}
	
	public String getNames()
	{
		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		String[] columns = {DatabaseHandler.SAVEGAME_ID, DatabaseHandler.NAME, DatabaseHandler.PASSWORD, DatabaseHandler.DIFFICULTY_LEVEL};
		Cursor cursor = db.query(DatabaseHandler.SAVEGAME_TABLE, columns, null, null, null, null, null);
		StringBuffer buffer = new StringBuffer();
		while(cursor.moveToNext())
		{
			int index1 = cursor.getColumnIndex(DatabaseHandler.NAME);
			String name = cursor.getString(index1);
			buffer.append(name+" ");
		}
		return buffer.toString().trim();
	}
	
	public String[] getNamesArray()
	{
		int numberOfColumns = 0;
		
		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		String[] columns = {DatabaseHandler.SAVEGAME_ID, DatabaseHandler.NAME, DatabaseHandler.PASSWORD, DatabaseHandler.DIFFICULTY_LEVEL};
		Cursor cursor = db.query(DatabaseHandler.SAVEGAME_TABLE, columns, null, null, null, null, null);
		
		while(cursor.moveToNext())
		{
			numberOfColumns++;
		}
		String[] names = new String[numberOfColumns];
		int counter = 0;
		cursor = db.query(DatabaseHandler.SAVEGAME_TABLE, columns, null, null, null, null, null);
		while(cursor.moveToNext())
		{
			int index1 = cursor.getColumnIndex(DatabaseHandler.NAME);
			String name = cursor.getString(index1);
			names[counter] = name;
			counter++;
		}
		return names;
	}
	
	public String getID(String name, String password)
	{
		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		String[] columns = {DatabaseHandler.SAVEGAME_ID};
		String[] selectionArgs = {name, password};
		Cursor cursor = db.query(DatabaseHandler.SAVEGAME_TABLE, columns, 
				DatabaseHandler.NAME + " =? AND " + DatabaseHandler.PASSWORD + " =? ", 
				selectionArgs, null, null, null);
		StringBuffer buffer = new StringBuffer();
		while(cursor.moveToNext())
		{
			int index1 = cursor.getColumnIndex(DatabaseHandler.SAVEGAME_ID);
			int cid = cursor.getInt(index1);
			buffer.append(cid+"\n");
		}
		return buffer.toString();
	}
	
	public String[] getDataByID(String id)
	{
		int numberOfData = 3;
		
		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		String[] columns = {DatabaseHandler.SAVEGAME_ID, DatabaseHandler.NAME, DatabaseHandler.PASSWORD, DatabaseHandler.DIFFICULTY_LEVEL};
		Cursor cursor = db.query(DatabaseHandler.SAVEGAME_TABLE, columns, null, null, null, null, null);
		String[] data = new String[numberOfData];
		while(cursor.moveToNext())
		{
			int index1 = cursor.getColumnIndex(DatabaseHandler.SAVEGAME_ID);
			int cid = cursor.getInt(index1);
			int index2 = cursor.getColumnIndex(DatabaseHandler.NAME);
			String name = cursor.getString(index2);
			int index3 = cursor.getColumnIndex(DatabaseHandler.DIFFICULTY_LEVEL);
			String difficultyLevel = cursor.getString(index3);
			if(cid == Integer.parseInt(id))
			{
				data[0] = String.valueOf(cid);
				data[1] = name;
				data[2] = difficultyLevel;
			}
		}
		return data;
	}
	
	
	static class DatabaseHandler extends SQLiteOpenHelper
	{
		private static final String DATABASE_NAME = "savegame.db";
		private static final int DATABASE_VERSION = 5;
		
		private static final String SAVEGAME_TABLE = "savegame";
		
		private static final String SAVEGAME_ID = "_id";
		private static final String NAME = "name";
		private static final String PASSWORD = "password";
		private static final String DIFFICULTY_LEVEL = "difficultyLevel";
		private static final String QUERY = "CREATE TABLE " + SAVEGAME_TABLE + " (" +
				SAVEGAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255), " +
				PASSWORD + " VARCHAR(255), " + DIFFICULTY_LEVEL + " VARCHAR(255));";
		private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + SAVEGAME_TABLE;
		
		private Context context;

		public DatabaseHandler(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db)
		{
			
			try
			{
				db.execSQL(QUERY);
				if(Root.DEBUG) Message.message(context, "onCreate called");
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
				if(Root.DEBUG) Message.message(context, "onUpgrade called");
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