package com.stoicavlad.carnet.data;

import com.stoicavlad.carnet.data.note.MateriiDatabase;

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
    MateriiDatabase providesNoteDatabase(SqlHelper sqlHelper){
        return new MateriiDatabase(sqlHelper);
    }

}

