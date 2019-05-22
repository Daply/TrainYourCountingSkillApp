package com.dariapro.traincounting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dariapro.traincounting.fragment.ExampleStartFragment;

public class ExampleStartActivity extends SingleFragmentActivity {

    public static final String EXTRA_CHOICE = "com.dariapro.traincounting.choice";
    public static final String MODE = "com.dariapro.traincounting.mode";

    @Override
    protected Fragment createFragment() {
        String value = getIntent().getExtras().getString(MODE);
        Bundle bundle = new Bundle();
        bundle.putString(MODE, value);
        Fragment fragment = new ExampleStartFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Intent newExampleIntent(Context packegeContext){
        Intent intent = new Intent(packegeContext, ExamplePagerActivity.class);
        return intent;
    }
}
