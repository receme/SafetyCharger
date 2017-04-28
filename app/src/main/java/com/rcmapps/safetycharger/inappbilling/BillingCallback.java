package com.rcmapps.safetycharger.inappbilling;


public interface BillingCallback {
    void onRestorePurchase(boolean mIsPremium);
    void onPurchaseSuccess();
    void onPurchaseFailure();
}
