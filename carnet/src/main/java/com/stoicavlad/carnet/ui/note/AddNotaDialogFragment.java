package com.stoicavlad.carnet.ui.note;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ToggleButton;

import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.api.MateriiDatabase;
import com.stoicavlad.carnet.data.model.Materie;
import com.stoicavlad.carnet.data.model.Nota;
import com.stoicavlad.carnet.data.otto.BusProvider;
import com.stoicavlad.carnet.data.otto.DataSetChangedEvent;
import com.stoicavlad.carnet.data.provider.CarnetContract;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 1/26/14.
 */
public class AddNotaDialogFragment extends DialogFragment implements Button.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final int MATERII_LOADER = 0;
    @InjectView(R.id.materie_spinner) public Spinner mMaterieSpinner;
    private CursorAdapter mCursorAdapter;
    private Button mOKButton;
    private int lastClicked = -1;
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

        //Adding button events
        for (int i = 1; i <= 10; i++) {
            String buttonID = "button_" + i;
            int resID = getResources().getIdentifier(buttonID, "id", "com.stoicavlad.carnet");
            Button btn = (Button) mRootView.findViewById(resID);
            btn.setOnClickListener(this);
        }

        //Setting Spinner adapter
        mCursorAdapter = new SimpleNoteAdapter(getActivity(),null,0);
        mMaterieSpinner.setAdapter(mCursorAdapter);

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

        Button selectedButton = (Button) mRootView.findViewById(lastClicked);

        int nota = 0;
        if(selectedButton.getText()!=null){
            nota = Integer.parseInt(selectedButton.getText().toString());
        }
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
    public void onClick(View view) {
        view.setPressed(true);
        if (lastClicked != -1) {
            ToggleButton mLastButton = (ToggleButton) mRootView.findViewById(lastClicked);
            mLastButton.setChecked(false);
        }
        if (lastClicked == view.getId()) {
            lastClicked = -1;
        } else {
            lastClicked = view.getId();
        }
        getOkButton().setEnabled(lastClicked != -1);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                getActivity(),
                CarnetContract.MaterieEntry.CONTENT_URI,
                CarnetContract.MaterieEntry.COLUMNS_WITOUT_NOTE,
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
