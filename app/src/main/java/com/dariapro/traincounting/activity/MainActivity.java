package com.dariapro.traincounting.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.dariapro.traincounting.fragment.MainFragment;

/**
 * @author Pleshchankova Daria
 *
 */
public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }

    public static Intent newExampleIntent(Context packageContext){
        return new Intent(packageContext, RandomQuestionStartActivity.class);
    }

    public static Intent newCategoryIntent(Context packageContext){
        return new Intent(packageContext, CategoryActivity.class);
    }
}
