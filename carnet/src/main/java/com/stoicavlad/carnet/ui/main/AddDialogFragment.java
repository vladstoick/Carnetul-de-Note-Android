package com.stoicavlad.carnet.ui.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;

import com.stoicavlad.carnet.R;

/**
 * Created by Vlad on 1/27/14.
 */
public class AddDialogFragment extends DialogFragment {
    public interface AddDialogFragmentListener {
        public void onItemSelected(int position);
    }

    private AddDialogFragmentListener mListener;

    public AddDialogFragment(){}

    public void setListener(AddDialogFragmentListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.add_title));
        builder.setItems(R.array.add_options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onItemSelected(i);
            }
        });
        return builder.create();
    }
}
