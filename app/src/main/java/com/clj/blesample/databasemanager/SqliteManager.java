package com.clj.blesample.databasemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.clj.blesample.model.MaintenaceServiceDTO;

import java.util.ArrayList;
import java.util.List;

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


    public boolean addMaintenanceServiceData(String m_issueID,String issueFixedDate,String personName,String issue,String deviceID){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        /*try {*/

        ContentValues contentValues = new ContentValues();

        contentValues.put(MISSUE_ID, m_issueID);
        contentValues.put(FIXED_DATE, issueFixedDate);
        contentValues.put(PERSON_NAME, personName);
        contentValues.put(ISSUE, issue);
        contentValues.put(DEVICE_ID, deviceID);

        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues) != -1;

    }

    public List<MaintenaceServiceDTO> getAllM_ServiceData(String mDeviceId){

        List<MaintenaceServiceDTO> maintenaceServiceDTOList=new ArrayList<>();

        SQLiteDatabase selectAllData = getReadableDatabase();

       Cursor cursor=selectAllData.rawQuery("select missue_id,fixed_date,mperson,issue,deviceid from maintenaceservice where deviceid=?", new String[]{mDeviceId});


       if(cursor.moveToFirst()){

           do {

               MaintenaceServiceDTO maintenaceServiceDTO=new MaintenaceServiceDTO(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));

               maintenaceServiceDTOList.add(maintenaceServiceDTO);

           }
           while (cursor.moveToNext());

       }


        return maintenaceServiceDTOList;


    }





}
