package com.stoicavlad.carnet.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.stoicavlad.carnet.data.model.Absenta;
import com.stoicavlad.carnet.data.model.Materie;
import com.stoicavlad.carnet.data.model.Nota;

import java.sql.SQLException;

import butterknife.InjectView;

/**
 * Created by Vlad on 2/10/14.
 */
public class OrmliteSqlHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;
    private Dao<Absenta, Integer> absenteDao = null;
    private Dao<Materie, Integer> materiiDao = null;
    private Dao<Nota, Integer> noteDao = null;
    public OrmliteSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(OrmliteSqlHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Absenta.class);
            TableUtils.createTable(connectionSource, Materie.class);
            TableUtils.createTable(connectionSource, Nota.class);
        } catch (SQLException e) {
            Log.e(OrmliteSqlHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            Log.i(OrmliteSqlHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Absenta.class, true);
            TableUtils.dropTable(connectionSource, Materie.class, true);
            TableUtils.dropTable(connectionSource, Nota.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(OrmliteSqlHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }
    public Dao<Absenta, Integer> getAbsenteDao() throws SQLException {
        if (absenteDao == null) {
            absenteDao = getDao(Absenta.class);
        }
        return absenteDao;
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
}