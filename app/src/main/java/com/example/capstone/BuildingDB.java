package com.example.capstone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class BuildingDB extends SQLiteOpenHelper {

    public static final String BUILDING_TABLE = "BUILDING_TABLE";
    public static final String BUILDING_NAME = "BUILDING_NAME";
    public static final String BUILDING_ADDRESS = "BUILDING_ADDRESS";
    public static final String BUILDING_LATITUDE = "BUILDING_LATITUDE";
    public static final String BUILDING_LONGITUDE = "BUILDING_LONGITUDE";
    public static final String BUILDING_INFO = "BUILDING_INFO";
    public static final String BUILDING_HISTORY = "BUILDING_HISTORY";
    public static final String BUILDING_NICKNAME = "BUILDING_NICKNAME";
    public static final String ID = "ID";


    public BuildingDB(@Nullable Context context) {
        super(context, "buildindInfo.db",  null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + BUILDING_TABLE +
                " (" + ID + " INTEGER PRIMARY KEY, " +
                BUILDING_NAME + " TEXT, " +
                BUILDING_ADDRESS + " TEXT, " +
                BUILDING_LATITUDE + " NUMERIC, " +
                BUILDING_LONGITUDE + " NUMERIC, " +
                BUILDING_INFO + " TEXT, " +
                BUILDING_HISTORY + " TEXT, " +
                BUILDING_NICKNAME + " TEXT)";

        Log.d("BuildingDB", "onCreate() called  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
