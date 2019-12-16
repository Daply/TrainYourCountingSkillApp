package com.dariapro.trainyourcountingskills.fragment;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.entity.Question;
import com.dariapro.trainyourcountingskills.exception.ExtraIsNullException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author Pleshchankova Daria
 *
 */
public class AnswerFragment extends Fragment {

    private TextView questionSolution = null;
    private TextView questionAnswer = null;

    private Question question = null;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.answer_fragment, menu);
    }

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

        final View view = inflater.inflate(R.layout.answer_fragment, container,false);

        questionSolution = view.findViewById(R.id.question_solution);
        questionSolution.setText(question.getSolution());
        questionSolution.setMovementMethod(new ScrollingMovementMethod());
        questionAnswer = view.findViewById(R.id.question_answer);
        questionAnswer.setText(question.getRightAnswer());

        return view;
    }

    private void getExtras() {
        Bundle args = getArguments();
        if (args != null) {
            try {
                this.question = (Question) args.getSerializable(getContext()
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
