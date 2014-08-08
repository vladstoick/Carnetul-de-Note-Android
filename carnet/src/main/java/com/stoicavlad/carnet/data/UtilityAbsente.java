package com.stoicavlad.carnet.data;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.stoicavlad.carnet.data.provider.CarnetContract;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Vlad on 08-Aug-14.
 */
public class UtilityAbsente {

    public static class DateAndCount implements Comparable<DateAndCount>{
        public final long date;
        public int count = 0;
        public DateAndCount(long date){
            this.date = date;
        }

        @Override
        public int compareTo(@NonNull DateAndCount dateAndCount) {
            return dateAndCount.count - count;
        }
    }

    public static long[] getScutiriNecesare(Cursor cursor, int absenteNecesare){
        ArrayList<DateAndCount> dateAndCountArray = new ArrayList<DateAndCount>();

        cursor.moveToFirst();
        long lastDateValue = -1;
        int lastPos = -1;
        while(!cursor.isAfterLast()){
            long date = cursor.getLong(CarnetContract.AbsentaEntry.COL_DATE);
            if(date != lastDateValue){
                lastPos++;
                dateAndCountArray.add(new DateAndCount(date));
            }
            dateAndCountArray.get(lastPos).count++;
            lastDateValue = date;
            cursor.moveToNext();
        }

        Collections.sort(dateAndCountArray);

        int absenteNemotivate = cursor.getCount() - (absenteNecesare - 1);
        int i = 0;
        ArrayList<Long> zileNecesare = new ArrayList<Long>();
        //GREEDY TIME
        while (absenteNemotivate >= 0 && i < dateAndCountArray.size()) {
            zileNecesare.add(dateAndCountArray.get(i).date);
            absenteNemotivate -= dateAndCountArray.get(i).count;
            i++;
        }
        long[] dateArray = new long[zileNecesare.size()];
        for(i = 0; i < zileNecesare.size(); i++){
            dateArray[i] = zileNecesare.get(i);
        }
        return dateArray;
    }
}
