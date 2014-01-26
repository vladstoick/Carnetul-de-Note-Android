package com.stoicavlad.carnet.data;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.stoicavlad.carnet.data.note.Materie;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Vlad on 1/26/14.
 */
@Singleton
public class SqlHelper extends SQLiteOpenHelper
{
    private static int DBVERSION = 1;
    private static String DB_NAME = "carnet.db";


    public static String MATERII_TABLE = "materii";
    public static String NOTE_TABLE = "note";

    public static String COLUMN_ID = "id";
    public static String COLUMN_TITLE = "title";
    public static String COLUMN_MATERIE_FATHER = "materie";
    public static String COLUMN_NOTA = "nota";

    private static String CREATE_NOTE_TABLE = "CREATE TABLE " + NOTE_TABLE + " ( " +
             COLUMN_MATERIE_FATHER + " text , " +
            COLUMN_NOTA +" int " + ")";
    private static String CREATE_MATERII_TABLE = "CREATE TABLE " + MATERII_TABLE + " ( " +
            COLUMN_TITLE + " text primary key )";
    public static String[] MATERII_COLUMNS = {COLUMN_TITLE};
    public static String[] NOTE_COLUMNS = {COLUMN_ID, COLUMN_MATERIE_FATHER, COLUMN_NOTA };

    public SqlHelper(Application application) {
        super(application, DB_NAME, null, DBVERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_MATERII_TABLE);
        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        //TODO
    }



}
