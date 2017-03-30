package com.rcmapps.safetycharger.listeners;

import android.view.View;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.presenters.MainPresenter;
import com.rcmapps.safetycharger.utils.PreferenceContants;
import com.rcmapps.safetycharger.utils.SharedPreferenceUtils;
import com.rcmapps.safetycharger.utils.UtilMethods;


public class ButtonClickListener implements View.OnClickListener {

    private MainPresenter presenter;
    private SharedPreferenceUtils sharedPreferenceUtils;

    public ButtonClickListener(MainPresenter _presenter, SharedPreferenceUtils sharedPreferenceUtils){
        this.presenter = _presenter;
        this.sharedPreferenceUtils = sharedPreferenceUtils;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setPasswordBtn:
                presenter.setPassword(sharedPreferenceUtils.getString(PreferenceContants.KEY_PASSWORD,PreferenceContants.KEY_PASSWORD_DEFAULT_VALUE));
                break;
            case R.id.chooseAlarmToneBtn:
                UtilMethods.printLog("pressed chooseAlarm btn");
                break;
        }
    }
}
