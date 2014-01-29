package com.stoicavlad.carnet.ui.note;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.model.Materie;

public class NoteDetailFragment extends Fragment {

    private static final String ARG_MATERIE = "materie";


    private Materie materie;

    public static NoteDetailFragment newInstance(Materie materie) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MATERIE, materie);

        fragment.setArguments(args);
        return fragment;
    }

    public NoteDetailFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_note_detail, container, false);
    }


}
