package com.rcmapps.safetycharger.listeners;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.view.View;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.activites.SettingsActivity;
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
            case R.id.toggleAlarmBtn:
                boolean state = sharedPreferenceUtils.getBoolean(PreferenceContants.KEY_IS_SERVICE_RUNNING,false);
                state = !state;
                presenter.onCheckedChanged(state);
                break;
            case R.id.chooseAlarmToneBtn:
                presenter.onClickChooseAlarmBtn();
                break;
            case R.id.settingsBtn:
                presenter.onClickSettingsBtn();
                //presenter.setPassword(sharedPreferenceUtils.getString(PreferenceContants.KEY_PASSWORD,PreferenceContants.KEY_PASSWORD_DEFAULT_VALUE));
                break;
        }
    }
}
