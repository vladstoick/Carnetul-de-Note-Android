package com.stoicavlad.carnet.data.api;

import android.app.Application;

import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.data.OrmliteSqlHelper;
import com.stoicavlad.carnet.data.model.Materie;
import com.stoicavlad.carnet.data.model.Nota;
import com.stoicavlad.carnet.data.otto.BusProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class MateriiDatabase {
    OrmliteSqlHelper ormliteSqlHelper;
    CarnetApp application;
    @Inject
    public MateriiDatabase(OrmliteSqlHelper ormliteSqlHelper, Application application) {
        this.ormliteSqlHelper = ormliteSqlHelper;
        this.application = (CarnetApp) application;
        BusProvider.getInstance().register(this);
    }

    public MateriiDatabase(OrmliteSqlHelper ormliteSqlHelper){
        this.ormliteSqlHelper = ormliteSqlHelper;
    }

    public Materie[] getMaterii() {
        try {
            List<Materie> materii = ormliteSqlHelper.getMateriiDao().queryForAll();
            return materii.toArray(new Materie[materii.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Materie[0];
        }
    }

    public double getMedieGenerala(){
        Materie[] materii = getMaterii();
        if(materii.length == 0){
            return 0;
        }
        double rezultat = 0;
        for(Materie materie:materii){
            double medie = materie.getMedie();
            medie = Math.ceil(medie);
            rezultat += medie;
        }
        return rezultat/materii.length;
    }

    //MATERII

    public Materie getMaterie(int id) {
        try {
            return ormliteSqlHelper.getMateriiDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Materie[] getMateriiFaraTeza() {
        Materie[] materii = getMaterii();
        ArrayList<Materie> materiiFaraTeza = new ArrayList<Materie>();
        for (Materie materie : materii) {
            boolean areTeza = false;
            for (int j = 0; j < materie.getNote().length; j++) {
                Nota nota = materie.getNote()[j];
                if (nota.tip == Nota.TIP_NOTA_TEZA) {
                    areTeza = true;
                }
            }
            if (!areTeza) {
                materiiFaraTeza.add(materie);
            }
        }
        return materiiFaraTeza.toArray(new Materie[materiiFaraTeza.size()]);
    }

    public boolean addMaterie(String title) {
        try {
            Materie materie = new Materie(title);
            ormliteSqlHelper.getMateriiDao().create(materie);
            if(application!=null) application.updateWidget();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    public boolean deleteMaterie(Materie materie) {
        try {
            ormliteSqlHelper.getMateriiDao().delete(materie);
            if(application!=null) application.updateWidget();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean renameMaterie(Materie materie, String title) {
        try {
            materie.name = title;
            ormliteSqlHelper.getMateriiDao().update(materie);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean addNota(int notaValue, Materie materie, int type) {
        Nota nota = new Nota();
        nota.nota = notaValue;
        nota.materie = materie;
        nota.tip = type;
        try {
            materie.note.add(nota);
            ormliteSqlHelper.getMateriiDao().update(materie);
            if(application!=null) application.updateWidget();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteNota(Nota nota) {
        try {
            ormliteSqlHelper.getNoteDao().delete(nota);
            if(application!=null) application.updateWidget();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

}
