package com.dariapro.traincounting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.dariapro.traincounting.R;
import com.dariapro.traincounting.dao.ExampleLab;
import com.dariapro.traincounting.entity.Question;
import com.dariapro.traincounting.fragment.ExampleFragment;

import java.util.List;
import java.util.UUID;

public class ExamplesPagerActivity extends AppCompatActivity {


    public static final String EXTRA_EXAMPLE_ID = "com.dariapro.traincounting.example_id";

    private ViewPager viewPager;
    private List<Question> questions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_pager);

        final long questionId = (long) getIntent().getSerializableExtra(EXTRA_EXAMPLE_ID);

        viewPager = findViewById(R.id.activity_question_view_pager);

        questions = ExampleLab.get(this).getExamples();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                if(questionId != 0){
                    Question example = questions.get(position);
                    return ExampleFragment.newInstance(example.getQuestionId());
                }
                else{
                    return ExampleFragment.newInstance(questionId);
                }
            }

            @Override
            public int getCount() {
                if(questions.size() == 0){
                    return 1;
                }
                return questions.size();
            }
        });

        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getQuestionId() == questionId) {
                viewPager.setCurrentItem(i);
                break;
            }
        }
        if(questions.size() == 0){
            viewPager.setCurrentItem(0);
        }
    }

    public static Intent newIntent(Context packageContext, UUID recipeId){
        Intent intent = new Intent(packageContext, ExamplesPagerActivity.class);
        intent.putExtra(EXTRA_EXAMPLE_ID, recipeId);
        return intent;
    }

}
