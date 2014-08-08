package com.stoicavlad.carnet.ui.note;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract;
import com.stoicavlad.carnet.ui.utils.NotaSelector;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 1/26/14.
 */
public class NotaSelectorDialogFragment extends DialogFragment {

    protected static final String TAG_MATERIE_ID = "MATERIE_ID";
    protected int materieId;

    protected static final String TAG_XML = "XML";
    protected int layoutXml;

    protected int selectedValue;

    @InjectView(R.id.nota_selector) NotaSelector mNotaSelector;

    private int lastClicked = -1;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Initial inflating
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(layoutXml, null);
        ButterKnife.inject(this, rootView);

        String title = getString(R.string.add_teza);

        builder.setView(rootView)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        okButtonSelected();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NotaSelectorDialogFragment.this.getDialog().cancel();
                    }
                })
                .setTitle(title);

        return builder.create();
    }

    protected void okButtonSelected() {
        selectedValue = mNotaSelector.selectedValue;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mNotaSelector.setOnNotaSelectormListener(new NotaSelector.OnNotaSelectorListener() {
            @Override
            public void didChangeValue(boolean isSelected) {
                AlertDialog alertDialog = (AlertDialog) getDialog();
                Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setEnabled(isSelected);
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {
            Button positiveButton = (Button)d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setEnabled(false);
        }

    }
}
