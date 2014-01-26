package com.stoicavlad.carnet.dialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.data.NoteDatabase;
import com.stoicavlad.carnet.fragments.dummy.DummyContent;
import com.stoicavlad.carnet.R;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 1/26/14.
 */
public class AddNotaDialogFragment extends DialogFragment implements Button.OnClickListener{
    @Inject NoteDatabase noteDatabase;
    @InjectView(R.id.materie_spinner) Spinner mMaterieSpinner;
    private int lastClicked = -1;
    private View mRootView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        CarnetApp.get(getActivity()).inject(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        mRootView = inflater.inflate(R.layout.dialog_addnota,null);
        ButterKnife.inject(this,mRootView);
        for(int i=1;i<=10;i++){
            String buttonID = "button_"+i;
            int resID = getResources().getIdentifier(buttonID, "id", "com.stoicavlad.carnet");
            Button btn = (Button) mRootView.findViewById(resID);
            btn.setOnClickListener(this);
        }
        SpinnerAdapter mAdapter =   new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
        mMaterieSpinner.setAdapter(mAdapter);
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
        builder.setTitle(getString(R.string.add_nota));
        return builder.create();
    }

    @Override
    public void onClick(View view) {
        view.setPressed(true);

        if(lastClicked!=-1 && lastClicked!=view.getId()){
            ToggleButton mLastButton = (ToggleButton) mRootView.findViewById(lastClicked);
            mLastButton.setChecked(false);
        }
        lastClicked = view.getId();
    }
}
