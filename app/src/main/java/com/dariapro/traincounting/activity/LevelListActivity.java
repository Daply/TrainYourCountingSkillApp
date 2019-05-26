package com.dariapro.traincounting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dariapro.traincounting.Extras;
import com.dariapro.traincounting.fragment.LevelListFragment;

import java.util.UUID;

public class LevelListActivity extends SingleFragmentActivity  {

    @Override
    protected Fragment createFragment() {
        String value = getIntent().getExtras().getString(Extras.MODE);
        long categoryId = getIntent().getExtras().getLong(Extras.EXTRA_CATEGORY_ID);
        Bundle bundle = new Bundle();
        bundle.putString(Extras.MODE, value);
        bundle.putLong(Extras.EXTRA_CATEGORY_ID, categoryId);
        Fragment fragment = new LevelListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Intent newIntent(Context packegeContext, long levelId){
        Intent intent = new Intent(packegeContext, ProblemsListActivity.class);
        intent.putExtra(Extras.EXTRA_LEVEL_ID, levelId);
        return intent;
    }
}
