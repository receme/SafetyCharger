package com.rcmapps.safetycharger.fragments;


import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Button;
import android.widget.Toast;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.utils.UtilMethods;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.app_settings);

        EditTextPreference passwordPref = (EditTextPreference) findPreference("key_password");
        passwordPref.setOnPreferenceChangeListener(this);
        passwordPref.
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {

        switch (preference.getKey()) {
            case "key_instruction":
                UtilMethods.printLog("show instruction");
                break;
            case "key_about":
                UtilMethods.printLog("show about");
                break;
        }

        return true;
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference.getKey().equals("key_password")) {

            if (((String) newValue).isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.newpassword_notnull), Toast.LENGTH_SHORT).show();
                return false;
            }

            if (((String) newValue).length() < 8) {
                Toast.makeText(getActivity(), getString(R.string.password_length), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }
}
