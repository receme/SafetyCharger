package com.rcmapps.safetycharger.presenters;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.interfaces.MainView;
import com.rcmapps.safetycharger.models.PasswordChanger;

public class MainPresenter {

    private MainView view;

    public MainPresenter(MainView view){
        this.view = view;
    }

    public void init() {
        view.initView();
        view.defineClickListener();
    }

    public void setPassword(String prevPassword) throws IllegalArgumentException {

        if(prevPassword == null){
            throw new IllegalArgumentException("previous password cannot be null");
        }

        view.showPasswordChangeDialog(prevPassword);
    }

    public void confirmNewPassword(PasswordChanger passwordChanger) {
        PasswordChanger.Response response = passwordChanger.isValid();

        if(!response.isValid){
            view.showError("",response.message);
            return;
        }

        view.saveNewPassword(passwordChanger.getNewPassword());
        view.closePasswordChangeDialog();
        view.showToast(view.getResourceString(R.string.password_change_successful));
    }

    public void cancelPasswordChange() {
        view.closePasswordChangeDialog();
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

    public MainView getMainView(){
        return view;
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
}
