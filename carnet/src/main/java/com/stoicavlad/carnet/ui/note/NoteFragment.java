package com.stoicavlad.carnet.ui.note;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.squareup.otto.Subscribe;
import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.data.BusProvider;
import com.stoicavlad.carnet.data.MateriiDatabase;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.DataSetChangedEvent;
import com.stoicavlad.carnet.ui.general.ComplexNoteAdapter;

import javax.inject.Inject;

public class NoteFragment extends Fragment implements AbsListView.OnItemClickListener {

    @Inject
    MateriiDatabase materiiDatabase;

    private OnFragmentInteractionListener mListener;
    private AbsListView mListView;
    private ListAdapter mAdapter;
    public NoteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CarnetApp.get(getActivity()).inject(this);
        mAdapter = new ComplexNoteAdapter(getActivity(), materiiDatabase.getMaterii());
        BusProvider.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notefragment, container, false);
        setHasOptionsMenu(true);
        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void showAddNotaDialogFragment();
    }

    @Subscribe public void onDataSetChanged(DataSetChangedEvent event){
        mAdapter = new ComplexNoteAdapter(getActivity(), materiiDatabase.getMaterii());
        ((AdapterView<ListAdapter>)mListView).setAdapter(mAdapter);
    }
}
