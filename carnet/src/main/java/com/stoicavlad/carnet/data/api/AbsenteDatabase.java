package com.stoicavlad.carnet.data.api;

import com.stoicavlad.carnet.data.SqlHelper;
import com.stoicavlad.carnet.data.model.Absenta;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Vlad on 1/27/14.
 */
public class AbsenteDatabase {
    SqlHelper sqlHelper;
    @Inject public AbsenteDatabase(SqlHelper sqlHelper){
        this.sqlHelper = sqlHelper;
    }

    public Absenta[] getAbsente(){
        ArrayList<Absenta> absente = new ArrayList<Absenta>();

        return (Absenta[])absente.toArray(new Absenta[absente.size()]);
    }
}
