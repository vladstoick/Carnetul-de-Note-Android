package com.stoicavlad.carnet.data.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Vlad on 2/28/14.
 */
@DatabaseTable(tableName = "purtare")
public class Purtare {
    @DatabaseField( generatedId = true)
    public int id;
    @DatabaseField
    public int nota;

    public Purtare(){}
}
