package com.stoicavlad.carnet.data.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.stoicavlad.carnet.data.SqlHelper;
import com.stoicavlad.carnet.data.model.Absenta;
import com.stoicavlad.carnet.data.model.Materie;

import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by Vlad on 1/27/14.
 */
public class AbsenteDatabase {
    SqlHelper sqlHelper;
    @Inject public AbsenteDatabase(SqlHelper sqlHelper){
        this.sqlHelper = sqlHelper;
    }

    public Absenta[] getAbsente(){

        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        if(db == null){
            return new Absenta[]{};
        }
        ArrayList<Absenta> absente = new ArrayList<Absenta>();
        Cursor cursor = db.query(SqlHelper.ABSENTE_TABLE, SqlHelper.ABSENTE_COLUMNS,
                null, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            absente.add(new Absenta(cursor));
            cursor.moveToNext();
        }
        return (Absenta[])absente.toArray(new Absenta[absente.size()]);
    }

    public boolean addAbsenta(Calendar c){
        ContentValues values = new ContentValues();
        long time = c.getTimeInMillis();
        values.put(SqlHelper.COLUMN_DATE, time);
        SQLiteDatabase sqlLiteDatabase = sqlHelper.getWritableDatabase();
        if (sqlLiteDatabase != null) {
            sqlLiteDatabase.insertWithOnConflict(SqlHelper.ABSENTE_TABLE, null,
                    values,SQLiteDatabase.CONFLICT_FAIL);
            return true;
        }
        return false;
    }
}
