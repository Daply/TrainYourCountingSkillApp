package com.dariapro.traincounting.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.dariapro.traincounting.fragment.MainFragment;

public class MainActivity extends SingleFragmentActivity {

    public static final String EXTRA_CHOICE = "com.dariapro.traincounting.choice";
    public static final String MODE = "com.dariapro.traincounting.mode";

    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }

    public static Intent newExampleIntent(Context packegeContext){
        Intent intent = new Intent(packegeContext, ExampleStartActivity.class);
        return intent;
    }

    public static Intent newCategoryIntent(Context packegeContext){
        Intent intent = new Intent(packegeContext, CategoryActivity.class);
        return intent;
    }
}
