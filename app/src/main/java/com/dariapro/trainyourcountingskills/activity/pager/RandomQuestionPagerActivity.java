package com.dariapro.trainyourcountingskills.activity.pager;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.activity.ScoresActivity;
import com.dariapro.trainyourcountingskills.dialog.ResultDialog;
import com.dariapro.trainyourcountingskills.entity.QuestionType;
import com.dariapro.trainyourcountingskills.entity.Record;
import com.dariapro.trainyourcountingskills.exception.ExtraIsNullException;
import com.dariapro.trainyourcountingskills.fragment.QuestionFragment;
import com.dariapro.trainyourcountingskills.viewmodel.RecordViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @author Pleshchankova Daria
 *
 */
public class RandomQuestionPagerActivity extends FragmentActivity {

    public static final int REQUEST_EVENT = 1;

    private final long SECOND = 1000;
    private final long MINUTE = 60000;

    private String modeValue = null;
    private QuestionType questionType = null;

    private int level = 0;
    private int time = 0;

    private boolean plusOperator = false;
    private boolean minusOperator = false;
    private boolean multiplyOperator = false;
    private boolean divideOperator = false;
    private boolean rootOperator = false;

    private TextView timerView = null;
    private CountDownTimer timer = null;
    private long milliseconds = 0;

    private int countNumberOfAnsweredQuestions = 0;

    private ViewPager viewPager = null;
    private QuestionFragmentPagerAdapter pagerAdapter = null;

    private AlertDialog resultDialog = null;

    private RecordViewModel recordViewModel = null;
    private Record currentRecord = null;

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

