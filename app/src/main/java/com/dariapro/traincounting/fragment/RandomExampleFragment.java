package com.dariapro.traincounting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dariapro.traincounting.R;
import com.dariapro.traincounting.entity.Question;
import com.dariapro.traincounting.random.RandomExampleGenerator;

public class RandomExampleFragment extends Fragment {

    public static final int REQUEST_EVENT = 1;
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    private TextView problemTask;
    private EditText answerField;
    private String answer = null;
    private Button answerButton;

    private Question question = null;

    public static RandomExampleFragment newInstance(int page) {
        RandomExampleFragment pageFragment = new RandomExampleFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.random_example_item, container,false);

        RandomExampleGenerator randomExampleGenerator = new RandomExampleGenerator();
        question = randomExampleGenerator.generateTwoRandomNumbersExample(1, 1);

        problemTask = view.findViewById(R.id.rand_example_expression);
        problemTask.setText(question.getExample());
        answerField = view.findViewById(R.id.rand_example_answer);
        answer = answerField.getText().toString();
        answerButton = view.findViewById(R.id.rand_example_answer_button);
        answerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (answer.equals(question.getRightAnswer())) {
                    Toast.makeText(getContext(), "Correct", Toast.LENGTH_SHORT);
                }
                else {
                    Toast.makeText(getContext(), "Wrong", Toast.LENGTH_SHORT);
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
