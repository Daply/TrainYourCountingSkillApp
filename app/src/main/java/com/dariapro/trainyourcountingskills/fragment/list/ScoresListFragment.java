package com.dariapro.trainyourcountingskills.fragment.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.adapter.entity.ScoreAdapter;
import com.dariapro.trainyourcountingskills.entity.QuestionType;
import com.dariapro.trainyourcountingskills.entity.Record;
import com.dariapro.trainyourcountingskills.viewmodel.RecordViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author Pleshchankova Daria
 *
 */
public class ScoresListFragment extends ListFragment {

    private RecordViewModel recordViewModel = null;
    private TextView noScores;
    private QuestionType questionType;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.scores_fragment, menu);
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

        View view = inflater.inflate(R.layout.scores_list_fragment, container,false);
        noScores = view.findViewById(R.id.no_scores);
        noScores.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.score_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    public void getExtras() {
        Bundle args = getArguments();
        if (args != null) {
            questionType = QuestionType
                        .valueOf(args.getString(getContext().getString(R.string.QUESTION_TYPE)));
        }
    }

    private void updateUI(){
        if(entityAdapter == null){
            entityAdapter = new ScoreAdapter(getContext(), getActivity());
        }
        else{
            entityAdapter.notifyDataSetChanged();
        }
        if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(entityAdapter);
        }
        initData();
    }

    private void setDataAdapter(List<Record> records){
        entityAdapter.setData(records);
    }

    public void initData() {
        recordViewModel = ViewModelProviders.of(this).get(RecordViewModel.class);
        recordViewModel.getRecordsByTypeSortedByLevel(questionType.ordinal())
                .observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(@Nullable List<Record> records) {
                setDataAdapter(records);
                if (records.isEmpty()) {
                    noScores.setVisibility(View.VISIBLE);
                    noScores.setText(getContext().getResources().getString(R.string.no_scores));
                }
            }
        });
    }

}
