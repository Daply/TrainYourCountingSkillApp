package com.dariapro.traincounting.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.dariapro.traincounting.R;
import com.dariapro.traincounting.dao.QuestionDao;
import com.dariapro.traincounting.database.AppDatabase;
import com.dariapro.traincounting.entity.Question;
import com.dariapro.traincounting.fragment.ExampleFragment;
import com.dariapro.traincounting.view.model.QuestionViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProblemsPagerActivity extends FragmentActivity {

    public static final String EXTRA_EXAMPLE_ID = "com.dariapro.traincounting.example_id";
    private static final String ARG_EXAMPLE = "com.dariapro.traincounting.example";

    private ViewPager viewPager;

    private QuestionViewModel questionViewModel;
    private List<Question> questions;

    private long currentQuestionId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_pager);

        final long questionId = (long) getIntent().getSerializableExtra(EXTRA_EXAMPLE_ID);
        currentQuestionId = questionId;
        viewPager = findViewById(R.id.activity_question_view_pager);
        final FragmentManager fragmentManager = getSupportFragmentManager();
         viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                ExampleFragment exampleFragment = new ExampleFragment();
                if(questionId != 0){
                    Question example = questions.get(position);
                    return exampleFragment.newInstance(example, "simple");
                }
                return exampleFragment;
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

    public ViewPager getPager() {
        return viewPager;
    }

    private void initData() {
        this.questions = new ArrayList<>();
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getQuestionList().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(@Nullable List<Question> questions) {
                setQuestions(questions);
            }
        });
    }

    public static Intent newIntent(Context packageContext, UUID recipeId){
        Intent intent = new Intent(packageContext, ProblemsPagerActivity.class);
        intent.putExtra(EXTRA_EXAMPLE_ID, recipeId);
        return intent;
    }

}
