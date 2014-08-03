package com.stoicavlad.carnet.ui.note;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract;

import butterknife.ButterKnife;

/**
 * Created by Vlad on 1/26/14.
 */
public class AddTezaDialogFragment extends DialogFragment implements Button.OnClickListener {
    private Button mOKButton;
    private int materieId;

    private int lastClicked = -1;
    private View mRootView;

    public static AddTezaDialogFragment newInstance(int id){
        AddTezaDialogFragment dialogFragment = new AddTezaDialogFragment();
        Bundle arguments = new Bundle();

        arguments.putInt("ID",id);
        dialogFragment.setArguments(arguments);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.materieId = getArguments().getInt("ID");

        //Initial inflating
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mRootView = inflater.inflate(R.layout.dialog_addteza, null);
        ButterKnife.inject(this, mRootView);

        //Adding button events
        for (int i = 1; i <= 10; i++) {
            String buttonID = "button_" + i;
            int resID = getResources().getIdentifier(buttonID, "id", "com.stoicavlad.carnet");
            Button btn = (Button) mRootView.findViewById(resID);
            btn.setOnClickListener(this);
        }

         //Getting title
        String title = getString(R.string.add_teza);
        builder.setView(mRootView)
                // Add action buttons
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        okButtonSelected();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddTezaDialogFragment.this.getDialog().cancel();
                    }
                });

        builder.setTitle(title);
        return builder.create();
    }

    void okButtonSelected() {

        Button selectedButton = (Button) mRootView.findViewById(lastClicked);
        int teza = 0;
        if(selectedButton.getText()!=null){
            teza = Integer.parseInt(selectedButton.getText().toString());
        }
        ContentValues materieValues = new ContentValues();
        materieValues.put(CarnetContract.MaterieEntry.COLUMN_TEZA, teza);
        getActivity().getContentResolver()
                .update(CarnetContract.MaterieEntry.buildMaterieUri(materieId),
                    materieValues, null, null);

    }

    private Button getOkButton() {
        if (mOKButton == null) {
            AlertDialog alertDialog = (AlertDialog) getDialog();
            mOKButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        }
        return this.mOKButton;
    }

    @Override
    public void onStart() {
        super.onStart();
        getOkButton().setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        view.setPressed(true);
        if (lastClicked != -1) {
            ToggleButton mLastButton = (ToggleButton) mRootView.findViewById(lastClicked);
            mLastButton.setChecked(false);
        }
        if (lastClicked == view.getId()) {
            lastClicked = -1;
        } else {
            lastClicked = view.getId();
        }
        getOkButton().setEnabled(lastClicked != -1);
    }
}
