package com.stoicavlad.carnet.data.model;

import android.database.Cursor;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Vlad on 1/26/14.
 */
@DatabaseTable(tableName = "materii")
public class Materie {
    @DatabaseField( generatedId = true)
    public int id;
    @DatabaseField
    public String name;
    @ForeignCollectionField(eager = false)
    public ForeignCollection<Nota> note;


    public Materie(){}

    public Materie(String name) {
        this.name = name;
    }

    public Materie(Cursor cursor) {
        this.name = cursor.getString(0);
    }

    //NOTE

    public Nota[] getNote() {
        return note.toArray(new Nota[note.size()]);
    }

    public Nota[] getNoteFaraTeza() {
        ArrayList<Nota> note = new ArrayList<Nota>();
        for (Nota nota : this.note) {
            if (nota.tip != Nota.TIP_NOTA_TEZA) {
                note.add(nota);
            }
        }
        return note.toArray(new Nota[note.size()]);
    }

    public Nota getTeza() {
        Nota note[]=getNote();
        for (Nota aNote : note) {
            if (aNote.tip == Nota.TIP_NOTA_TEZA) {
                return aNote;
            }
        }
        return null;
    }


    public double getMedie() {
        double rezultat = 0;
        int teza = 0;
        for (Nota nota:note) {
            if (nota.tip == Nota.TIP_NOTA_TEZA) {
                teza = nota.nota;
            } else {
                rezultat = rezultat + nota.nota;
            }
        }
        if (note.size() == 0 || (note.size() == 1 && teza != 0)) {
            return 0;
        }
        if (teza != 0) {
            double medieOral = rezultat / (note.size() - 1);
            rezultat = (teza + medieOral * 3) / 4;
        } else {
            rezultat = rezultat / note.size();
        }
        return rezultat;
    }

    public String getMedieAsString(double medie) {
        if (medie == 0) {
            return "-";
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(medie);
    }

    public String getMedieAsString() {
        double medie = getMedie();
        if (medie == 0) {
            return "-";
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(medie);
    }

    public String getNoteAsString(String intro) {
        StringBuilder stringBuilder = new StringBuilder();
        int noteAdaugate = 0;
        for (Nota nota:note) {
            if (nota.tip == Nota.TIP_NOTA_SIMPLA) {
                if (noteAdaugate > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(nota.nota);
                noteAdaugate++;
            }
        }
        return intro + " : " + stringBuilder.toString();
    }



}
