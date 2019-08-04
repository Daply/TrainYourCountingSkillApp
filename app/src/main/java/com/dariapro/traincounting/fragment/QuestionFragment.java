package com.dariapro.traincounting.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dariapro.traincounting.R;
import com.dariapro.traincounting.activity.SimpleQuestionPagerActivity;
import com.dariapro.traincounting.activity.RandomQuestionPagerActivity;
import com.dariapro.traincounting.entity.Mode;
import com.dariapro.traincounting.entity.Question;
import com.dariapro.traincounting.entity.QuestionType;
import com.dariapro.traincounting.exception.ExtraIsNullException;
import com.dariapro.traincounting.random.RandomQuestionGenerator;
import com.dariapro.traincounting.view.model.QuestionViewModel;

/**
 * @author Pleshchankova Daria
 *
 */
public class QuestionFragment extends Fragment implements View.OnClickListener{

    private String modeValue = null;

    private TextView problemTask = null;
    private Button answerButton = null;
    private Button skipButton = null;
    private EditText answerField = null;
    private String answer = null;
    private TextView correctAnswer = null;

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
    private Button deleteButton = null;

    private Question question = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getExtras();

        final View view = inflater.inflate(R.layout.question_fragment, container,false);

        problemTask = view.findViewById(R.id.question_expression);
        problemTask.setText(question.getQuestion());
        answerField = view.findViewById(R.id.question_answer);
        answerField.setFocusable(false);
        answerButton = view.findViewById(R.id.question_answer_button);
        answerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                answer = answerField.getText().toString();
                if (answer.equals(question.getRightAnswer())) {
                    correctAnswer = view.findViewById(R.id.question_answer_result);
                    correctAnswer.setText(getContext().getString(R.string.CORRECT));
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
                    correctAnswer = view.findViewById(R.id.question_answer_result);
                    correctAnswer.setText(getContext().getString(R.string.NOT_CORRECT));
                }
            }
        });
        skipButton = view.findViewById(R.id.skip_button);
        if (modeValue.equals(Mode.SIMPLE.name())) {
            skipButton.setVisibility(View.GONE);
        }
        if (modeValue.equals(Mode.RANDOM.name())) {
            skipButton.setOnClickListener(this);
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
                RandomQuestionGenerator randomQuestionGenerator = new RandomQuestionGenerator();
                if (questionType.equals(QuestionType.EXPRESSION)) {
                    question = randomQuestionGenerator.generateRandomExpression(level);
                } else {
                    boolean plusOperator = args.getBoolean(getContext()
                                                .getString(R.string.PLUS_EXTRA));
                    boolean minusOperator = args.getBoolean(getContext()
                                                .getString(R.string.MINUS_EXTRA));
                    boolean multiplyOperator = args.getBoolean(getContext()
                                                .getString(R.string.MULTIPLY_EXTRA));
                    boolean divideOperator = args.getBoolean(getContext()
                                                .getString(R.string.DIVIDE_EXTRA));
                    boolean rootOperator = args.getBoolean(getContext()
                                                .getString(R.string.ROOT_EXTRA));
                    question = randomQuestionGenerator.generateQuestion(level, plusOperator, minusOperator,
                            multiplyOperator, divideOperator, rootOperator);
                }
            }
            if (this.modeValue.equals(Mode.SIMPLE.name())) {
                try {
                    this.question = (Question) getArguments().getSerializable(getContext()
                                                             .getString(R.string.ARG_QUESTION));
                    if (question == null){
                        throw new ExtraIsNullException("Extra " +
                                getContext().getString(R.string.ARG_QUESTION) +
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

    public void setQuestionPassed(Question question) {
        question.setPassed(true);
        QuestionViewModel questionViewModel = ViewModelProviders
                .of(this).get(QuestionViewModel.class);
        questionViewModel.update(question);
    }

    public void clearFields() {
        answerField.setText(new String());
        correctAnswer.setText(new String());
    }

    public void shiftRandomQuestion(boolean skip) {
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

    public void shiftSimpleQuestion() {
        SimpleQuestionPagerActivity activity = (SimpleQuestionPagerActivity) getActivity();
        answerField.setEnabled(false);
        setQuestionPassed(question);
        if (activity != null) {
            int position = activity.getCurrentQuestion();
            activity.setCurrentQuestion(position + 1);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.question_fragment, menu);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skip_button:
                shiftRandomQuestion(true);
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

    public void cutText() {
        String text = answerField.getText().toString();
        text = text.substring(0, text.length()-1);
        answerField.setText(text);
        answerField.moveCursorToVisibleOffset();
    }
}
