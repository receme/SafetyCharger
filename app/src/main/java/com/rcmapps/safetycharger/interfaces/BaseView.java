package com.rcmapps.safetycharger.interfaces;

public interface BaseView {
    void showError(String title, String message);

    String getResourceString(int stringId);

    void showToast(String message);

    void closeApp();
}
