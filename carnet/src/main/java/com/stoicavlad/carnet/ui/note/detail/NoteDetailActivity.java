package com.stoicavlad.carnet.ui.note.detail;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.api.MateriiDatabase;
import com.stoicavlad.carnet.data.model.Materie;

import java.util.Locale;

import javax.inject.Inject;

public class NoteDetailActivity extends FragmentActivity implements ActionBar.TabListener {

    public static final String TAG_MATERIE = "MATERIE";
    @Inject
    MateriiDatabase materiiDatabase;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private int materie_id;
    private Materie materie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CarnetApp.get(getApplicationContext()).inject(this);
        if (savedInstanceState == null) {
            if (getIntent().hasExtra(TAG_MATERIE)) {
                materie_id = getIntent().getIntExtra(TAG_MATERIE, -1);
                materie = materiiDatabase.getMaterie(materie_id);
                setTitle(materie.name);
            }
        }
        setContentView(R.layout.activity_note_detail);
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return NoteDetailFragment.newInstance(materie.id);
            }
            return NoteDetailMedieFragment.newInstance(materie.id);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.note).toUpperCase(l);
                case 1:
                    return getString(R.string.medie).toUpperCase(l);
            }
            return null;
        }
    }


}
