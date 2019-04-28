package com.dariapro.traincounting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dariapro.traincounting.fragment.LevelListFragment;

import java.util.UUID;

public class LevelListActivity extends SingleFragmentActivity  {

    public static final String EXTRA_LEVEL_ID = "com.dariapro.traincounting._id";
    public static final String MODE = "com.dariapro.traincounting.mode";

    @Override
    protected Fragment createFragment() {
        String value = getIntent().getExtras().getString(MODE);
        Bundle bundle = new Bundle();
        bundle.putString(MODE, value);
        Fragment fragment = new LevelListFragment();
        fragment.setArguments(bundle);
        return new LevelListFragment();
    }

    public static Intent newIntent(Context packegeContext, UUID levelId){
        Intent intent = new Intent(packegeContext, ProblemsListActivity.class);
        intent.putExtra(EXTRA_LEVEL_ID, levelId);
        return intent;
    }
}
