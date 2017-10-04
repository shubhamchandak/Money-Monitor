package com.example.android.moneymonitor.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.moneymonitor.data.PetContract.PetEntry;

/**
 * Created by W10 on 9/13/2017.
 */

public class PetDbHelper extends SQLiteOpenHelper {

    private static final int VERSION_NUMBER = 1; // Version number
    private static final String DATABASE_NAME = "shelter.db"; // name of database file

    public PetDbHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
/* Create a String that contains SQL statement for creating table*/
         String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + PetEntry.TABLE_NAME + " ("
                + PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PetEntry.COLUMN_PET_INDIVIDUAL_ID + " INTEGER NOT NULL, "
                + PetEntry.COLUMN_PET_DATE + " Text NOT NULL, "
                + PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                + PetEntry.COLUMN_PET_BREED + " TEXT, "
                + PetEntry.COLUMN_PET_ID_TYPE + " INTEGER NOT NULL, "
                + PetEntry.COLUMN_PET_GENDER + " INTEGER DEFAULT 0, "
                + PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0 " + ");" ;

        db.execSQL(SQL_CREATE_PETS_TABLE); // Execute the SQL statement.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // do nothing as we don't have upgraded version(this is our first version)
    }
}
