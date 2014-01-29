package com.stoicavlad.carnet.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;

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

    public Nota[] getNote() {
        return note;
    }

    public void setNote(Nota[] note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public String getMedie() {
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
            return "-";
        }
        if (teza != 0) {
            double medieOral = rezultat / (note.length - 1);
            rezultat = (teza + medieOral * 3) / 4;
        } else {
            rezultat = rezultat / note.length;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(rezultat);
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

    public int getTeza() {
        for (int i = 0; i < note.length; i++) {
            if (note[i].tip == Nota.TIP_NOTA_TEZA) {
                return note[i].nota;
            }
        }
        return 0;
    }

    //PARCELABLE

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeArray(this.note);
    }

    public Materie(Parcel parcel) {
        this.name = parcel.readString();
        this.note = (Nota[]) parcel.readArray(Nota.class.getClassLoader());
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
