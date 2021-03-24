package com.example.bodima;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class databaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "bodimdetails.db";
    public static final String TABLE_NAME = "students_data";

    public databaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(month VARCHAR,month_fee VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean addColumn(String columnName){
        SQLiteDatabase appDatabase = this.getReadableDatabase();
        String ADD_COLUMN = "ALTER TABLE "+TABLE_NAME +" ADD COLUMN "+columnName + " TEXT;";
        try {
            appDatabase.execSQL(ADD_COLUMN);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addRow(String column, String value){
        SQLiteDatabase appDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column,value);
        long i = appDatabase.insert(TABLE_NAME,null,contentValues);
        appDatabase.close();
        return i != -1;
    }

    public boolean addValues(String rowName, String columnName, String value){
        SQLiteDatabase appDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnName,value);
        long i = appDatabase.update(TABLE_NAME,contentValues,"month = ?",new String[]{rowName});
        appDatabase.close();
        return i == 1;
    }

    public String getValueInDatabase(String rowName , String columnName){
        String value = "";
        SQLiteDatabase appDatabase = this.getReadableDatabase();
        Cursor c = appDatabase.rawQuery("select * from " + TABLE_NAME, null);
        int count = c.getCount();
        if (count==0){
            return null;
        }
        c.moveToFirst();
        String temp;
        while (true) {
            temp = c.getString(0);
            if (temp.equals(rowName)) {
                break;
            }
            c.moveToNext();
        }

        try {
            value = c.getString(c.getColumnIndex(columnName));
        }catch (Exception e){
            appDatabase.close();
            return null;
        }finally {
            c.close();
        }
        appDatabase.close();
        return value;
    }

    public ArrayList<String> returnAllValuesInColumn(String columnName){
        ArrayList<String> values = new ArrayList<>();
        SQLiteDatabase appDatabase = this.getReadableDatabase();
        try {
            Cursor res = appDatabase.rawQuery("select * from " + TABLE_NAME, null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                values.add(res.getString(res.getColumnIndex(columnName)));
                res.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        appDatabase.close();
        return values;
    }

    public String[] getAllColumnNames(){
        String[] columnNames;
        SQLiteDatabase appDatabase = this.getReadableDatabase();
        Cursor c = appDatabase.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE 0", null);
        try {
            columnNames = c.getColumnNames();
        } finally {
            c.close();
        }
        appDatabase.close();
        return columnNames;
    }
}
