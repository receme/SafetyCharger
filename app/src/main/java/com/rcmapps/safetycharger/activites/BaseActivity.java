package com.rcmapps.safetycharger.activites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rcmapps.safetycharger.interfaces.BaseView;
import com.rcmapps.safetycharger.utils.SharedPreferenceUtils;
import com.rcmapps.safetycharger.utils.UtilMethods;

public class BaseActivity extends AppCompatActivity implements BaseView{

    public SharedPreferenceUtils sharedPreferenceUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferenceUtils = SharedPreferenceUtils.getInstance(this);
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
    public void showToast(String message) {
        UtilMethods.showToastMessage(this,message);
    }

    @Override
    public void closeApp() {
        finish();
    }
}
