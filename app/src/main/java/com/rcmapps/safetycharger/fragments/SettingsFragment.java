package com.rcmapps.safetycharger.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.activites.AboutActivity;
import com.rcmapps.safetycharger.activites.InstructionActivity;
import com.rcmapps.safetycharger.interfaces.SettingsView;
import com.rcmapps.safetycharger.presenters.SettingsFragmentPresenter;
import com.rcmapps.safetycharger.utils.PreferenceContants;
import com.rcmapps.safetycharger.utils.SharedPreferenceUtils;
import com.rcmapps.safetycharger.utils.UtilMethods;

public class SettingsFragment extends PreferenceFragmentCompat implements SettingsView {

    private SettingsFragmentPresenter presenter;
    private PasswordChangeDialogFragment passwordChangeDialogFragment = new PasswordChangeDialogFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SettingsFragmentPresenter(this);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.app_settings);

    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {

        switch (preference.getKey()) {
            case PreferenceContants.KEY_PASSWORD:
                passwordChangeDialogFragment = new PasswordChangeDialogFragment();
                passwordChangeDialogFragment.setPresenter(presenter);
                passwordChangeDialogFragment.show(getChildFragmentManager(), PasswordChangeDialogFragment.class.getSimpleName());
                break;
            case PreferenceContants.KEY_INSTRUCTION: {
                Intent intent = new Intent(getActivity(), InstructionActivity.class);
                startActivity(intent);
                break;
            }
            case PreferenceContants.KEY_ABOUT: {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
            }
        }

        return true;
    }

    @Override
    public void saveNewPassword(String newPassword) {
        SharedPreferenceUtils.getInstance(getActivity()).putString(PreferenceContants.KEY_PASSWORD, newPassword);
    }

    @Override
    public void closePasswordChangeDialog() {

        if (passwordChangeDialogFragment != null) {
            passwordChangeDialogFragment.dismiss();
        }
    }

    @Override
    public void showError(String title, String message) {
        UtilMethods.showSimpleAlertWithMessage(getActivity(), title, message);
    }

    @Override
    public String getResourceString(int stringId) {
        return getActivity().getResources().getString(stringId);
    }

    @Override
    public void showToast(String message) {
        UtilMethods.showToastMessage(getActivity(), message);
    }

    @Override
    public void closeApp() {

    }
}
