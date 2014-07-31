package com.stoicavlad.carnet.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

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

    public static String getNoteFromCursorLoader(CursorLoader loader, String prefix){
        Cursor cursor = loader.loadInBackground();
        cursor.moveToFirst();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix + ": ");
        while(!cursor.isAfterLast()){
            int nota = cursor.getInt(CarnetContract.NoteEntry.COL_VALUE);
            stringBuilder.append(nota);
            if(!cursor.isLast()){
                stringBuilder.append(", ");
            }
            cursor.moveToNext();
        }
        return stringBuilder.toString();
    }
}
