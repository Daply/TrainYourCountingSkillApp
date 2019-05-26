package com.dariapro.traincounting.fragment;

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
import com.dariapro.traincounting.activity.ExamplePagerActivity;
import com.dariapro.traincounting.activity.ProblemsPagerActivity;
import com.dariapro.traincounting.entity.Question;
import com.dariapro.traincounting.random.RandomExampleGenerator;

public class ExampleFragment extends Fragment {

    public static final int REQUEST_EVENT = 1;

    public String modeValue = null;

    private TextView problemTask;
    private EditText answerField;
    private String answer = null;
    private Button answerButton;
    private TextView correctAnswer;

    private Question question = null;

    public static ExampleFragment newInstance(Question example, String mode) {
        Bundle args = new Bundle();
        args.putSerializable(Extras.ARG_EXAMPLE, example);
        args.putSerializable(Extras.MODE, mode);
        ExampleFragment exampleFragment = new ExampleFragment();
        exampleFragment.setArguments(args);
        return exampleFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.modeValue = getArguments().getString(Extras.MODE);

        final View view = inflater.inflate(R.layout.example_item, container,false);

        if (this.modeValue.equals("random")) {
            RandomExampleGenerator randomExampleGenerator = new RandomExampleGenerator();
            question = randomExampleGenerator.generateTwoRandomNumbersExample(1, 1);
        }
        else {
            this.question = (Question) getArguments().getSerializable(Extras.ARG_EXAMPLE);
        }

        problemTask = view.findViewById(R.id.example_expression);
        problemTask.setText(question.getExample());
        answerField = view.findViewById(R.id.example_answer);
        answerButton = view.findViewById(R.id.example_answer_button); //rand_example_answer_result
        answerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                answer = answerField.getText().toString();
                if (answer.equals(question.getRightAnswer())) {
                    correctAnswer = view.findViewById(R.id.example_answer_result);
                    correctAnswer.setText("Correct");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (modeValue.equals("random")) {
                                ExamplePagerActivity activity = (ExamplePagerActivity) getActivity();
                                int position = activity.getPager().getCurrentItem();
                                activity.getPager().setCurrentItem(position + 1);
                            }
                            else if (modeValue.equals("simple")) {
                                ProblemsPagerActivity activity = (ProblemsPagerActivity) getActivity();
                                int position = activity.getPager().getCurrentItem();
                                activity.getPager().setCurrentItem(position + 1);
                            }
                        }
                    }, 500);
                }
                else {
                    correctAnswer = view.findViewById(R.id.example_answer_result);
                    correctAnswer.setText("Not correct");
                }
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.random_example, menu);
    }
}
