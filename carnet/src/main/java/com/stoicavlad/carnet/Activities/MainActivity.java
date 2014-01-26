package com.stoicavlad.carnet.Activities;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import com.stoicavlad.carnet.dialogFragments.AddNotaDialogFragment;
import com.stoicavlad.carnet.fragments.NavigationDrawerFragment;
import com.stoicavlad.carnet.fragments.NoteFragment;
import com.stoicavlad.carnet.R;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        NoteFragment.OnFragmentInteractionListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    /**
     * Part of @{NoteFragment.OnFragmentInteractionListner}
     * Shows a AddNotaDialogFragment
     */
    @Override
    public void showAddNotaDialogFragment() {
        DialogFragment newFragment = new AddNotaDialogFragment();
        newFragment.show(getSupportFragmentManager(), "dialog");
    }
}
