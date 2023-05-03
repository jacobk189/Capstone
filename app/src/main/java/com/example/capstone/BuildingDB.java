package com.example.capstone;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstone.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

//Set up database for project to handle everything correctly

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


    public BuildingDB(@Nullable MainActivity context) {
        super(context, "buildindInfo.db",  null, 1);
    }

    public BuildingDB(@Nullable Fragment fragment) {
        super(fragment.getContext(), "buildindInfo.db",  null, 1);
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


        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<BuildingModel> showbuildings(){
        Log.d("BuildingDB", "showbuildings() method called");
        List<BuildingModel> returnlist = new ArrayList<>();

        String queryString = "SELECT * FROM " + BUILDING_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null, null);

        if (cursor.moveToFirst()){
            //loop through the cursor and create new building objects put them into return list
            do {
                int buildingID = cursor.getInt(0);
                String buildingName = cursor.getString(1);
                String buildingAddress = cursor.getString(2);
                Double buildingLatitude = cursor.getDouble(3);
                Double buildingLongitude = cursor.getDouble(4);
                String buildingInfo = cursor.getString(5);
                String buildingHistory = cursor.getString(6);
                String buildingNickname = cursor.getString(7);

                BuildingModel newBuilding = new BuildingModel(buildingID, buildingName, buildingAddress, buildingLatitude, buildingLongitude, buildingInfo, buildingHistory, buildingNickname);
                returnlist.add(newBuilding);

            } while (cursor.moveToNext());
        }
        else{
            //failed, do not add anything to the list
        }

        cursor.close();
        db.close();
        Log.d("BuildingDB", "showbuildings() returned: " + returnlist.toString());
        return returnlist;
    }
}
