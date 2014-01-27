package com.stoicavlad.carnet.data.note;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.stoicavlad.carnet.data.BusProvider;
import com.stoicavlad.carnet.data.SqlHelper;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Vlad on 1/26/14.
 */
@Singleton
public class MateriiDatabase {
    SqlHelper sqlHelper;
    @Inject public MateriiDatabase(SqlHelper sqlHelper){
        this.sqlHelper = sqlHelper;
        BusProvider.getInstance().register(this);
    }
    public Materie[] getMaterii(){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        if(db == null){
            return new Materie[]{};
        }
        ArrayList<Materie> materii = new ArrayList<Materie>();
        Cursor cursor = db.query(SqlHelper.MATERII_TABLE, SqlHelper.MATERII_COLUMNS,
                null, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Materie materie = new Materie(cursor);
            materie.setNote(getNoteForMaterie(materie,db));
            materii.add(materie);
            cursor.moveToNext();
        }

        return (Materie[])materii.toArray(new Materie[materii.size()]);
    }

    public Materie[] getMateriiFaraTeza(){
        Materie[] materii = getMaterii();
        ArrayList<Materie> materiiFaraTeza = new ArrayList<Materie>();
        for(int i=0;i<materii.length;i++){
            Materie materie = materii[i];
            boolean areTeza = false;
            for(int j=0;j<materie.getNote().length;j++){
                Nota nota = materie.getNote()[j];
                if(nota.tip == Nota.TIP_NOTA_TEZA){
                    areTeza = true;
                }
            }
            if(areTeza == false){
                materiiFaraTeza.add(materie);
            }
        }
        return (Materie[])materiiFaraTeza.toArray(new Materie[materiiFaraTeza.size()]);
    }

    private Nota[] getNoteForMaterie(Materie materie, SQLiteDatabase db){
        ArrayList<Nota> note = new ArrayList<Nota>();
        Cursor cursor = db.query(SqlHelper.NOTE_TABLE, SqlHelper.NOTE_COLUMNS,
                SqlHelper.COLUMN_MATERIE_FATHER + " = \"" + materie.getName()+"\"",
                null, null, null, null, null);
        cursor.moveToFirst();
        while ((!cursor.isAfterLast())){
            note.add(new Nota(cursor));
            cursor.moveToNext();
        }
        return (Nota[])note.toArray(new Nota[note.size()]);

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
    public boolean addNota(int nota,String materie,int type){
        ContentValues values = new ContentValues();
        values.put(SqlHelper.COLUMN_MATERIE_FATHER, materie);
        values.put(SqlHelper.COLUMN_NOTA, nota);
        values.put(SqlHelper.COLUMN_TYPE, type);
        SQLiteDatabase sqlLiteDatabase = sqlHelper.getWritableDatabase();
        if (sqlLiteDatabase != null) {
            sqlLiteDatabase.insertWithOnConflict(SqlHelper.NOTE_TABLE, null,
                    values,SQLiteDatabase.CONFLICT_FAIL);
            return true;
        }
        return false;
    }

}
