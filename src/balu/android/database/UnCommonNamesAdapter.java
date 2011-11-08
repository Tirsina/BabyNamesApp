package balu.android.database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import balu.android.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UnCommonNamesAdapter {

	public static final String DATABASE_TABLE_2 = "un_common_names";
	
	public static final String UN_COMMON_NAME_ROWID = "_id";
	public static final String UN_COMMON_NAME = "un_common_name";
		
	private final Context context;
	private SQLiteDatabase database;

	public static final String TAG = "UN_COMMON_NAMES_TABLE";	
	private BabyNamesDBHelper baby_names_db_helper;

	public UnCommonNamesAdapter(Context context) {
		
	    this.context = context;
	    this.open();
	      
	    try{
	    	InputStream is = context.getResources().openRawResource(R.raw.uncommonnames);
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        String strLine = null;

	    	while ((strLine = br.readLine()) != null) {
	    		strLine = strLine.trim();    		  
		    	this.createUnCommonName(strLine);
		    }
	    	is.close();
	    }
	    catch (Exception e){
	    	Log.i(TAG, "Error while inserting common names into table");
	    }
	    this.close();
	}

	public UnCommonNamesAdapter open() throws SQLException {
		
		Log.i(TAG, "OPening DataBase Connection....");
		baby_names_db_helper = new BabyNamesDBHelper(context);
		database = baby_names_db_helper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		database.close();
	}
		  
	public long createUnCommonName(String uncommonName) {
		Log.i(TAG, "Inserting record...");

	    ContentValues initialValues = new ContentValues();

	    initialValues.put(UN_COMMON_NAME, uncommonName);
	    
	    return database.insert(DATABASE_TABLE_2, null, initialValues);
	}
	
	public boolean deleteUnCommonName(long rowId) {
	    return database.delete(DATABASE_TABLE_2, UN_COMMON_NAME_ROWID + "=" + rowId, null) > 0;
	}
	 
	public Cursor fetchAllUnCommonNames() {	 
	    return database.query(DATABASE_TABLE_2, 
	    		new String[] {UN_COMMON_NAME_ROWID, UN_COMMON_NAME,},
	    		null, null, null, null, null);
	}
	 
	public Cursor fetchCommonName(long uncommonNameId) throws SQLException {
		
		Cursor mCursor = database.query(true, DATABASE_TABLE_2, new String[] {
				UN_COMMON_NAME_ROWID, UN_COMMON_NAME}, UN_COMMON_NAME_ROWID + "=" +
				uncommonNameId, null, null, null, null, null);
		
	    if(mCursor != null) {
	    	mCursor.moveToFirst();
	    }
	    return mCursor;
	}
	
	public boolean updateUnCommonName(int uncommonNameId, String uncommonName) {
	    ContentValues args = new ContentValues();
	    args.put(UN_COMMON_NAME, uncommonName);
	   
	    return database.update(DATABASE_TABLE_2, args, UN_COMMON_NAME_ROWID, null) > 0;
	  }
	 

}