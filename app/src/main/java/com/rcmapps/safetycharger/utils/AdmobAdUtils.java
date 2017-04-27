package com.rcmapps.safetycharger.utils;


import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

public class AdmobAdUtils {

    private static final String ADMOB_APP_ID = "ca-app-pub-3996731798982494/4759163715";

    Context context;
    InterstitialAd mInterstitialAd;
    Random random;

    public AdmobAdUtils(Context context) {
        this.context = context;
        this.mInterstitialAd = new InterstitialAd(context);
        this.mInterstitialAd.setAdUnitId(ADMOB_APP_ID);

        this.random = new Random();

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
        if (mInterstitialAd != null && mInterstitialAd.isLoaded() && random.nextInt() % 3 == 0) {
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
