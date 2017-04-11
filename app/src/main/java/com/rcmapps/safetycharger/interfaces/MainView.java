package com.rcmapps.safetycharger.interfaces;

public interface MainView extends BaseView{
    void initView();
    void defineClickListener();
    void showPasswordChangeDialog(String prevPassword);
    void saveNewPassword(String newPassword);
    void closePasswordChangeDialog();
    void showEnterPasswordDialog();
    void closeEnterPasswordDialog();
    void setSafetyAlarm();
    void stopSafetyAlarm();
    boolean isPasswordSet();
    void uncheckAlarmSwitch();


    String getSavedPassword();
}
