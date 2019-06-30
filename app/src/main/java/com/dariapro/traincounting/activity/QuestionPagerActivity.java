package com.dariapro.traincounting.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.dariapro.traincounting.Extras;
import com.dariapro.traincounting.R;
import com.dariapro.traincounting.entity.Question;
import com.dariapro.traincounting.fragment.QuestionFragment;
import com.dariapro.traincounting.view.model.QuestionViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pleshchankova Daria
 *
 */
public class QuestionPagerActivity extends FragmentActivity {

    private ViewPager viewPager;

    private QuestionViewModel questionViewModel;
    private List<Question> questions;

    private long currentQuestionId = 0;

    private String modeValue = null;
    private long levelId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        modeValue = getIntent().getExtras().getString(Extras.MODE);
        levelId = getIntent().getExtras().getLong(Extras.EXTRA_LEVEL_ID);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_pager);

        initData();

        final long questionId = (long) getIntent().getSerializableExtra(Extras.EXTRA_QUESTION_ID);
        currentQuestionId = questionId;
        viewPager = findViewById(R.id.activity_question_view_pager);
        final FragmentManager fragmentManager = getSupportFragmentManager();
         viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                if(questionId != 0 && !questions.isEmpty()){
                    Question question = questions.get(position);
                    return QuestionFragment.newInstance(question, modeValue);
                }
                Bundle bundle = new Bundle();
                bundle.putString(Extras.MODE, modeValue);
                Fragment questionFragment = new QuestionFragment();
                questionFragment.setArguments(bundle);
                return questionFragment;
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

    public void setItemPager() {
        viewPager.getAdapter().notifyDataSetChanged();
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
        this.viewPager.setCurrentItem(position);
    }

    private void initData() {
        this.questions = new ArrayList<>();
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getQuestionListByLevel(levelId).observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(@Nullable List<Question> questions) {
                setQuestions(questions);
            }
        });
    }

}
