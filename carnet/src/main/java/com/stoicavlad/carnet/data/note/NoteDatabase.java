package com.stoicavlad.carnet.data.note;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.stoicavlad.carnet.data.SqlHelper;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Vlad on 1/26/14.
 */
@Singleton
public class NoteDatabase {
    SqlHelper sqlHelper;
    @Inject public NoteDatabase(SqlHelper sqlHelper){
        this.sqlHelper = sqlHelper;
    }
    public Materie[] getMaterii(){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(SqlHelper.MATERII_TABLE, SqlHelper.MATERII_COLUMNS,
                null, null, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<Materie> materii = new ArrayList<Materie>();
        while (!cursor.isAfterLast()) {
            materii.add(new Materie(cursor));
            cursor.moveToNext();
        }
        //TODO REMOVE
        if(materii.isEmpty()){
            addMaterie("Sport");
            addMaterie("Romana");
            addMaterie("Biologie");
            return getMaterii();
        }
        return (Materie[])materii.toArray(new Materie[materii.size()]);
    }
    public boolean addMaterie(String title){
        ContentValues values = new ContentValues();
        values.put(SqlHelper.COLUMN_TITLE, title);

        SQLiteDatabase sqlLiteDatabase = sqlHelper.getWritableDatabase();
        if (sqlLiteDatabase != null) {
            sqlLiteDatabase.insertWithOnConflict(SqlHelper.MATERII_TABLE, null,
                    values,SQLiteDatabase.CONFLICT_FAIL);
           return true;
        }
        return false;
    }
}
