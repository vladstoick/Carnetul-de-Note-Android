package com.stoicavlad.carnet.ui.main;

import android.content.Intent;
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
import com.stoicavlad.carnet.ui.materie.AddMaterieDialogFragment;
import com.stoicavlad.carnet.ui.note.AddNotaDialogFragment;
import com.stoicavlad.carnet.ui.note.NoteListFragment;
import com.stoicavlad.carnet.ui.note.detail.NoteDetailActivity;
import com.stoicavlad.carnet.ui.utils.SimpleDialogFragment;

import java.util.Calendar;

import javax.inject.Inject;

public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        NoteListFragment.OnFragmentInteractionListener,
        DatePickerDialogFragment.DatePickerDialogHandler {
    @Inject
    MateriiDatabase materiiDatabase;
    @Inject
    AbsenteDatabase absenteDatabase;
    private int mStackLevel = 0;
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
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new NoteListFragment();
                break;
            case 2:
                fragment = new AbsentaFragment();
                break;
            default:
                fragment = new NoteListFragment();
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        mStackLevel = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mNavigationDrawerFragment.isDrawerOpen() == false) {
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
            case android.R.id.home: {
                popFragment();
                return true;

            }
        }
        return super.onOptionsItemSelected(item);
    }

    //FRAGMENT MANAGMENT

    public void addFragment(Fragment fragment, boolean addToBack) {
        if (addToBack) {
            modifyStackLevelBy(1);
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    public void popFragment() {
        modifyStackLevelBy(-1);
        getSupportFragmentManager().popBackStack();
    }

    public void modifyStackLevelBy(int x) {
        mStackLevel = mStackLevel + x >= 0 ? mStackLevel - x : 0;
        mNavigationDrawerFragment.mDrawerToggle.setDrawerIndicatorEnabled(mStackLevel == 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        modifyStackLevelBy(-1);
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
                        showAddTemaDialogFragment();
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
        AddNotaDialogFragment dialogFragment = new AddNotaDialogFragment(Nota.TIP_NOTA_SIMPLA);
        dialogFragment.show(getSupportFragmentManager(), "ADD_NOTA");
    }

    private void showAddTezaDialogFragment() {
        if (materiiDatabase.getMateriiFaraTeza().length > 0) {
            AddNotaDialogFragment dialogFragment = new AddNotaDialogFragment(Nota.TIP_NOTA_TEZA);
            dialogFragment.show(getSupportFragmentManager(), "ADD_NOTA");
        } else {
            SimpleDialogFragment dialogFragment =
                    new SimpleDialogFragment(getString(R.string.add_teza_full));
            dialogFragment.show(getSupportFragmentManager(), "WARNING");
        }

    }

    private void showAddAbsentaDialogFragment() {
        Calendar calendar = Calendar.getInstance();
        DatePickerBuilder dpb = new DatePickerBuilder()
                .setFragmentManager(getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setYear(calendar.get(calendar.YEAR))
                .setDayOfMonth(calendar.get(calendar.DAY_OF_MONTH))
                .setMonthOfYear(calendar.get(calendar.MONTH));
        dpb.show();
    }

    @Override
    public void onDialogDateSet(int i, int i2, int i3, int i4) {
        Calendar c = Calendar.getInstance();
        c.set(i2, i3, i4);
        if (absenteDatabase.addAbsenta(c)) {
            BusProvider.getInstance()
                    .post(new DataSetChangedEvent(DataSetChangedEvent.TAG_ABSENTA));
        }
    }

    private void showAddTemaDialogFragment() {

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
