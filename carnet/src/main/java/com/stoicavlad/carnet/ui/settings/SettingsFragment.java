package com.stoicavlad.carnet.ui.settings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract;
import com.stoicavlad.carnet.ui.setup.SetupActivity;

import java.util.prefs.PreferenceChangeListener;


public class SettingsFragment extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener{

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        bindPreferenceSummaryToValue(findPreference("pref_maxim_absente"));
        bindPreferenceSummaryToValue(findPreference("pref_reseteaza_materii"));
        bindPreferenceSummaryToValue(findPreference("pref_reseteaza_absente"));
        bindPreferenceSummaryToValue(findPreference("pref_reseteaza_full"));
    }

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


    @SuppressLint("CommitPrefEdits")
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
            if ("pref_reseteaza_materii".equals(preference.getKey())) {
                getActivity().getContentResolver()
                        .delete(CarnetContract.MaterieEntry.CONTENT_URI,null,null);
            } else if("pref_reseteaza_absente".equals(preference.getKey())){
                getActivity().getContentResolver()
                        .delete(CarnetContract.AbsentaEntry.CONTENT_URI,null,null);
            } else if("pref_reseteaza_full".equals(preference.getKey())){
                getActivity().getContentResolver()
                        .delete(CarnetContract.AbsentaEntry.CONTENT_URI,null,null);
                getActivity().getContentResolver()
                        .delete(CarnetContract.AbsentaEntry.CONTENT_URI,null,null);
                Intent intent = new Intent(getActivity(), SetupActivity.class);
                SharedPreferences settings = PreferenceManager
                        .getDefaultSharedPreferences(getActivity());
                settings.edit().remove("SETUP_DONE_V2").commit();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
        return true;
    }

}
