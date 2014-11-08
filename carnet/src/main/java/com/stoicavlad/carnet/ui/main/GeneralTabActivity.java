package com.stoicavlad.carnet.ui.main;

import android.annotation.SuppressLint;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;


/**
 * Created by Vlad on 30-Jul-14.
 */
@SuppressLint("Registered")
public class GeneralTabActivity extends ActionBarActivity implements ActionBar.TabListener {

    ViewPager mViewPager;
    FragmentPagerAdapter mSectionsPagerAdapter;

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
}
