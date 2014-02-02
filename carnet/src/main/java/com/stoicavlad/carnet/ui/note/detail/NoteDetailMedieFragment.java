package com.stoicavlad.carnet.ui.note.detail;


import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.model.Materie;
import com.stoicavlad.carnet.data.model.VariantaMedie;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class NoteDetailMedieFragment extends Fragment implements CheckBox.OnClickListener,
EditText.OnEditorActionListener{

    private static final String ARG_MATERIE = "materie";
    @InjectView(R.id.list)
    StickyListHeadersListView mListView;
    CheckBox mCheckbox;
    EditText mEditText;
    NoteDetailMedieAdapter mAdapter;
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
        View header = View.inflate(getActivity(), R.layout.fragment_note_detail_medie_header, null);
        mCheckbox = (CheckBox) header.findViewById(R.id.checkBox);
        mEditText = (EditText) header.findViewById(R.id.editText);
        mEditText.setOnEditorActionListener(this);
        mListView.addHeaderView(header);
        mListView.setDrawingListUnderStickyHeader(false);
        setAdapter(1);
        return view;
    }

    public void setAdapter(int nrNote) {
        ArrayList<VariantaMedie> varianteMedie = new ArrayList<VariantaMedie>();
        int medie = (int) Math.ceil(materie.getMedie());
        for (int i = medie; i <= 10; i++) {
            VariantaMedie variantaMedie = new VariantaMedie(i,materie,nrNote);
            if(variantaMedie.posibil){
                varianteMedie.add(variantaMedie);
            }
        }
        VariantaMedie[] variantaMediiArray =
                varianteMedie.toArray(new VariantaMedie[varianteMedie.size()]);
        mAdapter = new NoteDetailMedieAdapter(getActivity(), variantaMediiArray);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        int nrNote = Integer.parseInt(v.getText().toString());
        setAdapter(nrNote);
        return false;
    }
}

