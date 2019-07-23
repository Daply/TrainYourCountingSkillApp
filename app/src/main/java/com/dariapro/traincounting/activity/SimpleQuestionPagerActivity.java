package com.dariapro.traincounting.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.dariapro.traincounting.R;
import com.dariapro.traincounting.entity.Question;
import com.dariapro.traincounting.entity.QuestionType;
import com.dariapro.traincounting.exception.ExtraIsNullException;
import com.dariapro.traincounting.fragment.QuestionFragment;
import com.dariapro.traincounting.view.model.QuestionViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pleshchankova Daria
 *
 */
public class SimpleQuestionPagerActivity extends FragmentActivity {

    private ViewPager viewPager = null;

    private List<Question> questions = null;

    private Question currentQuestion = null;
    private long currentQuestionId = 0;

    private String modeValue = null;
    private long levelId = 0;

    protected Fragment createFragment() {
        Bundle args = new Bundle();
        if (modeValue != null) {
            args.putString(getApplicationContext().getString(R.string.MODE), modeValue);
        }
        if (currentQuestion != null) {
            args.putSerializable(getApplicationContext().getString(R.string.ARG_QUESTION), currentQuestion);
        }
        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setArguments(args);
        return questionFragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getExtras();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_pager);

        initData();

        viewPager = findViewById(R.id.activity_question_view_pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                if (questions != null && !questions.isEmpty()) {
                    currentQuestion = questions.get(position);
                }
                return createFragment();
            }

            @Override
            public int getCount() {
                if(questions.size() == 0){
                    return 1;
                }
                return questions.size();
            }
        });
        setItemPager();
    }

    private void getExtras() {
        Bundle args = getIntent().getExtras();
        if (args != null) {
            try {
                modeValue = args.getString(getApplicationContext().getString(R.string.MODE));
                if (modeValue == null){
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
                currentQuestionId = args.getLong(getApplicationContext().getString(R.string.EXTRA_QUESTION_ID));
                if (currentQuestionId == 0){
                    throw new ExtraIsNullException("Extra " +
                            getApplicationContext().getString(R.string.EXTRA_QUESTION_ID) +
                            " is equal 0 in " + getClass().getName());
                }
            }
            catch (ExtraIsNullException e) {
                Log.e(getApplicationContext().getString(R.string.TAG),
                        getApplicationContext().getString(R.string.MODE) + " or " +
                                getApplicationContext().getString(R.string.EXTRA_LEVEL_ID) + " or " +
                                getApplicationContext().getString(R.string.EXTRA_QUESTION_ID) +
                                " didn't passed");
            }
        }
    }

    public void setItemPager() {
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getQuestionId() == currentQuestionId) {
                viewPager.setCurrentItem(i);
                break;
            }
        }
        if(questions.size() == 0){
            viewPager.setCurrentItem(0);
        }
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        setItemPager();
    }

    public int getCurrentQuestion() {
        return this.viewPager.getCurrentItem();
    }

    public void setCurrentQuestion(int position) {
        if (position < questions.size()) {
            currentQuestionId = questions.get(position).getQuestionId();
        }
        this.viewPager.setCurrentItem(position);
    }

    private void initData() {
        this.questions = new ArrayList<>();
        QuestionViewModel questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getQuestionListByLevelSorted(levelId).observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(@Nullable List<Question> questions) {
                setQuestions(questions);
            }
        });
    }

}
