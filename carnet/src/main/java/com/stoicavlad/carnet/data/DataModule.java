package com.stoicavlad.carnet.data;

import android.app.Application;

import com.stoicavlad.carnet.data.api.AbsenteDatabase;
import com.stoicavlad.carnet.data.api.MateriiDatabase;

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
    @Provides @Singleton SqlHelper sqlHelper(Application application){
        return new SqlHelper(application);
    }
    @Provides @Singleton
    MateriiDatabase providesNoteDatabase(SqlHelper sqlHelper){
        return new MateriiDatabase(sqlHelper);
    }
    @Provides @Singleton
    AbsenteDatabase providedAbsenteDatabse(SqlHelper sqlHelper){
        return new AbsenteDatabase(sqlHelper);
    }
}
