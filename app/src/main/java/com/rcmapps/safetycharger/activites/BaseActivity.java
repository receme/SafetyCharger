package com.rcmapps.safetycharger.activites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rcmapps.safetycharger.utils.SharedPreferenceUtils;

/**
 * Created by receme on 3/31/17.
 */

public class BaseActivity extends AppCompatActivity {

    public SharedPreferenceUtils sharedPreferenceUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferenceUtils = SharedPreferenceUtils.getInstance(this);
    }
}
