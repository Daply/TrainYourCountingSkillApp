package com.dariapro.trainyourcountingskills.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.activity.AnswerActivity;
import com.dariapro.trainyourcountingskills.activity.pager.RandomQuestionPagerActivity;
import com.dariapro.trainyourcountingskills.activity.pager.SimpleQuestionPagerActivity;
import com.dariapro.trainyourcountingskills.entity.Mode;
import com.dariapro.trainyourcountingskills.entity.Question;
import com.dariapro.trainyourcountingskills.entity.QuestionType;
import com.dariapro.trainyourcountingskills.exception.ExtraIsNullException;
import com.dariapro.trainyourcountingskills.random.arguments.GeneratorArguments;
import com.dariapro.trainyourcountingskills.random.provider.ExpressionGeneratorProvider;
import com.dariapro.trainyourcountingskills.random.provider.GeneratorProvider;
import com.dariapro.trainyourcountingskills.random.provider.ProblemGeneratorProvider;
import com.dariapro.trainyourcountingskills.random.provider.QuestionGeneratorProvider;
import com.dariapro.trainyourcountingskills.random.generator.RandomGenerator;
import com.dariapro.trainyourcountingskills.viewmodel.QuestionViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

/**
 * @author Pleshchankova Daria
 *
 */
public class QuestionFragment extends Fragment implements View.OnClickListener{

    public static final int REQUEST_EVENT = 1;

    private String modeValue = null;
    private long currentQuestionId = 0;
    private Question question = null;

    private TextView questionTitle = null;
    private TextView problemTask = null;
    private Button answerButton = null;
    private Button skipButton = null;
    private Button getAnswerButton = null;
    private EditText answerField = null;
    private String answer = null;

