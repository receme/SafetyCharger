package com.rcmapps.safetycharger.activites;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rcmapps.safetycharger.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LicenseActivity extends BaseActivity {

    private String[] licenseName = new String[]{
            "Butter Knife",
            "Android Open Source",
            "Eventbus",
            "Firebase",
            "TapTargetView",
            "Crescento",
            "Android-SwitchIcon"};


    private int[] licenseFile = new int[]{
            R.raw.butterknife,
            R.raw.androidopensource,
            R.raw.eventbus,
            R.raw.firebase,
            R.raw.taptargetview,
            R.raw.crescento,
            R.raw.switchicon};

    @BindView(R.id.listview)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        List<String> licenses = new ArrayList<>();

        for (int i = 0; i < licenseFile.length; i++) {
            licenses.add(readFileContent(licenseFile[i], licenseName[i]));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, licenses);
        listView.setAdapter(adapter);
    }

    @Override
    public String getActivityTitle() {
        return getString(R.string.licenses);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_license;
    }

    private String readFileContent(int fileId,String licenseName) {
        InputStream is = getResources().openRawResource(fileId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder entireFile = new StringBuilder();
        entireFile.append(licenseName+"\n\n");
        try {
            while ((line = br.readLine()) != null) { // <--------- place readLine() inside loop
                entireFile.append(line);
                entireFile.append("\n");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return entireFile.toString();
    }
}
