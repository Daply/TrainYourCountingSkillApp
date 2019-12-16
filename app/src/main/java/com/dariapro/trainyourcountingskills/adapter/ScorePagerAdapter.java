package com.dariapro.trainyourcountingskills.adapter;

import android.content.Context;
import android.os.Bundle;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.entity.QuestionType;
import com.dariapro.trainyourcountingskills.fragment.list.ScoresListFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @author Pleshchankova Daria
 *
 */
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
        ScoresListFragment fragment = new ScoresListFragment();
        String questionType = null;
        switch (position) {
            case 0:
                questionType = QuestionType.QUESTION.name();
                break;
            case 1:
                questionType = QuestionType.EXPRESSION.name();
                break;
            case 2:
                questionType = QuestionType.PERCENTS_PROBLEM.name();
                break;
            default:
                questionType = new String();
        }
        bundle.putString(context.getString(R.string.QUESTION_TYPE), questionType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
