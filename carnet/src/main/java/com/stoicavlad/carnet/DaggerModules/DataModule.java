package com.stoicavlad.carnet.DaggerModules;

import com.stoicavlad.carnet.data.NoteDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vlad on 1/26/14.
 */
@Module(
        complete = false,
        library = true
)
public class DataModule {
    @Provides @Singleton NoteDatabase providesNoteDatabase(){
        return new NoteDatabase();
    }
}
