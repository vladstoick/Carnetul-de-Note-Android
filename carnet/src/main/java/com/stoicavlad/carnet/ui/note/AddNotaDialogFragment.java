package com.stoicavlad.carnet.ui.note;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ToggleButton;

import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.data.BusProvider;
import com.stoicavlad.carnet.data.DataSetChangedEvent;
import com.stoicavlad.carnet.data.note.Materie;
import com.stoicavlad.carnet.data.note.MateriiDatabase;
import com.stoicavlad.carnet.R;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 1/26/14.
 */
public class AddNotaDialogFragment extends DialogFragment implements Button.OnClickListener{
    @Inject MateriiDatabase materiiDatabase;
    @InjectView(R.id.materie_spinner) Spinner mMaterieSpinner;
    private Button mOKButton;
    private int type ;
    private int lastClicked = -1;
    private View mRootView;
    public AddNotaDialogFragment(int type){
        this.type = type;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BusProvider.getInstance().register(this);
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
        SpinnerAdapter mAdapter =   new NoteAdapter(getActivity(), materiiDatabase.getMaterii());
        mMaterieSpinner.setAdapter(mAdapter);
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
                        AddNotaDialogFragment.this.getDialog().cancel();
                    }
                });
        builder.setTitle(getString(R.string.add_nota));
        return builder.create();
    }

    public void okButtonSelected(){
        Button selectedButton = (Button) mRootView.findViewById(lastClicked);
        Materie materie =
                materiiDatabase.getMaterii()[mMaterieSpinner.getSelectedItemPosition()];
        int nota = Integer.parseInt(selectedButton.getText().toString());
        if(materiiDatabase.addNota(nota, materie.getName(), type) ) {
            BusProvider.getInstance()
                    .post(new DataSetChangedEvent(DataSetChangedEvent.TAG_MATERIE));
        }
    }

    private Button getOkButton(){
        if(mOKButton == null ){
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
        if(lastClicked!=-1){
            ToggleButton mLastButton = (ToggleButton) mRootView.findViewById(lastClicked);
            mLastButton.setChecked(false);
        }
        if(lastClicked == view.getId()){
            lastClicked = -1;
        } else {
            lastClicked = view.getId();
        }
        getOkButton().setEnabled(lastClicked != -1);
    }
}
