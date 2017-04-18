package com.rcmapps.safetycharger.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rcmapps.safetycharger.R;

public class InstructionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
    }

    @Override
    public String getActivityTitle() {
        return getString(R.string.instructions);
    }
}
