package com.dariapro.trainyourcountingskills.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.dariapro.trainyourcountingskills.R;

/**
 * @author Pleshchankova Daria
 *
 */
public class ResultDialog extends Dialog {

    public Activity currentActivity;

    public ResultDialog(Activity currentActivity) {
        super(currentActivity);
        this.currentActivity = currentActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.result_dialog);
    }

}
