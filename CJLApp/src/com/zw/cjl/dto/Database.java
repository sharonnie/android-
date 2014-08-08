package com.zw.cjl.dto;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Database {
	public static final String DB_NAME="cjl.db";
	private static final String CAR_TABLE_NAME = "cars";
	private static final String COACH_TABLE_NAME = "coachs";
	private static final String STUDENT_TABLE_NAME = "students";
	private static final String ORDER_TABLE_NAME = "orders";
	
	long carCount = 0;
	long studentCount = 0;
	long coachCount = 0;
	long orderCount = 0;
	
	public long getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(long orderCount) {
		this.orderCount = orderCount;
	}

	SQLiteDatabase db = null;
	Context ctx;
	
	public long getCarCount() {
		return carCount;
	}

	public void setCarCount(long carCount) {
		this.carCount = carCount;
	}

	public long getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(long studentCount) {
		this.studentCount = studentCount;
	}

	public long getCoachCount() {
		return coachCount;
	}

	public void setCoachCount(long coachCount) {
		this.coachCount = coachCount;
	}

	public Database(Context context) {
		ctx = context;
		db = ctx.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
		
		createCarTable();
		createCoachTable();
		createStudentTable();
		createOrderTable();
	}
	
	public void close(){
		if (null != db)
		{
			db.close();
			db = null;
		}		
	}
	
	private void createCarTable() {
		db.execSQL("DROP TABLE IF EXISTS "+CAR_TABLE_NAME);    
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
        		CAR_TABLE_NAME +
        		" (id INTEGER PRIMARY KEY, " +
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
		db.execSQL("DROP TABLE IF EXISTS " + COACH_TABLE_NAME);   
		db.execSQL("CREATE TABLE IF NOT EXISTS " +
				COACH_TABLE_NAME +
        		" (id INTEGER PRIMARY KEY, " +
        		"jxid INTEGER, " +
        		"xysl INTEGER, " +
        		"xbry VARCHAR(64), " +
        		"xjcx VARCHAR(64), " +
        		"xm VARCHAR(64), " +
        		"sjhm VARCHAR(64), " +
        		"sfzmhm VARCHAR(64), " +
        		"jxmc VARCHAR(64), " +
        		"division VARCHAR(64), " +
        		"cityDivision VARCHAR(64), " +
        		"cphm VARCHAR(64))");  
    }

	private void createStudentTable() {
		db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE_NAME);  
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " +
				STUDENT_TABLE_NAME +
        		" (id INTEGER PRIMARY KEY, " +
        		"jxid INTEGER, " +
        		"sfzmhm VARCHAR(64), " +
        		"division VARCHAR(64), " +
        		"xykh VARCHAR(64), " +
        		"jxmc VARCHAR(64), " +
        		"xm VARCHAR(64), " +
        		"sjhm VARCHAR(64), " +
        		"xbmc VARCHAR(64), " +
        		"cityDivision VARCHAR(64), " +
        		"sqcx VARCHAR(64), " +
        		"sqcxmc VARCHAR(64))");  
        		
    }
	
	private void createOrderTable() {
		db.execSQL("DROP TABLE IF EXISTS " + ORDER_TABLE_NAME);  
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " +
				ORDER_TABLE_NAME +
        		" (id VARCHAR(64) PRIMARY KEY, " +
        		"name VARCHAR(64), " +
        		"identity VARCHAR(64), " +
        		"stage VARCHAR(64), " +
        		"type VARCHAR(64), " +
        		"sqcx VARCHAR(64), " +
        		"examDate VARCHAR(64), " +
        		"examLocation VARCHAR(64), " +
        		"commitDate VARCHAR(64), " +
        		"status VARCHAR(64), " +
        		"reason VARCHAR(64))");  
        		
    }
	
	public List<Car> getCars(int offset, int limit) {
		
		List<Car> list = new ArrayList<Car>();
		
		if (offset < carCount && limit > 0)
		{
			Cursor cursor = db.query(CAR_TABLE_NAME, 
									 null, 
									 null, 
									 null, 
									 null, 
									 null, 
									 null, 
									 offset + "," + limit);		
			
			while (cursor.moveToNext())
			{
				Car c = new Car(cursor.getLong(cursor.getColumnIndex("id")),
						cursor.getLong(cursor.getColumnIndex("orgId")),
						cursor.getLong(cursor.getColumnIndex("schoolId")),
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
		}
	
		return list;
	}
	
	public List<Coach> getCoachs(int offset, int limit) 
	{
			
		List<Coach> list = new ArrayList<Coach>();
		
		if (offset < coachCount && limit > 0)
		{
			Cursor cursor = db.query(COACH_TABLE_NAME, 
									 null, 
									 null, 
									 null, 
									 null, 
									 null, 
									 null, 
									 offset + "," + limit);		
			
			while (cursor.moveToNext())
			{
				Coach c = new Coach(cursor.getLong(cursor.getColumnIndex("id")),
						cursor.getLong(cursor.getColumnIndex("jxid")),
						cursor.getLong(cursor.getColumnIndex("xysl")),
						cursor.getString(cursor.getColumnIndex("xbry")),
						cursor.getString(cursor.getColumnIndex("xjcx")),
						cursor.getString(cursor.getColumnIndex("xm")),
						cursor.getString(cursor.getColumnIndex("sjhm")),
						cursor.getString(cursor.getColumnIndex("sfzmhm")),
						cursor.getString(cursor.getColumnIndex("jxmc")),
						cursor.getString(cursor.getColumnIndex("division")),
						cursor.getString(cursor.getColumnIndex("cityDivision")),
						cursor.getString(cursor.getColumnIndex("cphm")));
				
				list.add(c);
			}
			
			cursor.close();
		}
		
		return list;
	}
	
	public List<Student> getStudents(int offset, int limit) 
	{
			
		List<Student> list = new ArrayList<Student>();
		
		if (offset < studentCount && limit > 0)
		{
			Cursor cursor = db.query(STUDENT_TABLE_NAME, 
									 null, 
									 null, 
									 null, 
									 null, 
									 null, 
									 null, 
									 offset + "," + limit);		
			
			while (cursor.moveToNext())
			{
				Student s = new Student(cursor.getLong(cursor.getColumnIndex("id")),
						cursor.getLong(cursor.getColumnIndex("jxid")),
						cursor.getString(cursor.getColumnIndex("xykh")),
						cursor.getString(cursor.getColumnIndex("xbmc")),
						cursor.getString(cursor.getColumnIndex("sfzmhm")),
						cursor.getString(cursor.getColumnIndex("sqcxmc")),
						cursor.getString(cursor.getColumnIndex("sjhm")),
						cursor.getString(cursor.getColumnIndex("xm")),
						cursor.getString(cursor.getColumnIndex("sqcx")),
						cursor.getString(cursor.getColumnIndex("division")),
						cursor.getString(cursor.getColumnIndex("cityDivision")),
						cursor.getString(cursor.getColumnIndex("jxmc")));
				
				list.add(s);
			}
			
			cursor.close();
		}
		
		return list;
	}
	
	public List<Order> getOrders(int offset, int limit) 
	{
			
		List<Order> list = new ArrayList<Order>();
		
		if (offset < orderCount && limit > 0)
		{
			Cursor cursor = db.query(ORDER_TABLE_NAME, 
									 null, 
									 null, 
									 null, 
									 null, 
									 null, 
									 null, 
									 offset + "," + limit);		
			
			while (cursor.moveToNext())
			{
				Order o = new Order(cursor.getString(cursor.getColumnIndex("id")),
						cursor.getString(cursor.getColumnIndex("name")),
						cursor.getString(cursor.getColumnIndex("identity")),
						cursor.getString(cursor.getColumnIndex("stage")),
						cursor.getString(cursor.getColumnIndex("type")),
						cursor.getString(cursor.getColumnIndex("sqcx")),
						cursor.getString(cursor.getColumnIndex("examDate")),
						cursor.getString(cursor.getColumnIndex("examLocation")),
						cursor.getString(cursor.getColumnIndex("commitDate")),
						cursor.getString(cursor.getColumnIndex("status")),
						cursor.getString(cursor.getColumnIndex("reason")));
				
				list.add(o);
			}
			
			cursor.close();
		}
		
		return list;
	}
	
	public List<Car> queryCars(String having, int offset, int limit) 
	{
		List<Car> list = new ArrayList<Car>();
		
		Cursor cursor = db.query(CAR_TABLE_NAME, 
				                 null, 
				                 "ownerName LIKE upper('"+having+"%') or carNo LIKE upper('%"+having+"%')", 
				                 null,
				                 null, 
				                 null, 
				                 null, 
				                 offset + "," + limit);	
		
		while (cursor.moveToNext())
		{
			Car c = new Car(cursor.getLong(cursor.getColumnIndex("id")),
					cursor.getLong(cursor.getColumnIndex("orgId")),
					cursor.getLong(cursor.getColumnIndex("schoolId")),
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
	
	public List<Coach> queryCoachs(String having, int offset, int limit) 
	{
		List<Coach> list = new ArrayList<Coach>();
		
		Cursor cursor = db.query(COACH_TABLE_NAME, 
				                 null, 
				                 "xm LIKE upper('"+having+"%') or sjhm LIKE upper('%"+having+"%')", 
				                 null,
				                 null, 
				                 null, 
				                 null, 
				                 offset + "," + limit);	
		
		while (cursor.moveToNext())
		{
			Coach c = new Coach(cursor.getLong(cursor.getColumnIndex("id")),
					cursor.getLong(cursor.getColumnIndex("jxid")),
					cursor.getLong(cursor.getColumnIndex("xysl")),
					cursor.getString(cursor.getColumnIndex("xbry")),
					cursor.getString(cursor.getColumnIndex("xjcx")),
					cursor.getString(cursor.getColumnIndex("xm")),
					cursor.getString(cursor.getColumnIndex("sjhm")),
					cursor.getString(cursor.getColumnIndex("sfzmhm")),
					cursor.getString(cursor.getColumnIndex("jxmc")),
					cursor.getString(cursor.getColumnIndex("division")),
					cursor.getString(cursor.getColumnIndex("cityDivision")),
					cursor.getString(cursor.getColumnIndex("cphm")));
			
			list.add(c);
		}
		
		cursor.close();
		
		return list;
	}
	
	public List<Student> queryStudents(String having, int offset, int limit) {
		List<Student> list = new ArrayList<Student>();
		
		Cursor cursor = db.query("students", 
				                 null, 
				                 "xm LIKE upper('"+having+"%') or sjhm LIKE upper('%"+having+"%')", 
				                 null,
				                 null, 
				                 null, 
				                 null, 
				                 offset + "," + limit);	
		
		while (cursor.moveToNext())
		{
			Student s = new Student(cursor.getLong(cursor.getColumnIndex("id")),
					cursor.getLong(cursor.getColumnIndex("jxid")),
					cursor.getString(cursor.getColumnIndex("xykh")),
					cursor.getString(cursor.getColumnIndex("xbmc")),
					cursor.getString(cursor.getColumnIndex("sfzmhm")),
					cursor.getString(cursor.getColumnIndex("sqcxmc")),
					cursor.getString(cursor.getColumnIndex("sjhm")),
					cursor.getString(cursor.getColumnIndex("xm")),
					cursor.getString(cursor.getColumnIndex("sqcx")),
					cursor.getString(cursor.getColumnIndex("division")),
					cursor.getString(cursor.getColumnIndex("cityDivision")),
					cursor.getString(cursor.getColumnIndex("jxmc")));
			
			list.add(s);
		}
		
		cursor.close();
		
		return list;
	}
	
	public List<Order> queryOrders(String having, int offset, int limit) {
		List<Order> list = new ArrayList<Order>();
		
		Cursor cursor = db.query(ORDER_TABLE_NAME, 
				                 null, 
				                 "name LIKE upper('"+having+"%') or examDate LIKE upper('%"+having+"%')", 
				                 null,
				                 null, 
				                 null, 
				                 null, 
				                 offset + "," + limit);	
		
		while (cursor.moveToNext())
		{
			Order o = new Order(cursor.getString(cursor.getColumnIndex("id")),
					cursor.getString(cursor.getColumnIndex("name")),
					cursor.getString(cursor.getColumnIndex("identity")),
					cursor.getString(cursor.getColumnIndex("stage")),
					cursor.getString(cursor.getColumnIndex("type")),
					cursor.getString(cursor.getColumnIndex("sqcx")),
					cursor.getString(cursor.getColumnIndex("examDate")),
					cursor.getString(cursor.getColumnIndex("examLocation")),
					cursor.getString(cursor.getColumnIndex("commitDate")),
					cursor.getString(cursor.getColumnIndex("status")),
					cursor.getString(cursor.getColumnIndex("reason")));
			
			list.add(o);
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
		
				db.insert(CAR_TABLE_NAME, null, values);
			}
		} finally {
			db.setTransactionSuccessful();
			db.endTransaction();
		}
	}
	
	public void insertCoachs(List<Coach> coachList) {
		db.beginTransaction();
		try {
				
			for (Coach c : coachList)
			{
				ContentValues values = new ContentValues();
				values.put("id", c.id);
				values.put("jxid", c.jxid);
				values.put("xysl", c.xysl);
				values.put("xbry", c.xbry);
				values.put("xjcx", c.xjcx);
				values.put("xm", c.xm);
				values.put("sjhm", c.sjhm);
				values.put("sfzmhm", c.sfzmhm);
				values.put("jxmc", c.jxmc);
				values.put("division", c.division);
				values.put("cityDivision", c.cityDivision);
				values.put("cphm", c.cphm);
		
				db.insert(COACH_TABLE_NAME, null, values);
			}
		} finally {
			db.setTransactionSuccessful();
			db.endTransaction();
		}
	}
	
	public void insertStudent(List<Student> studentList) {
		db.beginTransaction();
		try {
				
			for (Student s : studentList)
			{
				ContentValues values = new ContentValues();
				values.put("id", s.id);
				values.put("jxid", s.jxid);
				values.put("xykh", s.xykh);
				values.put("xbmc", s.xbmc);
				values.put("sfzmhm", s.sfzmhm);
				values.put("sqcxmc", s.sqcxmc);
				values.put("sjhm", s.sjhm);
				values.put("xm", s.xm);
				values.put("sqcx", s.sqcx);
				values.put("division", s.division);
				values.put("cityDivision", s.cityDivision);
				values.put("jxmc", s.jxmc);
		
				db.insert(STUDENT_TABLE_NAME, null, values);
			}
		} finally {
			db.setTransactionSuccessful();
			db.endTransaction();
		}
	}
	
	public void insertOrder(List<Order> orderList) {
		db.beginTransaction();
		try {
				
			for (Order o : orderList)
			{
				ContentValues values = new ContentValues();
				values.put("id", o.id);
				values.put("name", o.name);
				values.put("identity", o.identity);
				values.put("stage", o.stage);
				values.put("type", o.type);
				values.put("sqcx", o.sqcx);
				values.put("examDate", o.examDate);
				values.put("examLocation", o.examLocation);
				values.put("commitDate", o.commitDate);
				values.put("status", o.status);
				values.put("reason", o.reason);
		
				db.insert(ORDER_TABLE_NAME, null, values);
			}
		} finally {
			db.setTransactionSuccessful();
			db.endTransaction();
		}
	}
}
