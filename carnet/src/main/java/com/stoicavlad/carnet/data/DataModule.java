package com.stoicavlad.carnet.data;

import android.app.Application;
import android.content.Context;

import com.stoicavlad.carnet.DaggerModules.ForApplication;
import com.stoicavlad.carnet.data.note.NoteDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vlad on 1/26/14.
 */

@Module(
        complete = false,
        library = true,
        includes = ApiModule.class
)
public class DataModule {
    @Provides @Singleton SqlHelper sqlHelper(Application application){
        return new SqlHelper(application);
    }
}
