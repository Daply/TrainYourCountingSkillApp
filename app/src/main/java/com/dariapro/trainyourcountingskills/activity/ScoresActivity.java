package com.dariapro.trainyourcountingskills.activity;

import android.support.v4.app.Fragment;

import com.dariapro.trainyourcountingskills.fragment.ScoresFragment;

public class ScoresActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ScoresFragment();
    }
}
