package com.rcmapps.safetycharger.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.utils.PreferenceContants;
import com.rcmapps.safetycharger.utils.SharedPreferenceUtils;

public class InstructionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.viewInstuctionBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceUtils sharedPreferenceUtils = SharedPreferenceUtils.getInstance(InstructionActivity.this);
                sharedPreferenceUtils.putBoolean(PreferenceContants.KEY_IS_FIRSTRUN,true);
                finish();
                Intent intent = new Intent(InstructionActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public String getActivityTitle() {
        return getString(R.string.instructions);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_instruction;
    }
}
