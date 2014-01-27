package com.stoicavlad.carnet.data.note;

import android.database.Cursor;

/**
 * Created by Vlad on 1/27/14.
 */
public class Nota {
    public String materieFather;
    public int date;
    public int nota;
    public int tip;
    public static final int TIP_NOTA_SIMPLA = 1;
    public static final int TIP_NOTA_TEZA = 2;
    public Nota(String materieFather, int date, int nota) {
        this.materieFather = materieFather;
        this.date = date;
        this.nota = nota;
    }

    public Nota(Cursor cursor){
        this.materieFather = cursor.getString(0);
        this.nota = cursor.getInt(1);
        this.date = cursor.getInt(2);
        this.tip = cursor.getInt(3);
    }
}
