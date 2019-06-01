package com.dariapro.traincounting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dariapro.traincounting.Extras;
import com.dariapro.traincounting.fragment.CategoryListFragment;

/**
 * @author Pleshchankova Daria
 *
 */
public class CategoryActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        String value = getIntent().getExtras().getString(Extras.MODE);
        Bundle bundle = new Bundle();
        bundle.putString(Extras.MODE, value);
        Fragment fragment = new CategoryListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Intent newIntent(Context packageContext, long categoryId){
        Intent intent = new Intent(packageContext, LevelListActivity.class);
        intent.putExtra(Extras.EXTRA_CATEGORY_ID, categoryId);
        return intent;
    }
}
