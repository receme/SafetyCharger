package com.rcmapps.safetycharger.inappbilling;


import android.app.Activity;
import android.util.Log;

import com.rcmapps.safetycharger.BuildConfig;
import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.inappbilling.utils.IabHelper;
import com.rcmapps.safetycharger.inappbilling.utils.IabResult;
import com.rcmapps.safetycharger.inappbilling.utils.Inventory;
import com.rcmapps.safetycharger.inappbilling.utils.Purchase;

public class InappBillingManager implements IabHelper.OnIabPurchaseFinishedListener {

    //private static final String TEST_ITEM_SKU = "android.test.purchased";
    private static final String ITEM_SKU_PURCHASED = "com.rcmapps.adfree";
    private static final String PAYLOAD = "adfreeversion";

    private String base64EncodedPublicKey;
    private Activity activity;
    private IabHelper iabHelper;

    private boolean mIsPremium;
    private BillingCallback callback;

    public void setBillingCallback(BillingCallback callback) {
        this.callback = callback;
    }

    public InappBillingManager(Activity activity) {
        this.activity = activity;
    }

    public void setup() {
        base64EncodedPublicKey = activity.getString(R.string.inappbilling_key);
        iabHelper = new IabHelper(activity, base64EncodedPublicKey);

        iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                if (result.isSuccess()) {
                    System.out.println("billing setup success");

                    iabHelper.queryInventoryAsync(mGotInventoryListener);
                } else {
                    System.out.println("billing setup failure");
                }
            }
        });
    }

    public void startBilling() {

        try {
            iabHelper.launchPurchaseFlow(activity, ITEM_SKU_PURCHASED, 10001,
                    this, PAYLOAD);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            System.out.println("Purchaged could not be done");
        }

    }

    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase info) {
        if (result.isSuccess()) {
            if (info.getSku().equals(ITEM_SKU_PURCHASED)) {
                System.out.println("Purchase successful");

                if (callback != null) {
                    callback.onPurchaseSuccess();
                }
            }
        } else {
            System.out.println("Purchase failed");
            if (callback != null) {
                if (result.getResponse() == 7) {
                    callback.onPurchaseFailure(result.getMessage());
                }
            }
        }
    }

    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d("TAG", "Query inventory finished.");

            if (BuildConfig.DEBUG) {
                if (callback != null) {
                    callback.onRestorePurchase(false);
                    return;
                }
            }

            // Have we been disposed of in the meantime? If so, quit.
            if (iabHelper == null) {
                Log.d("TAG", "IabHelper instance was disposed. inventory finished.");
                return;
            }

            // Is it a failure?
            if (result.isFailure()) {
                Log.d("TAG", "Failed to query inventory: " + result);
                return;
            }

            Log.d("TAG", "Query inventory was successful.");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // Do we have the premium upgrade?
            Purchase adfreePurchase = inventory.getPurchase(ITEM_SKU_PURCHASED);
            //mIsPremium = (adfreePurchase != null && verifyDeveloperPayload(adfreePurchase));
            mIsPremium = (adfreePurchase != null);
            Log.d("TAG", "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));

//            // Do we have the infinite gas plan?
//            Purchase infiniteGasPurchase = inventory.getPurchase(SKU_INFINITE_GAS);
//            mSubscribedToInfiniteGas = (infiniteGasPurchase != null &&
//                    verifyDeveloperPayload(infiniteGasPurchase));
//            Log.d(TAG, "User " + (mSubscribedToInfiniteGas ? "HAS" : "DOES NOT HAVE")
//                    + " infinite gas subscription.");
//            if (mSubscribedToInfiniteGas) mTank = TANK_MAX;
//
//            // Check for gas delivery -- if we own gas, we should fill up the tank immediately
//            Purchase gasPurchase = inventory.getPurchase(SKU_GAS);
//            if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
//                Log.d(TAG, "We have gas. Consuming it.");
//                mHelper.consumeAsync(inventory.getPurchase(SKU_GAS), mConsumeFinishedListener);
//                return;
//            }

            if (callback != null) {
                callback.onRestorePurchase(mIsPremium);
            }

//            setWaitScreen(false);
//            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };

    private boolean verifyDeveloperPayload(Purchase purchase) {
        String developerPayload = purchase.getDeveloperPayload();
        if (developerPayload.equals(PAYLOAD)) {
            return true;
        }
        return false;
    }
}
