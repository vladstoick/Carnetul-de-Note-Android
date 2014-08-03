package com.stoicavlad.carnet.data;

import android.database.Cursor;

import com.stoicavlad.carnet.data.provider.CarnetContract;

import java.util.Calendar;

/**
 * Created by Vlad on 30-Jul-14.
 */
public class Utility {

    public static String getDateFromLong(long date){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        return c.get(Calendar.DAY_OF_MONTH) + "." + (c.get(Calendar.MONTH) + 1) + "."
                + c.get(Calendar.YEAR);
    }

    public static double getMedieForMaterie(int teza, double medieNote){
        double medieMaterie = 0;
        if(medieNote == 0){
            medieMaterie = teza;
        } else {
            medieMaterie = teza == 0 ? medieNote : (teza + medieNote*3)/4;
        }
        return Math.round(medieMaterie);
    }

    public static double getMedieGeneralaFromCursor(Cursor cursor, int purtare) {
        if(cursor.getCount()==0){
            return purtare;
        }
        double medie = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int teza = cursor.getInt(CarnetContract.MaterieEntry.COL_MEDIE_TEZA);
            double medieNote = cursor.getDouble(CarnetContract.MaterieEntry.COL_MEDIE_NOTE);
            double medieMaterie = getMedieForMaterie(teza,medieNote);
            medieMaterie = medieMaterie == 0 ? 10 : medieMaterie;
            medie += medieMaterie;
            cursor.moveToNext();
        }
        return ( medie + purtare ) / (cursor.getCount() + 1 ) ;
    }
}
