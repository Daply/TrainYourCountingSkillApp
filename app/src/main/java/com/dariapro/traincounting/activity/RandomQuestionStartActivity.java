package com.dariapro.traincounting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dariapro.traincounting.Extras;
import com.dariapro.traincounting.fragment.RandomQuestionStartFragment;

/**
 * @author Pleshchankova Daria
 *
 */
public class RandomQuestionStartActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        String value = getIntent().getExtras().getString(Extras.MODE);
        Bundle bundle = new Bundle();
        bundle.putString(Extras.MODE, value);
        Fragment fragment = new RandomQuestionStartFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Intent newQuestionIntent(Context packageContext){
        Intent intent = new Intent(packageContext, RandomQuestionPagerActivity.class);
        return intent;
    }
}
