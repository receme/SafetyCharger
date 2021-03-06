package com.rcmapps.safetycharger.presenters;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.interfaces.MainView;
import com.rcmapps.safetycharger.models.PasswordChanger;
import com.rcmapps.safetycharger.utils.PreferenceContants;

public class MainPresenter {

    private MainView view;

    public MainPresenter(MainView view){
        this.view = view;
    }

    public void init() {
        view.initView();
        view.defineClickListener();

        if(view.getBooleanPref(PreferenceContants.KEY_IS_FIRSTRUN,true)){
            view.showInstruction();
        }
    }

    public void onCheckedChanged(boolean isChecked) {
        if(isChecked){

            if(view.isPasswordSet()){
                view.setSafetyAlarm();
            }
            else{
                view.showToast(view.getResourceString(R.string.set_password_first));
                view.uncheckAlarmSwitch();
            }
        }
        else{
            view.stopSafetyAlarm();
        }
    }

    public void checkIfAlarmStarted(boolean isAlarmOn) {
        if(isAlarmOn){
            view.showEnterPasswordDialog();
        }
        else {
            view.closeEnterPasswordDialog();
        }
    }

    public void checkCableStatus(boolean cableConnected) {
        if(cableConnected){
            view.closeEnterPasswordDialog();
        }
        else{
            view.showEnterPasswordDialog();
        }
    }

    public void closeApp() {
        view.closeApp();
    }

    public void validatePassword(String password) {

        if(password==null|| password.isEmpty()){
            return;
        }

        if(password.equals(view.getSavedPassword())){
            view.closeEnterPasswordDialog();
            view.stopSafetyAlarm();
        }
        else{
            view.showToast(view.getResourceString(R.string.password_not_match));
        }
    }

    public void onClickSettingsBtn() {
        view.showSettingsScreen();
    }

    public void onClickChooseAlarmBtn() {
        view.showAlarmChooser();
    }
}
