package com.rcmapps.safetycharger.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.interfaces.AlertActionCallback;

public class UtilMethods {

    private static ProgressDialog progressDialog;

    public static void showProgressDialog(Context context, @Nullable String message) {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }

        if (message == null) {
            message = context.getResources().getString(R.string.please_wait);
        }
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);

        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.show();
            }
        });
    }

    public static void hideProgressDialog(Activity activity) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.cancel();
                    progressDialog = null;
                }
            }
        });

    }

    public static void showSimpleAlertWithMessage(final Activity context, final String title, final String message) {

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });

    }

    public static void showAlertWithActions(final Activity context, final String title, final String message, String titleOK, String titleCancel, final AlertActionCallback callback) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);

        if (titleOK.isEmpty() == false) {
            builder.setPositiveButton(titleOK, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                    if (callback != null) {
                        callback.dismissAlertWithAction((AlertActionCallback.AlertAction.OK));
                    }
                }
            });
        }

        if (titleCancel.isEmpty()) {
            titleCancel = "Cancel";
        }

        builder.setNegativeButton(titleCancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                if (callback != null) {
                    callback.dismissAlertWithAction((AlertActionCallback.AlertAction.CANCEL));
                }
            }
        });

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                builder.show();
            }
        });

    }

    public static void showToastMessage(final Activity activity, final String message) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity,message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void printLog(String message){
        Log.d("DEBUG",message);
    }

}
