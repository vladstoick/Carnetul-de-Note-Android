package com.stoicavlad.carnet.ui.main;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.doomonafireball.betterpickers.datepicker.DatePickerBuilder;
import com.doomonafireball.betterpickers.datepicker.DatePickerDialogFragment;
import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.api.MateriiDatabase;
import com.stoicavlad.carnet.data.model.Materie;
import com.stoicavlad.carnet.data.model.Nota;
import com.stoicavlad.carnet.data.provider.CarnetContract;
import com.stoicavlad.carnet.ui.note.AddMaterieDialogFragment;
import com.stoicavlad.carnet.ui.note.AddNotaDialogFragment;
import com.stoicavlad.carnet.ui.note.NoteListFragment;
import com.stoicavlad.carnet.ui.note.detail.NoteDetailActivity;
import com.stoicavlad.carnet.ui.settings.SettingsActivity;
import com.stoicavlad.carnet.ui.utils.SimpleDialogFragment;

import java.util.Calendar;

import javax.inject.Inject;

public class MainActivity extends GeneralTabActivity
        implements NoteListFragment.OnFragmentInteractionListener,
        DatePickerDialogFragment.DatePickerDialogHandler{

    private boolean adaugaAbsenta = false;
    @Inject
    public
    MateriiDatabase materiiDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        CarnetApp.get(getApplicationContext()).inject(this);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        String[] tabNames = getResources().getStringArray(R.array.tab_names);
        mSectionsPagerAdapter = new MainActivitySectionsPagerAdapter(getSupportFragmentManager(),
                tabNames);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        AddDialogFragment dialogFragment = new AddDialogFragment();
        dialogFragment.setListener(new AddDialogFragment.AddDialogFragmentListener() {
            @Override
            public void onItemSelected(int position) {
                switch (position) {
                    case 0: showAddNotaDialogFragment(); break;
                    case 1: showAddTezaDialogFragment(); break;
                    case 2: showAddAbsentaDialogFragment(); break;
                    case 3: showAddScutireDialogFragment(); break;
                    case 4: showAddMaterieDialogFragment(); break;
                    default:
                }
            }
        });
        dialogFragment.show(getSupportFragmentManager(), "ADD");
    }


    private void showAddNotaDialogFragment() {
        AddNotaDialogFragment dialogFragment = AddNotaDialogFragment
                .newInstance(Nota.TIP_NOTA_SIMPLA);
        dialogFragment.show(getSupportFragmentManager(), "ADD_NOTA");
    }

    private void showAddTezaDialogFragment() {
        if (materiiDatabase.getMateriiFaraTeza().length > 0) {
            AddNotaDialogFragment dialogFragment = AddNotaDialogFragment
                    .newInstance(Nota.TIP_NOTA_TEZA);
            dialogFragment.show(getSupportFragmentManager(), "ADD_NOTA");
        } else {
            SimpleDialogFragment dialogFragment =
                    SimpleDialogFragment.newInstance(getString(R.string.add_teza_full));
            dialogFragment.show(getSupportFragmentManager(), "WARNING");
        }

    }

    private void showAddAbsentaDialogFragment() {
        adaugaAbsenta = true;
        Calendar calendar = Calendar.getInstance();
        DatePickerBuilder dpb = new DatePickerBuilder()
                .setFragmentManager(getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setYear(calendar.get(Calendar.YEAR))
                .setDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH))
                .setMonthOfYear(calendar.get(Calendar.MONTH));
        dpb.show();
    }

    private void showAddScutireDialogFragment(){
        Calendar calendar = Calendar.getInstance();
        DatePickerBuilder dpb = new DatePickerBuilder()
                .setFragmentManager(getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setYear(calendar.get(Calendar.YEAR));
        dpb.show();
    }

    @Override
    public void onDialogDateSet(int i, int year, int month, int day) {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(0);
        c.set(year, month, day);
        long date = c.getTimeInMillis();
        if (adaugaAbsenta) {
            ContentValues absentaValues = new ContentValues();
            absentaValues.put(CarnetContract.AbsentaEntry.COLUMN_DATE, c.getTimeInMillis());
            getApplicationContext().getContentResolver()
                    .insert(CarnetContract.AbsentaEntry.CONTENT_URI, absentaValues);

        } else {
            String dateInString = date + "";
            getApplicationContext().getContentResolver()
                    .delete(CarnetContract.AbsentaEntry.CONTENT_URI,
                            CarnetContract.AbsentaEntry.COLUMN_DATE + " = ?",
                            new String[]{ dateInString } );
        }
        adaugaAbsenta = false;
    }


    private void showAddMaterieDialogFragment() {
        AddMaterieDialogFragment dialogFragment = new AddMaterieDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "ADD_MATERIE");
    }

    //FRAGMENT INTERACTION

    @Override
    public void showNotaDetailFragment(Materie materie) {
        Intent intent = new Intent(this, NoteDetailActivity.class);
        intent.putExtra(NoteDetailActivity.TAG_MATERIE, materie.id);
        startActivity(intent);
    }
}
