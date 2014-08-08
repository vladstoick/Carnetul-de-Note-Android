package com.stoicavlad.carnet.ui.note;

import android.app.Dialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract;

/**
 * Created by Vlad on 1/26/14.
 */
public class AddNotaDialogFragment extends NotaSelectorDialogFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int MATERII_LOADER = 0;
    private SimpleCursorAdapter mCursorAdapter;
    private Spinner mSpinner;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        layoutXml = R.layout.dialog_addnota;

        mCursorAdapter = new SimpleCursorAdapter(getActivity(),
            android.R.layout.simple_list_item_1,
            null,
            new String[]{CarnetContract.MaterieEntry.COLUMN_NAME},
            new int[] {android.R.id.text1}
        );
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.add_nota);
        mSpinner = (Spinner) rootView.findViewById(R.id.materie_spinner);
        mSpinner.setAdapter(mCursorAdapter);
        getLoaderManager().initLoader(MATERII_LOADER,null,this);
        return dialog;
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

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                getActivity(),
                CarnetContract.MaterieEntry.CONTENT_URI,
                CarnetContract.MaterieEntry.COLUMNS_SIMPLE,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mCursorAdapter.swapCursor(null);
    }
}