    private Button oneButton = null;
    private Button twoButton = null;
    private Button threeButton = null;
    private Button fourButton = null;
    private Button fiveButton = null;
    private Button sixButton = null;
    private Button sevenButton = null;
    private Button eightButton = null;
    private Button nineButton = null;
    private Button zeroButton = null;
    private Button clearAllButton = null;
    private ImageButton deleteButton = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.question_fragment, menu);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getExtras();

        final View view = inflater.inflate(R.layout.question_fragment, container,false);

        initData();

        questionTitle = view.findViewById(R.id.question_title);
        LinearLayout questionTitleLayout = view.findViewById(R.id.question_title_layout);
        if (modeValue.equals(Mode.RANDOM.name())) {
            questionTitleLayout.setVisibility(View.GONE);
        }
        else {
            questionTitle.setText(question.getTitle());
        }
        problemTask = view.findViewById(R.id.question_expression);
        problemTask.setText(question.getQuestion());
        problemTask.setMovementMethod(new ScrollingMovementMethod());
        answerField = view.findViewById(R.id.question_answer);
        answerField.setFocusable(false);
        answerButton = view.findViewById(R.id.question_answer_button);
        answerButton.setOnClickListener(new View.OnClickListener(){
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                answer = answerField.getText().toString();
                if (answer.equals(question.getRightAnswer())) {
                    answerButton.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),
                            R.color.colorGreen));
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (modeValue.equals(Mode.RANDOM.name())) {
                                shiftRandomQuestion(false);
                            }
                            else if (modeValue.equals(Mode.SIMPLE.name())) {
                                shiftSimpleQuestion();
                            }
                        }
                    }, 500);
                }
                else {
                    answerButton.setBackgroundTintList(ContextCompat.getColorStateList(getContext(),
                            R.color.colorRed));
                }
            }
        });
        skipButton = view.findViewById(R.id.skip_button);
        getAnswerButton = view.findViewById(R.id.see_answer_button);
        if (modeValue.equals(Mode.SIMPLE.name())) {
            skipButton.setVisibility(View.GONE);
            getAnswerButton.setOnClickListener(this);
        }
        if (modeValue.equals(Mode.RANDOM.name())) {
            skipButton.setOnClickListener(this);
            getAnswerButton.setVisibility(View.GONE);
        }

        oneButton = view.findViewById(R.id.one_button);
        oneButton.setOnClickListener(this);
        twoButton = view.findViewById(R.id.two_button);
        twoButton.setOnClickListener(this);
        threeButton = view.findViewById(R.id.three_button);
        threeButton.setOnClickListener(this);
        fourButton = view.findViewById(R.id.four_button);
        fourButton.setOnClickListener(this);
        fiveButton = view.findViewById(R.id.five_button);
        fiveButton.setOnClickListener(this);
        sixButton = view.findViewById(R.id.six_button);
        sixButton.setOnClickListener(this);
        sevenButton = view.findViewById(R.id.seven_button);
        sevenButton.setOnClickListener(this);
        eightButton = view.findViewById(R.id.eight_button);
        eightButton.setOnClickListener(this);
        nineButton = view.findViewById(R.id.nine_button);
        nineButton.setOnClickListener(this);
        zeroButton = view.findViewById(R.id.zero_button);
        zeroButton.setOnClickListener(this);

        deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(this);
        clearAllButton = view.findViewById(R.id.clear_all_button);
        clearAllButton.setOnClickListener(this);

        return view;
    }

    private void getExtras() {
        Bundle args = getArguments();
        if (args != null) {
            try {
                this.modeValue = args.getString(getContext().getString(R.string.MODE));
                if (modeValue == null){
                    throw new ExtraIsNullException("Extra " +
                            getContext().getString(R.string.MODE) +
                            " is null in " + getClass().getName());
                }
            }
            catch (ExtraIsNullException e) {
                Log.e(getContext().getString(R.string.TAG),
                        getContext().getString(R.string.MODE) + " didn't passed");
            }
            if (this.modeValue.equals(Mode.RANDOM.name())) {
                QuestionType questionType = QuestionType.valueOf(args.getString(getContext()
                        .getString(R.string.QUESTION_TYPE)));
                int level = args.getInt(getContext().getString(R.string.LEVEL_EXTRA));
                setGeneratedQuestion(args, questionType, level);
            }
            if (this.modeValue.equals(Mode.SIMPLE.name())) {
                try {
                    this.currentQuestionId = args.getLong(getContext().getString(R.string.EXTRA_QUESTION_ID));
                    if (currentQuestionId == 0){
                        throw new ExtraIsNullException("Extra " +
                                getContext().getString(R.string.EXTRA_QUESTION_ID) +
                                " is null in " + getClass().getName());
                    }
                }
                catch (ExtraIsNullException e) {
                    Log.e(getContext().getString(R.string.TAG),
                            getContext().getString(R.string.ARG_QUESTION) + " didn't passed");
                }
            }
        }
    }

    private void setGeneratedQuestion(Bundle args, QuestionType questionType, int level) {
        RandomGenerator randomGenerator = new RandomGenerator();
        GeneratorArguments genArgs = new GeneratorArguments();
        genArgs.setLevel(level);
        GeneratorProvider generatorProvider = null;
        if (questionType.equals(QuestionType.PERCENTS_PROBLEM)) {
            generatorProvider = new ProblemGeneratorProvider();
            question = randomGenerator.generate(genArgs, generatorProvider);
        }
        else if (questionType.equals(QuestionType.EXPRESSION)) {
            genArgs.setPlus(true);
            genArgs.setMinus(true);
            genArgs.setMultiply(true);
            genArgs.setDivide(true);
            generatorProvider = new ExpressionGeneratorProvider();
            question = randomGenerator.generate(genArgs, generatorProvider);
        } else if (questionType.equals(QuestionType.QUESTION)){
            generatorProvider = new QuestionGeneratorProvider();
            genArgs.setPlus(args.getBoolean(getContext()
                    .getString(R.string.PLUS_EXTRA)));
            genArgs.setMinus(args.getBoolean(getContext()
                    .getString(R.string.MINUS_EXTRA)));
            genArgs.setMultiply(args.getBoolean(getContext()
                    .getString(R.string.MULTIPLY_EXTRA)));
            genArgs.setDivide(args.getBoolean(getContext()
                    .getString(R.string.DIVIDE_EXTRA)));
            genArgs.setRoot(args.getBoolean(getContext()
                    .getString(R.string.ROOT_EXTRA)));
            question = randomGenerator.generate(genArgs, generatorProvider);
        }
    }

    private void initData() {
        if (this.modeValue.equals(Mode.SIMPLE.name())) {
            QuestionViewModel questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
            this.question = questionViewModel.getQuestionById(this.currentQuestionId);
        }
    }

    private void setQuestionPassed(Question question) {
        question.setPassed(true);
        QuestionViewModel questionViewModel = ViewModelProviders
                .of(this).get(QuestionViewModel.class);
        questionViewModel.update(question);
    }

    private void clearFields() {
        answerField.setText(new String());
    }

    private void shiftRandomQuestion(boolean skip) {
        RandomQuestionPagerActivity activity = (RandomQuestionPagerActivity) getActivity();
        if (activity != null) {
            int position = activity.getCurrentQuestion();
            activity.setCurrentQuestion(position + 1);
            activity.removePreviousQuestion();
            if (!skip) {
                clearFields();
                activity.increaseScore();
            }
        }
    }

    private void shiftSimpleQuestion() {
        SimpleQuestionPagerActivity activity = (SimpleQuestionPagerActivity) getActivity();
        answerField.setEnabled(false);
        setQuestionPassed(question);
        // Saved for different future occurrences
        //if (activity != null) {
            //int position = activity.getCurrentQuestion();
            //activity.setCurrentQuestion(position + 1);
        //}
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skip_button:
                shiftRandomQuestion(true);
                break;
            case R.id.see_answer_button:
                switchToAnswerActivity();
                break;
            case R.id.one_button:
                setText(1);
                break;
            case R.id.two_button:
                setText(2);
                break;
            case R.id.three_button:
                setText(3);
                break;
            case R.id.four_button:
                setText(4);
                break;
            case R.id.five_button:
                setText(5);
                break;
            case R.id.six_button:
                setText(6);
                break;
            case R.id.seven_button:
                setText(7);
                break;
            case R.id.eight_button:
                setText(8);
                break;
            case R.id.nine_button:
                setText(9);
                break;
            case R.id.zero_button:
                setText(0);
                break;
            case R.id.delete_button:
                cutText();
                break;
            case R.id.clear_all_button:
                answerField.setText(new String());
                break;
        }
    }

    public void setText(int number) {
        String text = answerField.getText().toString();
        text = text + number;
        answerField.setText(text);
        answerField.moveCursorToVisibleOffset();
    }

    private void cutText() {
        String text = answerField.getText().toString();
        if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
            answerField.setText(text);
            answerField.moveCursorToVisibleOffset();
        }
    }

    private void switchToAnswerActivity() {
        Intent intent = new Intent(getContext(), AnswerActivity.class);
        intent.putExtra(getContext().getString(R.string.ARG_QUESTION), question);
        startActivityForResult(intent, REQUEST_EVENT);
    }
}
