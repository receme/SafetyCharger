package com.rcmapps.safetycharger.utils;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.rcmapps.safetycharger.R;

import java.util.Random;

public class AdmobAdUtils {

    private static Context context;
    private static InterstitialAd mInterstitialAd;
    private static AdmobAdUtils instance;

    public static AdmobAdUtils getInstance(Context ctx){

        if(instance == null){
            instance = new AdmobAdUtils(ctx);
        }
        return instance;
    }


    private AdmobAdUtils(Context context) {
        this.context = context;
        this.mInterstitialAd = new InterstitialAd(context);
        this.mInterstitialAd.setAdUnitId(context.getString(R.string.admob_app_id));

        this.mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(int i) {
                requestNewInterstitial();
            }

            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
    }

    public void startLoadingAd() {
        requestNewInterstitial();
    }

    public void showAd() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()
                && !SharedPreferenceUtils.getInstance(context).getBoolean(PreferenceContants.KEY_PREMIUM,false)) {
            mInterstitialAd.show();
        }
    }

    private void requestNewInterstitial() {
        if (UtilMethods.isInternetAvailable(context) && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder()
                    //.addTestDevice("F0BC99DD3A2447A38D525DBA2A80CFA6")
                    .build();

            mInterstitialAd.loadAd(adRequest);
        }
    }
}
