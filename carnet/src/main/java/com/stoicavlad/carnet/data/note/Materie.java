package com.stoicavlad.carnet.data.note;

import android.database.Cursor;

/**
 * Created by Vlad on 1/26/14.
 */
public class Materie {
    private String name;

    public Materie(String name) {
        this.name = name;
    }

    public Materie(Cursor cursor){
        this.name = cursor.getString(0);
    }


    public String getName() {
        return name;
    }
}
