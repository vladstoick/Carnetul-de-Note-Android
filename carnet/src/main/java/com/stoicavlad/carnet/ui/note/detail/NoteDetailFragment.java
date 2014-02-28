package com.stoicavlad.carnet.ui.note.detail;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    int materieId;
    @InjectView(R.id.list)
    StickyListHeadersListView mListView;
    NoteDetailAdapter mAdapter;

    public NoteDetailFragment() {
        // Required empty public constructor
    }

    public static NoteDetailFragment newInstance(int materieId) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_MATERIE, materieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        CarnetApp.get(getActivity()).inject(this);
        if (getArguments() != null) {
            materieId = getArguments().getInt(ARG_MATERIE);

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
        Materie materie = materiiDatabase.getMaterie(materieId);
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
