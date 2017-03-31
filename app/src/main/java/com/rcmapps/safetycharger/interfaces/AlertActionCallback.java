package com.rcmapps.safetycharger.interfaces;


public interface AlertActionCallback {
    enum AlertAction {
        OK, CANCEL
    }

    void dismissAlertWithAction(AlertAction action);
}
