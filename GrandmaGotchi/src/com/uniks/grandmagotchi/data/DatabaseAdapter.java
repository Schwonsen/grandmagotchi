package com.uniks.grandmagotchi.data;

import java.util.LinkedList;

import com.uniks.grandmagotchi.util.Message;
import com.uniks.grandmagotchi.util.Root;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
	
	public long insertFoodData(String id, String foodName, String foodCount)
	{
		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		
		contentValues.put(DatabaseHandler.FOOD_ID, id);
		contentValues.put(DatabaseHandler.FOOD_NAME, foodName);
		contentValues.put(DatabaseHandler.FOOD_COUNT, foodCount);
		
		long status = db.insert(DatabaseHandler.FOOD_TABLE, null, contentValues);
		return status;
	}
	
	public void updateFoodData(String id, String foodName, String foodCount)
	{
		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		
		contentValues.put(DatabaseHandler.FOOD_COUNT, foodCount);
		String[] whereArgs = {id, foodName};
		
		db.update(DatabaseHandler.FOOD_TABLE, contentValues, DatabaseHandler.FOOD_ID+ "=? AND "
				+ DatabaseHandler.FOOD_NAME + "=?", whereArgs);
	}
	
	public void deleteFoodRow(String id)
	{
	    SQLiteDatabase db = databaseHandler.getWritableDatabase();
	    
	    db.delete(DatabaseHandler.FOOD_TABLE, "FOOD_ID=?", new String[] { id });
	    
	    Log.e("DATABASE", "DELETED");
//	    try
//	    {
//	        
//	    }
//	    catch(Exception e)
//	    {
//	        e.printStackTrace();
//	    }
//	    finally
//	    {
//	        db.close();
//	    }
	}
	
	public LinkedList<FoodAttributes> getFoodDataById(String id)
	{

			SQLiteDatabase db = databaseHandler.getWritableDatabase();
			String[] columns = {DatabaseHandler.FOOD_ID, DatabaseHandler.FOOD_NAME, DatabaseHandler.FOOD_COUNT};
			Cursor cursor = db.query(DatabaseHandler.FOOD_TABLE, columns, null, null, null, null, null);
			LinkedList<FoodAttributes> foodAttributes = new LinkedList<FoodAttributes>();
			while(cursor.moveToNext())
			{
				int index1 = cursor.getColumnIndex(DatabaseHandler.FOOD_ID);
				int cid = cursor.getInt(index1);
				int index2 = cursor.getColumnIndex(DatabaseHandler.FOOD_NAME);
				String foodName = cursor.getString(index2);
				int index3 = cursor.getColumnIndex(DatabaseHandler.FOOD_COUNT);
				String foodCount = cursor.getString(index3);
				if(cid == Integer.parseInt(id))
				{
					FoodAttributes fA = new FoodAttributes();
					Log.e("Get From DB", id);
					fA.setName(foodName);
					Log.e("Get From DB", foodName);
					fA.setCount(Integer.valueOf(foodCount));
					Log.e("Get From DB", foodCount);
					foodAttributes.add(fA);
				}
			}
			return foodAttributes;
	}
	
	
	public String getAllFoodData()
	{
		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		String[] columns = {DatabaseHandler.FOOD_ID, DatabaseHandler.FOOD_NAME, DatabaseHandler.FOOD_COUNT};
		Cursor cursor = db.query(DatabaseHandler.FOOD_TABLE, columns, null, null, null, null, null);
		StringBuffer buffer = new StringBuffer();
		while(cursor.moveToNext())
		{
			int index1 = cursor.getColumnIndex(DatabaseHandler.FOOD_ID);
			int cid = cursor.getInt(index1);
			int index2 = cursor.getColumnIndex(DatabaseHandler.FOOD_NAME);
			String name = cursor.getString(index2);
			int index3 = cursor.getColumnIndex(DatabaseHandler.FOOD_COUNT);
			String count = cursor.getString(index3);
			buffer.append(cid+" "+name+" "+count+"\n");
		}
		return buffer.toString();
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
		private static final int DATABASE_VERSION = 10;
		
		private static final String SAVEGAME_TABLE = "savegame";
		
		private static final String SAVEGAME_ID = "_id";
		private static final String NAME = "name";
		private static final String PASSWORD = "password";
		private static final String DIFFICULTY_LEVEL = "difficultyLevel";
		private static final String QUERY = "CREATE TABLE " + SAVEGAME_TABLE + " (" +
				SAVEGAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255), " +
				PASSWORD + " VARCHAR(255), " + DIFFICULTY_LEVEL + " VARCHAR(255));";
		private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + SAVEGAME_TABLE;
		
		
		private static final String FOOD_TABLE = "savefood";
		
		private static final String FOOD_ID = "_id";
		private static final String FOOD_NAME = "foodName";
		private static final String FOOD_COUNT = "foodCount";
		private static final String QUERY_FOOD = "CREATE TABLE " + FOOD_TABLE + " (" +
				FOOD_ID + " VARCHAR(255), " + FOOD_NAME + " VARCHAR(255), " + 
				FOOD_COUNT + " VARCHAR(255));";
		private static final String DROP_TABLE_FOOD = "DROP TABLE IF EXISTS " + FOOD_TABLE;
		
		
		
		
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
				db.execSQL(QUERY_FOOD);
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
				db.execSQL(DROP_TABLE_FOOD);
				onCreate(db);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

}