package com.stoicavlad.carnet.data;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Singleton;

/**
 * Created by Vlad on 1/26/14.
 */
@Singleton
public class SqlHelper extends SQLiteOpenHelper {
    private static int DBVERSION = 3;
    private static String DB_NAME = "carnet.db";


    public static String MATERII_TABLE = "materii";
    public static String NOTE_TABLE = "note";
    public static String ABSENTE_TABLE = "absente";


    public static String COLUMN_ID = "id";
    public static String COLUMN_TITLE = "title";
    public static String COLUMN_MATERIE_FATHER = "materie";
    public static String COLUMN_NOTA = "nota";
    public static String COLUMN_DATE = "data";
    public static String COLUMN_TYPE = "tip";

    private static String CREATE_ABSENTE_TABLE = "CREATE TABLE " + ABSENTE_TABLE + " ( " +
            COLUMN_DATE + " long , " +
            COLUMN_ID + " integer primary key autoincrement" +
            ")";

    private static String CREATE_NOTE_TABLE = "CREATE TABLE " + NOTE_TABLE + " ( " +
            COLUMN_MATERIE_FATHER + " text , " +
            COLUMN_NOTA + " int , " +
            COLUMN_DATE + " int ," +
            COLUMN_TYPE + " int , " +
            COLUMN_ID + " integer primary key autoincrement " +
            ")";
    private static String CREATE_MATERII_TABLE = "CREATE TABLE " + MATERII_TABLE + " ( " +
            COLUMN_TITLE + " text primary key )";
    public static String[] MATERII_COLUMNS = {COLUMN_TITLE};
    public static String[] NOTE_COLUMNS = {COLUMN_MATERIE_FATHER, COLUMN_NOTA,
            COLUMN_DATE, COLUMN_TYPE};
    public static String[] ABSENTE_COLUMNS = {COLUMN_DATE};

    public SqlHelper(Application application) {
        super(application, DB_NAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_MATERII_TABLE);
        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE);
        sqLiteDatabase.execSQL(CREATE_ABSENTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        if (i == 1 && i2 == 2) {
            sqLiteDatabase.execSQL("DROP TABLE " + ABSENTE_TABLE);
        }
        if (i == 2 && i2 == 3) {
            sqLiteDatabase.execSQL("DROP TABLE " + ABSENTE_TABLE);
        }
    }


}
