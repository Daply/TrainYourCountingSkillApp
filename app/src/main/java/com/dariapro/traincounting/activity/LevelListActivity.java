package com.dariapro.traincounting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.dariapro.traincounting.R;
import com.dariapro.traincounting.exception.ExtraIsNullException;
import com.dariapro.traincounting.fragment.LevelListFragment;

/**
 * @author Pleshchankova Daria
 *
 */
public class LevelListActivity extends SingleFragmentActivity  {

    @Override
    protected Fragment createFragment() {
        Bundle args = getIntent().getExtras();
        String value = null;
        long categoryId = 0;
        if (args != null) {
            try {
                value = args.getString(getApplicationContext().getString(R.string.MODE));
                if (value == null){
                    throw new ExtraIsNullException("Extra " +
                            getApplicationContext().getString(R.string.MODE) +
                            " is null in " + getClass().getName());
                }
                categoryId = args.getLong(getApplicationContext().getString(R.string.EXTRA_CATEGORY_ID));
                if (categoryId == 0){
                    throw new ExtraIsNullException("Extra " +
                            getApplicationContext().getString(R.string.EXTRA_CATEGORY_ID) +
                            " is equal 0 in " + getClass().getName());
                }
            }
            catch (ExtraIsNullException e) {
                Log.e(getApplicationContext().getString(R.string.TAG),
                        getApplicationContext().getString(R.string.MODE) + " or " +
                                getApplicationContext().getString(R.string.EXTRA_CATEGORY_ID) +
                                " didn't passed");
            }

        }
        Bundle bundle = new Bundle();
        bundle.putString(getApplicationContext().getString(R.string.MODE), value);
        bundle.putLong(getApplicationContext().getString(R.string.EXTRA_CATEGORY_ID), categoryId);
        Fragment fragment = new LevelListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Intent newIntent(Context packageContext, long levelId){
        Intent intent = new Intent(packageContext, QuestionListActivity.class);
        intent.putExtra(packageContext.getString(R.string.EXTRA_LEVEL_ID), levelId);
        return intent;
    }
}
