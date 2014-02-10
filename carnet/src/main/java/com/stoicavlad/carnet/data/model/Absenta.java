package com.stoicavlad.carnet.data.model;

import android.database.Cursor;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Calendar;

/**
 * Created by Vlad on 1/27/14.
 */
@DatabaseTable(tableName = "absente")
public class Absenta {
    @DatabaseField(canBeNull = false)
    private long date;
    @DatabaseField(generatedId = true)
    private int id;

    public Absenta(){}

    public Absenta(long date) {
        this.date = date;
    }

    public Absenta(Cursor cursor) {
        this.date = cursor.getLong(0);
    }

    public String getDate() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        return c.get(Calendar.DAY_OF_MONTH) + "." + (c.get(Calendar.MONTH) + 1) + "."
                + c.get(Calendar.YEAR);
    }

    public Calendar getCalendar() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        return c;
    }
}
