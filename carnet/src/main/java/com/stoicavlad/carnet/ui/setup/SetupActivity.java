package com.stoicavlad.carnet.ui.setup;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.crashlytics.android.Crashlytics;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract;
import com.stoicavlad.carnet.ui.main.MainActivity;

import java.util.ArrayList;

public class SetupActivity extends ActionBarActivity
        implements SetupFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);

        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        if(settings.contains("SETUP_DONE_V2")){
            gotoMainActivity();
        }
        setContentView(R.layout.activity_setup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            SetupFragment setupFragment = new SetupFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, setupFragment)
                    .commit();
        }

        showDialog();
    }

    @Override
    public void onNextSelected(ArrayList<String> materiiDeAdaugat) {
        ArrayList<ContentValues> materieValuesList = new ArrayList<ContentValues>();
        for(String name:materiiDeAdaugat){
            ContentValues materieValue = new ContentValues();
            materieValue.put(CarnetContract.MaterieEntry.COLUMN_NAME,name);
            materieValuesList.add(materieValue);
        }
        ContentValues[] materieValuesArray = materieValuesList
                .toArray(new ContentValues[materieValuesList.size()]);
        getApplicationContext().getContentResolver()
                .bulkInsert(CarnetContract.MaterieEntry.CONTENT_URI, materieValuesArray);
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        settings.edit().putString("SETUP_DONE_V2","true").apply();
        gotoMainActivity();

    }

    private void gotoMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showDialog(){
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .content(getString(R.string.materii_setup))
                .positiveText(getString(android.R.string.ok))
                .build();
        dialog.show();

    }

}

