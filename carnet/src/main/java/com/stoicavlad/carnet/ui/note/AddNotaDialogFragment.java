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
import com.stoicavlad.carnet.data.otto.BusProvider;
import com.stoicavlad.carnet.data.otto.DataSetChangedEvent;
import com.stoicavlad.carnet.data.model.Materie;
import com.stoicavlad.carnet.data.api.MateriiDatabase;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.model.Nota;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 1/26/14.
 */
public class AddNotaDialogFragment extends DialogFragment implements Button.OnClickListener{
    @Inject MateriiDatabase materiiDatabase;
    @InjectView(R.id.materie_spinner) Spinner mMaterieSpinner;
    private Materie[] materii;
    private Button mOKButton;
    private int type ;
    private int lastClicked = -1;
    private View mRootView;
    public AddNotaDialogFragment(int type){
        this.type = type;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Register for Dagger and Otto
        BusProvider.getInstance().register(this);
        CarnetApp.get(getActivity()).inject(this);

        //Initial inflating
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mRootView = inflater.inflate(R.layout.dialog_addnota,null);
        ButterKnife.inject(this,mRootView);

        //Adding button events
        for(int i=1;i<=10;i++){
            String buttonID = "button_"+i;
            int resID = getResources().getIdentifier(buttonID, "id", "com.stoicavlad.carnet");
            Button btn = (Button) mRootView.findViewById(resID);
            btn.setOnClickListener(this);
        }

        //Setting Spinner adapter
        materii = type == Nota.TIP_NOTA_SIMPLA ? materiiDatabase.getMaterii() :
                materiiDatabase.getMateriiFaraTeza();
        SpinnerAdapter mAdapter =   new SimpleNoteAdapter(getActivity(), materii);
        mMaterieSpinner.setAdapter(mAdapter);

        //Getting title
        String title = type == Nota.TIP_NOTA_SIMPLA ? getString(R.string.add_nota) :
                getString(R.string.add_teza);
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
        builder.setTitle(title);
        return builder.create();
    }

    public void okButtonSelected(){
        Button selectedButton = (Button) mRootView.findViewById(lastClicked);
        Materie materie = materii[mMaterieSpinner.getSelectedItemPosition()];
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
