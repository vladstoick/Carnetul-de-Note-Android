package com.stoicavlad.carnet;

import android.app.Application;

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
        }
)
public class AndroidModule {
    private final CarnetApp application;

    public AndroidModule(CarnetApp application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

}