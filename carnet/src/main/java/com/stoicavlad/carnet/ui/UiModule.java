package com.stoicavlad.carnet.ui;

import com.stoicavlad.carnet.ui.Absente.AbsenteFragment;
import com.stoicavlad.carnet.ui.note.NoteListFragment;
import com.stoicavlad.carnet.ui.materie.AddMaterieDialogFragment;
import com.stoicavlad.carnet.ui.note.AddNotaDialogFragment;
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
                NoteListFragment.class,
                //MATERIE
                AddMaterieDialogFragment.class,
                //ABSENTE
                AbsenteFragment.class
        },
        complete = false,
        library = true
)
public class UiModule {

}