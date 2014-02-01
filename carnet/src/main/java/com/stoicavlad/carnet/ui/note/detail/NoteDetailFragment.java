package com.stoicavlad.carnet.ui.note.detail;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.model.Materie;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class NoteDetailFragment extends Fragment {

    private static final String ARG_MATERIE = "materie";
    @InjectView(R.id.list)
    StickyListHeadersListView mListView;
    NoteDetailAdapter mAdapter;
    private Materie materie;

    public NoteDetailFragment() {
        // Required empty public constructor
    }

    public static NoteDetailFragment newInstance(Materie materie) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MATERIE, materie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            materie = getArguments().getParcelable(ARG_MATERIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_detail, container, false);
        ButterKnife.inject(this, view);
        mListView.setDrawingListUnderStickyHeader(false);
        if (materie.getTeza() != null) {
            mAdapter = new NoteDetailAdapter(getActivity(), materie.getNoteFaraTeza(),
                    materie.getTeza());
        } else {
            mAdapter = new NoteDetailAdapter(getActivity(), materie.getNoteFaraTeza());
        }
        mListView.setAdapter(mAdapter);
        return view;
    }


}
