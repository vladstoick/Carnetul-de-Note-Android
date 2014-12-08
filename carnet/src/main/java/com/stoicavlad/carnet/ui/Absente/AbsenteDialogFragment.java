package com.stoicavlad.carnet.ui.absente;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.stoicavlad.carnet.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Vlad on 3/5/14.
 */
public class AbsenteDialogFragment extends DialogFragment {
    private static final String TAG_DATES = "DATES";

    public static AbsenteDialogFragment newInstance(long[] dates){
        AbsenteDialogFragment dialogFragment = new AbsenteDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putLongArray(TAG_DATES, dates);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        long[] mDates = getArguments().getLongArray(TAG_DATES);

//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());


        ArrayList<String> datesAsString = new ArrayList<String>();
        DateFormat dateFormat = DateFormat.getDateInstance();

        for(long dateMsValue : mDates){
            Date date = new Date();
            date.setTime(dateMsValue);
            String rezultat = dateFormat.format(date);
            datesAsString.add(rezultat);
        }
        String[] datesStringArray = datesAsString.toArray(new String[datesAsString.size()]);
        builder.title(R.string.scutiri_necesare)
                .items(datesStringArray)
                .positiveText(android.R.string.ok);

//        builder.it(datesStringArray, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });

        return builder.build();
    }
}
