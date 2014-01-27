package com.stoicavlad.carnet.ui.materie;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.TextView;

import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.note.MateriiDatabase;
import com.stoicavlad.carnet.utils.KeyboardUtils;

import javax.inject.Inject;

/**
 * Created by Vlad on 1/27/14.
 */
public class AddMaterieDialogFragment extends DialogFragment{
    @Inject MateriiDatabase materiiDatabase;
    EditText mEditText;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        CarnetApp.get(getActivity()).inject(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mEditText = new EditText(getActivity());
        mEditText.setHint(R.string.add_materie_edit_text_hint);

        builder.setView(mEditText)
                .setTitle(R.string.add_materie)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        materiiDatabase.addMaterie(mEditText.getText().toString());
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        mEditText.requestFocus();
        Dialog dialog = builder.create();

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
