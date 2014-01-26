package com.stoicavlad.carnet.data;

import android.app.Application;

import com.stoicavlad.carnet.data.note.NoteDatabase;

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
public class ApiModule {
    @Provides
    @Singleton
    NoteDatabase providesNoteDatabase(SqlHelper sqlHelper){
        return new NoteDatabase(sqlHelper);
    }

}

