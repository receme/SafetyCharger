package com.rcmapps.safetycharger.listeners;

import android.view.View;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.presenters.MainPresenter;

/**
 * Created by receme on 12/1/16.
 */
public class ButtonClickListener implements View.OnClickListener {

    private MainPresenter presenter;

    public ButtonClickListener(MainPresenter _presenter){
        this.presenter = _presenter;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setPasswordBtn:
                break;
            case R.id.chooseAlarmToneBtn:
                break;
        }
    }
}
