package com.stoicavlad.carnet.data.model;

import android.database.Cursor;

/**
 * Created by Vlad on 1/27/14.
 */
public class Absenta {
    private int data;

    public Absenta(int data) {
        this.data = data;
    }
    public Absenta(Cursor cursor){
        this.data = cursor.getInt(0);
    }
}
