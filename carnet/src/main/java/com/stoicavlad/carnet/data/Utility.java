package com.stoicavlad.carnet.data;

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
}
