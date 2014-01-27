package com.stoicavlad.carnet.data.otto;

import com.squareup.otto.Bus;

/**
 * Created by Vlad on 1/27/14.
 */
public class BusProvider{
    static Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }
}