package com.dariapro.traincounting.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.TextView;

import com.dariapro.traincounting.Extras;
import com.dariapro.traincounting.R;
import com.dariapro.traincounting.entity.Record;
import com.dariapro.traincounting.fragment.QuestionFragment;
import com.dariapro.traincounting.view.model.RecordViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pleshchankova Daria
 *
 */
public class RandomQuestionPagerActivity extends FragmentActivity {

    private String modeValue = null;
    private boolean expression = false;

    private int level = 0;
    private int time = 0;

    private boolean plusOperator = false;
    private boolean minusOperator = false;
    private boolean multiplyOperator = false;
    private boolean divideOperator = false;

    private TextView timerView;
    private CountDownTimer timer;
    private long milliseconds = 0;

    private TextView scoreView;
    private TextView bestScoreView;
    private int countNumberOfAnsweredQuestions = 0;

    private ViewPager pager = null;
    private QuestionFragmentPagerAdapter pagerAdapter = null;

    private RecordViewModel recordViewModel;
    private Record currentRecord;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.question, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getExtras();
        initData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

        timerView = findViewById(R.id.timer);
        startTimer();

        pager = (ViewPager) findViewById(R.id.rand_q_view_pager);
        pagerAdapter = new QuestionFragmentPagerAdapter(getSupportFragmentManager(),
                                                        level,
                                                        modeValue,
                                                        expression,
                                                        plusOperator,
                                                        minusOperator,
                                                        multiplyOperator,
                                                        divideOperator);
        pager.setAdapter(pagerAdapter);
    }

    private void getExtras() {
        modeValue = getIntent().getExtras().getString(Extras.MODE);
        expression = getIntent().getExtras().getBoolean(Extras.EXPRESSION_EXTRA);
        level = getIntent().getExtras().getInt(Extras.LEVEL_EXTRA);
        time = getIntent().getExtras().getInt(Extras.TIME_EXTRA);
        milliseconds = time * 60000;
        plusOperator = getIntent().getExtras().getBoolean(Extras.PLUS_EXTRA);
        minusOperator = getIntent().getExtras().getBoolean(Extras.MINUS_EXTRA);
        multiplyOperator = getIntent().getExtras().getBoolean(Extras.MULTIPLY_EXTRA);
        divideOperator = getIntent().getExtras().getBoolean(Extras.DIVIDE_EXTRA);
    }

    private void initData() {
        recordViewModel = ViewModelProviders.of(this).get(RecordViewModel.class);
        if (expression) {
            this.currentRecord = recordViewModel.getRecordByLevelAndType(this.level, 1);
        }
        else {
            this.currentRecord = recordViewModel.getRecordByLevelAndType(this.level, 0);
        }
    }

    public void startTimer() {
        timer = new CountDownTimer(milliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                milliseconds = millisUntilFinished;
                updateTimer(false);
            }

            @Override
            public void onFinish() {
                updateTimer(true);
            }
        }.start();
    }

    public void stopTimer() {
        timer.cancel();
    }

    public void updateTimer(boolean finish) {
        int minutes = (int) (milliseconds / 60000);
        int seconds = (int) (milliseconds % 60000 / 1000);
        if (finish) {
            seconds = 0;
        }
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
        if (finish) {
            setScoreView();
        }
    }

    public void setScoreView() {
        setContentView(R.layout.score);
        scoreView = findViewById(R.id.score_view);
        scoreView.setText("Your score is " + this.countNumberOfAnsweredQuestions +
                          " questions in " + this.time + " minute(s)");
        bestScoreView = findViewById(R.id.best_score_view);
        if (this.currentRecord != null) {
            double bestCoefficient = (double) this.currentRecord.getNumberOfQuestions()/
                    (double) this.currentRecord.getTime();
            double currentCoefficient = (double) this.countNumberOfAnsweredQuestions/
                    (double) this.time;
            if (bestCoefficient > currentCoefficient) {
                bestScoreView.setText("Best score is " +
                        this.currentRecord.getNumberOfQuestions() +
                        " questions in " + this.currentRecord.getTime() + " minute(s)");
            }
            else {
                bestScoreView.setText("New Best Score!");
            }
        }
        else {
            recordViewModel = ViewModelProviders.of(this).get(RecordViewModel.class);
            Record newRecord = new Record();
            newRecord.setNumberOfQuestions(this.countNumberOfAnsweredQuestions);
            newRecord.setTime(this.time);
            newRecord.setLevel(this.level);
            if (expression) {
                newRecord.setType(1);
            }
            else {
                newRecord.setType(0);
            }
            recordViewModel.insert(newRecord);
            bestScoreView.setText("New Best Score!");
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

    public void increaseScore() {
        this.countNumberOfAnsweredQuestions++;
    }

    private class QuestionFragmentPagerAdapter extends FragmentPagerAdapter {

        private int level;
        private String modeValue = null;
        private boolean expression = false;

        private boolean plus = false;
        private boolean minus = false;
        private boolean multiply = false;
        private boolean divide = false;

        private long baseId = 0;

        public QuestionFragmentPagerAdapter(FragmentManager fm, int level,
                                            String modeValue,
                                            boolean expression,
                                            boolean plus,
                                            boolean minus,
                                            boolean multiply,
                                            boolean divide) {
            super(fm);
            this.level = level;
            this.modeValue = modeValue;
            this.expression = expression;
            this.plus = plus;
            this.minus = minus;
            this.multiply = multiply;
            this.divide = divide;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString(Extras.MODE, this.modeValue);
            bundle.putInt(Extras.LEVEL_EXTRA, this.level);
            bundle.putBoolean(Extras.EXPRESSION_EXTRA, expression);
            bundle.putBoolean(Extras.PLUS_EXTRA, plus);
            bundle.putBoolean(Extras.MINUS_EXTRA, minus);
            bundle.putBoolean(Extras.MULTIPLY_EXTRA, multiply);
            bundle.putBoolean(Extras.DIVIDE_EXTRA, divide);
            Fragment fragment = new QuestionFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public long getItemId(int position) {
            return baseId + position;
        }

        @Override
        public int getCount() {
            return 1;
        }

        public void notifyChangeInPosition(int n) {
            baseId += getCount() + n;
        }
    }
}
