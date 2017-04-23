package com.rcmapps.safetycharger.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
            case PreferenceContants.KEY_RESET_ALARM:
                showConfirmationDialog();
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

    private void showConfirmationDialog() {

        final MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(),R.raw.siren);
        mediaPlayer.start();

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(getResourceString(R.string.confirm));
        dialog.setMessage(getResourceString(R.string.sure_to_change_alarm));
        dialog.setPositiveButton(getResourceString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferenceUtils.getInstance(getActivity()).clear(PreferenceContants.KEY_SELECTED_ALARM_TONE_URI);
                if(mediaPlayer!=null){
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                }

            }
        });
        dialog.setNegativeButton(getResourceString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mediaPlayer!=null){
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                }

            }
        });
        dialog.setCancelable(false);
        dialog.create();
        dialog.show();
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
