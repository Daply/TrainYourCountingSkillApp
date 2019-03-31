package com.dariapro.traincounting.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.dariapro.traincounting.fragment.CategoryListFragment;

import java.util.UUID;

public class CategoryActivity extends SingleFragmentActivity {

    public static final String EXTRA_CATEGORY_ID = "com.dariapro.traincounting.category_id";

    @Override
    protected Fragment createFragment() {
        return new CategoryListFragment();
    }

    public static Intent newIntent(Context packegeContext, UUID categoryId){
        Intent intent = new Intent(packegeContext, LevelListActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        return intent;
    }
}
