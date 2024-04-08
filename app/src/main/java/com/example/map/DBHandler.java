package com.example.map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "mapdb";
    // below int is our database version
    private static final int DB_VERSION = 1;
    // below variable is for our table name.
    private static final String TABLE_NAME = "locations";
    // below variable is for our id column.
    private static final String ID_COL = "id";
    // below variable is for our course name column
    private static final String LAT_COL = "latitude";
    // below variable id for our course duration column.
    private static final String LON_COL = "longitude";
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LAT_COL + " TEXT,"
                + LON_COL + " TEXT)";
        db.execSQL(query);
    }

    public boolean addNewLocation(String locationLat, String locationLong) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LAT_COL, locationLat);
        values.put(LON_COL, locationLong);
        db.insert(TABLE_NAME, null, values);
        db.close();
        return true;
    }

    public List<Location> getAllLocations() {
        List<Location> locationsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Location location = new Location();
                location.setId(cursor.getInt(cursor.getColumnIndex(ID_COL)));
                location.setLatitude(cursor.getString(cursor.getColumnIndex(LAT_COL)));
                location.setLongitude(cursor.getString(cursor.getColumnIndex(LON_COL)));
                locationsList.add(location);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return locationsList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
