package com.zw.cjl.dto;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Database {
	public static final String DB_NAME="cjl.db";
	// cars, students, coaches
	SQLiteDatabase db = null;
	Context ctx;
	
	public Database(Context context) {
		ctx = context;
		db = ctx.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
		
		createCarTable();
		createCoachTable();
		createStudentTable();
	}
	
	public void close(){
		if (null != db)
		{
			db.close();
			db = null;
		}		
	}
	
	public int getCarTableRows() {
		int num = 0;
		Cursor cursor = db.query("cars", new String[]{"id"}, null, null, null, null, null);
		num = cursor.getCount();
		return num;
	}
	
	private void createCarTable() {
		db.execSQL("DROP TABLE IF EXISTS cars");    
        db.execSQL("CREATE TABLE IF NOT EXISTS cars " +
        		"(id INTEGER PRIMARY KEY, " +
        		"ownerName VARCHAR(64), " +
        		"schoolName VARCHAR(64), " +
        		"carKind VARCHAR(64), " +
        		"status VARCHAR(64), " +
        		"cityDivision VARCHAR(64), " +
        		"simNo VARCHAR(64), " +
        		"schoolCode VARCHAR(64), " +
        		"orgId INTEGER, " +
        		"carType VARCHAR(64), " +
        		"carNo VARCHAR(64), " +
        		"division VARCHAR(64), " +
        		"deviceNo VARCHAR(64), " +
        		"schoolId INTEGER, " +
        		"ownerPhone VARCHAR(64))");  
	}
	
	private void createCoachTable() {
		db.execSQL("DROP TABLE IF EXISTS coachs");    
        db.execSQL("CREATE TABLE IF NOT EXISTS coachs (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age INTEGER,grade INTEGER, info VARCHAR)");  
	}

	private void createStudentTable() {
		db.execSQL("DROP TABLE IF EXISTS students");    
        db.execSQL("CREATE TABLE IF NOT EXISTS students (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age INTEGER,grade INTEGER, info VARCHAR)");  
	}
	
	public List<Car> getCars(int offset, int limit) {
		
		List<Car> list = new ArrayList<Car>();
		
		Cursor cursor = db.query("cars", 
								 null, 
								 null, 
								 null, 
								 null, 
								 null, 
								 null, 
								 offset + "," + limit);		
		
		while (cursor.moveToNext())
		{
			Car c = new Car(cursor.getInt(cursor.getColumnIndex("id")),
					cursor.getInt(cursor.getColumnIndex("orgId")),
					cursor.getInt(cursor.getColumnIndex("schoolId")),
					cursor.getString(cursor.getColumnIndex("ownerName")),
					cursor.getString(cursor.getColumnIndex("schoolName")),
					cursor.getString(cursor.getColumnIndex("carKind")),
					cursor.getString(cursor.getColumnIndex("status")),
					cursor.getString(cursor.getColumnIndex("cityDivision")),
					cursor.getString(cursor.getColumnIndex("simNo")),
					cursor.getString(cursor.getColumnIndex("schoolCode")),
					cursor.getString(cursor.getColumnIndex("carType")),
					cursor.getString(cursor.getColumnIndex("carNo")),
					cursor.getString(cursor.getColumnIndex("division")),
					cursor.getString(cursor.getColumnIndex("deviceNo")),
					cursor.getString(cursor.getColumnIndex("ownerPhone")));
			
			list.add(c);
		}
		
		cursor.close();
		
		return list;
	}
	
	public List<Car> queryCars(String having, int limit) {
		List<Car> list = new ArrayList<Car>();
		
		Cursor cursor = db.query("cars", 
				                 null, 
				                 "ownerName LIKE upper('"+having+"%') or carNo LIKE upper('"+having+"%')", 
				                 null,
				                 null, 
				                 null, 
				                 null, 
				                 "0," + limit);	
		
		while (cursor.moveToNext())
		{
			Car c = new Car(cursor.getInt(cursor.getColumnIndex("id")),
					cursor.getInt(cursor.getColumnIndex("orgId")),
					cursor.getInt(cursor.getColumnIndex("schoolId")),
					cursor.getString(cursor.getColumnIndex("ownerName")),
					cursor.getString(cursor.getColumnIndex("schoolName")),
					cursor.getString(cursor.getColumnIndex("carKind")),
					cursor.getString(cursor.getColumnIndex("status")),
					cursor.getString(cursor.getColumnIndex("cityDivision")),
					cursor.getString(cursor.getColumnIndex("simNo")),
					cursor.getString(cursor.getColumnIndex("schoolCode")),
					cursor.getString(cursor.getColumnIndex("carType")),
					cursor.getString(cursor.getColumnIndex("carNo")),
					cursor.getString(cursor.getColumnIndex("division")),
					cursor.getString(cursor.getColumnIndex("deviceNo")),
					cursor.getString(cursor.getColumnIndex("ownerPhone")));
			
			list.add(c);
		}
		
		cursor.close();
		
		return list;
	}
	
	public void insertCars(List<Car> carList) {
		db.beginTransaction();
		try {
				
			for (Car c : carList)
			{
				ContentValues values = new ContentValues();
				values.put("id", c._id);
				values.put("orgId", c._orgId);
				values.put("schoolId", c._schoolId);
				values.put("ownerName", c._ownerName);
				values.put("schoolName", c._schoolName);
				values.put("carKind", c._carKind);
				values.put("status", c._status);
				values.put("cityDivision", c._cityDivision);
				values.put("simNo", c._simNo);
				values.put("schoolCode", c._schoolCode);
				values.put("carType", c._carType);
				values.put("carNo", c._carNo);
				values.put("division", c._division);
				values.put("deviceNo", c._deviceNo);
				values.put("ownerPhone", c._ownerPhone);
		
				db.insert("cars", null, values);
			}
		} finally {
			db.setTransactionSuccessful();
			db.endTransaction();
		}
	}
	
	public void insertStudent(Student s) {
		
	}
}
