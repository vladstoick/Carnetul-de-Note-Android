package com.stoicavlad.carnet.ui.main;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.widget.DatePicker;

import com.stoicavlad.carnet.data.provider.CarnetContract;

import java.util.Calendar;

/**
 * Created by Vlad on 07-Dec-14.
 */
public class DatePickerDialogFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private static final String TYPE = "type";
    public static int TYPE_ABSENTA = 1;
    public static int TYPE_SCUTIRE = 2;
    private static int type;

    /**
     * variable due to listener called twice in android < 5.0
     */
    private boolean calledOnce = false;

    public DatePickerDialogFragment() {
    }

    public static DatePickerDialogFragment newInstance(int type){
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
        datePickerDialogFragment.setArguments(bundle);
        return datePickerDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        type = getArguments().getInt(TYPE);

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if(calledOnce == true){
            return;
        }
        calledOnce = true;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(0);
        c.set(year, monthOfYear, dayOfMonth);
        long date = c.getTimeInMillis();
        if (type == TYPE_ABSENTA) {
            ContentValues absentaValues = new ContentValues();
            absentaValues.put(CarnetContract.AbsentaEntry.COLUMN_DATE, c.getTimeInMillis());
            getActivity().getContentResolver()
                    .insert(CarnetContract.AbsentaEntry.CONTENT_URI, absentaValues);

        } else {
            String dateInString = date + "";
            getActivity().getContentResolver()
                    .delete(CarnetContract.AbsentaEntry.CONTENT_URI,
                            CarnetContract.AbsentaEntry.COLUMN_DATE + " = ?",
                            new String[]{ dateInString } );
        }
    }

}
