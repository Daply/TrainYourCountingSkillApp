package com.dariapro.trainyourcountingskills.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.exception.ExtraIsNullException;
import com.dariapro.trainyourcountingskills.fragment.QuestionListFragment;

/**
 * @author Pleshchankova Daria
 *
 */
public class QuestionListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        Bundle args = getIntent().getExtras();
        String value = null;
        long levelId = 0;
        if (args != null) {
            try {
                value = args.getString(getApplicationContext().getString(R.string.MODE));
                if (value == null){
                    throw new ExtraIsNullException("Extra " +
                            getApplicationContext().getString(R.string.MODE) +
                            " is null in " + getClass().getName());
                }
                levelId = args.getLong(getApplicationContext().getString(R.string.EXTRA_LEVEL_ID));
                if (levelId == 0){
                    throw new ExtraIsNullException("Extra " +
                            getApplicationContext().getString(R.string.EXTRA_LEVEL_ID) +
                            " is equal 0 in " + getClass().getName());
                }
            }
            catch (ExtraIsNullException e) {
                Log.e(getApplicationContext().getString(R.string.TAG),
                        getApplicationContext().getString(R.string.MODE) + " or " +
                                getApplicationContext().getString(R.string.EXTRA_LEVEL_ID) +
                                " didn't passed");
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString(getApplicationContext().getString(R.string.MODE), value);
        bundle.putLong(getApplicationContext().getString(R.string.EXTRA_LEVEL_ID), levelId);
        Fragment fragment = new QuestionListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Intent newIntent(Context packageContext, long questionId){
        Intent intent = new Intent(packageContext, SimpleQuestionPagerActivity.class);
        intent.putExtra(packageContext.getString(R.string.EXTRA_QUESTION_ID), questionId);
        return intent;
    }
}
