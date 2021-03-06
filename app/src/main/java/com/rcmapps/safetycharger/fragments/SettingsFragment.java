package com.rcmapps.safetycharger.fragments;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.PopupMenu;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.activites.AboutActivity;
import com.rcmapps.safetycharger.activites.InstructionActivity;
import com.rcmapps.safetycharger.activites.SettingsActivity;
import com.rcmapps.safetycharger.inappbilling.BillingCallback;
import com.rcmapps.safetycharger.inappbilling.InappBillingManager;
import com.rcmapps.safetycharger.interfaces.Callback;
import com.rcmapps.safetycharger.interfaces.SettingsView;
import com.rcmapps.safetycharger.presenters.SettingsFragmentPresenter;
import com.rcmapps.safetycharger.utils.InstructionManager;
import com.rcmapps.safetycharger.utils.PreferenceContants;
import com.rcmapps.safetycharger.utils.SharedPreferenceUtils;
import com.rcmapps.safetycharger.utils.UtilMethods;

public class SettingsFragment extends PreferenceFragmentCompat implements SettingsView, Callback, BillingCallback {

    private SettingsFragmentPresenter presenter;
    private PasswordChangeDialogFragment passwordChangeDialogFragment = new PasswordChangeDialogFragment();
    private InstructionManager instructionManager = new InstructionManager();
    private InappBillingManager billingManager;
    private boolean isGooglePlayserviceAvailable;
    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SettingsFragmentPresenter(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerForContextMenu(getListView());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
        initBillingManager(activity);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (activity != null) {
            initBillingManager(activity);
        }

        if (getBooleanPref(PreferenceContants.KEY_IS_FIRSTRUN, true)) {
            instructionManager.showInstructionOnTapPasswordPref(getActivity(), this);
        }
    }

    private void initBillingManager(Activity activity) {
        isGooglePlayserviceAvailable = UtilMethods.isGooglePlayServicesAvailable(activity);

        billingManager = new InappBillingManager(activity);
        billingManager.setBillingCallback(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (billingManager != null && isGooglePlayserviceAvailable) {
            billingManager.setup();
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.app_settings);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {

        if (activity == null) {
            return false;
        }

        switch (preference.getKey()) {
            case PreferenceContants.KEY_PASSWORD:
                checkIfPasswordisAlreadySet();
                break;
            case PreferenceContants.KEY_RESET_ALARM:
                showConfirmationDialog();
                break;
            case PreferenceContants.KEY_INSTRUCTION: {
                Intent intent = new Intent(getActivity(), InstructionActivity.class);
                startActivity(intent);
                break;
            }
            case PreferenceContants.KEY_REMOVE_AD:

                if(!UtilMethods.isInternetAvailable(activity)){
                    showToast(getResourceString(R.string.internet_not_available));
                    return false;
                }

                if (SharedPreferenceUtils.getInstance(getActivity()).getBoolean(PreferenceContants.KEY_PREMIUM, false)) {
                    UtilMethods.showSimpleAlertWithMessage(getActivity(), getResourceString(R.string.alert), getResourceString(R.string.item_already_purchased));
                } else {

                    if (isGooglePlayserviceAvailable) {
                        billingManager.startBilling();
                    } else {
                        showToast(getResourceString(R.string.google_playservice_not_available));
                    }
                }

                break;
            case PreferenceContants.KEY_ABOUT: {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
            }
            case PreferenceContants.KEY_OTHER_APPS: {
                activity.openContextMenu(getListView());
                break;
            }
        }

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        activity.getMenuInflater().inflate(R.menu.app_links,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        String url = null;

        switch (item.getItemId()){
            case R.id.app1:
                url = activity.getResources().getString(R.string.app1_url);
                break;
            case R.id.app2:
                url = activity.getResources().getString(R.string.app2_url);
                break;
            case R.id.app3:
                url = activity.getResources().getString(R.string.app3_url);
                break;
            case R.id.app4:
                url = activity.getResources().getString(R.string.app4_url);
                break;
            case R.id.app5:
                url = activity.getResources().getString(R.string.app5_url);
                break;
            case R.id.app6:
                url = activity.getResources().getString(R.string.app6_url);
                break;
        }

        Uri webpage = Uri.parse(url);
        Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(myIntent);

        return true;
    }

    private void checkIfPasswordisAlreadySet() {

        String password = SharedPreferenceUtils.getInstance(getActivity()).getString(PreferenceContants.KEY_PASSWORD, "");

        if (password.isEmpty()) {
            showPasswordChangeDialog();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setMessage(getActivity().getString(R.string.password_already_set));
            dialog.setCancelable(false);
            dialog.setPositiveButton(getResourceString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showPasswordChangeDialog();
                }
            });
            dialog.setNegativeButton(getResourceString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.create();
            dialog.show();
        }
    }

    private void showPasswordChangeDialog() {
        try {
            passwordChangeDialogFragment = new PasswordChangeDialogFragment();
            passwordChangeDialogFragment.setPresenter(presenter);
            android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
            passwordChangeDialogFragment.show(fragmentManager, PasswordChangeDialogFragment.class.getSimpleName());
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void showConfirmationDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(getResourceString(R.string.confirm));
        dialog.setMessage(getResourceString(R.string.sure_to_change_alarm));
        dialog.setPositiveButton(getResourceString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferenceUtils.getInstance(getActivity()).clear(PreferenceContants.KEY_SELECTED_ALARM_TONE_URI);
            }
        });
        dialog.setNegativeButton(getResourceString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
    public void continueInstruction() {
        instructionManager.showInstructionOnTapBackButton(getActivity(), ((SettingsActivity) getActivity()).getToolbar());
    }

    @Override
    public void onRestorePurchase(boolean mIsPremium) {

        SharedPreferenceUtils.getInstance(getActivity()).putBoolean(PreferenceContants.KEY_PREMIUM, mIsPremium);
    }

    @Override
    public void onPurchaseSuccess() {
        SharedPreferenceUtils.getInstance(getActivity()).putBoolean(PreferenceContants.KEY_PREMIUM, true);
    }

    @Override
    public void onPurchaseFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
    public boolean getBooleanPref(String prefName, boolean defaultVal) {
        return SharedPreferenceUtils.getInstance(getActivity()).getBoolean(prefName, defaultVal);
    }

    @Override
    public void showToast(String message) {
        UtilMethods.showToastMessage(getActivity(), message);
    }

    @Override
    public void closeApp() {

    }

    @Override
    public void onTargetViewDismissed() {
        showPasswordChangeDialog();
    }


}
