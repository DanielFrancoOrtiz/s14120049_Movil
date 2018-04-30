package com.example.myapplication.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DB extends SQLiteOpenHelper{

private static final String DATABASE_NAME = "blockDb";
private static final int DATABASE_VERSION = 1;
private static final String TABLE_BLOCKLIST = "blocklist";
private static final String BLOCKLIST_ID = "id";
private static final String BLOCKLIST_NUMBER = "number";
private static final String BLOCKLIST_NAME = "name";
 
public DB(Context context){
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
}

@Override
public void onCreate(SQLiteDatabase db){
    String CREATE_BLOCK_TABLE = "CREATE TABLE " + TABLE_BLOCKLIST + "("
            + BLOCKLIST_ID + " INTEGER PRIMARY KEY," + BLOCKLIST_NUMBER + " TEXT,"
            + BLOCKLIST_NAME + " TEXT" + ")";
    db.execSQL(CREATE_BLOCK_TABLE);
}
 
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOCKLIST);
    onCreate(db);
}

public void addBlockedNumber(String number, String name){
	    SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(BLOCKLIST_NUMBER, number);
	    values.put(BLOCKLIST_NAME, name);
	    db.insert(TABLE_BLOCKLIST, null, values);
	    db.close();
	}

public String[] listBlockedNumbers(){
    ArrayList temp_array = new ArrayList();
    String[] block_array = new String[0];
    String sqlQuery = "SELECT * FROM " + TABLE_BLOCKLIST;
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor c = db.rawQuery(sqlQuery, null);
    if (c.moveToFirst()){
        do{
              temp_array.add(c.getString(c.getColumnIndex(BLOCKLIST_NUMBER)) + ";"+c.getString(c.getColumnIndex(BLOCKLIST_NAME)));
        }while(c.moveToNext());
     }
    c.close();
    block_array = (String[]) temp_array.toArray(block_array);
    return block_array;
}


public void deleteEntry(String number){
	String DELETE_ENTRY = "DELETE FROM " + TABLE_BLOCKLIST + " WHERE " + BLOCKLIST_NUMBER + "='" + number + "'";
	SQLiteDatabase db = this.getWritableDatabase();
	db.execSQL(DELETE_ENTRY);
}


}