package com.dariapro.trainyourcountingskills.activity;

import android.os.Bundle;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.adapter.ScorePagerAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

/**
 * @author Pleshchankova Daria
 *
 */
public class ScoresActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ScorePagerAdapter scorePagerAdapter;
    private TabItem tabQuestionScores;
    private TabItem tabExpressionScores;
    private TabItem tabPercentsScores;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores_activity);
        tabLayout = findViewById(R.id.scores_tab_layout);
        tabQuestionScores = findViewById(R.id.question_scores_tab);
        tabExpressionScores = findViewById(R.id.expression_scores_tab);
        tabPercentsScores = findViewById(R.id.percents_scores_tab);
        viewPager = findViewById(R.id.scores_pager);

        scorePagerAdapter = new ScorePagerAdapter(getSupportFragmentManager(),
                getApplicationContext(), tabLayout.getTabCount());
        viewPager.setAdapter(scorePagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

}
