package com.stoicavlad.carnet.data.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.stoicavlad.carnet.data.OrmliteSqlHelper;
import com.stoicavlad.carnet.data.SqlHelper;
import com.stoicavlad.carnet.data.model.Materie;
import com.stoicavlad.carnet.data.model.Nota;
import com.stoicavlad.carnet.data.otto.BusProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class MateriiDatabase {
    OrmliteSqlHelper ormliteSqlHelper;

    @Inject
    public MateriiDatabase(OrmliteSqlHelper ormliteSqlHelper) {
        this.ormliteSqlHelper = ormliteSqlHelper;
        BusProvider.getInstance().register(this);
    }

    public Materie[] getMaterii() {
        try {
            List<Materie> materii = ormliteSqlHelper.getMateriiDao().queryForAll();
            return materii.toArray(new Materie[materii.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Materie[0];
        }
    }

    //MATERII

    public Materie getMaterie(String name) {
        try {
            return ormliteSqlHelper.getMateriiDao().queryForId(name);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Materie[] getMateriiFaraTeza() {
        Materie[] materii = getMaterii();
        ArrayList<Materie> materiiFaraTeza = new ArrayList<Materie>();
        for (Materie materie : materii) {
            boolean areTeza = false;
            for (int j = 0; j < materie.getNote().length; j++) {
                Nota nota = materie.getNote()[j];
                if (nota.tip == Nota.TIP_NOTA_TEZA) {
                    areTeza = true;
                }
            }
            if (!areTeza) {
                materiiFaraTeza.add(materie);
            }
        }
        return materiiFaraTeza.toArray(new Materie[materiiFaraTeza.size()]);
    }

    public boolean addMaterie(String title) {
        try {
            Materie materie = new Materie(title);
            ormliteSqlHelper.getMateriiDao().create(materie);
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    public boolean deleteMaterie(Materie materie) {
//        SQLiteDatabase sqLiteDatabase = sqlHelper.getWritableDatabase();
//        if (sqLiteDatabase != null) {
//            sqLiteDatabase.delete(SqlHelper.MATERII_TABLE,
//                    SqlHelper.COLUMN_TITLE + " = \"" + materie.getName() + "\"", null);
//            sqLiteDatabase.delete(SqlHelper.NOTE_TABLE,
//                    SqlHelper.COLUMN_MATERIE_FATHER + "=\"" + materie.getName() + "\"", null);
//            return true;
//        }
        return false;
    }

    public boolean renameMaterie(Materie materie, String title) {
//        ContentValues values = new ContentValues();
//        values.put(SqlHelper.COLUMN_TITLE, title);
//        SQLiteDatabase sqLiteDatabase = sqlHelper.getWritableDatabase();
//        if (sqLiteDatabase != null) {
//            sqLiteDatabase.update(SqlHelper.MATERII_TABLE, values,
//                    SqlHelper.COLUMN_TITLE + " = \"" + materie.getName() + "\"", null);
//            return true;
//        }
        return false;
    }



    public boolean addNota(int nota, String materie, int type) {
//        ContentValues values = new ContentValues();
//        values.put(SqlHelper.COLUMN_MATERIE_FATHER, materie);
//        values.put(SqlHelper.COLUMN_NOTA, nota);
//        values.put(SqlHelper.COLUMN_TYPE, type);
//        SQLiteDatabase sqlLiteDatabase = sqlHelper.getWritableDatabase();
//        if (sqlLiteDatabase != null) {
//            sqlLiteDatabase.insertWithOnConflict(SqlHelper.NOTE_TABLE, null,
//                    values, SQLiteDatabase.CONFLICT_FAIL);
//            return true;
//        }
        return false;
    }

    public boolean deleteNota(Nota nota) {
//        SQLiteDatabase sqLiteDatabase = sqlHelper.getWritableDatabase();
//        if (sqLiteDatabase != null) {
//            sqLiteDatabase.delete(SqlHelper.NOTE_TABLE,
//                    SqlHelper.COLUMN_ID + " = " + nota.id, null);
//            return true;
//        }
        return false;
    }

}
