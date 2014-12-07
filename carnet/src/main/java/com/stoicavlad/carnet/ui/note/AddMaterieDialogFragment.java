package com.stoicavlad.carnet.ui.note;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialogCompat;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract;

/**
 * Created by Vlad on 1/27/14.
 */
public class AddMaterieDialogFragment extends DialogFragment {

    private EditText mEditText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        final MaterialDialogCompat.Builder builder = new MaterialDialogCompat.Builder(getActivity());
       final MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
//        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        mEditText = new EditText(getActivity());
        mEditText.setHint(R.string.add_materie_edit_text_hint);



        builder.customView(mEditText)
                .title(getString(R.string.add_materie))
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .callback(new MaterialDialog.SimpleCallback() {
                    @Override
                    public void onPositive(MaterialDialog materialDialog) {
                        Editable mEditTextEditable = mEditText.getText();
                        if (mEditTextEditable != null) {
                            String name = mEditTextEditable.toString();
                            ContentValues materieValue = new ContentValues();
                            materieValue.put(CarnetContract.MaterieEntry.COLUMN_NAME, name);
                            getActivity().getContentResolver()
                                    .insert(CarnetContract.MaterieEntry.CONTENT_URI, materieValue);
                        }
                    }
                });

        final MaterialDialog dialog = builder.build();

//        final Button positiveButton = (Button)
//        positiveButton.setEnabled(false);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dialog.getActionButton(DialogAction.POSITIVE).setEnabled(s.length()>0);
//                positiveButton.setEnabled(s.length()>0);
            }
        });
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
