package com.stoicavlad.carnet.data.api;

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

    @Inject
    public MateriiDatabase(OrmliteSqlHelper ormliteSqlHelper) {
        this.ormliteSqlHelper = ormliteSqlHelper;
        BusProvider.getInstance().register(this);
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
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    public boolean deleteMaterie(Materie materie) {
        try {
            ormliteSqlHelper.getMateriiDao().delete(materie);
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
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteNota(Nota nota) {
        try {
            ormliteSqlHelper.getNoteDao().delete(nota);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

}
