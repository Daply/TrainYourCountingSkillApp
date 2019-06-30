package com.dariapro.traincounting.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dariapro.traincounting.Extras;
import com.dariapro.traincounting.R;
import com.dariapro.traincounting.fragment.QuestionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pleshchankova Daria
 *
 */
public class RandomQuestionPagerActivity extends FragmentActivity {

    private String modeValue = null;
    private int level = 0;
    private int time = 0;

    public static final String TAG = "exLogs";
    public static int PAGE_COUNT = 10;

    private TextView timerView;
    private CountDownTimer timer;
    long milliseconds = 60000;

    private ViewPager pager = null;
    private QuestionFragmentPagerAdapter pagerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        modeValue = getIntent().getExtras().getString(Extras.MODE);
        level = getIntent().getExtras().getInt(Extras.LEVEL_SEEKBAR_PROGRESS);
        time = getIntent().getExtras().getInt(Extras.TIME_SEEKBAR_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

        timerView = findViewById(R.id.timer);
        startTimer();

        pager = (ViewPager) findViewById(R.id.rand_q_view_pager);
        pagerAdapter = new QuestionFragmentPagerAdapter(getSupportFragmentManager(), modeValue);
        pager.setAdapter(pagerAdapter);
    }

    public void startTimer() {
        timer = new CountDownTimer(milliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                milliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void stopTimer() {
        timer.cancel();
    }

    public void updateTimer() {
        int minutes = (int) (milliseconds / 60000);
        int seconds = (int) (milliseconds % 60000 / 1000);
        String minutesStr = String.valueOf(minutes);
        String secondsStr = String.valueOf(seconds);
        if (minutes < 10) {
            minutesStr = "0" + minutes;
        }
        if (seconds < 10) {
            secondsStr = "0" + seconds;
        }
        String timerViewText = minutesStr + ":" + secondsStr;
        timerView.setText(timerViewText);
        if (minutes == 0 && seconds == 0) {
            stopTimer();
        }
    }

    public int getCurrentQuestion() {
        return this.pager.getCurrentItem();
    }

    public void setCurrentQuestion(int position) {
        this.pager.setCurrentItem(position);
    }

    public void removePreviousQuestion() {
        int position = pager.getCurrentItem();
        pagerAdapter.notifyDataSetChanged();
        pagerAdapter.notifyChangeInPosition(position);
    }

    private class QuestionFragmentPagerAdapter extends FragmentPagerAdapter {

        private String modeValue = null;
        private long baseId = 0;

        public QuestionFragmentPagerAdapter(FragmentManager fm, String modeValue) {
            super(fm);
            this.modeValue = modeValue;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString(Extras.MODE, this.modeValue);
            Fragment fragment = new QuestionFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getItemPosition(Object object) {
            // refresh all fragments when data set changed
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public long getItemId(int position) {
            // give an ID different from position when position has been changed
            return baseId + position;
        }

        @Override
        public int getCount() {
            return 1;
        }

        public void notifyChangeInPosition(int n) {
            // shift the ID returned by getItemId outside the range of all previous fragments
            baseId += getCount() + n;
        }
    }
}
