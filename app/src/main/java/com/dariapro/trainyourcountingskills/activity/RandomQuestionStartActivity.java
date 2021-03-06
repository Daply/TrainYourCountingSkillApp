package com.dariapro.trainyourcountingskills.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.activity.pager.RandomQuestionPagerActivity;
import com.dariapro.trainyourcountingskills.entity.QuestionType;
import com.dariapro.trainyourcountingskills.exception.ExtraIsNullException;
import com.dariapro.trainyourcountingskills.fragment.RandomQuestionStartFragment;

import androidx.fragment.app.Fragment;

/**
 * @author Pleshchankova Daria
 *
 */
public class RandomQuestionStartActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Bundle args = getIntent().getExtras();
        String value = null;
        QuestionType questionType = null;
        if (args != null) {
            try {
                value = args.getString(getApplicationContext().getString(R.string.MODE));
                if (value == null){
                    throw new ExtraIsNullException("Extra " +
                            getApplicationContext().getString(R.string.MODE) +
                            " is null in " + getClass().getName());
                }
                questionType = QuestionType.valueOf(args.getString(getApplicationContext()
                        .getString(R.string.QUESTION_TYPE)));
            }
            catch (ExtraIsNullException e) {
                Log.e(getApplicationContext().getString(R.string.TAG),
                        getApplicationContext().getString(R.string.MODE) + " or " +
                                getApplicationContext().getString(R.string.QUESTION_TYPE) +
                                " didn't passed");
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString(getApplicationContext().getString(R.string.MODE), value);
        bundle.putString(getApplicationContext().getString(R.string.QUESTION_TYPE), questionType.name());
        Fragment fragment = new RandomQuestionStartFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Intent newQuestionIntent(Context packageContext){
        return new Intent(packageContext, RandomQuestionPagerActivity.class);
    }
}
