package com.dariapro.trainyourcountingskills.fragment.list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.adapter.entity.QuestionEntityAdapter;
import com.dariapro.trainyourcountingskills.entity.Level;
import com.dariapro.trainyourcountingskills.entity.Question;
import com.dariapro.trainyourcountingskills.exception.ExtraIsNullException;
import com.dariapro.trainyourcountingskills.viewmodel.LevelViewModel;
import com.dariapro.trainyourcountingskills.viewmodel.QuestionViewModel;

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
public class QuestionListFragment extends ListFragment {

    private long levelId;
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

    @Override
    public void getExtras() {
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
        if(entityAdapter == null){
            entityAdapter = new QuestionEntityAdapter(getContext(), getActivity(), this.modeValue);
            recyclerView.setAdapter(entityAdapter);
        }
        else{
            entityAdapter.notifyDataSetChanged();
        }
        initData();
    }

    @Override
    public void initData() {
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getQuestionListByLevelSorted(this.levelId).observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(@Nullable List<Question> questions) {
                entityAdapter.setData(questions);
                int numberOfAllQuestions = questions.size();
                int numberOfPassedQuestions = questionViewModel.getPassedQuestionListByLevel(levelId);
                if (numberOfAllQuestions == numberOfPassedQuestions) {
                    updateLevel();
                }
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

}