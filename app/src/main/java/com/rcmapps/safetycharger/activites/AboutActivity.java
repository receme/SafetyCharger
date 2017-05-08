package com.rcmapps.safetycharger.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.utils.UtilMethods;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.versionTv)
    TextView versionTv;
    @BindView(R.id.licensesBtn)
    Button licensesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        versionTv.setText(getString(R.string.version)+" "+ UtilMethods.getVesionText(this));

        licensesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, LicenseActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public String getActivityTitle() {
        return getString(R.string.about);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }
}
