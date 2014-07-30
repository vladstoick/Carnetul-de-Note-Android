package com.stoicavlad.carnet.data.api;

import android.app.Application;

import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.data.OrmliteSqlHelper;
import com.stoicavlad.carnet.data.model.AbsentaUtility;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Vlad on 1/27/14.
 */
public class AbsenteDatabase {
    private OrmliteSqlHelper ormliteSqlHelper;
    private CarnetApp application;
    @Inject
    public AbsenteDatabase(OrmliteSqlHelper ormliteSqlHelper, Application application) {
        this.application = (CarnetApp) application;
        this.ormliteSqlHelper = ormliteSqlHelper;
    }

    public AbsenteDatabase(OrmliteSqlHelper ormliteSqlHelper) {
        this.ormliteSqlHelper = ormliteSqlHelper;
    }

    public AbsentaUtility[] getAbsente() {
//        try {
//            List<AbsentaUtility> absente = ormliteSqlHelper.getAbsenteDao().queryForAll();
//            return absente.toArray(new AbsentaUtility[absente.size()]);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return new AbsentaUtility[0];
//        }
        return new AbsentaUtility[0];
    }

    public boolean addAbsenta(Calendar c) {
//        try {
//            AbsentaUtility absenta = new AbsentaUtility(c.getTimeInMillis());
//            ormliteSqlHelper.getAbsenteDao().create(absenta);
//            if(application!=null){
//                application.updateWidget();
//            }
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
        return true;
    }

    public boolean addScutire(Calendar c){
//        try{
//            List<AbsentaUtility> absente = ormliteSqlHelper.getAbsenteDao().queryForAll();
//            for(AbsentaUtility absenta:absente){
//                Calendar calAbsenta = absenta.getCalendar();
//                if(isSameDay(calAbsenta,c)){
//                    ormliteSqlHelper.getAbsenteDao().delete(absenta);
//                }
//            }
//            return true;
//        } catch (SQLException e){
//            e.printStackTrace();
//            return false;
//        }
        return true;
    }

    public Calendar[] calculeazaScutiriOptim() {
//        AbsentaUtility[] absente = getAbsente();
//        ArrayList<Calendar> absenteCauate = new ArrayList<Calendar>();
//        ArrayList<Integer> absenteCount = new ArrayList<Integer>();
//        for (AbsentaUtility anAbsente : absente) {
//            Calendar dateToBeAdded = anAbsente.getCalendar();
//            boolean adauagat = false;
//            for (int j = 0; j < absenteCauate.size(); j++) {
//                Calendar dateToBeCompared = absenteCauate.get(j);
//                if (isSameDay(dateToBeAdded, dateToBeCompared)) {
//                    Integer newCount = absenteCount.get(j) + 1;
//                    absenteCount.set(j, newCount);
//                    adauagat = true;
//                    break;
//                }
//            }
//            if (!adauagat) {
//                absenteCauate.add(dateToBeAdded);
//                absenteCount.add(1);
//            }
//        }
//
//
//        //sortam dupa nrAbsente
//        for (int i = 0; i < absenteCount.size() - 1; i++) {
//            for (int j = i + 1; j < absenteCount.size(); j++) {
//                if (absenteCount.get(i) < absenteCount.get(j)) {
//                    Integer aux = absenteCount.get(i);
//                    absenteCount.set(i, absenteCount.get(j));
//                    absenteCount.set(j, aux);
//                    Calendar aux2 = absenteCauate.get(i);
//                    absenteCauate.set(i, absenteCauate.get(j));
//                    absenteCauate.set(j, aux2);
//                }
//            }
//        }
//        int absenteNemotivate = absente.length, i = 0;
//        ArrayList<Calendar> zileNecesare = new ArrayList<Calendar>();
//        //GREEDY TIME
//        while (absenteNemotivate >= 0 && i<absenteCauate.size()) {
//            zileNecesare.add(absenteCauate.get(i));
//            absenteNemotivate -= absenteCount.get(i);
//            i++;
//        }
//        return zileNecesare.toArray(new Calendar[zileNecesare.size()]);
        return null;    }

    private boolean isSameDay(Calendar a, Calendar b) {

        return a.get(Calendar.YEAR) == b.get(Calendar.YEAR)
                && a.get(Calendar.MONTH) == b.get(Calendar.MONTH)
                && a.get(Calendar.DAY_OF_MONTH) == b.get(Calendar.DAY_OF_MONTH);
    }

    public boolean resetAbsente(){
        AbsentaUtility[] absente = getAbsente();
        for(AbsentaUtility absenta:absente){
            try{
                ormliteSqlHelper.getAbsenteDao().delete(absenta);
            } catch (SQLException e){
                return false;
            }
        }
        return true;
    }

}
