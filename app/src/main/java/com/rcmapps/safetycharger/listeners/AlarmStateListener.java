package com.rcmapps.safetycharger.listeners;

import android.widget.CompoundButton;

import com.rcmapps.safetycharger.presenters.MainPresenter;

/**
 * Created by receme on 12/1/16.
 */
public class AlarmStateListener implements CompoundButton.OnCheckedChangeListener {

    private MainPresenter presenter;

    public AlarmStateListener(MainPresenter _presenter){
        this.presenter = _presenter;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

        if(isChecked){

        }
        else{

        }
    }
}
