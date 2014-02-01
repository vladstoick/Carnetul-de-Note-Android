package com.stoicavlad.carnet.ui;

import com.stoicavlad.carnet.ui.absente.AbsentaFragment;
import com.stoicavlad.carnet.ui.main.MainActivity;
import com.stoicavlad.carnet.ui.materie.AddMaterieDialogFragment;
import com.stoicavlad.carnet.ui.note.AddNotaDialogFragment;
import com.stoicavlad.carnet.ui.note.ComplexNoteAdapter;
import com.stoicavlad.carnet.ui.note.NoteListFragment;

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
                ComplexNoteAdapter.class,
                //MATERIE
                AddMaterieDialogFragment.class,

                //ABSENTE
                AbsentaFragment.class
        },
        complete = false,
        library = true
)
public class UiModule {

}