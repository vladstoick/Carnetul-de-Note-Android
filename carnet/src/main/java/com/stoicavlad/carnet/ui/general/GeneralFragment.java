package com.stoicavlad.carnet.ui.general;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.api.AbsenteDatabase;
import com.stoicavlad.carnet.data.api.MateriiDatabase;
import com.stoicavlad.carnet.data.otto.BusProvider;
import com.stoicavlad.carnet.data.otto.DataSetChangedEvent;
import java.text.DecimalFormat;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class GeneralFragment extends Fragment {
    @Inject
    MateriiDatabase materiiDatabase;
    @Inject
    AbsenteDatabase absenteDatabase;
    @InjectView(R.id.medieGenerala)
    TextView mMedieGenerala;
    @InjectView(R.id.purtare)
    TextView mPurtare;
    @InjectView(R.id.absente)
    TextView mAbsente;
    public GeneralFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CarnetApp.get(getActivity()).inject(this);
        BusProvider.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_general, container, false);
        ButterKnife.inject(this, rootView);
        mMedieGenerala.setText(materiiDatabase.getMedieGenerala() + " ");
        materiiDatabase.setPurtare(7);
        int purtare = materiiDatabase.getPurtare();

        mPurtare.setText(getString(R.string.purtare) + ": " + purtare);
        double medie = materiiDatabase.getMedieGenerala();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String medieString = decimalFormat.format(medie);
        mMedieGenerala.setText( medieString );
        if(medie<4.5){
            mMedieGenerala.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else {
            mMedieGenerala.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }
        int absente = absenteDatabase.getAbsente().length;
        mAbsente.setText(absente + "");
        if(absente>10){
            mAbsente.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else {
            mAbsente.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }
        return rootView;
    }

    @Subscribe
    public void onDataSetChanged(DataSetChangedEvent event) {
        if (event.tag.equals(DataSetChangedEvent.TAG_ABSENTA)) {
            int absente = absenteDatabase.getAbsente().length;
            mAbsente.setText(absente + "");
            if(absente>10){
                mAbsente.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            } else {
                mAbsente.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            }
        }
    }

}
