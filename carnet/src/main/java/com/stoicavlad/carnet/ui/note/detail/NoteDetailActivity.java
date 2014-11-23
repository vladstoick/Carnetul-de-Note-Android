package com.stoicavlad.carnet.ui.note.detail;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.UtilityMaterie;
import com.stoicavlad.carnet.data.provider.CarnetContract;
import com.stoicavlad.carnet.ui.note.AddTezaDialogFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import icepick.Icepick;
import icepick.Icicle;

public class NoteDetailActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG_MATERIE_ID = "MATERIE_ID";
    public static final int MATERIE_LOADER = 0;

    @Icicle public int mMaterieId;
    private String mMaterieName;

    @InjectView(R.id.medie) TextView mMedieTextView;
    @InjectView(R.id.teza) TextView mTezaTextView;
    @InjectView(R.id.modifyTeza) ImageButton mModifyTezaImageButton;
    @InjectView(R.id.deleteTeza) ImageButton mDeleteTezaImageButton;
    @InjectView(R.id.seperator) View mSeparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        ButterKnife.inject(this);

        if (savedInstanceState == null) {
            if (getIntent().hasExtra(TAG_MATERIE_ID)) {
                mMaterieId = getIntent().getIntExtra(TAG_MATERIE_ID, -1);
            }
        } else {
            Icepick.restoreInstanceState(this,savedInstanceState);
        }

        getLoaderManager().initLoader(MATERIE_LOADER, null, this);

        String[] tabNames = {getString(R.string.note), getString(R.string.medie)};
        NoteDetailSectionsPagerAdapter pagerAdapter =
                new NoteDetailSectionsPagerAdapter(getFragmentManager(), mMaterieId, tabNames);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);



        mDeleteTezaImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues materieValues = new ContentValues();
                materieValues.put(CarnetContract.MaterieEntry.COLUMN_TEZA, 0);
                getContentResolver().update(CarnetContract.MaterieEntry.buildMaterieUri(mMaterieId),
                                materieValues, null, null);
            }
        });

    }



    @Override public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                this,
                CarnetContract.MaterieEntry.buildMaterieUri(mMaterieId),
                CarnetContract.MaterieEntry.COLUMNS_DETAIL,
                null,
                null,
                null
        );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        cursor.moveToFirst();
        if(!cursor.isAfterLast()) {

            final String name = cursor.getString(CarnetContract.MaterieEntry.COL_DETAIL_NAME);

            mModifyTezaImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DialogFragment addTezaDialogFragment = AddTezaDialogFragment
                            .newInstance(mMaterieId, name);
                    addTezaDialogFragment.show(getFragmentManager(),"ADD_TEZA");
                }
            });

            int teza = cursor.getInt(CarnetContract.MaterieEntry.COL_DETAIL_TEZA);
            if(teza == 0) {
                mTezaTextView.setText(getString(R.string.teza) + ": -");
                mModifyTezaImageButton.setImageResource(R.drawable.ic_action_content_new_dark);
                mDeleteTezaImageButton.setVisibility(View.GONE);
                mSeparator.setVisibility(View.GONE);
            } else {
                mTezaTextView.setText(getString(R.string.teza) + ": " + String.valueOf(teza));
                mModifyTezaImageButton.setImageResource(R.drawable.ic_action_edit);
                mDeleteTezaImageButton.setVisibility(View.VISIBLE);
                mSeparator.setVisibility(View.VISIBLE);
            }

            double medieNote = cursor.getDouble(CarnetContract.MaterieEntry.COL_DETAIL_MEDIE);

            double medie = UtilityMaterie.getMedieForMaterie(teza, medieNote);
            if(medie > 0) {
                mMedieTextView.setText(UtilityMaterie.getTwoDecimalsFromMaterie(medie));
            } else {
                mMedieTextView.setText("-");
            }

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
    }

}
