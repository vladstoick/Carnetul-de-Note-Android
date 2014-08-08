package com.stoicavlad.carnet.ui.note.detail;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.VariantaMedie;
import com.stoicavlad.carnet.data.provider.CarnetContract;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class NoteDetailMedieFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String ARG_MATERIE = "materie";
    private static final int MATERIE_LOADER = 0;

    private int mMaterieId;
    private Cursor mMaterieCursor;

    @InjectView(R.id.list) public StickyListHeadersListView mListView;
    private CheckBox mCheckbox;
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
            this.mMaterieId = getArguments().getInt(ARG_MATERIE,-1);

            getLoaderManager().initLoader(MATERIE_LOADER, null, this);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_detail_medie, null, false);

        ButterKnife.inject(this, view);

        View header = View.inflate(getActivity(), R.layout.fragment_note_detail_medie_header, null);

        mCheckbox = (CheckBox) header.findViewById(R.id.checkBox);
        mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setAdapter();
            }
        });

        mListView.addHeaderView(header);
        mListView.setDrawingListUnderStickyHeader(false);
        mListView.setAreHeadersSticky(false);

        mAdapter = new NoteDetailMedieAdapter(getActivity());
        mListView.setAdapter(mAdapter);


        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                getActivity(),
                CarnetContract.MaterieEntry.buildMaterieUri(mMaterieId),
                CarnetContract.MaterieEntry.COLUMNS_DETAIL_MEDIE,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mMaterieCursor = cursor;
        setAdapter();
    }

    private void setAdapter() {
        mMaterieCursor.moveToFirst();
        int sumaNote = mMaterieCursor.getInt(CarnetContract.MaterieEntry.COL_DETAIL_MEDIE_SUMA);
        int nrNote = mMaterieCursor.getInt(CarnetContract.MaterieEntry.COL_DETAIL_MEDIE_COUNT_NOTE);
        int teza = mMaterieCursor.getInt(CarnetContract.MaterieEntry.COL_DETAIL_MEDIE_TEZA);
        //TODO ce se intampla daca nu exista note
        if(teza == 0){
            mCheckbox.setVisibility(View.VISIBLE);
            if(mCheckbox.isChecked()){
                ArrayList<VariantaMedie> variante = VariantaMedie
                        .getVarianteMedieWithPossibleTeza(sumaNote, nrNote);
                mAdapter.setVariante(variante);
            } else {
                ArrayList<VariantaMedie> variante = VariantaMedie
                        .getVarianteMedieWithoutTeza(sumaNote, nrNote);
                mAdapter.setVariante(variante);
            }
        } else {
            ArrayList<VariantaMedie> variante = VariantaMedie
                    .getVarianteMedieWithTeza(sumaNote, nrNote, teza);
            mAdapter.setVariante(variante);
            mCheckbox.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.setVariante(null);
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


}

