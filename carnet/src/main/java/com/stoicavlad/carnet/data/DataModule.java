package com.stoicavlad.carnet.data;

import com.stoicavlad.carnet.data.note.NoteDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vlad on 1/26/14.
 */
@Module(
        complete = true,
        library = true
)
public class DataModule {
    @Provides @Singleton NoteDatabase providesNoteDatabase(){
        return new NoteDatabase();
    }
}
