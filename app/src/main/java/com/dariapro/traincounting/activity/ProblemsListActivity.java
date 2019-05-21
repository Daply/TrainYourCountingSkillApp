package com.dariapro.traincounting.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.dariapro.traincounting.fragment.ProblemListFragment;

public class ProblemsListActivity extends SingleFragmentActivity{

    public static final String EXTRA_QUESTION_ID = "com.dariapro.traincounting.example_id";

    @Override
    protected Fragment createFragment() {
        return new ProblemListFragment();
    }

    public static Intent newIntent(Context packegeContext, long questionId){
        Intent intent = new Intent(packegeContext, ProblemsPagerActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);

        return intent;
    }
}
