package com.stoicavlad.carnet.DaggerModules;

import android.content.Context;

import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.data.DataModule;
import com.stoicavlad.carnet.ui.UiModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vlad on 1/26/14.
 */
@Module(
        includes = {
                UiModule.class,
                DataModule.class
        },
        injects = {
                CarnetApp.class
        },
        library = true
)
public class AndroidModule {
    private final CarnetApp application;

    public AndroidModule(CarnetApp application) {
        this.application = application;
    }

    /**
     * Allow the application context to be injected but require that it be annotated with
     * {@link ForApplication @ForApplication} to explicitly differentiate it from an activity context.
     */
    @Provides @Singleton @ForApplication
    Context provideApplicationContext() {
        return application;
    }

}