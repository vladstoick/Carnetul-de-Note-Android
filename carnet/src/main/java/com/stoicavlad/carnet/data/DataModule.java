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
    @Provides
    @Singleton
    OrmliteSqlHelper ormliteSqlHelper(Application application){
        return new OrmliteSqlHelper(application);
    }
    @Provides
    @Singleton
    MateriiDatabase providesNoteDatabase(OrmliteSqlHelper sqlHelper) {
        return new MateriiDatabase(sqlHelper);
    }

    @Provides
    @Singleton
    AbsenteDatabase providedAbsenteDatabse(OrmliteSqlHelper sqlHelper) {
        return new AbsenteDatabase(sqlHelper);
    }
}
