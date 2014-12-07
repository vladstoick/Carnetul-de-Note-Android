package com.stoicavlad.carnet.ui.main;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.astuetz.PagerSlidingTabStrip;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.ui.note.AddMaterieDialogFragment;
import com.stoicavlad.carnet.ui.note.AddNotaDialogFragment;
import com.stoicavlad.carnet.ui.note.NoteListFragment;
import com.stoicavlad.carnet.ui.note.detail.NoteDetailActivity;
import com.stoicavlad.carnet.ui.settings.SettingsActivity;

import java.util.Calendar;

public class MainActivity extends GeneralTabActivity
        implements NoteListFragment.OnFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] tabNames = getResources().getStringArray(R.array.tab_names);
        mSectionsPagerAdapter = new MainActivitySectionsPagerAdapter(getFragmentManager(),
                tabNames);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.add){
            showAddDialogFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddDialogFragment() {
        MaterialDialog dialog= new MaterialDialog.Builder(this)
                .title(getString(R.string.add_title))
                .items(R.array.add_options)
                .negativeText(getString(android.R.string.cancel))
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog materialDialog,
                                            View view, int position, CharSequence charSequence) {
                                switch (position) {
                                    case 0: showAddNotaDialogFragment(); break;
                                    case 1: showAddAbsentaDialogFragment(); break;
                                    case 2: showAddScutireDialogFragment(); break;
                                    case 3: showAddMaterieDialogFragment(); break;
                                    default:break;
                                }
                    }
                }).build();
        dialog.show();
    }


    private void showAddNotaDialogFragment() {
        AddNotaDialogFragment dialogFragment = new AddNotaDialogFragment();
        dialogFragment.show(getFragmentManager(), "ADD_NOTA");
    }

    private void showAddAbsentaDialogFragment() {
        DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment
                .newInstance(DatePickerDialogFragment.TYPE_ABSENTA);
        datePickerDialogFragment.show(getFragmentManager(), "ADD_ABSENTA");
    }

    private void showAddScutireDialogFragment(){
        DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment
                .newInstance(DatePickerDialogFragment.TYPE_SCUTIRE);
        datePickerDialogFragment.show(getFragmentManager(), "ADD_SCUTIRE");
    }

    private void showAddMaterieDialogFragment() {
        AddMaterieDialogFragment dialogFragment = new AddMaterieDialogFragment();
        dialogFragment.show(getFragmentManager(), "ADD_MATERIE");
    }

    //FRAGMENT INTERACTION

    @Override
    public void showNotaDetail(int materieId) {
        Intent intent = new Intent(this, NoteDetailActivity.class);
        intent.putExtra(NoteDetailActivity.TAG_MATERIE_ID, materieId);
        startActivity(intent);
    }
}
