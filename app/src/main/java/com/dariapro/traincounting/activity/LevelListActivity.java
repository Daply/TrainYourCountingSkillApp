package com.dariapro.traincounting.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.dariapro.traincounting.fragment.CategoryListFragment;
import com.dariapro.traincounting.fragment.LevelListFragment;

import java.util.UUID;

public class LevelListActivity extends SingleFragmentActivity  {

    public static final String EXTRA_LEVEL_ID = "com.dariapro.traincounting._id";

    @Override
    protected Fragment createFragment() {
        return new LevelListFragment();
    }

    public static Intent newIntent(Context packegeContext, UUID levelId){
        Intent intent = new Intent(packegeContext, ExamplesListActivity.class);
        intent.putExtra(EXTRA_LEVEL_ID, levelId);
        return intent;
    }
}
