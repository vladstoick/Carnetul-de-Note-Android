package com.stoicavlad.carnet.ui.absente;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vlad on 3/5/14.
 */
public class AbsenteDialogFragment extends DialogFragment {
    private static String TAG_DATES = "DATES";
    private long[] mDates;

    public static AbsenteDialogFragment newInstance(long[] dates){
        AbsenteDialogFragment dialogFragment = new AbsenteDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putLongArray(TAG_DATES, dates);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDates = getArguments().getLongArray(TAG_DATES);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        ArrayList<String> datesAsString = new ArrayList<String>();
        DateFormat dateFormat = DateFormat.getDateInstance();

        for(long dateMsValue : mDates){
            Date date = new Date();
            date.setTime(dateMsValue);
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
