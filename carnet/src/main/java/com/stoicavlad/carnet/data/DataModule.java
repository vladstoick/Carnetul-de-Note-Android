package com.stoicavlad.carnet.data;

import android.app.Application;
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
