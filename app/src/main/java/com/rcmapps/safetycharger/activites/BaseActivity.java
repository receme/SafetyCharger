package com.rcmapps.safetycharger.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.interfaces.BaseView;
import com.rcmapps.safetycharger.utils.PreferenceContants;
import com.rcmapps.safetycharger.utils.SharedPreferenceUtils;
import com.rcmapps.safetycharger.utils.UtilMethods;

public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    public SharedPreferenceUtils sharedPreferenceUtils;
    public static int isAppOpen = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getActivityTitle());
        setSupportActionBar(toolbar);

        sharedPreferenceUtils = SharedPreferenceUtils.getInstance(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isAppOpen++;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(sharedPreferenceUtils.getBoolean(PreferenceContants.KEY_IS_ALARM_STARTED,false)){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isAppOpen--;
    }

    @Override
    public void showError(String title, String message) {
        UtilMethods.showSimpleAlertWithMessage(this,title,message);
    }

    @Override
    public String getResourceString(int stringId) {
        return getString(stringId);
    }

    @Override
    public boolean getBooleanPref(String prefName,boolean defaultVal){
        return sharedPreferenceUtils.getBoolean(prefName,defaultVal);
    }

    @Override
    public void showToast(String message) {
        UtilMethods.showToastMessage(this,message);
    }

    @Override
    public void closeApp() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if(sharedPreferenceUtils.getBoolean(PreferenceContants.KEY_IS_ALARM_STARTED,false)){

        }
        else{
            super.onBackPressed();
        }
    }

    public abstract String getActivityTitle();
    public abstract int getLayoutId();
}
