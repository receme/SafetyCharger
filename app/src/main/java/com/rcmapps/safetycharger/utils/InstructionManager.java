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
                        "To enable alarm, you have to set a password",
                        "Please tap here. This will show the settings page")
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
                "Set a password", "This password will be needed to stop the alarm")
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

    public void showInstructionOnTapBackButton(final Activity activity, Toolbar toolbar, final Callback callback) {

        TapTargetView.showFor(activity, TapTarget.forToolbarNavigationIcon(toolbar,"Go back to home screen")
                .cancelable(false).dimColor(android.R.color.black),new TapTargetView.Listener(){
            @Override
            public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                super.onTargetDismissed(view, userInitiated);

                MainActivity.isFromInstructionFlow = true;
                activity.finish();
            }
        });


    }

    public void showIntructionOnTapSetAlarmBtn(Activity activity) {

        new TapTargetSequence(activity)
                .targets(TapTarget.forView(activity.findViewById(R.id.toggleAlarmBtn),
                        "To enable alarm you have to press this. Alarm will be fired immediately, " +
                                "if your device is not connected to any charging source.")
                        .cancelable(true).transparentTarget(true).targetRadius(120).dimColor(android.R.color.black)).start();
    }
}
