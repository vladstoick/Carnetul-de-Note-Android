package com.stoicavlad.carnet.ui.main;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.UtilityMaterie;
import com.stoicavlad.carnet.data.provider.CarnetContract;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class GeneralFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    @InjectView(R.id.medieGenerala) public TextView mMedieGenerala;
    @InjectView(R.id.purtare) public TextView mPurtare;
    @InjectView(R.id.absente) public TextView mAbsente;
    @InjectView(R.id.editButton) public ImageButton mEditButton;

    public static String PURTARE_TAG = "purtare";

    private final static int ABSENTE_LOADER = 0;
    private final static int MATERIE_LOADER = 1;
    private Cursor mGeneralCursor;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(ABSENTE_LOADER, null, this);
        getLoaderManager().initLoader(MATERIE_LOADER, null, this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_general, container, false);
        ButterKnife.inject(this, rootView);
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPurtareDialogFragment dialogFragment = new ModifyPurtareDialogFragment();
                dialogFragment.setOnPurtareChangedListener(new ModifyPurtareDialogFragment.OnPurtareChangedInterface() {
                    @Override
                    public void purtareDidChanged() {
                        setMedieUI();
                    }
                });
                dialogFragment.show(getFragmentManager(), "DF");
            }
        });
        return rootView;
    }

    void setMedieUI() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        int purtare = preferences.getInt("PURTARE_TAG",10);
        double medie = UtilityMaterie.getMedieGeneralaFromCursor(mGeneralCursor, purtare);
        mMedieGenerala.setText(String.valueOf(medie));
        mPurtare.setText(getString(R.string.purtare) + ": " + purtare);
        mMedieGenerala.setText(UtilityMaterie.getTwoDecimalsFromMaterie(medie));
        if (medie < 4.5) {
            mMedieGenerala.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else {
            mMedieGenerala.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }

    }

    private void setAbsenteUI() {
        int absente = mGeneralCursor.getCount();
        SharedPreferences sharedPreferences = getActivity()
                .getPreferences(Context.MODE_PRIVATE);
        int absenteNecesare = Integer
                .valueOf(sharedPreferences.getString("pref_maxim_absente", "10"));
        if (this.isAdded()) {
            mAbsente.setText(absente + "");
            if (absente >= absenteNecesare) {
                mAbsente.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            } else {
                mAbsente.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            }
        }
    }

    //LOADER

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if(i==ABSENTE_LOADER){
            return new CursorLoader(
                    getActivity(),
                    CarnetContract.AbsentaEntry.CONTENT_URI,
                    null, null, null, null
            );
        } else if (i== MATERIE_LOADER){
            return new CursorLoader(
                    getActivity(),
                    CarnetContract.MaterieEntry.CONTENT_URI,
                    CarnetContract.MaterieEntry.COLUMNS_MEDIE,
                    null, null, null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if(cursor!=null) {
            mGeneralCursor = cursor;
            if (cursorLoader.getId() == ABSENTE_LOADER) {
                setAbsenteUI();
            } else if (cursorLoader.getId() == MATERIE_LOADER) {
                setMedieUI();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
}
