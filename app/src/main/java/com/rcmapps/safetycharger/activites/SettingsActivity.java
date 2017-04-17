package com.rcmapps.safetycharger.activites;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    final static String ACTION_PREFS_ONE = "com.example.prefs.PREFS_ONE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (savedInstanceState == null) {
            Fragment preferenceFragment = new SettingsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.pref_container, preferenceFragment);
            ft.commit();
        }
    }


}
