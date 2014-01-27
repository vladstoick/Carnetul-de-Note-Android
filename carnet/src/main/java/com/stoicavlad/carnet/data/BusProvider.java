package com.stoicavlad.carnet.data;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

/**
 * Created by Vlad on 1/27/14.
 */
public class BusProvider{
    static Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }
}