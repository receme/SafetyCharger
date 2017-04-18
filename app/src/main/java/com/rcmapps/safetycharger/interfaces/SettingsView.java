package com.rcmapps.safetycharger.interfaces;


public interface SettingsView extends BaseView{
    void saveNewPassword(String newPassword);
    void closePasswordChangeDialog();
}
