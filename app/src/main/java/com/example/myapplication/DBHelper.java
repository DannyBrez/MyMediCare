package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT, firstName TEXT, age INT, postcode TEXT, phoneNumber INT, gpPhoneNumber INT)");
        MyDB.execSQL("create Table readings(id INTEGER primary key autoincrement, date TEXT, temperature TEXT, bloodPressure TEXT, heartrate TEXT, username TEXT references users)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists readings");
        onCreate(MyDB);
    }

    //User Table Methods
    public Boolean insertData(String username, String password, String firstName, String age, String postcode, String phoneNumber, String gpPhoneNumber) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("firstName", firstName);
        contentValues.put("age", age);
        contentValues.put("postcode", postcode);
        contentValues.put("phoneNumber", phoneNumber);
        contentValues.put("gpPhoneNumber", gpPhoneNumber);
        long result = MyDB.insert("users", null, contentValues);
        if (result == -1) return false;
        else
            return true;
    }

    public Boolean updateData(String username, String age, String postcode, String phoneNumber, String gpPhoneNumber) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("age", age);
        contentValues.put("postcode", postcode);
        contentValues.put("phoneNumber", phoneNumber);
        contentValues.put("gpPhoneNumber", gpPhoneNumber);
        Cursor cursor = MyDB.rawQuery("select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
        {
            long result = MyDB.update("users", contentValues, "username = ?", new String[]{username});
            if (result == -1)
                return false;
            else
                return true;
        }
        else
            return false;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public Boolean checkusernamepassword (String username, String password)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public Cursor fetchUserData(String username)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where username = ?", new String[] {username});
        return cursor;
    }

    //Readings Table Methods
    public Boolean insertDataReadings(String date, String temperature, String bloodPressure, String heartrate, String username)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues readingValues = new ContentValues();
        readingValues.put("date", date);
        readingValues.put("temperature", temperature);
        readingValues.put("bloodPressure", bloodPressure);
        readingValues.put("heartrate", heartrate);
        readingValues.put("username", username);
        long result = MyDB.insert("readings", null, readingValues);
        if (result == -1) return false;
        else
            return true;
    }
    public Cursor fetchReadingData(String username)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from readings where username = ?", new String[] {username});
        return cursor;
    }

    public ArrayList<Readings> getAllData(String username)
    {
        ArrayList<Readings> arrayList = new ArrayList<>();
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from readings where username = ?", new String[] {username});

        while(cursor.moveToNext())
        {
            String date = cursor.getString(1);
            String temp = cursor.getString(2);
            String bp = cursor.getString(3);
            String heart = cursor.getString(4);

            Readings readings = new Readings(date, temp, bp, heart);

            arrayList.add(readings);
        }
        return arrayList;
    }
}


