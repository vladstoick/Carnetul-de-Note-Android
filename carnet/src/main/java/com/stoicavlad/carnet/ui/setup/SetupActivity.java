package com.stoicavlad.carnet.ui.setup;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.crashlytics.android.Crashlytics;
import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.api.MateriiDatabase;
import com.stoicavlad.carnet.data.provider.CarnetContract;
import com.stoicavlad.carnet.ui.main.MainActivity;
import com.stoicavlad.carnet.ui.utils.SimpleDialogFragment;

import java.util.ArrayList;

import javax.inject.Inject;

public class SetupActivity extends FragmentActivity implements SetupFragment.OnFragmentInteractionListener {

    @Inject
    public
    MateriiDatabase materiiDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);

        CarnetApp.get(getApplicationContext()).inject(this);
        SharedPreferences settings = getSharedPreferences("appPref", Context.MODE_PRIVATE);
        if(settings.contains("SETUP_DONE")){
            gotoMainActivity();
        }
        setContentView(R.layout.activity_setup);
        if (savedInstanceState == null) {
            SetupFragment setupFragment = new SetupFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, setupFragment)
                    .commit();
        }
        SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment
                .newInstance(getString(R.string.materii_setup));
        simpleDialogFragment.show(getSupportFragmentManager(),"TAG");
    }

    @Override
    public void onNextSelected(ArrayList<String> materiiDeAdaugat) {
        ArrayList<ContentValues> materieValuesList = new ArrayList<ContentValues>();
        for(String name:materiiDeAdaugat){
            ContentValues materieValue = new ContentValues();
            materieValue.put(CarnetContract.MaterieEntry.COLUMN_NAME,name);
            materieValue.put(CarnetContract.MaterieEntry.COLUMN_TEZA,0);
            materieValuesList.add(materieValue);
        }
        ContentValues[] materieValuesArray = materieValuesList
                .toArray(new ContentValues[materieValuesList.size()]);
        getApplicationContext().getContentResolver()
                .bulkInsert(CarnetContract.MaterieEntry.CONTENT_URI, materieValuesArray);
        SharedPreferences settings = getSharedPreferences("appPref", Context.MODE_PRIVATE);
        settings.edit().putString("SETUP_DONE","true").apply();
        gotoMainActivity();

    }

    void gotoMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

