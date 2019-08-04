package com.dariapro.traincounting.fragment;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.dariapro.traincounting.R;
import com.dariapro.traincounting.activity.RandomQuestionStartActivity;
import com.dariapro.traincounting.entity.QuestionType;
import com.dariapro.traincounting.exception.ExtraIsNullException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pleshchankova Daria
 *
 */
public class RandomQuestionStartFragment extends Fragment {

    public static final int REQUEST_EVENT = 1;

    private String modeValue = null;
    private QuestionType questionType = null;

    private int chosenLevel = 0;
    private int chosenTime = 0;

    private Spinner levelSpinner = null;
    private Spinner timeSpinner = null;
    private CheckBox plusCheckBox = null;
    private CheckBox minusCheckBox = null;
    private CheckBox multiplyCheckBox = null;
    private CheckBox divideCheckBox = null;
    private CheckBox rootCheckBox = null;
    private Button start = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getExtras();

        View view = null;
        if (inflater != null) {
            view = inflater.inflate(R.layout.random_question_start_fragment,
                    container, false);
        }

        ArrayAdapter<String> levelsAdapter = new ArrayAdapter<>(getContext(),
                R.layout.spinner_item, getResources().getStringArray(R.array.levels));
        levelsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        levelSpinner = view.findViewById(R.id.level_spinner);
        if (levelSpinner != null) {
            levelSpinner.setAdapter(levelsAdapter);
            levelSpinner.setPrompt(getContext().getString(R.string.level_spinner));
            levelSpinner.setSelection(0);
            levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    String [] levels = getResources().getStringArray(R.array.levels_map);
                    chosenLevel = Integer.parseInt(levels[position]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
        }

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(getContext(),
                R.layout.spinner_item, getResources().getStringArray(R.array.time));
        timeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        timeSpinner = view.findViewById(R.id.time_spinner);
        if (timeSpinner != null) {
            timeSpinner.setAdapter(timeAdapter);
            timeSpinner.setPrompt(getContext().getString(R.string.time_spinner));
            timeSpinner.setSelection(0);
            timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    String [] time = getResources().getStringArray(R.array.time_map);
                    chosenTime = Integer.parseInt(time[position]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
        }

        plusCheckBox = view.findViewById(R.id.plus);
        minusCheckBox = view.findViewById(R.id.minus);
        multiplyCheckBox = view.findViewById(R.id.multiply);
        divideCheckBox = view.findViewById(R.id.divide);
        rootCheckBox = view.findViewById(R.id.root);
        if (questionType.equals(QuestionType.EXPRESSION)) {
            view.findViewById(R.id.operators_title).setVisibility(View.GONE);
            view.findViewById(R.id.operators_layout).setVisibility(View.GONE);
        }

        start = view.findViewById(R.id.start_randoms);

        final String sendMode = modeValue;
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = RandomQuestionStartActivity.newQuestionIntent(getActivity());
                intent.putExtra(getContext().getString(R.string.MODE), sendMode);
                intent.putExtra(getContext().getString(R.string.QUESTION_TYPE), questionType.name());
                intent.putExtra(getContext().getString(R.string.LEVEL_EXTRA), chosenLevel);
                intent.putExtra(getContext().getString(R.string.TIME_EXTRA), chosenTime);
                intent.putExtra(getContext().getString(R.string.PLUS_EXTRA), plusCheckBox.isChecked());
                intent.putExtra(getContext().getString(R.string.MINUS_EXTRA), minusCheckBox.isChecked());
                intent.putExtra(getContext().getString(R.string.MULTIPLY_EXTRA), multiplyCheckBox.isChecked());
                intent.putExtra(getContext().getString(R.string.DIVIDE_EXTRA), divideCheckBox.isChecked());
                intent.putExtra(getContext().getString(R.string.ROOT_EXTRA), rootCheckBox.isChecked());
                startActivityForResult(intent, REQUEST_EVENT);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.random_question_start_fragment, menu);
    }

    private void getExtras() {
        Bundle args = getArguments();
        if (args != null) {
            try {
                this.modeValue = args.getString(getContext().getString(R.string.MODE));
                if (modeValue == null){
                    throw new ExtraIsNullException("Extra " + getContext().getString(R.string.MODE) +
                            " is null in " + getClass().getName());
                }
                questionType = QuestionType.valueOf(args.getString(getContext()
                        .getString(R.string.QUESTION_TYPE)));
                if (questionType == null) {
                    throw new ExtraIsNullException("Extra " +
                            getContext().getString(R.string.QUESTION_TYPE) +
                            " is null in " + getClass().getName());
                }
            }
            catch (ExtraIsNullException e) {
                Log.e(getContext().getString(R.string.TAG),
                        getContext().getString(R.string.MODE) + " or " +
                                getContext().getString(R.string.QUESTION_TYPE) +
                                " didn't passed");
            }
        }
    }
}
