package com.dariapro.traincounting.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.dariapro.traincounting.Extras;
import com.dariapro.traincounting.R;
import com.dariapro.traincounting.activity.MainActivity;

/**
 * @author Pleshchankova Daria
 *
 */
public class MainFragment extends Fragment {

    public static final int REQUEST_EVENT = 1;

    private String modeValue = null;

    private Button randomExamplesButton;
    private Button randomExpressionsButton;
    private Button solveProblemsButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container,false);
        randomExamplesButton = view.findViewById(R.id.random_examples);
        randomExpressionsButton = view.findViewById(R.id.random_expressions);
        solveProblemsButton = view.findViewById(R.id.solve_problems);

        randomExamplesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.newExampleIntent(getActivity());
                intent.putExtra(Extras.MODE, "random");
                startActivityForResult(intent, REQUEST_EVENT);
            }
        });

        randomExpressionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.newExampleIntent(getActivity());
                intent.putExtra(Extras.MODE, "random");
                startActivityForResult(intent, REQUEST_EVENT);
            }
        });

        solveProblemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.newCategoryIntent(getActivity());
                intent.putExtra(Extras.MODE, "simple");
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

}
