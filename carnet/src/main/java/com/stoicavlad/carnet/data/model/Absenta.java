package com.stoicavlad.carnet.data.model;

import android.database.Cursor;

import java.util.Calendar;

/**
 * Created by Vlad on 1/27/14.
 */
public class Absenta {
    private long date;

    public Absenta(int date) {
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
