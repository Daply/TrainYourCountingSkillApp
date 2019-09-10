package com.dariapro.traincounting.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.dariapro.traincounting.R;

/**
 * @author Pleshchankova Daria
 *
 */
public class AnswerListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return null;
    }

    public static Intent newIntent(Context packageContext, long questionId){
        Intent intent = new Intent(packageContext, AnswerActivity.class);
        intent.putExtra(packageContext.getString(R.string.EXTRA_QUESTION_ID), questionId);
        return intent;
    }

}
