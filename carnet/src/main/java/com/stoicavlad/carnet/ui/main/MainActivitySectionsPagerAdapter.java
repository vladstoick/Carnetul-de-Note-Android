package com.stoicavlad.carnet.ui.main;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.stoicavlad.carnet.ui.absente.AbsentaFragment;
import com.stoicavlad.carnet.ui.note.NoteListFragment;

import java.util.Locale;

/**
 * Created by Vlad on 30-Jul-14.
 */
public class MainActivitySectionsPagerAdapter extends FragmentPagerAdapter {

    String[] tabNames;

    public MainActivitySectionsPagerAdapter(FragmentManager fm,String[] tabNames) {
        super(fm);
        this.tabNames = tabNames;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new GeneralFragment();
            case 1: return new NoteListFragment();
            case 2: return new AbsentaFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        return tabNames[position].toUpperCase(l);
    }
}

