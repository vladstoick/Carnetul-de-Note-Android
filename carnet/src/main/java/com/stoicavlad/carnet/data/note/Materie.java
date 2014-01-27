package com.stoicavlad.carnet.data.note;

import android.database.Cursor;

import java.text.DecimalFormat;

/**
 * Created by Vlad on 1/26/14.
 */
public class Materie {
    private String name;
    private Nota [] note;
    public Materie(String name) {
        this.name = name;
    }

    public Materie(Cursor cursor){
        this.name = cursor.getString(0);
    }

    public Nota[] getNote() {
        return note;
    }

    public void setNote(Nota[] note) {
        this.note = note;
    }

    public String getMedie(){

        double rezultat = 0 ;
        int teza = 0 ;
        for(int i=0;i<note.length;i++){
            if(note[i].tip == Nota.TIP_NOTA_TEZA){
                teza = note[i].nota;
            } else{
                rezultat = rezultat + note[i].nota;
            }
        }
        if(note.length == 0 || (note.length == 1 && teza!=0)){
            return "-";
        }
        if(teza !=0 ){
            double medieOral = rezultat / (note.length-1);
            rezultat = ( teza + medieOral*3 ) /4;
        } else {
            rezultat = rezultat / note.length;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(rezultat);
    }

    public String getName() {
        return name;
    }
}
