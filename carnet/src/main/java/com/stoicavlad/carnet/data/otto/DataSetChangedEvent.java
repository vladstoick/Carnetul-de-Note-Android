package com.stoicavlad.carnet.data.otto;

/**
 * Created by Vlad on 1/27/14.
 */
public class DataSetChangedEvent {
    public static final String TAG_MATERIE = "materie";
    public static final String TAG_ABSENTA = "absenta";
    public String tag;
    public DataSetChangedEvent(String tag){
        this.tag = tag;
    }
}
