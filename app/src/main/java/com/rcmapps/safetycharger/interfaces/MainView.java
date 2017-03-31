package com.rcmapps.safetycharger.interfaces;

public interface MainView extends BaseView{
    void defineClickListener();
    void showPasswordChangeDialog(String prevPassword);
    void saveNewPassword(String newPassword);
    void closePasswordChangeDialog();
    void showConfirmation(String message);
}
