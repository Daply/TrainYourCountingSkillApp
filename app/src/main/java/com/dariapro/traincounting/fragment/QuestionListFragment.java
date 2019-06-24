package com.dariapro.traincounting.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dariapro.traincounting.Extras;
import com.dariapro.traincounting.R;
import com.dariapro.traincounting.activity.QuestionListActivity;
import com.dariapro.traincounting.entity.Question;
import com.dariapro.traincounting.view.model.QuestionViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pleshchankova Daria
 *
 */
public class QuestionListFragment extends Fragment {

    public static final int REQUEST_EVENT = 1;

    private String modeValue = null;
    private long levelId;

    private RecyclerView recyclerView;
    private ExampleAdapter adapter;

    private QuestionViewModel questionViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        modeValue = getArguments().getString(Extras.MODE);
        levelId = getArguments().getLong(Extras.EXTRA_LEVEL_ID);

        View view = inflater.inflate(R.layout.question_list_fragment, container,false);
        recyclerView = view.findViewById(R.id.question_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.problem_list_fragment, menu);
    }

    private void updateUI(){
        if(adapter == null){
            adapter = new ExampleAdapter();
            recyclerView.setAdapter(adapter);
        }
        else{
            adapter.notifyDataSetChanged();
        }
        initData();
    }

    private void initData() {
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getQuestionListByLevel(this.levelId).observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(@Nullable List<Question> questions) {
                adapter.setQuestions(questions);
            }
        });
    }

    private class ExampleHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Question question;

        public TextView titleTextView;

        public ExampleHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            titleTextView = itemView.findViewById(R.id.question_item_level_title);
        }

        public void bindEvent(Question ex){
            question = ex;
            titleTextView.setText(ex.getTitle());
        }

        @Override
        public void onClick(View v) { ;
            Intent intent = QuestionListActivity.newIntent(getActivity(), question.getQuestionId());
            intent.putExtra(Extras.MODE, modeValue);
            intent.putExtra(Extras.EXTRA_LEVEL_ID, levelId);
            startActivityForResult(intent, REQUEST_EVENT);
        }
    }

    private class ExampleAdapter extends RecyclerView.Adapter<ExampleHolder>{

        private List<Question> examples;

        public ExampleAdapter() {
            examples = new ArrayList<Question>();
        }

        public ExampleAdapter(List<Question> examples) {
            this.examples = examples;
        }

        @Override
        public ExampleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.question_item_list, parent, false);
            return new ExampleHolder(view);
        }

        @Override
        public void onBindViewHolder(ExampleHolder holder, int position) {
            Question example = examples.get(position);
            holder.bindEvent(example);
        }

        public void setQuestions(List<Question> examples){
            this.examples = examples;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return examples.size();
        }
    }



}