package com.dariapro.trainyourcountingskills.adapter;

import android.content.Context;
import android.os.Bundle;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.entity.QuestionType;
import com.dariapro.trainyourcountingskills.fragment.QuestionScoresFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ScorePagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private int numOfTabs;

    public ScorePagerAdapter(FragmentManager fm, Context context, int numOfTabs) {
        super(fm);
        this.context = context;
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        QuestionScoresFragment fragment = new QuestionScoresFragment();
        switch (position) {
            case 0:
                bundle.putString(context.getString(R.string.QUESTION_TYPE),
                        QuestionType.QUESTION.name());
                fragment.setArguments(bundle);
                return fragment;
            case 1:
                bundle.putString(context.getString(R.string.QUESTION_TYPE),
                        QuestionType.EXPRESSION.name());
                fragment.setArguments(bundle);
                return fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
