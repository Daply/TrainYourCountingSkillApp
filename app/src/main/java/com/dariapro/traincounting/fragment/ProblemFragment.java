package com.dariapro.traincounting.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.dariapro.traincounting.R;
import com.dariapro.traincounting.entity.Question;

public class ProblemFragment extends Fragment{

    private static final String ARG_EXAMPLE_ID = "com.dariapro.traincounting.example_id";

    private Question question = null;
    private TextView problemTask;
    private EditText answerField;
    private String answer = null;
    private Button answerButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.question = new Question();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.problem_fragment, container, false);
        problemTask = v.findViewById(R.id.example_expression);
        problemTask.setText(question.getExample());
        answerField = v.findViewById(R.id.example_answer);
        answer = answerField.getText().toString();
        answerButton = v.findViewById(R.id.example_answer_button);
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
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.category_list_fragment, menu);
    }

    public static ProblemFragment newInstance(long eventID){
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXAMPLE_ID, eventID);

        ProblemFragment problemFragment = new ProblemFragment();

        if(eventID == 0){
            Question newExample = new Question();
            problemFragment.question = newExample;
        }

        problemFragment.setArguments(args);
        return problemFragment;
    }
}
