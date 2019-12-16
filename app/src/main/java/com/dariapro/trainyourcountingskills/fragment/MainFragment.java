package com.dariapro.trainyourcountingskills.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.activity.MainActivity;
import com.dariapro.trainyourcountingskills.activity.ScoresActivity;
import com.dariapro.trainyourcountingskills.entity.Mode;
import com.dariapro.trainyourcountingskills.entity.QuestionType;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author Pleshchankova Daria
 *
 */
public class MainFragment extends Fragment {

    public static final int REQUEST_EVENT = 1;

    private Button randomExamplesButton = null;
    private Button randomExpressionsButton = null;
    private Button solvePercentProblemsButton = null;
    private Button solveProblemsButton = null;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.main_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.scores:
                Intent intent = new Intent(getContext(), ScoresActivity.class);
                this.startActivity(intent);
                break;
//            case R.id.en_lang:
//                setAppLanguage("en");
//                break;
//            case R.id.ru_lang:
//                setAppLanguage("ru");
//                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setAppLanguage("ru");
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container,false);
        randomExamplesButton = view.findViewById(R.id.random_examples);
        randomExpressionsButton = view.findViewById(R.id.random_expressions);
        solvePercentProblemsButton = view.findViewById(R.id.solve_percent_problems);
        // solveProblemsButton = view.findViewById(R.id.solve_problems);

        randomExamplesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.newExampleIntent(getActivity());
                intent.putExtra(getContext().getString(R.string.MODE), Mode.RANDOM.name());
                intent.putExtra(getContext().getString(R.string.QUESTION_TYPE),
                        QuestionType.QUESTION.name());
                startActivityForResult(intent, REQUEST_EVENT);
            }
        });


        randomExpressionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.newExampleIntent(getActivity());
                intent.putExtra(getContext().getString(R.string.MODE), Mode.RANDOM.name());
                intent.putExtra(getContext().getString(R.string.QUESTION_TYPE),
                        QuestionType.EXPRESSION.name());
                startActivityForResult(intent, REQUEST_EVENT);
            }
        });

        solvePercentProblemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.newExampleIntent(getActivity());
                intent.putExtra(getContext().getString(R.string.MODE), Mode.RANDOM.name());
                intent.putExtra(getContext().getString(R.string.QUESTION_TYPE),
                        QuestionType.PERCENTS_PROBLEM.name());
                startActivityForResult(intent, REQUEST_EVENT);
            }
        });

//        solveProblemsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = MainActivity.newCategoryIntent(getActivity());
//                intent.putExtra(getContext().getString(R.string.MODE), Mode.SIMPLE.name());
//                startActivityForResult(intent, REQUEST_EVENT);
//            }
//        });

        return view;
    }

    private void setAppLanguage(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getContext().getResources().updateConfiguration(config,
                getContext().getResources().getDisplayMetrics());
    }

}
