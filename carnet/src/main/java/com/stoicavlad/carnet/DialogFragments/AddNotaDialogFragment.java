package com.stoicavlad.carnet.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.stoicavlad.carnet.R;

/**
 * Created by Vlad on 1/26/14.
 */
public class AddNotaDialogFragment extends DialogFragment implements Button.OnClickListener{
    private int lastClicked = -1;
    private View mRootView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        mRootView = inflater.inflate(R.layout.dialog_addnota,null);
        for(int i=1;i<=10;i++){
            String buttonID = "button_"+i;
            int resID = getResources().getIdentifier(buttonID, "id", "com.stoicavlad.carnet");
            Button btn = (Button) mRootView.findViewById(resID);
            btn.setOnClickListener(this);
        }
        builder.setView(mRootView)
                // Add action buttons
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // sign in the user ...
            }
        })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddNotaDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onClick(View view) {
        view.setPressed(true);
        if(lastClicked!=-1){
            ToggleButton mLastButton = (ToggleButton) mRootView.findViewById(lastClicked);
            mLastButton.setChecked(false);
        }
        lastClicked = view.getId();
    }
}
