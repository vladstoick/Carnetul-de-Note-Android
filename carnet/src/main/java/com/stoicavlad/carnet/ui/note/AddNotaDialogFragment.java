package com.stoicavlad.carnet.ui.note;

import android.app.Dialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.afollestad.materialdialogs.MaterialDialog;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract;

/**
 * Created by Vlad on 1/26/14.
 */
public class AddNotaDialogFragment extends NotaSelectorDialogFragment{
    private static final int MATERII_LOADER = 0;
    private SimpleCursorAdapter mCursorAdapter;
    private Spinner mSpinner;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        layoutXml = R.layout.dialog_addnota;


        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.add_nota);
        mSpinner = (Spinner) rootView.findViewById(R.id.materie_spinner);

        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Cursor cursor = new CursorLoader(
                getActivity(),
                CarnetContract.MaterieEntry.CONTENT_URI,
                CarnetContract.MaterieEntry.COLUMNS_SIMPLE,
                null,
                null,
                null
        ).loadInBackground();
        if(cursor.getCount() == 0){
            this.dismiss();
            new MaterialDialog.Builder(getActivity())
                    .content(getString(R.string.add_nota_materie_error))
                    .positiveText(getString(android.R.string.ok))
                    .build()
                    .show();
        } else {
            mCursorAdapter = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{CarnetContract.MaterieEntry.COLUMN_NAME},
                    new int[]{android.R.id.text1}
            );
            mSpinner.setAdapter(mCursorAdapter);
        }
    }

    @Override
    protected void okButtonSelected() {
        super.okButtonSelected();
        Cursor cursor = mCursorAdapter.getCursor();
        int position = mSpinner.getSelectedItemPosition();

        cursor.moveToPosition(position);
        int materieId = cursor.getInt(CarnetContract.MaterieEntry.COL_ID);
        int nota = selectedValue;

        ContentValues notaValues = new ContentValues();
        notaValues.put(CarnetContract.NoteEntry.COLUMN_VALUE,nota);
        notaValues.put(CarnetContract.NoteEntry.COLUMN_MATERIE_ID,materieId);
        getActivity().getContentResolver().insert(CarnetContract.NoteEntry.CONTENT_URI,notaValues);
    }

}
