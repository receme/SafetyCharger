package com.rcmapps.safetycharger.utils;


import android.app.Activity;
import android.graphics.Rect;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.activites.MainActivity;
import com.rcmapps.safetycharger.activites.SettingsActivity;
import com.rcmapps.safetycharger.interfaces.Callback;

import org.greenrobot.eventbus.EventBus;

public class InstructionManager {

    public void showIntructionOnTapSettingsBtn(final MainActivity activity) {

        new TapTargetSequence(activity)
                .targets(TapTarget.forView(activity.findViewById(R.id.settingsBtn),
                        activity.getString(R.string.goto_settingspage_title),
                        activity.getString(R.string.goto_settingspage_desc))
                        .cancelable(false).id(1).dimColor(android.R.color.black))
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {

                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        if (lastTarget.id() == 1 && targetClicked) {
                            activity.showSettingsScreen();
                        }
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                }).start();

    }

    public void showInstructionOnTapPasswordPref(final Activity activity, final Callback callback) {

        TapTargetView.showFor(activity, TapTarget.forBounds(new Rect(10, 100, 400, 400),
                activity.getString(R.string.set_password_title),
                activity.getString(R.string.set_password_desc))
                        .cancelable(true).transparentTarget(true).targetRadius(100).dimColor(android.R.color.black),

                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                    }

                    @Override
                    public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                        super.onTargetDismissed(view, userInitiated);

                        callback.onTargetViewDismissed();
                    }
                });
    }

    public void showInstructionOnTapBackButton(final Activity activity, Toolbar toolbar) {

        TapTargetView.showFor(activity, TapTarget.forToolbarNavigationIcon(toolbar,
                activity.getString(R.string.goback_to_homescreen))
                .cancelable(false).dimColor(android.R.color.black), new TapTargetView.Listener() {
            @Override
            public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                super.onTargetDismissed(view, userInitiated);

                MainActivity.isFromInstructionFlow = true;
                activity.finish();
            }
        });


    }

    public void showIntructionOnTapSetAlarmBtn(final Activity activity) {

        new TapTargetSequence(activity)
                .targets(TapTarget.forView(activity.findViewById(R.id.toggleAlarmBtn),
                        activity.getString(R.string.enable_alarm_instruction))
                        .cancelable(true).transparentTarget(true)
                        .targetRadius(120).dimColor(android.R.color.black))
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        SharedPreferenceUtils.getInstance(activity).putBoolean(PreferenceContants.KEY_IS_FIRSTRUN, false);
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        SharedPreferenceUtils.getInstance(activity).putBoolean(PreferenceContants.KEY_IS_FIRSTRUN, false);
                    }
                }).start();
    }
}
