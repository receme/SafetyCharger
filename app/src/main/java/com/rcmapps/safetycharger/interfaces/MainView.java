package com.rcmapps.safetycharger.interfaces;

public interface MainView extends BaseView{
    void initView();
    void defineClickListener();
    void showEnterPasswordDialog();
    void closeEnterPasswordDialog();
    void setSafetyAlarm();
    void stopSafetyAlarm();
    boolean isPasswordSet();
    void uncheckAlarmSwitch();


    String getSavedPassword();

    void updateToggleButton(int resource);

    void showSettingsScreen();

    void showAlarmChooser();
}
