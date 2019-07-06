package com.dariapro.traincounting.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dariapro.traincounting.Extras;
import com.dariapro.traincounting.R;
import com.dariapro.traincounting.activity.QuestionPagerActivity;
import com.dariapro.traincounting.activity.RandomQuestionPagerActivity;
import com.dariapro.traincounting.dao.QuestionDao;
import com.dariapro.traincounting.entity.Question;
import com.dariapro.traincounting.random.RandomQuestionGenerator;
import com.dariapro.traincounting.view.model.QuestionViewModel;
import com.dariapro.traincounting.view.model.RecordViewModel;

/**
 * @author Pleshchankova Daria
 *
 */
public class QuestionFragment extends Fragment {

    public static final int REQUEST_EVENT = 1;

    private String modeValue = null;
    private int level = 0;
    private boolean expression = false;

    private boolean plusOperator = false;
    private boolean minusOperator = false;
    private boolean multiplyOperator = false;
    private boolean divideOperator = false;

    private TextView problemTask;
    private EditText answerField;
    private String answer = null;
    private Button answerButton;
    private TextView correctAnswer;

    private Question question = null;

    public static QuestionFragment newInstance(Question example, String mode) {
        Bundle args = new Bundle();
        args.putSerializable(Extras.ARG_EXAMPLE, example);
        args.putSerializable(Extras.MODE, mode);
        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setArguments(args);
        return questionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getExtras();

        final View view = inflater.inflate(R.layout.question_item, container,false);

        problemTask = view.findViewById(R.id.question_expression);
        problemTask.setText(question.getQuestion());
        answerField = view.findViewById(R.id.question_answer);
        answerButton = view.findViewById(R.id.question_answer_button);
        answerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                answer = answerField.getText().toString();
                if (answer.equals(question.getRightAnswer())) {
                    correctAnswer = view.findViewById(R.id.question_answer_result);
                    correctAnswer.setText("Correct");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (modeValue.equals("random")) {
                                RandomQuestionPagerActivity activity = (RandomQuestionPagerActivity) getActivity();
                                int position = activity.getCurrentQuestion();
                                clearFields();
                                activity.setCurrentQuestion(position + 1);
                                activity.removePreviousQuestion();
                            }
                            else if (modeValue.equals("simple")) {
                                QuestionPagerActivity activity = (QuestionPagerActivity) getActivity();
                                answerField.setEnabled(false);
                                setQuestionPassed(question);
                                int position = activity.getCurrentQuestion();
                                activity.setCurrentQuestion(position + 1);
                            }
                        }
                    }, 500);
                }
                else {
                    correctAnswer = view.findViewById(R.id.question_answer_result);
                    correctAnswer.setText("Not correct");
                }
            }
        });
        return view;
    }

    private void getExtras() {
        this.modeValue = getArguments().getString(Extras.MODE);
        this.expression = getArguments().getBoolean(Extras.EXPRESSION_EXTRA);
        if (this.modeValue.equals("random")) {
            this.level = getArguments().getInt(Extras.LEVEL_EXTRA);
            RandomQuestionGenerator randomQuestionGenerator = new RandomQuestionGenerator();
            if (expression) {
                question = randomQuestionGenerator.generateRandomExpression(level);
            }
            else {
                this.plusOperator = getArguments().getBoolean(Extras.PLUS_EXTRA);
                this.minusOperator = getArguments().getBoolean(Extras.MINUS_EXTRA);
                this.multiplyOperator = getArguments().getBoolean(Extras.MULTIPLY_EXTRA);
                this.divideOperator = getArguments().getBoolean(Extras.DIVIDE_EXTRA);
                question = randomQuestionGenerator.generateTwoRandomNumbersExample(level,
                                                                                    plusOperator,
                                                                                    minusOperator,
                                                                                    multiplyOperator,
                                                                                    divideOperator);
            }
        }
        else {
            this.question = (Question) getArguments().getSerializable(Extras.ARG_EXAMPLE);
        }
    }

    public void setQuestionPassed(Question question) {
        question.setPassed(true);
        QuestionViewModel questionViewModel = ViewModelProviders
                .of(this).get(QuestionViewModel.class);
        questionViewModel.update(question);
    }

    public void clearFields() {
        answerField.setText("");
        correctAnswer.setText("");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.random_example, menu);
    }
}
