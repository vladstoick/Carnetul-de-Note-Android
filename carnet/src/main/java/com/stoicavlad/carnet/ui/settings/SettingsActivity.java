package com.stoicavlad.carnet.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract;
import com.stoicavlad.carnet.ui.setup.SetupActivity;

@SuppressWarnings("deprecation")
public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        if (preference instanceof ListPreference) {
            this.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        } else if (preference instanceof ConfirmPreference) {
            ((ConfirmPreference) preference).setValue(false);
            this.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getBoolean(preference.getKey(), false));
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {

        if (preference instanceof ListPreference) {
            String stringValue = value.toString();
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(stringValue);
            preference.setSummary(
                    index >= 0
                            ? listPreference.getEntries()[index]
                            : null);

        } else if (preference instanceof ConfirmPreference) {
            if(!(Boolean)value){
                return true;
            }
            if ("pref_reseteaza_note".equals(preference.getKey())) {
                //TODO
//                if (materiiDatabase.deleteAllNote()) {
//                    BusProvider.getInstance()
//                            .post(new DataSetChangedEvent(DataSetChangedEvent.TAG_MATERIE));
//                }
            } else if("pref_reseteaza_absente".equals(preference.getKey())){
                getApplicationContext().getContentResolver()
                        .delete(CarnetContract.AbsentaEntry.CONTENT_URI,null,null);
            } else if("pref_reseteaza_full".equals(preference.getKey())){
                getApplicationContext().getContentResolver()
                        .delete(CarnetContract.AbsentaEntry.CONTENT_URI,null,null);
                //TODO
//                materiiDatabase.deleteAllMaterii();
                Intent intent = new Intent(this, SetupActivity.class);
                SharedPreferences settings =
                        getSharedPreferences("appPref", Context.MODE_PRIVATE);
                settings.edit().remove("SETUP_DONE").commit();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        bindPreferenceSummaryToValue(findPreference("pref_maxim_absente"));
        bindPreferenceSummaryToValue(findPreference("pref_reseteaza_note"));
        bindPreferenceSummaryToValue(findPreference("pref_reseteaza_absente"));
        bindPreferenceSummaryToValue(findPreference("pref_reseteaza_full"));
    }

}
