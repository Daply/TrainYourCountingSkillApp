package com.dariapro.trainyourcountingskills.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.exception.ExtraIsNullException;
import com.dariapro.trainyourcountingskills.fragment.CategoryListFragment;

/**
 * @author Pleshchankova Daria
 *
 */
public class CategoryActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Bundle args = getIntent().getExtras();
        String value = null;
        if (args != null) {
            try {
                value = args.getString(getApplicationContext().getString(R.string.MODE));
                if (value == null){
                    throw new ExtraIsNullException("Extra " +
                            getApplicationContext().getString(R.string.MODE) +
                            " is null in " + getClass().getName());
                }
            }
            catch (ExtraIsNullException e) {
                Log.e(getApplicationContext().getString(R.string.TAG),
                        getApplicationContext().getString(R.string.MODE) + " didn't passed");
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString(getApplicationContext().getString(R.string.MODE), value);
        Fragment fragment = new CategoryListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Intent newIntent(Context packageContext, long categoryId){
        Intent intent = new Intent(packageContext, LevelListActivity.class);
        intent.putExtra(packageContext.getString(R.string.EXTRA_CATEGORY_ID), categoryId);
        return intent;
    }
}
