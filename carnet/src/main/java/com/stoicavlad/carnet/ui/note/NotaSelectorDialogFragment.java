package com.stoicavlad.carnet.ui.note;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.ui.utils.NotaSelector;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 1/26/14.
 */
public class NotaSelectorDialogFragment extends DialogFragment {

    protected static final String TAG_MATERIE_ID = "MATERIE_ID";
    protected int materieId;

    protected int layoutXml;
    protected String title;

    protected View rootView;

    protected int selectedValue;

    @InjectView(R.id.nota_selector) NotaSelector mNotaSelector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public MaterialDialog onCreateDialog(Bundle savedInstanceState) {

        //Initial inflating
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        rootView = inflater.inflate(layoutXml, null);
        ButterKnife.inject(this, rootView);


        builder.customView(rootView)
                .title(title)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .callback(new MaterialDialog.Callback() {
                    @Override
                    public void onPositive(MaterialDialog materialDialog) {
                        okButtonSelected();
                    }

                    @Override
                    public void onNegative(MaterialDialog materialDialog) {

                    }
                });
        MaterialDialog dialog = builder.build();
        TextView positiveButton = (TextView) dialog.getActionButton(DialogAction.POSITIVE);
        positiveButton.setEnabled(false);
        return dialog;
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
                MaterialDialog dialog = (MaterialDialog) getDialog();
                TextView button = (TextView) dialog.getActionButton(DialogAction.POSITIVE);
                button.setEnabled(isSelected);
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
