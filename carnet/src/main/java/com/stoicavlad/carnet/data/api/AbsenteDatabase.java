package com.stoicavlad.carnet.data.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.stoicavlad.carnet.data.SqlHelper;
import com.stoicavlad.carnet.data.model.Absenta;

import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by Vlad on 1/27/14.
 */
public class AbsenteDatabase {
    SqlHelper sqlHelper;

    @Inject
    public AbsenteDatabase(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    public Absenta[] getAbsente() {

        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        if (db == null) {
            return new Absenta[]{};
        }
        ArrayList<Absenta> absente = new ArrayList<Absenta>();
        Cursor cursor = db.query(SqlHelper.ABSENTE_TABLE, SqlHelper.ABSENTE_COLUMNS,
                null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            absente.add(new Absenta(cursor));
            cursor.moveToNext();
        }
        return (Absenta[]) absente.toArray(new Absenta[absente.size()]);
    }

    public boolean addAbsenta(Calendar c) {
        ContentValues values = new ContentValues();
        long time = c.getTimeInMillis();
        values.put(SqlHelper.COLUMN_DATE, time);
        SQLiteDatabase sqlLiteDatabase = sqlHelper.getWritableDatabase();
        if (sqlLiteDatabase != null) {
            sqlLiteDatabase.insertWithOnConflict(SqlHelper.ABSENTE_TABLE, null,
                    values, SQLiteDatabase.CONFLICT_FAIL);
            return true;
        }
        return false;
    }

    public int calculeazaScutiriOptim() {
        Absenta[] absente = getAbsente();
        ArrayList<Calendar> absenteCauate = new ArrayList<Calendar>();
        ArrayList<Integer> absenteCount = new ArrayList<Integer>();
        for (int i = 0; i < absente.length; i++) {
            Calendar dateToBeAdded = absente[i].getCalendar();
            boolean adauagat = false;
            for (int j = 0; j < absenteCauate.size(); j++) {
                Calendar dateToBeCompared = absenteCauate.get(j);
                if (isSameDay(dateToBeAdded, dateToBeCompared)) {
                    Integer newCount = absenteCount.get(j) + 1;
                    absenteCount.set(j, newCount);
                    adauagat = true;
                    break;
                }
            }
            if (!adauagat) {
                absenteCauate.add(dateToBeAdded);
                absenteCount.add(1);
            }
        }


        //sortam dupa nrAbsente
        for (int i = 0; i < absenteCount.size(); i++) {
            for (int j = i + 1; j <= absenteCount.size(); j++) {
                if (absenteCount.get(i) < absenteCount.get(j)) {
                    Integer aux = absenteCount.get(i);
                    absenteCount.set(i, absenteCount.get(j));
                    absenteCount.set(j, aux);
                    Calendar aux2 = absenteCauate.get(i);
                    absenteCauate.set(i, absenteCauate.get(j));
                    absenteCauate.set(j, aux2);
                }
            }
        }
        int absenteMotivate = 0, i = 0;
        //GREEDY TIME
        return absenteCount.size();
    }

    private boolean isSameDay(Calendar a, Calendar b) {

        return a.get(Calendar.YEAR) == b.get(Calendar.YEAR)
                && a.get(Calendar.MONTH) == b.get(Calendar.MONTH)
                && a.get(Calendar.DAY_OF_MONTH) == b.get(Calendar.DAY_OF_MONTH);
    }
}
