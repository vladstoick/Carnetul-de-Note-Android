package com.stoicavlad.carnet.ui.note.detail;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.Locale;

/**
 * Created by Vlad on 02-Aug-14.
 */
public class NoteDetailSectionsPagerAdapter extends FragmentPagerAdapter {

    private final int mMaterieId;
    private final String[] mTabNames;

    public NoteDetailSectionsPagerAdapter(FragmentManager fm, int materieId, String[] tabNames) {
        super(fm);
        this.mMaterieId = materieId;
        this.mTabNames = tabNames;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return NoteDetailFragment.newInstance(mMaterieId);
        }
        return NoteDetailMedieFragment.newInstance(mMaterieId);
    }

    @Override
    public int getCount() {
        return mTabNames.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        return mTabNames[position].toUpperCase(l);
    }
}