        viewPager = findViewById(R.id.rand_q_view_pager);
        pagerAdapter = new QuestionFragmentPagerAdapter(getSupportFragmentManager(),
                                                        level,
                                                        modeValue,
                                                        questionType,
                                                        plusOperator,
                                                        minusOperator,
                                                        multiplyOperator,
                                                        divideOperator,
                                                        rootOperator);
        viewPager.setAdapter(pagerAdapter);
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
                questionType = QuestionType.valueOf(args.getString(getApplicationContext()
                        .getString(R.string.QUESTION_TYPE)));
                level = args.getInt(getApplicationContext().getString(R.string.LEVEL_EXTRA));
                if (level == 0){
                    throw new ExtraIsNullException("Extra " +
                            getApplicationContext().getString(R.string.LEVEL_EXTRA) +
                            " is equal 0 in " + getClass().getName());
                }
                time = args.getInt(getApplicationContext().getString(R.string.TIME_EXTRA));
                if (level == 0){
                    throw new ExtraIsNullException("Extra " +
                            getApplicationContext().getString(R.string.TIME_EXTRA) +
                            " is equal 0 in " + getClass().getName());
                }
                milliseconds = time * MINUTE;
                plusOperator = args.getBoolean(getApplicationContext().getString(R.string.PLUS_EXTRA));
                minusOperator = args.getBoolean(getApplicationContext().getString(R.string.MINUS_EXTRA));
                multiplyOperator = args.getBoolean(getApplicationContext().getString(R.string.MULTIPLY_EXTRA));
                divideOperator = args.getBoolean(getApplicationContext().getString(R.string.DIVIDE_EXTRA));
                rootOperator = args.getBoolean(getApplicationContext().getString(R.string.ROOT_EXTRA));
            }
            catch (ExtraIsNullException e) {
                Log.e(getApplicationContext().getString(R.string.TAG),
                        getApplicationContext().getString(R.string.MODE) + " or " +
                                getApplicationContext().getString(R.string.LEVEL_EXTRA) + " or " +
                                getApplicationContext().getString(R.string.TIME_EXTRA) +
                                " didn't passed");
            }
        }
    }

    private void initData() {
        recordViewModel = ViewModelProviders.of(this).get(RecordViewModel.class);
        this.currentRecord = recordViewModel
                .getRecordByLevelAndTypeAndTime(this.level, questionType.ordinal(), this.time);
    }

    public void startTimer() {
        timer = new CountDownTimer(milliseconds, SECOND) {
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
        int minutes = (int) (milliseconds / MINUTE);
        int seconds = (int) (milliseconds % MINUTE / SECOND);
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
        TextView scoreView = findViewById(R.id.score_view);
        String currentScore = getApplicationContext()
                .getResources()
                .getString(R.string.your_score) +
                " " + this.countNumberOfAnsweredQuestions + " " +
                getApplicationContext()
                        .getResources()
                        .getString(R.string.score_questions) +
                " " + this.time + " " +
                getApplicationContext()
                        .getResources()
                        .getString(R.string.score_time_scale);
        scoreView.setText(currentScore);
        TextView bestScoreView = findViewById(R.id.best_score_view);
        bestScoreView.setVisibility(View.GONE);
        Button goToMainScreenButton = findViewById(R.id.view_scores);
        goToMainScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        ScoresActivity.class);
                startActivityForResult(intent, REQUEST_EVENT);
            }
        });
        if (this.currentRecord != null) {
            if (this.currentRecord.getNumberOfQuestions() > this.countNumberOfAnsweredQuestions) {
                String bestScore = getApplicationContext()
                        .getResources()
                        .getString(R.string.your_best_score) +
                        " " + this.currentRecord.getNumberOfQuestions() + " " +
                        getApplicationContext()
                                .getResources()
                                .getString(R.string.score_questions) +
                        " " + this.currentRecord.getTime() + " " +
                        getApplicationContext()
                                .getResources()
                                .getString(R.string.score_time_scale);
                bestScoreView.setVisibility(View.VISIBLE);
                bestScoreView.setText(bestScore);
            }
            else {
                if (this.countNumberOfAnsweredQuestions > 0) {
                    ResultDialog congratsDialog =
                            new ResultDialog(RandomQuestionPagerActivity.this);
                    congratsDialog.show();
                    addNewRecord();
                }
            }
        }
        else {
            if (this.countNumberOfAnsweredQuestions > 0) {
                ResultDialog resultDialog =
                        new ResultDialog(RandomQuestionPagerActivity.this);
                resultDialog.show();
                addNewRecord();
            }
        }
    }

    public void addNewRecord() {
        recordViewModel = ViewModelProviders.of(this).get(RecordViewModel.class);
        if (this.currentRecord != null) {
            recordViewModel.delete(this.currentRecord);
        }
        Record newRecord = new Record();
        newRecord.setNumberOfQuestions(this.countNumberOfAnsweredQuestions);
        newRecord.setTime(this.time);
        newRecord.setLevel(this.level);
        newRecord.setType(questionType.ordinal());
        recordViewModel.insert(newRecord);
    }

    public int getCurrentQuestion() {
        return this.viewPager.getCurrentItem();
    }

    public void setCurrentQuestion(int position) {
        this.viewPager.setCurrentItem(position);
    }

    public void removePreviousQuestion() {
        int position = viewPager.getCurrentItem();
        pagerAdapter.notifyDataSetChanged();
        pagerAdapter.notifyChangeInPosition(position);
    }

    public void increaseScore() {
        this.countNumberOfAnsweredQuestions++;
    }

    private class QuestionFragmentPagerAdapter extends FragmentPagerAdapter {

        private int level;
        private String modeValue;
        private QuestionType questionType;

        private boolean plus;
        private boolean minus;
        private boolean multiply;
        private boolean divide;
        private boolean root;

        private long baseId = 0;

        private QuestionFragmentPagerAdapter(FragmentManager fm,
                                            int level,
                                            String modeValue,
                                            QuestionType questionType,
                                            boolean plus,
                                            boolean minus,
                                            boolean multiply,
                                            boolean divide,
                                             boolean root) {
            super(fm);
            this.level = level;
            this.modeValue = modeValue;
            this.questionType = questionType;
            this.plus = plus;
            this.minus = minus;
            this.multiply = multiply;
            this.divide = divide;
            this.root = root;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString(getApplicationContext().getString(R.string.MODE), this.modeValue);
            bundle.putInt(getApplicationContext().getString(R.string.LEVEL_EXTRA), this.level);
            bundle.putString(getApplicationContext().getString(R.string.QUESTION_TYPE),
                                                                     questionType.name());
            bundle.putBoolean(getApplicationContext().getString(R.string.PLUS_EXTRA), plus);
            bundle.putBoolean(getApplicationContext().getString(R.string.MINUS_EXTRA), minus);
            bundle.putBoolean(getApplicationContext().getString(R.string.MULTIPLY_EXTRA), multiply);
            bundle.putBoolean(getApplicationContext().getString(R.string.DIVIDE_EXTRA), divide);
            bundle.putBoolean(getApplicationContext().getString(R.string.ROOT_EXTRA), root);
            Fragment fragment = new QuestionFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
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

        private void notifyChangeInPosition(int n) {
            baseId += getCount() + n;
        }
    }
}
