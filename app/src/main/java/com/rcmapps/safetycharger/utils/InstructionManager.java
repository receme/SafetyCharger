package com.rcmapps.safetycharger.utils;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.activites.MainActivity;

public class InstructionManager {

    public void showIntructionOnTapSettingsBtn(final MainActivity activity){

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
                        if(lastTarget.id()==1 && targetClicked){
                            activity.showSettingsScreen();
                        }
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                }).start();

    }

    public void showInstructionOnTapPasswordPref(final Activity fragment){

        TapTargetView.showFor(fragment,TapTarget.forBounds(new Rect(10,100,400,400),
                "Set a password","This password will be needed to stop the alarm")
                        .cancelable(true).transparentTarget(true).targetRadius(100).dimColor(android.R.color.black),

                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                    }
                });
    }
}
