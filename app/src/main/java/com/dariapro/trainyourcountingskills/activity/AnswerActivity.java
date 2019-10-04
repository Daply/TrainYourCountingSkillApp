package com.dariapro.trainyourcountingskills.activity;

import android.os.Bundle;
import android.util.Log;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.entity.Question;
import com.dariapro.trainyourcountingskills.exception.ExtraIsNullException;
import com.dariapro.trainyourcountingskills.fragment.AnswerFragment;

import androidx.fragment.app.Fragment;

/**
 * @author Pleshchankova Daria
 *
 */
public class AnswerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Bundle args = getIntent().getExtras();
        Question currentQuestion = null;
        if (args != null) {
            try {
                currentQuestion = (Question) args.getSerializable(getApplicationContext()
                        .getString(R.string.ARG_QUESTION));
                if (currentQuestion == null){
                    throw new ExtraIsNullException("Extra " +
                            getApplicationContext().getString(R.string.ARG_QUESTION) +
                            " is equal 0 in " + getClass().getName());
                }
            }
            catch (ExtraIsNullException e) {
                Log.e(getApplicationContext().getString(R.string.TAG),
                                getApplicationContext().getString(R.string.ARG_QUESTION) +
                                " didn't passed");
            }

        }
        Bundle bundle = new Bundle();
        if (currentQuestion != null) {
            bundle.putSerializable(getApplicationContext().getString(R.string.ARG_QUESTION), currentQuestion);
        }
        Fragment fragment = new AnswerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
