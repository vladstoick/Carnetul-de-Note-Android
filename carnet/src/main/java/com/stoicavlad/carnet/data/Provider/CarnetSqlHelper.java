package com.stoicavlad.carnet.data.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.stoicavlad.carnet.data.provider.CarnetContract.AbsentaEntry;
/**
 * Created by Vlad on 30-Jul-14.
 */
public class CarnetSqlHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "carnet.db";

    public CarnetSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_ABSENTE_TABLE = "CREATE TABLE " + AbsentaEntry.TABLE_NAME + " (" +
                AbsentaEntry._ID + " INTEGER PRIMARY KEY," +
                AbsentaEntry.COLUMN_DATE + " LONG NOT NULL )";
        sqLiteDatabase.execSQL(SQL_CREATE_ABSENTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AbsentaEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
