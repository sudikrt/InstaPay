package com.alburaq.simled;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.style.LeadingMarginSpan;
import android.util.Log;
import android.widget.Toast;

public class DBAdapter {
	public static final String KEY_UNAME = "uname";
	public static final String KEY_PASS = "password";
	public static final String LOGIN_TABLE = "login";
	 public static final String KEY_ROWID = "_id";
	 public static final String KEY_DATE = "date";
	 public static final String KEY_TYPE = "type";
	 public static final String KEY_BAG = "bag";
	 public static final String KEY_CNO = "cno";
	 public static final String KEY_SNO = "sno";
	 public static final String KEY_BAL = "bal_amt";
	 public static final String KEY_TOTAL = "total";
	 private static final String TAG = "DBAdapter";
	 
	 public static final String KEY_NAME = "name";
	 public static final String KEY_MOB = "mob";
	 public static final String KEY_RPB = "rpb";
	 public static final String KEY_TAMT = "tamt";
	 public static final String KEY_PAMT = "pamt";
	 public static final String KEY_RAMT = "ramt";
	 public static final String KEY_STATUS = "status";
	 
	 public static final String KEY_ID = "id";
	 public static final String KEY_PDATE = "paidDate";
	 public static final String KEY_BBP = "bbp";
	 public static final String KEY_PMT = "pmt";
	 public static final String KEY_BAP = "bap";
	 public static final String KEY_INOUT = "inout";
	 
	 public static final String KEY_SRATE = "srate";
	 public static final String KEY_CRATE = "crate";
	 
	 private static final String DATABASE_NAME = "simpl";
	 private static final String DATABASE_TABLE = "godown";
	 private static final String DATABASE_TABLE_INVEN = "inven";
	 private static final String DATABASE_TABLE_PARTY = "party";
	 private static final String DATABASE_TABLE_BALANCE = "balance";
	 private static final String DATABASE_TABLE_RATE = "rate";
	 private static final int DATABASE_VERSION = 3;
	 
	 final Context context;
	 private DatabaseHelper DBHelper;
	 private SQLiteDatabase db;
	 String status="";

	private static final String LOG_TABLE_CREATE =
										"create table login (uname text primary key, password text)";
	private static final String DATABASE_CREATE_RATE =
										"create table rate (_id integer primary key autoincrement, "
			 								+ "crate double not null, srate double not null);";
	private static final String DATABASE_CREATE =
										"create table godown (_id integer primary key autoincrement, "
		      + "date text not null, type text not null ,bag integer not null, inout text not null);";
	 
	private static final String DATABASE_CREATE_INVEN =
									"create table inven (_id integer primary key autoincrement, "+
	 "cno integer not null, sno integer not null, bal_amt double not null, total double not null);";
	 
	private static final String DATABASE_CREATE_BALANCE =
									"create table balance (_id integer primary key autoincrement, "+
	 								"id integer not null, paidDate text not null, name text not null, "
									+ "bbp double not null, pmt double not null, bap double not null);";
	 
	 
	private static final String DATABASE_CREATE_PARTY =
									"create table party (_id integer primary key autoincrement, " +
									"date text not null, name text not null, mob text not null, " +
									" type text not null, bag integer not null, rpb double not null, " +
									"tamt double not null, pamt double not null, ramt double not null, " +
									"status text not null);";
	 public DBAdapter(Context ctx) {
		    this.context = ctx;
		    DBHelper = new DatabaseHelper(context);
	 }

	 private class DatabaseHelper extends SQLiteOpenHelper {

			public DatabaseHelper(Context context) {
				super(context, DATABASE_NAME, null, DATABASE_VERSION);
			}

