package com.stoicavlad.carnet.ui.note.detail;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.model.Materie;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class NoteDetailMedieFragment extends Fragment implements CheckBox.OnClickListener {

    private static final String ARG_MATERIE = "materie";
    @InjectView(R.id.list)
    StickyListHeadersListView mListView;
    CheckBox mCheckbox;
    private Materie materie;

    public NoteDetailMedieFragment() {
    }

    public static NoteDetailMedieFragment newInstance(Materie materie) {
        NoteDetailMedieFragment fragment = new NoteDetailMedieFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_detail_medie, container, false);
        ButterKnife.inject(this, view);
        mCheckbox = new CheckBox(getActivity());
        mCheckbox.setText(R.string.with_teza);
        mCheckbox.setOnClickListener(this);
        mListView.addHeaderView(mCheckbox);
        mListView.setDrawingListUnderStickyHeader(false);
        mListView.setAdapter(new NoteDetailAdapter(getActivity(), materie.getNoteFaraTeza()));
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
