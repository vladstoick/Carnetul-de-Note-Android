package com.stoicavlad.carnet.data.note;

import javax.inject.Singleton;

/**
 * Created by Vlad on 1/26/14.
 */
@Singleton
public class NoteDatabase {
    public Materie[] getMaterii(){
        return new Materie[]{
            new Materie("Romana"),
            new Materie("Sport"),
            new Materie("Biologie")
        };
    }
}
