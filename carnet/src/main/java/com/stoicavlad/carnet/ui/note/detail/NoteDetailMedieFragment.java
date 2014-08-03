package com.stoicavlad.carnet.ui.note.detail;


import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.stoicavlad.carnet.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NoteDetailMedieFragment extends Fragment implements CheckBox.OnClickListener,
        EditText.OnEditorActionListener {

    private static final String ARG_MATERIE = "materie";
    private int materieId;

    @InjectView(R.id.list)
    public ListView mListView;
    private CheckBox mCheckbox;
    private EditText mEditText;
    private TextView mMediaCurenta;
    private NoteDetailMedieAdapter mAdapter;


    public NoteDetailMedieFragment() {
    }

    public static NoteDetailMedieFragment newInstance(int materieId) {
        NoteDetailMedieFragment fragment = new NoteDetailMedieFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MATERIE, materieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.materieId = getArguments().getInt(ARG_MATERIE,-1);
            //TODO
            //materie = materiiDatabase.getMaterie(mMaterieId);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_detail_medie, container, false);
        ButterKnife.inject(this, view);
        View header = View.inflate(getActivity(), R.layout.fragment_note_detail_medie_header, null);
        mCheckbox = (CheckBox) header.findViewById(R.id.checkBox);
        mCheckbox.setOnClickListener(this);
        mEditText = (EditText) header.findViewById(R.id.editText);
        mEditText.setOnEditorActionListener(this);
        mMediaCurenta = (TextView) header.findViewById(R.id.media_curenta);
        mListView.addHeaderView(header);
        setUI();
        return view;
    }

    private void setUI() {
        //TODO
//        try {
//            //checkbox
//            if (materie.getTeza() != null) {
//                mCheckbox.setVisibility(View.GONE);
//            } else {
//                mCheckbox.setVisibility(View.VISIBLE);
//            }
//            //media curenta
//            double medie = materie.getMedie();
//            mMediaCurenta.setText(getString(R.string.medie_curenta) + " : " + medie);
//            if (medie < 4.50) {
//                mMediaCurenta.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
//            } else {
//                mMediaCurenta.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
//            }
//            setAdapter(1);
//        } catch (IllegalStateException e) {
//            //do nothin yet;
//        }
    }

//    VariantaMedie[] getVariantaMedie(int medie, int nrNote) {
//        if (materie.getTeza() != null) {
//            VariantaMedie rez = new VariantaMedie(medie, materie, nrNote, materie.getTeza().nota);
//            return new VariantaMedie[]{rez};
//        } else if (mCheckbox.isChecked()) {
//            return VariantaMedie.getVarianteTeza(medie, materie, nrNote);
//        } else {
//            VariantaMedie rez = new VariantaMedie(medie, materie, nrNote);
//            return new VariantaMedie[]{rez};
//        }
//    }
//
//    void setAdapter(int nrNote) {
//        ArrayList<VariantaMedie> varianteMedie = new ArrayList<VariantaMedie>();
//        int medie = (int) Math.ceil(materie.getMedie());
//        if (medie < 5) {
//            medie = 5;
//        }
//        for (int i = medie; i <= 10; i++) {
//            VariantaMedie[] varArrray = getVariantaMedie(i, nrNote);
//            for (VariantaMedie variantaMedie : varArrray) {
//                if (variantaMedie.posibil) {
//                    varianteMedie.add(variantaMedie);
//                }
//            }
//        }
//        VariantaMedie[] variantaMediiArray =
//                varianteMedie.toArray(new VariantaMedie[varianteMedie.size()]);
//        mAdapter = new NoteDetailMedieAdapter(getActivity(), variantaMediiArray);
//        mListView.setAdapter(mAdapter);
//    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.checkBox) {
            //TODO
//            setAdapter(Integer.parseInt(mEditText.getText().toString()));
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        int nrNote = Integer.parseInt(v.getText().toString());
        //TODO
//        setAdapter(nrNote);
        return false;
    }
}

