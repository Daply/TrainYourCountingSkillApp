package com.dariapro.traincounting.activity;

import android.support.v4.app.Fragment;

import com.dariapro.traincounting.fragment.ScoresFragment;

public class ScoresActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ScoresFragment();
    }
}
