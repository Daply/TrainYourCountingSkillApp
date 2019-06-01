package com.dariapro.traincounting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dariapro.traincounting.Extras;
import com.dariapro.traincounting.fragment.QuestionListFragment;

/**
 * @author Pleshchankova Daria
 *
 */
public class QuestionListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        String value = getIntent().getExtras().getString(Extras.MODE);
        long levelId = getIntent().getExtras().getLong(Extras.EXTRA_LEVEL_ID);
        Bundle bundle = new Bundle();
        bundle.putString(Extras.MODE, value);
        bundle.putLong(Extras.EXTRA_LEVEL_ID, levelId);
        Fragment fragment = new QuestionListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Intent newIntent(Context packageContext, long questionId){
        Intent intent = new Intent(packageContext, QuestionPagerActivity.class);
        intent.putExtra(Extras.EXTRA_QUESTION_ID, questionId);
        return intent;
    }
}
