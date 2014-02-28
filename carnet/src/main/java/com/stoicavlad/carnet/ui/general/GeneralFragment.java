package com.stoicavlad.carnet.ui.general;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.api.AbsenteDatabase;
import com.stoicavlad.carnet.data.api.MateriiDatabase;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_general, container, false);
        ButterKnife.inject(this, rootView);
        mMedieGenerala.setText(materiiDatabase.getMedieGenerala() + " ");
        SharedPreferences sp = getActivity()
                .getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        int purtare = sp.getInt("PURTARE", 10);
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
        if(medie>10){
            mAbsente.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else {
            mAbsente.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }
        return rootView;
    }


}
