package com.stoicavlad.carnet.DaggerModules;

import com.stoicavlad.carnet.Activities.MainActivity;
import com.stoicavlad.carnet.dialogFragments.AddNotaDialogFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vlad on 1/26/14.
 */
@Module(
        injects = {
                MainActivity.class,
                AddNotaDialogFragment.class,
        },
        complete = false,
        library = true
)
public class UiModule {
//    @Provides
//    @Singleton
//    AppContainer provideAppContainer() {
//        return AppContainer.DEFAULT;
//    }

}