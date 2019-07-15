package com.dariapro.traincounting.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.dariapro.traincounting.Extras;
import com.dariapro.traincounting.R;
import com.dariapro.traincounting.activity.RandomQuestionStartActivity;

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

    private String modeValue;
    private boolean expression;

    private Spinner levelSpinner;
    private List<String> levels;
    private Map<Integer, Integer> levelsMap;
    private int chosenLevel = 0;

    private Spinner timeSpinner;
    private List<String> time;
    private Map<Integer, Integer> timeMap;
    private int chosenTime = 0;

    private CheckBox plusCheckBox;
    private CheckBox minusCheckBox;
    private CheckBox multiplyCheckBox;
    private CheckBox divideCheckBox;

    private Button start;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        getExtras();

        View view = inflater.inflate(R.layout.random_question_start_fragment, container,false);

        ArrayAdapter<String> levelsAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_item, levels);
        levelsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        levelSpinner = (Spinner) view.findViewById(R.id.level_spinner);
        levelSpinner.setAdapter(levelsAdapter);
        levelSpinner.setPrompt("Levels");
        levelSpinner.setSelection(0);
        levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                chosenLevel = levelsMap.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_item, time);
        timeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        timeSpinner = (Spinner) view.findViewById(R.id.time_spinner);
        timeSpinner.setAdapter(timeAdapter);
        timeSpinner.setPrompt("Time");
        timeSpinner.setSelection(0);
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                chosenTime = timeMap.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        plusCheckBox = view.findViewById(R.id.plus);
        minusCheckBox = view.findViewById(R.id.minus);
        multiplyCheckBox = view.findViewById(R.id.multiply);
        divideCheckBox = view.findViewById(R.id.divide);
        if (expression) {
            view.findViewById(R.id.operators_title).setVisibility(View.GONE);
            view.findViewById(R.id.operators_layout).setVisibility(View.GONE);
        }

        start = view.findViewById(R.id.start_randoms);

        final String sendMode = modeValue;
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = RandomQuestionStartActivity.newQuestionIntent(getActivity());
                intent.putExtra(Extras.MODE, sendMode);
                intent.putExtra(Extras.EXPRESSION_EXTRA, expression);
                intent.putExtra(Extras.LEVEL_EXTRA, chosenLevel);
                intent.putExtra(Extras.TIME_EXTRA, chosenTime);
                intent.putExtra(Extras.PLUS_EXTRA, plusCheckBox.isChecked());
                intent.putExtra(Extras.MINUS_EXTRA, minusCheckBox.isChecked());
                intent.putExtra(Extras.MULTIPLY_EXTRA, multiplyCheckBox.isChecked());
                intent.putExtra(Extras.DIVIDE_EXTRA, divideCheckBox.isChecked());
                startActivityForResult(intent, REQUEST_EVENT);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.main_fragment, menu);
    }

    public void init() {
        levels = new ArrayList<>();
        levels.add("Level 1");
        levels.add("Level 2");
        levels.add("Level 3");
        levels.add("Level 4");
        levels.add("Level 5");

        levelsMap = new HashMap<>();
        levelsMap.put(0, 1);
        levelsMap.put(1, 2);
        levelsMap.put(2, 3);
        levelsMap.put(3, 4);
        levelsMap.put(4, 5);

        time = new ArrayList<>();
        time.add("1 minute");
        time.add("5 minutes");
        time.add("10 minutes");
        time.add("15 minutes");
        time.add("20 minutes");

        timeMap = new HashMap<>();
        timeMap.put(0, 1);
        timeMap.put(1, 5);
        timeMap.put(2, 10);
        timeMap.put(3, 15);
        timeMap.put(4, 20);
    }

    private void getExtras() {
        modeValue = getArguments().getString(Extras.MODE);
        expression = getArguments().getBoolean(Extras.EXPRESSION_EXTRA);
    }
}
