package com.dariapro.trainyourcountingskills.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.activity.QuestionListActivity;
import com.dariapro.trainyourcountingskills.entity.Level;
import com.dariapro.trainyourcountingskills.entity.Question;
import com.dariapro.trainyourcountingskills.exception.ExtraIsNullException;
import com.dariapro.trainyourcountingskills.viewmodel.LevelViewModel;
import com.dariapro.trainyourcountingskills.viewmodel.QuestionViewModel;

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
    private QuestionAdapter adapter;

    private QuestionViewModel questionViewModel;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.question_list_fragment, menu);
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

        View view = inflater.inflate(R.layout.question_list_fragment, container,false);
        recyclerView = view.findViewById(R.id.question_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void getExtras() {
        Bundle args = getArguments();
        if (args != null) {
            try {
                this.modeValue = args.getString(getContext().getString(R.string.MODE));
                if (modeValue == null) {
                    throw new ExtraIsNullException("Extra " +
                            getContext().getString(R.string.MODE) +
                            " is null in " + getClass().getName());
                }
                levelId = args.getLong(getContext().getString(R.string.EXTRA_LEVEL_ID));
                if (levelId == 0) {
                    throw new ExtraIsNullException("Extra " +
                            getContext().getString(R.string.EXTRA_LEVEL_ID) +
                            " is equal 0 in " + getClass().getName());
                }
            }
            catch (ExtraIsNullException e) {
                Log.e(getContext().getString(R.string.TAG),
                        getContext().getString(R.string.MODE) + " or " +
                                getContext().getString(R.string.EXTRA_LEVEL_ID) +
                                " didn't passed");
            }
        }
    }

    private void updateUI(){
        if(adapter == null){
            adapter = new QuestionAdapter();
            recyclerView.setAdapter(adapter);
        }
        else{
            adapter.notifyDataSetChanged();
        }
        initData();
    }

    private void initData() {
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getQuestionListByLevelSorted(this.levelId).observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(@Nullable List<Question> questions) {
                adapter.setQuestions(questions);
            }
        });
    }

    private void updateLevel() {
        LevelViewModel levelViewModel = ViewModelProviders
                .of(this).get(LevelViewModel.class);
        Level currentLevel = levelViewModel.getLevelById(levelId);
        currentLevel.setPassed(true);
        levelViewModel.update(currentLevel);
    }

    private class QuestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Question question;

        private TextView titleTextView;
        private ImageView passedImageView;

        private QuestionHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            titleTextView = itemView.findViewById(R.id.question_item_level_title);
            passedImageView = itemView.findViewById(R.id.list_item_question_passed);
            passedImageView.setVisibility(View.GONE);
        }

        public void bindEvent(Question question){
            if (question == null) {
                return;
            }
            this.question = question;
            titleTextView.setText(this.question.getTitle());
            if (this.question.isPassed()) {
                passedImageView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = QuestionListActivity.newIntent(getActivity(), question.getQuestionId());
            intent.putExtra(getContext().getString(R.string.MODE), modeValue);
            intent.putExtra(getContext().getString(R.string.EXTRA_LEVEL_ID), levelId);
            startActivityForResult(intent, REQUEST_EVENT);
        }
    }

    private class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder>{

        private List<Question> questions;

        private QuestionAdapter() {
            questions = new ArrayList<>();
        }

        @Override
        @NonNull
        public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.question_item_list, parent, false);
            return new QuestionHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
            Question question = questions.get(position);
            holder.bindEvent(question);
        }

        public void setQuestions(List<Question> questions){
            this.questions = questions;
            notifyDataSetChanged();
            int numberOfAllQuestions = this.questions.size();
            int numberOfPassedQuestions = questionViewModel.getPassedQuestionListByLevel(levelId);
            if (numberOfAllQuestions == numberOfPassedQuestions) {
                updateLevel();
            }
        }

        @Override
        public int getItemCount() {
            return questions.size();
        }
    }

}