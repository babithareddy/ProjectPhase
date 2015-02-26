package com.example.hp.phase1;

/**
 * Created by BabithaReddy on 2/24/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.hp.phase1.user;

public class DB extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "RegistrationDB";
    private static final String[] COLUMNS_pswd = {"password"};
    private static final String[] COLUMNS_mobiles = {"mobilenum1","mobilenum2","mobilenum3","mobilenum4"};
    public DB(Context context) {
       super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create  table

        String CREATE_TABLE_USER = "CREATE TABLE if not exists userInfo (mobilenum TEXT, password TEXT, firstname TEXT, lastname TEXT, emailid TEXT, address1 TEXT,address2 TEXT, city TEXT, state TEXT, country TEXT)";
        String CREATE_TABLE_MOBILE = "CREATE TABLE if not exists frnsMobile (usermobile TEXT,mobilenum1 TEXT, mobilenum2 TEXT, mobilenum3 TEXT, mobilenum4 TEXT)";

        // create table
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_MOBILE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS userInfo");

        // create fresh books table
        this.onCreate(db);
    }
    public void addUser(user user){
        //for logging
      //  Log.d("addBook", book.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value

        ContentValues values = new ContentValues();
        values.put("mobilenum",user.mobileNum); // get title
        values.put("password",user.password); // get author
        values.put("firstname",user.firstName);
        values.put("lastname",user.lastName);
        values.put("emailid",user.email);
        values.put("address1",user.address1);
        values.put("address2",user.address2);
        values.put("city",user.city);
        values.put("state",user.state);
        values.put("country",user.country);

        // 3. insert
        db.insert("userInfo", // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public void addPhnum(mobilenumbersObj mobilenumbersObj,String mobilenum){
        //for logging

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS frnsMobile");
        // 2. create ContentValues to add key "column"/value
this.onCreate(db);
        ContentValues values = new ContentValues();
        values.put("usermobile",mobilenum);
        values.put("mobilenum1",mobilenumbersObj.mobilenum1);
        values.put("mobilenum2",mobilenumbersObj.mobilenum2);
        values.put("mobilenum3",mobilenumbersObj.mobilenum3);
        values.put("mobilenum4",mobilenumbersObj.mobilenum4);
try {
this.onCreate(db);
    // 3. insert
    db.insert("frnsMobile", // table
            null, //nullColumnHack
            values); // key/value -> keys = column names/ values = column values

    // 4. close
    db.close();
}
catch(Exception e)
{
    this.onCreate(db);
}
    }
    public String checkUser(String mob){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query("userInfo", // a. table
                        COLUMNS_pswd, // b. column names
                        " mobilenum = ?", // c. selections
                        new String[] { String.valueOf(mob) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object

      return ((cursor.getString(0)));


    }


    public mobilenumbersObj getMobilenum(String mob){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query("frnsMobile", // a. table
                        COLUMNS_mobiles, // b. column names
                        " usermobile = ?", // c. selections
                        new String[] { String.valueOf(mob) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
        mobilenumbersObj mobilenumbersObj=new mobilenumbersObj(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
        return (mobilenumbersObj);
    }
}