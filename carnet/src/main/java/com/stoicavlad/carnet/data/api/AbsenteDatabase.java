package com.stoicavlad.carnet.data.api;

import com.stoicavlad.carnet.data.OrmliteSqlHelper;
import com.stoicavlad.carnet.data.model.Absenta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Vlad on 1/27/14.
 */
public class AbsenteDatabase {
    OrmliteSqlHelper ormliteSqlHelper;

    @Inject
    public AbsenteDatabase(OrmliteSqlHelper ormliteSqlHelper) {
        this.ormliteSqlHelper = ormliteSqlHelper;
    }

    public Absenta[] getAbsente() {
        try {
            List<Absenta> absente = ormliteSqlHelper.getAbsenteDao().queryForAll();
            return absente.toArray(new Absenta[absente.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Absenta[0];
        }
    }

    public boolean addAbsenta(Calendar c) {
        try {
            Absenta absenta = new Absenta(c.getTimeInMillis());
            ormliteSqlHelper.getAbsenteDao().create(absenta);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Calendar[] calculeazaScutiriOptim() {
        Absenta[] absente = getAbsente();
        ArrayList<Calendar> absenteCauate = new ArrayList<Calendar>();
        ArrayList<Integer> absenteCount = new ArrayList<Integer>();
        for (int i = 0; i < absente.length; i++) {
            Calendar dateToBeAdded = absente[i].getCalendar();
            boolean adauagat = false;
            for (int j = 0; j < absenteCauate.size(); j++) {
                Calendar dateToBeCompared = absenteCauate.get(j);
                if (isSameDay(dateToBeAdded, dateToBeCompared)) {
                    Integer newCount = absenteCount.get(j) + 1;
                    absenteCount.set(j, newCount);
                    adauagat = true;
                    break;
                }
            }
            if (!adauagat) {
                absenteCauate.add(dateToBeAdded);
                absenteCount.add(1);
            }
        }


        //sortam dupa nrAbsente
        for (int i = 0; i < absenteCount.size() - 1; i++) {
            for (int j = i + 1; j < absenteCount.size(); j++) {
                if (absenteCount.get(i) < absenteCount.get(j)) {
                    Integer aux = absenteCount.get(i);
                    absenteCount.set(i, absenteCount.get(j));
                    absenteCount.set(j, aux);
                    Calendar aux2 = absenteCauate.get(i);
                    absenteCauate.set(i, absenteCauate.get(j));
                    absenteCauate.set(j, aux2);
                }
            }
        }
        int absenteNemotivate = absenteCount.size(), i = 0;
        ArrayList<Calendar> zileNecesare = new ArrayList<Calendar>();
        //GREEDY TIME
        while (absenteNemotivate >= 10) {
            zileNecesare.add(absenteCauate.get(i));
            absenteNemotivate -= absenteCount.get(i);
            i++;
        }
        return zileNecesare.toArray(new Calendar[zileNecesare.size()]);
    }

    private boolean isSameDay(Calendar a, Calendar b) {

        return a.get(Calendar.YEAR) == b.get(Calendar.YEAR)
                && a.get(Calendar.MONTH) == b.get(Calendar.MONTH)
                && a.get(Calendar.DAY_OF_MONTH) == b.get(Calendar.DAY_OF_MONTH);
    }
}
