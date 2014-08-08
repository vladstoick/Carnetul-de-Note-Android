package com.stoicavlad.carnet.data;

import android.database.Cursor;

import com.stoicavlad.carnet.data.provider.CarnetContract;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vlad on 08-Aug-14.
 */
public class UtilityAbsente {

    public static class DateAndCount implements Comparable<DateAndCount>{
        public long date;
        public int count = 0;
        public DateAndCount(long date){
            this.date = date;
        }

        @Override
        public int compareTo(DateAndCount dateAndCount) {
            return dateAndCount.count - count;
        }
    }

    public static long[] getScutiriNecesare(Cursor cursor, int absenteNecesare){
        DateAndCount[] dateAndCountArray = new DateAndCount[cursor.getCount()];

        cursor.moveToFirst();
        long lastDateValue = -1;
        int lastPos = -1;
        while(!cursor.isAfterLast()){
            long date = cursor.getLong(CarnetContract.AbsentaEntry.COL_DATE);
            if(date != lastDateValue){
                lastPos++;
                dateAndCountArray[lastPos] = new DateAndCount(date);
            }
            dateAndCountArray[lastPos].count++;
            lastDateValue = date;
            cursor.moveToNext();
        }

        Arrays.sort(dateAndCountArray);

        int absenteNemotivate = cursor.getCount() - absenteNecesare;
        int i = 0;
        ArrayList<Long> zileNecesare = new ArrayList<Long>();
        //GREEDY TIME
        while (absenteNemotivate >= 0 && i < dateAndCountArray.length) {
            zileNecesare.add(dateAndCountArray[i].date);
            absenteNemotivate -= dateAndCountArray[i].count;
            i++;
        }
        long[] dateArray = new long[zileNecesare.size()];
        for(i = 0; i < zileNecesare.size(); i++){
            dateArray[i] = zileNecesare.get(i);
        }
        return dateArray;
    }
}
