package com.rcmapps.safetycharger.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rcmapps.safetycharger.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnterPasswordDialogFragment extends AppCompatDialogFragment {

    @BindView(R.id.cancelBtn)
    Button cancelBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enterpassword,container,false);

        ButterKnife.bind(this,view);
        setCancelable(false);
        defineClickListener();
        return view;
    }

    private void defineClickListener() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
