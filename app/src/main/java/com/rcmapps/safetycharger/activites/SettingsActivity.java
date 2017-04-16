package com.rcmapps.safetycharger.activites;


import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;

import com.rcmapps.safetycharger.R;

public class SettingsActivity extends PreferenceActivity {

    final static String ACTION_PREFS_ONE = "com.example.prefs.PREFS_ONE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this,R.xml.app_settings,false);
    }
}
