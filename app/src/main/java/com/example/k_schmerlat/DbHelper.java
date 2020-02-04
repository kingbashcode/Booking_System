package com.example.k_schmerlat;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = DbHelper.class.getSimpleName();

    public static final String DB_Name = "kiosk.db";
    public static final int DB_Version = 1;

    public static final String Table_Drinks_List = "drinks_list";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DRINKNAME = "drinkname";
    public static final String COLUMN_PREIS = "preis";

    public static final String Table_Foods_List = "foods_list";

    public static final String COLUMN_ID1 = "_id1";
    public static final String COLUMN_FOODNAME = "foodname";
    public static final String COLUMN_PREIS1 = "preis";



    public static final String SQL_CREATE =
            "Create Table " + Table_Drinks_List +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DRINKNAME + " TEXT NOT NULL, " +
                    COLUMN_PREIS + " DOUBLE NOT NULL);";

    public static final String SQL_CREATE1 =
            "Create Table " + Table_Foods_List +
                    "(" + COLUMN_ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FOODNAME + " TEXT NOT NULL, " +
                    COLUMN_PREIS1 + " DOUBLE NOT NULL);";


    public DbHelper(Context context) {

        super(context, DB_Name, null, DB_Version);
        Log.d(LOG_TAG, "DbHelper has: " + getDatabaseName() + " created.");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "CREATES TABLE :" + SQL_CREATE);
            Log.d(LOG_TAG, "CREATES TABLE :" + SQL_CREATE1);
            db.execSQL(SQL_CREATE);
            db.execSQL(SQL_CREATE1);
        }
        catch (Exception ex){
            Log.e(LOG_TAG, "ERROR CREATION OF TABLE" + ex.getMessage());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
