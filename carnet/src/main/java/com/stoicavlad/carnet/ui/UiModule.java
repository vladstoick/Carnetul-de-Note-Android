package com.stoicavlad.carnet.ui;

import com.stoicavlad.carnet.ui.note.AddNotaDialogFragment;
import com.stoicavlad.carnet.ui.note.NoteFragment;
import com.stoicavlad.carnet.ui.main.MainActivity;

import dagger.Module;

/**
 * Created by Vlad on 1/26/14.
 */
@Module(
        injects = {
                MainActivity.class,
                //NOTE
                AddNotaDialogFragment.class,
                NoteFragment.class
        },
        complete = false,
        library = true
)
public class UiModule {

}