package com.rcmapps.safetycharger.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rcmapps.safetycharger.R;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    public String getActivityTitle() {
        return getString(R.string.about);
    }
}
