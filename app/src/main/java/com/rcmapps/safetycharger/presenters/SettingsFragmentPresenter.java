package com.rcmapps.safetycharger.presenters;


import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.interfaces.BaseView;
import com.rcmapps.safetycharger.interfaces.SettingsView;
import com.rcmapps.safetycharger.models.PasswordChanger;
import com.rcmapps.safetycharger.utils.PreferenceContants;

public class SettingsFragmentPresenter {

    private SettingsView view;

    public SettingsFragmentPresenter(SettingsView view) {
        this.view = view;
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

        if(view.getBooleanPref(PreferenceContants.KEY_IS_FIRSTRUN,true)){
            view.continueInstruction();
        }
    }

    public SettingsView getSettingsView() {
        return view;
    }
}
