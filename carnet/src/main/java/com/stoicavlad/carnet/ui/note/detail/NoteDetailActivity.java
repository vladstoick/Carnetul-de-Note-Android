package com.stoicavlad.carnet.ui.note.detail;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract;

import butterknife.ButterKnife;
import butterknife.InjectView;
import icepick.Icepick;
import icepick.Icicle;

public class NoteDetailActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG_MATERIE_ID = "MATERIE_ID";
    public static final int MATERIE_LOADER = 0;
    @Icicle public int mMaterieId;
    @InjectView(R.id.medie) TextView mMedieTextView;
    @InjectView(R.id.header) LinearLayout mHeaderView;

    private float mMedieTextViewMaxSize;
    private int mMedieTextViewMaxHeight;

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

        mMedieTextViewMaxSize = mMedieTextView.getTextSize();
        mMedieTextViewMaxHeight = mMedieTextView.getMeasuredHeight();
    }



    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                this,
                CarnetContract.MaterieEntry.buildMaterieUri(mMaterieId),
                CarnetContract.MaterieEntry.COLUMNS_WITOUT_NOTE,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        cursor.moveToFirst();
        if(!cursor.isAfterLast()) {
            String name = cursor.getString(CarnetContract.MaterieEntry.COL_NAME);
            setTitle(name);
            double medie = cursor.getDouble(CarnetContract.MaterieEntry.COL_MEDIE);
            mMedieTextView.setText(String.valueOf(medie));

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
    }

}
