package com.dariapro.traincounting.fragment;

import android.content.Context;
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
import com.dariapro.traincounting.entity.Category;
import com.dariapro.traincounting.entity.Question;

import java.util.UUID;

public class ExampleFragment extends Fragment{

    private static final String ARG_EXAMPLE_ID = "com.dariapro.traincounting.example_id";

    private Question question = null;
    private TextView exampleExpression;
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
        View v = inflater.inflate(R.layout.example_fragment, container, false);
        exampleExpression = v.findViewById(R.id.example_expression);
        exampleExpression.setText(question.getExample());
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

    public static ExampleFragment newInstance(UUID eventID){
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXAMPLE_ID, eventID);

        ExampleFragment exampleFragment = new ExampleFragment();

        if(eventID == null){
            Question newExample = new Question();
            exampleFragment.question = newExample;
        }

        exampleFragment.setArguments(args);
        return exampleFragment;
    }
}
