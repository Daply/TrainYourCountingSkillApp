package com.dariapro.trainyourcountingskills.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.entity.QuestionType;
import com.dariapro.trainyourcountingskills.entity.Record;
import com.dariapro.trainyourcountingskills.exception.ExtraIsNullException;
import com.dariapro.trainyourcountingskills.viewmodel.RecordViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Pleshchankova Daria
 *
 */
public class QuestionScoresFragment extends Fragment {

    private RecordViewModel recordViewModel = null;
    private TextView noScores;
    private RecyclerView recyclerView;
    private ScoreAdapter adapter;

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

    private void getExtras() {
        Bundle args = getArguments();
        if (args != null) {
            questionType = QuestionType
                        .valueOf(args.getString(getContext().getString(R.string.QUESTION_TYPE)));
        }
    }

    private void updateUI(){
        if(adapter == null){
            adapter = new ScoreAdapter();
            recyclerView.setAdapter(adapter);
        }
        else{
            adapter.notifyDataSetChanged();
        }
        initData();
    }

    private void initData() {
        recordViewModel = ViewModelProviders.of(this).get(RecordViewModel.class);
        recordViewModel.getRecordsByTypeSortedByLevel(questionType.ordinal())
                .observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(@Nullable List<Record> records) {
                adapter.setRecords(records);
            }
        });
    }

    private class ScoreHolder extends RecyclerView.ViewHolder {

        private Record record;

        private TextView levelTextView;
        private TextView timeTextView;
        private TextView questionsNumberTextView;

        private ScoreHolder(View itemView) {
            super(itemView);

            levelTextView = itemView.findViewById(R.id.score_item_level);
            timeTextView = itemView.findViewById(R.id.score_item_time);
            questionsNumberTextView = itemView.findViewById(R.id.score_item_questions_number);
        }

        public void bindEvent(Record record){
            if (record == null) {
                return;
            }
            this.record = record;
            levelTextView.setText(Integer.toString(this.record.getLevel()));
            timeTextView.setText(Integer.toString(this.record.getTime()) + " minutes");
            questionsNumberTextView.setText(Integer.toString(this.record.getNumberOfQuestions()));
        }
    }

    private class ScoreAdapter extends RecyclerView.Adapter<ScoreHolder>{

        private List<Record> records;

        private ScoreAdapter() {
            records = new ArrayList<>();
        }

        @Override
        @NonNull
        public ScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.score_item_list, parent, false);
            return new ScoreHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ScoreHolder holder, int position) {
            Record record = records.get(position);
            holder.bindEvent(record);
        }

        public void setRecords(List<Record> records){
            this.records = records;
            notifyDataSetChanged();
            if (this.records.isEmpty()) {
                noScores.setVisibility(View.VISIBLE);
                noScores.setText(getContext().getResources().getString(R.string.no_scores));
            }
        }

        @Override
        public int getItemCount() {
            return records.size();
        }
    }
}
