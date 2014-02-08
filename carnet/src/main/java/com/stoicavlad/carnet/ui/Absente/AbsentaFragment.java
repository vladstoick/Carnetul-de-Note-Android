package com.stoicavlad.carnet.ui.absente;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.common.eventbus.Subscribe;
import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.api.AbsenteDatabase;
import com.stoicavlad.carnet.data.model.Absenta;
import com.stoicavlad.carnet.data.otto.BusProvider;
import com.stoicavlad.carnet.data.otto.DataSetChangedEvent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AbsentaFragment extends Fragment implements AbsListView.OnItemClickListener,
        Button.OnClickListener {
    @Inject
    AbsenteDatabase absenteDatabase;
    private OnFragmentInteractionListener mListener;
    @InjectView(R.id.list)
    ListView mListView;
    private ListAdapter mAdapter;
    private Button mCalculate;

    public AbsentaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CarnetApp.get(getActivity()).inject(this);
        Absenta[] absente = absenteDatabase.getAbsente();
        mAdapter = new AbsenteAdapter(getActivity(), absente);
        BusProvider.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_absenta, container, false);
        ButterKnife.inject(this, view);
        mCalculate = new Button(getActivity());
        mCalculate.setText(R.string.calculeaza_absente);
        mCalculate.setOnClickListener(this);
        mListView.addHeaderView(mCalculate);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
//            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    @Override
    public void onClick(View v) {
        //calculeaza optim;

    }

    @Subscribe
    public void onAbsenteChanged(DataSetChangedEvent event) {
        if (event.tag == DataSetChangedEvent.TAG_ABSENTA) {
            mAdapter = new AbsenteAdapter(getActivity(), absenteDatabase.getAbsente());
            mListView.setAdapter(mAdapter);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
