package com.stoicavlad.carnet.ui.note;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract;
import com.stoicavlad.carnet.ui.utils.NotaSelector;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 1/26/14.
 */
public class AddNotaDialogFragment extends DialogFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int MATERII_LOADER = 0;
    @InjectView(R.id.materie_spinner) Spinner mMaterieSpinner;
    @InjectView(R.id.nota_selector) NotaSelector mNotaSelector;
    private SimpleCursorAdapter mCursorAdapter;
    private Button mOKButton;
    private View mRootView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(MATERII_LOADER,null,this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Initial inflating
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mRootView = inflater.inflate(R.layout.dialog_addnota, null);
        ButterKnife.inject(this, mRootView);

        //Setting Spinner adapter
        mCursorAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                null,
                new String[]{CarnetContract.MaterieEntry.COLUMN_NAME},
                new int[] {android.R.id.text1});
        mMaterieSpinner.setAdapter(mCursorAdapter);

        //setting listenr for nota selector

        mNotaSelector.setOnNotaSelectormListener(new NotaSelector.OnNotaSelectorListener() {
            @Override
            public void didChangeValue(boolean isSelected) {
                getOkButton().setEnabled(isSelected);
            }
        });

        //Getting title
        String title = getString(R.string.add_nota);
        builder.setView(mRootView)
                // Add action buttons
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        okButtonSelected();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddNotaDialogFragment.this.getDialog().cancel();
                    }
                });

        builder.setTitle(title);
        return builder.create();
    }

    void okButtonSelected() {
        Cursor cursor = mCursorAdapter.getCursor();
        int position = mMaterieSpinner.getSelectedItemPosition();

        cursor.moveToPosition(position);
        int materieId = cursor.getInt(CarnetContract.MaterieEntry.COL_ID);
        int nota = Integer.valueOf(mNotaSelector.selectedValue);

        ContentValues notaValues = new ContentValues();
        notaValues.put(CarnetContract.NoteEntry.COLUMN_VALUE,nota);
        notaValues.put(CarnetContract.NoteEntry.COLUMN_MATERIE_ID,materieId);
        getActivity().getContentResolver().insert(CarnetContract.NoteEntry.CONTENT_URI,notaValues);

    }

    private Button getOkButton() {
        if (mOKButton == null) {
            AlertDialog alertDialog = (AlertDialog) getDialog();
            mOKButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        }
        return this.mOKButton;
    }

    @Override
    public void onStart() {
        super.onStart();
        getOkButton().setEnabled(false);
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
