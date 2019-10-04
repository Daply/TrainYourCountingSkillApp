package com.dariapro.trainyourcountingskills.activity;

import android.content.Context;
import android.content.Intent;

import com.dariapro.trainyourcountingskills.fragment.MainFragment;

import androidx.fragment.app.Fragment;

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
