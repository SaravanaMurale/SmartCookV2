package com.clj.blesample.databasemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class SqliteManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "scsk";
    public static final String TABLE_NAME="maintenaceservice";
    public static final int DATABASE_VERSION = 1;

    public static final String COLUMN_ID="id";
    public static final String MISSUE_ID="missue_id";
    public static final String FIXED_DATE="fixed_date";
    public static final String PERSON_NAME="mperson";
    public static final String ISSUE="issue";
    public static final String DEVICE_ID="deviceid";


    public SqliteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT add_cart_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + MISSUE_ID + " varchar(200) NOT NULL,\n" +
                "    " + FIXED_DATE + " tinyint(4) NOT NULL,\n" +
                "    " + PERSON_NAME + " varchar(200) NOT NULL,\n" +
                "    " + ISSUE + " varchar(200) NOT NULL,\n" +
                "    " + DEVICE_ID + " varchar(200) NOT NULL\n" +
                ");";


        /*String sql = "CREATE TABLE " +TABLE_NAME+ "(\n" +
                "+COLUMN_ID+         INTEGER        PRIMARY KEY AUTOINCREMENT\n" +
                "                              UNIQUE\n" +
                "                              NOT NULL,\n" +
                "    missue_id  VARCHAR (50)   NOT NULL\n" +
                "                              UNIQUE,\n" +
                "    fixed_date DATETIME (200) NOT NULL,\n" +
                "    mperson    VARCHAR (200)  NOT NULL,\n" +
                "    issue      VARCHAR (300),\n" +
                "    deviceid   VARCHAR (200) \n" +
                ");";*/

        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean addMaintenanceServiceData(String m_issueID,String issueFixedDate,String personName,String issue){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        /*try {*/

        ContentValues contentValues = new ContentValues();

        contentValues.put(MISSUE_ID, m_issueID);
        contentValues.put(FIXED_DATE, issueFixedDate);
        contentValues.put(PERSON_NAME, personName);
        contentValues.put(ISSUE, issue);

        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues) != -1;

    }





}
