package com.stoicavlad.carnet.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Vlad on 1/26/14.
 */
public class Materie implements Parcelable {
    private String name;
    private Nota[] note;

    public Materie(String name) {
        this.name = name;
    }

    public Materie(Cursor cursor) {
        this.name = cursor.getString(0);
    }

    //NOTE

    public Nota[] getNote() {
        return note;
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
        for (int i = 0; i < note.length; i++) {
            if (note[i].tip == Nota.TIP_NOTA_TEZA) {
                return note[i];
            }
        }
        return null;
    }


    public void setNote(Nota[] note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public double getMedie() {
        double rezultat = 0;
        int teza = 0;
        for (int i = 0; i < note.length; i++) {
            if (note[i].tip == Nota.TIP_NOTA_TEZA) {
                teza = note[i].nota;
            } else {
                rezultat = rezultat + note[i].nota;
            }
        }
        if (note.length == 0 || (note.length == 1 && teza != 0)) {
            return 0;
        }
        if (teza != 0) {
            double medieOral = rezultat / (note.length - 1);
            rezultat = (teza + medieOral * 3) / 4;
        } else {
            rezultat = rezultat / note.length;
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
        for (int i = 0; i < note.length; i++) {
            if (note[i].tip == Nota.TIP_NOTA_SIMPLA) {
                if (noteAdaugate > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(note[i].nota);
                noteAdaugate++;
            }
        }
        return intro + " : " + stringBuilder.toString();
    }


    //PARCELABLE

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeParcelableArray(this.note, 0);
    }

    public Materie(Parcel parcel) {
        this.name = parcel.readString();
        Parcelable[] parcelables = parcel.readParcelableArray(Nota.class.getClassLoader());
        if (parcelables != null) {
            this.note = Arrays.copyOf(parcelables, parcelables.length, Nota[].class);
        }
    }

    public static final Creator<Materie> CREATOR = new Creator<Materie>() {

        public Materie createFromParcel(Parcel in) {
            return new Materie(in);
        }

        public Materie[] newArray(int size) {
            return new Materie[size];
        }
    };

}
