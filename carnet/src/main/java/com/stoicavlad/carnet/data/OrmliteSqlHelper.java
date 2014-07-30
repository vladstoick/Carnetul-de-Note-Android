package com.stoicavlad.carnet.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.stoicavlad.carnet.data.model.Materie;
import com.stoicavlad.carnet.data.model.Nota;
import com.stoicavlad.carnet.data.model.Purtare;

import java.sql.SQLException;

/**
 * Created by Vlad on 2/10/14.
 */
public class OrmliteSqlHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 2;
    private Dao<Materie, Integer> materiiDao = null;
    private Dao<Purtare, Integer> purtareDao = null;
    private Dao<Nota, Integer> noteDao = null;
    public OrmliteSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(OrmliteSqlHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Materie.class);
            TableUtils.createTable(connectionSource, Nota.class);
            TableUtils.createTable(connectionSource, Purtare.class);
        } catch (SQLException e) {
            Log.e(OrmliteSqlHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        if(oldVersion == 1){
            try{
                TableUtils.createTable(connectionSource, Purtare.class);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
//        try {
//            Log.i(OrmliteSqlHelper.class.getName(), "onUpgrade");
//            TableUtils.dropTable(connectionSource, Absenta.class, true);
//            TableUtils.dropTable(connectionSource, Materie.class, true);
//            TableUtils.dropTable(connectionSource, Nota.class, true);
//            TableUtils.dropTable(connectionSource, Purtare.class, true);
//            onCreate(db, connectionSource);
//        } catch (SQLException e) {
//            Log.e(OrmliteSqlHelper.class.getName(), "Can't drop databases", e);
//            throw new RuntimeException(e);
//        }

    }
    public Dao<Materie, Integer> getMateriiDao() throws SQLException{
        if(materiiDao == null ){
            materiiDao = getDao(Materie.class);
        }
        return materiiDao;
    }

    public Dao<Nota, Integer> getNoteDao() throws SQLException{
        if(noteDao == null ){
            noteDao = getDao(Nota.class);
        }
        return noteDao;
    }
    public Dao<Purtare, Integer> getPurtareDao() throws SQLException{
        if(purtareDao == null ){
            purtareDao = getDao(Purtare.class);
        }
        return purtareDao;
    }

}
