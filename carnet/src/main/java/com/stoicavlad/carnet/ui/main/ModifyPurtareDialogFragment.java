package com.stoicavlad.carnet.ui.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.ui.note.NotaSelectorDialogFragment;

/**
 * Created by Vlad on 08-Aug-14.
 */
public class ModifyPurtareDialogFragment extends NotaSelectorDialogFragment {

    public interface OnPurtareChangedInterface{
        public void purtareDidChanged();
    }

    private OnPurtareChangedInterface mListener;

    public void setOnPurtareChangedListener(OnPurtareChangedInterface mListener) {
        this.mListener = mListener;
    }

    @Override
    public MaterialDialog onCreateDialog(Bundle savedInstanceState) {
        layoutXml = R.layout.dialog_add_simple;
        title = getString(R.string.modifica_purtarea);
        return super.onCreateDialog(savedInstanceState);
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void okButtonSelected() {
        super.okButtonSelected();
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        preferences.edit().putInt("PURTARE_TAG",selectedValue).commit();
        CarnetApp.get(getActivity()).updateWidget();
        mListener.purtareDidChanged();
    }
}
