package com.stoicavlad.carnet.data.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Vlad on 1/27/14.
 */
@DatabaseTable(tableName = "note")
public class Nota{
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    public Materie materie;
    @DatabaseField
    public int date;
    @DatabaseField
    public int nota;
    @DatabaseField
    public int tip;

    public static final int TIP_NOTA_SIMPLA = 1;
    public static final int TIP_NOTA_TEZA = 2;

    public Nota(){}


}
