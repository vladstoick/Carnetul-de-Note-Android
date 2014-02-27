package com.stoicavlad.carnet.ui;

import com.stoicavlad.carnet.ui.absente.AbsentaFragment;
import com.stoicavlad.carnet.ui.main.MainActivity;
import com.stoicavlad.carnet.ui.materie.AddMaterieDialogFragment;
import com.stoicavlad.carnet.ui.note.AddNotaDialogFragment;
import com.stoicavlad.carnet.ui.note.NoteListFragment;
import com.stoicavlad.carnet.ui.note.detail.NoteDetailActivity;
import com.stoicavlad.carnet.ui.note.detail.NoteDetailFragment;
import com.stoicavlad.carnet.ui.note.detail.NoteDetailMedieFragment;
import com.stoicavlad.carnet.ui.setup.SetupActivity;
import com.stoicavlad.carnet.ui.widget.GeneralWidget;

import dagger.Module;

/**
 * Created by Vlad on 1/26/14.
 */
@Module(
        injects = {
                MainActivity.class,
                SetupActivity.class,
                //NOTE
                AddNotaDialogFragment.class,
                NoteListFragment.class,
                NoteDetailActivity.class,
                NoteDetailFragment.class,
                NoteDetailMedieFragment.class,
                //MATERIE
                AddMaterieDialogFragment.class,
                //ABSENTE
                AbsentaFragment.class,
                //WIDGET
                GeneralWidget.class
        },
        complete = false,
        library = true
)
public class UiModule {

}