package com.rcmapps.safetycharger.utils;

import android.content.Context;
import android.os.Handler;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.rcmapps.safetycharger.BuildConfig;
import com.rcmapps.safetycharger.R;

public class AdmobAdUtils {

    private static Context context;
    private static InterstitialAd mInterstitialAd;
    private static AdmobAdUtils instance;

    public static AdmobAdUtils getInstance(Context ctx) {

        if (instance == null) {
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

        boolean isPremium = SharedPreferenceUtils.getInstance(context).getBoolean(PreferenceContants.KEY_PREMIUM,false);
        System.out.println(isPremium?"Premium: Yes":"Premium: No");

        if (mInterstitialAd != null && mInterstitialAd.isLoaded() && !isPremium) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mInterstitialAd.show();
                }
            },2000);

        }
    }

    private void requestNewInterstitial() {
        if (UtilMethods.isInternetAvailable(context) && !mInterstitialAd.isLoaded()) {

            AdRequest.Builder adRequest = new AdRequest.Builder();
            if (BuildConfig.DEBUG){
                adRequest.addTestDevice("F0BC99DD3A2447A38D525DBA2A80CFA6");
            }

            mInterstitialAd.loadAd(adRequest.build());
        }
    }
}
