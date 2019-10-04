package com.dariapro.trainyourcountingskills.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.dariapro.trainyourcountingskills.R;

public class ResultDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity currentActivity;
    public Dialog dialog;
    public Button okButton;

    public ResultDialog(Activity currentActivity) {
        super(currentActivity);
        this.currentActivity = currentActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.result_dialog);
//        okButton = (Button) findViewById(R.id.ok);
//        okButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.ok:
//                currentActivity.finish();
//                break;
//        }
    }
}
