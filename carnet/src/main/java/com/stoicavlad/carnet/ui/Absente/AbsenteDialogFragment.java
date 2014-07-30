package com.stoicavlad.carnet.ui.absente;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.stoicavlad.carnet.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vlad on 3/5/14.
 */
class AbsenteDialogFragment extends DialogFragment {
    private Calendar[] dates;


    public AbsenteDialogFragment(Calendar[] dates) {
        this.dates = dates;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        ArrayList<String> datesAsString = new ArrayList<String>();
        DateFormat dateFormat = DateFormat.getDateInstance();
        for(Calendar calendar : dates){
            Date date = new Date();
            date.setTime(calendar.getTimeInMillis());
            String rezultat = dateFormat.format(date);
            datesAsString.add(rezultat);
        }
        String[] datesStringArray = datesAsString.toArray(new String[datesAsString.size()]);
        builder.setTitle(R.string.scutiri_necesare);
        builder.setItems(datesStringArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }
}
