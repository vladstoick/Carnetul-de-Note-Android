package com.stoicavlad.carnet.ui.note;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.text.Editable;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract;

/**
 * Created by Vlad on 1/27/14.
 */
public class AddMaterieDialogFragment extends DialogFragment {

    private EditText mEditText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        mEditText = new EditText(getActivity());
        mEditText.setHint(R.string.add_materie_edit_text_hint);


        builder.setView(mEditText)
                .setTitle(R.string.add_materie)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Editable mEditTextEditable = mEditText.getText();
                        if (mEditTextEditable != null){
                            String name = mEditTextEditable.toString();
                            ContentValues materieValue = new ContentValues();
                            materieValue.put(CarnetContract.MaterieEntry.COLUMN_NAME, name);
                            builder.getContext().getContentResolver()
                                    .insert(CarnetContract.MaterieEntry.CONTENT_URI,materieValue);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
