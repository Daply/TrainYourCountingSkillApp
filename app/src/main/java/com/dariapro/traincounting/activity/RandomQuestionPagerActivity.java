package com.dariapro.traincounting.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.dariapro.traincounting.Extras;
import com.dariapro.traincounting.R;
import com.dariapro.traincounting.fragment.QuestionFragment;

/**
 * @author Pleshchankova Daria
 *
 */
public class RandomQuestionPagerActivity extends FragmentActivity {

    private String modeValue = null;
    private int level = 0;
    private int time = 0;

    public static final String TAG = "exLogs";
    public static final int PAGE_COUNT = 10;

    private ViewPager pager = null;
    private PagerAdapter pagerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        modeValue = getIntent().getExtras().getString(Extras.MODE);
        level = getIntent().getExtras().getInt(Extras.LEVEL_SEEKBAR_PROGRESS);
        time = getIntent().getExtras().getInt(Extras.TIME_SEEKBAR_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

        pager = (ViewPager) findViewById(R.id.rand_q_view_pager);
        pagerAdapter = new ExampleFragmentPagerAdapter(getSupportFragmentManager(), modeValue);
        pager.setAdapter(pagerAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected, position = " + position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class ExampleFragmentPagerAdapter extends FragmentPagerAdapter {

        public String mode = null;

        public ExampleFragmentPagerAdapter(FragmentManager fm, String mode) {
            super(fm);
            this.mode = mode;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString(Extras.MODE, this.mode);
            Fragment fragment = new QuestionFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

    }

    public ViewPager getPager() {
        return pager;
    }

    public void setPager(ViewPager pager) {
        this.pager = pager;
    }

    public PagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    public void setPagerAdapter(PagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
    }
}
