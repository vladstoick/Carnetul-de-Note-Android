package com.stoicavlad.carnet.ui.note.detail;


import android.app.Fragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.api.MateriiDatabase;
import com.stoicavlad.carnet.data.model.Materie;
import com.stoicavlad.carnet.data.model.Nota;
import com.stoicavlad.carnet.data.otto.BusProvider;
import com.stoicavlad.carnet.data.otto.DataSetChangedEvent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class NoteDetailFragment extends Fragment
        implements NoteDetailAdapter.NoteDetailAdapterInteractionListener {
    @Inject
    MateriiDatabase materiiDatabase;
    private static final String ARG_MATERIE = "materie";
    String materieName;
    @InjectView(R.id.list)
    StickyListHeadersListView mListView;
    NoteDetailAdapter mAdapter;
    private Materie materie;

    public NoteDetailFragment() {
        // Required empty public constructor
    }

    public static NoteDetailFragment newInstance(String materie) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();

        args.putString(ARG_MATERIE, materie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        CarnetApp.get(getActivity()).inject(this);
        if (getArguments() != null) {
            materieName = getArguments().getString(ARG_MATERIE);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_detail, container, false);
        ButterKnife.inject(this, view);
        setAdapter();
        return view;
    }

    public void setAdapter(){
        materie = materiiDatabase.getMaterie(materieName);
        mListView.setDrawingListUnderStickyHeader(false);
        if (materie.getTeza() != null) {
            mAdapter = new NoteDetailAdapter(getActivity(), materie.getNoteFaraTeza(),
                    materie.getTeza());
        } else {
            mAdapter = new NoteDetailAdapter(getActivity(), materie.getNoteFaraTeza());
        }
        mAdapter.setOnDeleteListener(this);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onDeleteNota(Nota nota) {
        if(materiiDatabase.deleteNota(nota)){
            setAdapter();
            BusProvider.getInstance().post(new DataSetChangedEvent(DataSetChangedEvent.TAG_MATERIE));
        }
    }
}
