package com.rcmapps.safetycharger.activites;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rcmapps.safetycharger.R;
import com.rcmapps.safetycharger.interfaces.BaseView;
import com.rcmapps.safetycharger.utils.PreferenceContants;
import com.rcmapps.safetycharger.utils.SharedPreferenceUtils;
import com.rcmapps.safetycharger.utils.UtilMethods;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    public SharedPreferenceUtils sharedPreferenceUtils;
    public static int isAppOpen = 0;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        toolbar.setTitle(getActivityTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferenceUtils = SharedPreferenceUtils.getInstance(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        
        //System.out.println(FirebaseInstanceId.getInstance().getToken());

        //handle firebase notification data
        if (getIntent().getExtras() != null) {
            String url = null;
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                //Log.d(TAG, "Key: " + key + " Value: " + value);
                if(key.equals("URL")){
                    url = value.toString();
                }
            }
            if(url!=null && !url.isEmpty()){
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        isAppOpen++;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(sharedPreferenceUtils.getBoolean(PreferenceContants.KEY_IS_ALARM_STARTED,false)){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isAppOpen--;
    }

    @Override
    public void showError(String title, String message) {
        UtilMethods.showSimpleAlertWithMessage(this,title,message);
    }

    @Override
    public String getResourceString(int stringId) {
        return getString(stringId);
    }

    @Override
    public boolean getBooleanPref(String prefName,boolean defaultVal){
        return sharedPreferenceUtils.getBoolean(prefName,defaultVal);
    }

    @Override
    public void showToast(String message) {
        UtilMethods.showToastMessage(this,message);
    }

    @Override
    public void closeApp() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if(sharedPreferenceUtils.getBoolean(PreferenceContants.KEY_IS_ALARM_STARTED,false)){

        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return true;
    }

    public Toolbar getToolbar(){
        return toolbar;
    }

    public abstract String getActivityTitle();
    public abstract int getLayoutId();
}
