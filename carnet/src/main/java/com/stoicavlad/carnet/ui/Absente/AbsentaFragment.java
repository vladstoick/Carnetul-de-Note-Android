package com.stoicavlad.carnet.ui.absente;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.squareup.otto.Subscribe;
import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.api.AbsenteDatabase;
import com.stoicavlad.carnet.data.model.Absenta;
import com.stoicavlad.carnet.data.otto.BusProvider;
import com.stoicavlad.carnet.data.otto.DataSetChangedEvent;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AbsentaFragment extends Fragment implements Button.OnClickListener {
    @Inject
    AbsenteDatabase absenteDatabase;
    @InjectView(R.id.list)
    ListView mListView;
    private ListAdapter mAdapter;

    public AbsentaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CarnetApp.get(getActivity()).inject(this);
        Absenta[] absente = absenteDatabase.getAbsente();
        mAdapter = new AbsenteAdapter(getActivity(), absente);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BusProvider.getInstance().register(this);
        View view = inflater.inflate(R.layout.fragment_absenta, container, false);
        ButterKnife.inject(this, view);
        Button mCalculate = new Button(getActivity());
        mCalculate.setText(R.string.calculeaza_absente);
        mCalculate.setOnClickListener(this);
        mListView.addHeaderView(mCalculate);
        mListView.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void onClick(View v) {
        Calendar[] dates = absenteDatabase.calculeazaScutiriOptim();
        AbsenteDialogFragment dialogFragment = new AbsenteDialogFragment(dates);
        dialogFragment.show(getActivity().getFragmentManager() , " " );
    }

    @Subscribe
    public void onDataSetChanged(DataSetChangedEvent event) {
        if (event.tag.equals(DataSetChangedEvent.TAG_ABSENTA)) {
            mAdapter = new AbsenteAdapter(getActivity(), absenteDatabase.getAbsente());
            mListView.setAdapter(mAdapter);
        }
    }

}
