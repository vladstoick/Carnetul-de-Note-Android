package com.stoicavlad.carnet.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vlad on 1/27/14.
 */
public class Nota implements Parcelable{
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

    //PARCELABLE

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(materieFather);
        dest.writeInt(nota);
        dest.writeInt(date);
        dest.writeInt(tip);
    }

    public Nota(Parcel parcel){
        this.materieFather = parcel.readString();
        this.nota = parcel.readInt();
        this.date = parcel.readInt();
        this.tip = parcel.readInt();
    }

    public static final Creator<Nota> CREATOR = new Creator<Nota>() {

        public Nota createFromParcel(Parcel in) {
            return new Nota(in);
        }

        public Nota[] newArray(int size) {
            return new Nota[size];
        }
    };
}
