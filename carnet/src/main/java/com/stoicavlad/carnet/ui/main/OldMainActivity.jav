package com.stoicavlad.carnet.ui.main;

import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.doomonafireball.betterpickers.datepicker.DatePickerBuilder;
import com.doomonafireball.betterpickers.datepicker.DatePickerDialogFragment;
import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.api.AbsenteDatabase;
import com.stoicavlad.carnet.data.api.MateriiDatabase;
import com.stoicavlad.carnet.data.model.Materie;
import com.stoicavlad.carnet.data.model.Nota;
import com.stoicavlad.carnet.data.otto.BusProvider;
import com.stoicavlad.carnet.data.otto.DataSetChangedEvent;
import com.stoicavlad.carnet.ui.absente.AbsentaFragment;
import com.stoicavlad.carnet.ui.general.GeneralFragment;
import com.stoicavlad.carnet.ui.materie.AddMaterieDialogFragment;
import com.stoicavlad.carnet.ui.note.AddNotaDialogFragment;
import com.stoicavlad.carnet.ui.note.NoteListFragment;
import com.stoicavlad.carnet.ui.note.detail.NoteDetailActivity;
import com.stoicavlad.carnet.ui.settings.SettingsActivity;
import com.stoicavlad.carnet.ui.utils.SimpleDialogFragment;
import java.util.Calendar;


import javax.inject.Inject;

public class OldMainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        NoteListFragment.OnFragmentInteractionListener,
        DatePickerDialogFragment.DatePickerDialogHandler {
    private boolean adaugaAbsenta = false;
    @Inject
    public
    MateriiDatabase materiiDatabase;
    @Inject
    public
    AbsenteDatabase absenteDatabase;
    private int mPosition = 0;
    private static String POSITION_NAVIGATION = "POSITION";
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BusProvider.getInstance().register(this);
        setContentView(R.layout.activity_main);
        CarnetApp.get(getApplicationContext()).inject(this);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        if(savedInstanceState != null){
            mPosition = savedInstanceState.getInt(POSITION_NAVIGATION);
        }
        onNavigationDrawerItemSelected(mPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(POSITION_NAVIGATION,mPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new GeneralFragment();
                break;
            case 1:
                fragment = new NoteListFragment();

                break;
            default:
                fragment = new AbsentaFragment();
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mNavigationDrawerFragment.mDrawerToggle.isDrawerIndicatorEnabled() &&
                mNavigationDrawerFragment.mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.add:
                showAddDialogFragment();
                break;
            case R.id.action_settings:{
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //DIALOG FRAGMENTS

    private void showAddDialogFragment() {
        AddDialogFragment dialogFragment = new AddDialogFragment();
        dialogFragment.setListener(new AddDialogFragment.AddDialogFragmentListener() {
            @Override
            public void onItemSelected(int position) {
                switch (position) {
                    case 0:
                        showAddNotaDialogFragment();
                        break;
                    case 1:
                        showAddTezaDialogFragment();
                        break;
                    case 2:
                        showAddAbsentaDialogFragment();
                        break;
                    case 3:
                        showAddScutireDialogFragment();
                        break;
                    case 4:
                        showAddMaterieDialogFragment();
                        break;
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
        adaugaAbsenta = false;
        Calendar calendar = Calendar.getInstance();
        DatePickerBuilder dpb = new DatePickerBuilder()
                .setFragmentManager(getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setYear(calendar.get(Calendar.YEAR));
        dpb.show();
    }

    @Override
    public void onDialogDateSet(int i, int i2, int i3, int i4) {

        Calendar c = Calendar.getInstance();
        c.set(i2, i3, i4);
        if (adaugaAbsenta) {
            if (absenteDatabase.addAbsenta(c)) {
                BusProvider.getInstance()
                        .post(new DataSetChangedEvent(DataSetChangedEvent.TAG_ABSENTA));
            }
        } else {
            if(absenteDatabase.addScutire(c)){
                BusProvider.getInstance()
                        .post(new DataSetChangedEvent(DataSetChangedEvent.TAG_ABSENTA));
            }
        }
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
