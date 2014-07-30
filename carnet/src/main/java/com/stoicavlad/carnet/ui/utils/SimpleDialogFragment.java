package com.stoicavlad.carnet.ui.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Vlad on 1/27/14.
 */
public class SimpleDialogFragment extends DialogFragment {
    private String message;
    private SimpleDialogFragment(){}

    public static SimpleDialogFragment newInstance(String message){
        SimpleDialogFragment simpleDialogFragment = new SimpleDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("MESSAGE",message);
        simpleDialogFragment.setArguments(bundle);
        return simpleDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.message = getArguments().getString("MESSAGE");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }
}
