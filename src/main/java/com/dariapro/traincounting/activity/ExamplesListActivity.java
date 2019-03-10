package com.dariapro.traincounting.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.dariapro.traincounting.fragment.ExampleListFragment;

import java.util.UUID;

public class ExamplesListActivity extends SingleFragmentActivity{

    public static final String EXTRA_EXAMPLE_ID = "com.dariapro.traincounting._id";

    @Override
    protected Fragment createFragment() {
        return new ExampleListFragment();
    }

    public static Intent newIntent(Context packegeContext, UUID exampleId){
        Intent intent = new Intent(packegeContext, ExamplesPagerActivity.class);
        intent.putExtra(EXTRA_EXAMPLE_ID, exampleId);
        return intent;
    }
}