			@Override
			public void onCreate(SQLiteDatabase db) {
				try {
			        db.execSQL(DATABASE_CREATE);
			        db.execSQL(DATABASE_CREATE_INVEN);
			        db.execSQL(DATABASE_CREATE_PARTY);
			        db.execSQL(DATABASE_CREATE_BALANCE);
			        db.execSQL(DATABASE_CREATE_RATE);
					db.execSQL(LOG_TABLE_CREATE);
			      } catch (SQLException e) {
			        e.printStackTrace();
			      }

				ContentValues initialValues = new ContentValues();
			    initialValues.put(KEY_CNO, 0);
			    initialValues.put(KEY_SNO, 0);
			    initialValues.put(KEY_BAL, 0.0);
			    initialValues.put(KEY_TOTAL, 0.0);
			    db.insert(DATABASE_TABLE_INVEN, null, initialValues);
			    
			    ContentValues initialValues1 = new ContentValues();
			    initialValues1.put(KEY_SRATE, 20);
			    initialValues1.put(KEY_CRATE, 20);
			    db.insert(DATABASE_TABLE_RATE, null, initialValues1);

				initialValues = null;
				initialValues = new ContentValues();
				initialValues.put(KEY_UNAME, "admin");
				initialValues.put(KEY_PASS, "12345");
				db.insert(LOGIN_TABLE, null,initialValues);
			}

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				// TODO Auto-generated method stub
				Log.w(TAG, oldVersion + " to " + newVersion
				          + ", which will destroy all old data");
				      db.execSQL("DROP TABLE IF EXISTS contacts");
				      onCreate(db);
			}

	 }
	 
	 public DBAdapter open() throws SQLException {
		    db = DBHelper.getWritableDatabase();
		    return this;
	 }

	 public void close() {
		    DBHelper.close();
	 }
	 
	 public long insertContact(String date, String type, int bag, String inout) {
		    ContentValues initialValues = new ContentValues();
		    initialValues.put(KEY_DATE, date);
		    initialValues.put(KEY_TYPE, type);
		    initialValues.put(KEY_BAG, bag);
		    initialValues.put(KEY_INOUT, inout);
		    updateTableInvenGoDown(type, bag);
		    return db.insert(DATABASE_TABLE, null, initialValues);
	 }
	public boolean checkLogin(String uName, String uPass) {

		Cursor cursor =  db.query(LOGIN_TABLE, new String[]{KEY_UNAME, KEY_PASS}, KEY_UNAME + "=?",
                                                            new String[]{uName}, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getString(0).toString().equals(uName) &&
					cursor.getString(1).toString().equals(uPass)) {
				return true;
			}
		}
		return false;

	}

	private void updateTableInvenGoDown(String type, int bag) {
		// TODO Auto-generated method stub
		ContentValues initialValues = new ContentValues();
		Cursor mCursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE_INVEN, null);
		try{
		    if (mCursor != null) {
		      mCursor.moveToFirst();
		    }
		if(type.contentEquals("Chicken Manure")){
			int m=Integer.parseInt(mCursor.getString(1));
			bag+=m;
			initialValues.put(KEY_CNO,bag);
		}
		else
		{
			int m=Integer.parseInt(mCursor.getString(2));
			bag+=m;
			initialValues.put(KEY_SNO,bag);
		}
		db.update(DATABASE_TABLE_INVEN, initialValues, KEY_ROWID + "=" + 1, null);
		}catch(Exception e)
		{
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
		}
		
	}
	
	public long insertParty(String date, String name, String mob, String type, int bag, double rpb,
                                                            double tamt, double pamt, double ramt) {

		
		if(ramt>0)
		{
			status="PENDING";
		}
		else
		{
			status="PAID";
		}
		ContentValues initialValues = new ContentValues();
	    initialValues.put(KEY_DATE, date);
	    initialValues.put(KEY_NAME, name);
	    initialValues.put(KEY_MOB, mob);   
	    initialValues.put(KEY_TYPE, type);
	    initialValues.put(KEY_BAG, bag);
	    initialValues.put(KEY_RPB, rpb);
	    initialValues.put(KEY_TAMT, tamt);
	    initialValues.put(KEY_PAMT, pamt);
	    initialValues.put(KEY_RAMT, ramt);
	    initialValues.put(KEY_STATUS, status);
	    updateTableInvenParty(type,bag);
	    insertGodownFromParty(date,type,bag,"OUT");
	    return db.insert(DATABASE_TABLE_PARTY, null, initialValues);
 }
	private void insertGodownFromParty(String date, String type, int bag, String inout)
	{
		ContentValues initialValues = new ContentValues();
	    initialValues.put(KEY_DATE, date);
	    initialValues.put(KEY_TYPE, type);
	    initialValues.put(KEY_BAG, bag);
	    initialValues.put(KEY_INOUT, inout);
	    db.insert(DATABASE_TABLE, null, initialValues);
	}
	private void updateTableInvenParty(String type, int bag) {
		// TODO Auto-generated method stub
		ContentValues initialValues = new ContentValues();
		Cursor mCursor = db.rawQuery("SELECT * FROM "+DATABASE_TABLE_INVEN, null);
		try{
		    if (mCursor != null) {
		      mCursor.moveToFirst();
		    }
		if(type.contentEquals("Chicken Manure")){
			int m=Integer.parseInt(mCursor.getString(1));
			m-=bag;
			initialValues.put(KEY_CNO,m);
		}
		else
		{
			int m=Integer.parseInt(mCursor.getString(2));
			m-=bag;
			initialValues.put(KEY_SNO,m);
		}
		db.update(DATABASE_TABLE_INVEN, initialValues, KEY_ROWID + "=" + 1, null);
		}catch(Exception e)
		{
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
		}
		
	}

    public void updatePassword(String newPassword) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PASS, newPassword);
        db.update(LOGIN_TABLE,initialValues, KEY_UNAME + "=" + "'admin'", null);
    }
	public Cursor getAllGoDown() {
	    return db.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_DATE,
	        KEY_TYPE, KEY_BAG, KEY_INOUT }, null, null, null, null, null);
	}
	public Cursor getAllInven() {
	    return db.rawQuery("SELECT * FROM "+DATABASE_TABLE_INVEN, null);
	}
	
	public Cursor getAllParty() {
	    return db.query(DATABASE_TABLE_PARTY, new String[] { KEY_ROWID, KEY_DATE, KEY_NAME, KEY_MOB,
                                                            KEY_TYPE, KEY_BAG, KEY_RPB, KEY_TAMT,
                                                            KEY_PAMT, KEY_RAMT, KEY_STATUS },
                                                            null, null, null, null, null);
	}
	
	public int getCount(String selectedItem)
	{
		int i=0;
		Cursor c=db.rawQuery("SELECT * FROM "+DATABASE_TABLE_INVEN, null);
		if(c.moveToFirst())
		{
			if(selectedItem.contentEquals("Chicken Manure"))
			{
				i=Integer.parseInt(c.getString(1));
			}
			else
			{
				i=Integer.parseInt(c.getString(2));
			}
		}
		return i;
	}

	public Cursor getAllPending()
	{
		return db.query(DATABASE_TABLE_PARTY, new String[]{KEY_ROWID,  KEY_DATE,  KEY_NAME, KEY_MOB,
                                                            KEY_TYPE, KEY_BAG, KEY_TAMT, KEY_PAMT,
                                                            KEY_RAMT}, KEY_STATUS+"=?",
                                                            new String[]{"PENDING"}, null, null,null);
	}
	
	public Cursor getIdData(int id)
	{
		return db.query(DATABASE_TABLE_PARTY, new String[]{KEY_ROWID,  KEY_DATE,  KEY_NAME, KEY_MOB,
                                                            KEY_TYPE, KEY_BAG, KEY_TAMT, KEY_PAMT,
                                                            KEY_RAMT, KEY_STATUS}, KEY_ROWID + "=?",
                                                            new String[]{String.valueOf(id)},
                                                            null, null, null);
	}

	public void updatePayment(String name, int id, double bal, double rem, String status,
                                                                                    double paid) {
		// TODO Auto-generated method stub
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_PAMT, bal);
		initialValues.put(KEY_RAMT, rem);
		initialValues.put(KEY_STATUS, status);
		db.update(DATABASE_TABLE_PARTY, initialValues, KEY_ROWID + "=" + id , null);
				
		final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        String s = day + "/" + (month+1) + "/" +year;
		
		ContentValues initialValues1 = new ContentValues();
		initialValues1.put(KEY_ID, id);
		initialValues1.put(KEY_NAME, name);
		initialValues1.put(KEY_PDATE, s);
		initialValues1.put(KEY_BBP, rem);
		initialValues1.put(KEY_PMT, paid);
		initialValues1.put(KEY_BAP, bal);
		db.insert(DATABASE_TABLE_BALANCE, null, initialValues1);
	}
	public Cursor getBalanceDetails(int id)
	{
		return db.query(DATABASE_TABLE_BALANCE, new String[]{KEY_ID, KEY_PDATE, KEY_NAME, KEY_BBP,
                                                            KEY_PMT, KEY_BAP} , KEY_ID + "=?" ,
                                                            new String[]{String.valueOf(id)},
                                                            null, null,null);
	}
	public Cursor getPendingAmt()
	{
		return db.query(DATABASE_TABLE_PARTY, new String[]{KEY_TAMT, KEY_PAMT, KEY_RAMT},
                                    KEY_STATUS + "=?", new String[]{"PENDING"}, null, null, null);
	}
	public void updateRate(double d,String type)
	{
		ContentValues cv = new ContentValues();
		if(type.contentEquals("Chicken Manure"))
		{
			cv.put(KEY_CRATE, d);
		}
		else{
			cv.put(KEY_SRATE, d);
		}
		db.update(DATABASE_TABLE_RATE, cv, KEY_ROWID + "=" + 1, null);
	}
	public double getRate(String type)
	{
		double d=0;
		Cursor c = db.rawQuery("SELECT * FROM " + DATABASE_TABLE_RATE, null);
		if(c.moveToFirst())
		{
			if(type.contentEquals("Chicken Manure"))
			{
				d = c.getDouble(1);
			}
			else
			{
				d = c.getDouble(2);
			}
		}
		return d;
	}
}
