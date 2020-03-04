package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import com.example.android.pets.data.PetContract.PetEntry;

public class PetDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="shelter.db";
    private static final int DATABASE_version=1;


    public PetDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DATABASE_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_PET_CREATE_TABLE=" CREATE TABLE "+PetEntry.TABLE_NAME +" ( "
                +PetEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +PetEntry.COLUMN_PET_NAME+" TEXT NOT  NULL ,"
                +PetEntry.COLUMN_PET_BREED+" TEXT ,"
                +PetEntry.COLUMN_PET_GENDER+" INTEGER NOT NULL ,"
                +PetEntry.COLUMN_PET_WEIGHT+" INTEGER NOT NULL DEFAULT 0 );";
        sqLiteDatabase.execSQL(SQL_PET_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
