package com.stoicavlad.carnet.ui.main;

import android.app.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.MateriiDatabase;
import com.stoicavlad.carnet.data.Nota;
import com.stoicavlad.carnet.ui.materie.AddMaterieDialogFragment;
import com.stoicavlad.carnet.ui.note.AddNotaDialogFragment;
import com.stoicavlad.carnet.ui.note.NoteFragment;
import com.stoicavlad.carnet.ui.utils.SimpleDialogFragment;

import javax.inject.Inject;
import com.doomonafireball.betterpickers.*;
public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    @Inject MateriiDatabase materiiDatabase;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new NoteFragment();
//        switch (position){
//            case 0: fragment = new NoteFragment();
//            default: fragment= PlaceholderFragment.newInstance(position+1);
//        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mNavigationDrawerFragment.isDrawerOpen()==false){
            getMenuInflater().inflate(R.menu.main,menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:{
                showAddDialogFragment();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Shows and AddDialogFragment
     * After a user selects an option another showDialogFunction is called
     */
    private void showAddDialogFragment(){
        AddDialogFragment dialogFragment = new AddDialogFragment();
        dialogFragment.setListener(new AddDialogFragment.AddDialogFragmentListener() {
            @Override
            public void onItemSelected(int position) {
                switch (position){
                    case 0: showAddNotaDialogFragment(); break;
                    case 1: showAddTezaDialogFragment(); break;
                    case 2: showAddAbsentaDialogFragment(); break;
                    case 3: showAddTemaDialogFragment(); break;
                    case 4: showAddMaterieDialogFragment(); break;
                    default:
                }
            }
        });
        dialogFragment.show(getSupportFragmentManager(),"ADD");
    }

    /**
     * Shows an AddNotaDialogFragment
     */
    private void showAddNotaDialogFragment(){
        AddNotaDialogFragment dialogFragment = new AddNotaDialogFragment(Nota.TIP_NOTA_SIMPLA);
        dialogFragment.show(getSupportFragmentManager(),"ADD_NOTA");
    }
    /**
     * Shows an AddTezaDialogFragment
     */
    private void showAddTezaDialogFragment(){
        if(materiiDatabase.getMateriiFaraTeza().length > 0 ){
            AddNotaDialogFragment dialogFragment = new AddNotaDialogFragment(Nota.TIP_NOTA_TEZA);
            dialogFragment.show(getSupportFragmentManager(),"ADD_NOTA");
        } else {
            SimpleDialogFragment dialogFragment =
                    new SimpleDialogFragment(getString(R.string.add_teza_full));
            dialogFragment.show(getSupportFragmentManager(),"WARNING");
        }

    }
    /**
     * Shows an AddAbsentaDialogFragment
     */
    private void showAddAbsentaDialogFragment(){

    }
    /**
     * Shows an AddTemaDialogFragment
     */
    private void showAddTemaDialogFragment(){

    }
    /**
     * Shows an AddMaterieDialogFragment
     */
    private void showAddMaterieDialogFragment(){
        AddMaterieDialogFragment dialogFragment = new AddMaterieDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "ADD_MATERIE");
    }
}
