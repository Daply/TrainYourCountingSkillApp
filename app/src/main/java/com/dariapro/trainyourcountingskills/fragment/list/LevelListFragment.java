package com.dariapro.trainyourcountingskills.fragment.list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.adapter.entity.LevelEntityAdapter;
import com.dariapro.trainyourcountingskills.entity.Category;
import com.dariapro.trainyourcountingskills.entity.Level;
import com.dariapro.trainyourcountingskills.entity.Mode;
import com.dariapro.trainyourcountingskills.exception.ExtraIsNullException;
import com.dariapro.trainyourcountingskills.viewmodel.CategoryViewModel;
import com.dariapro.trainyourcountingskills.viewmodel.LevelViewModel;

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
public class LevelListFragment extends ListFragment {

    private long categoryId;
    private LevelViewModel levelViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.level_list_fragment, menu);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getExtras();

        View view = inflater.inflate(R.layout.level_list_fragment, container,false);
        recyclerView = view.findViewById(R.id.level_recycler_view);
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
                if (modeValue == null){
                    throw new ExtraIsNullException("Extra " + getContext().getString(R.string.MODE) +
                            " is null in " + getClass().getName());
                }
                this.categoryId = args.getLong(getContext().getString(R.string.EXTRA_CATEGORY_ID));
                if (categoryId == 0){
                    throw new ExtraIsNullException("Extra " +
                            getContext().getString(R.string.EXTRA_CATEGORY_ID) +
                            " is equal 0 in " + getClass().getName());
                }
            }
            catch (ExtraIsNullException e) {
                Log.e(getContext().getString(R.string.TAG),
                        getContext().getString(R.string.MODE) + " or " +
                                getContext().getString(R.string.EXTRA_CATEGORY_ID) +
                                " didn't passed");
            }
        }
    }

    private void updateUI(){
        if (this.modeValue.equals(Mode.SIMPLE.name())) {
            if (entityAdapter == null) {
                entityAdapter = new LevelEntityAdapter(getContext(), getActivity(), this.modeValue);
                recyclerView.setAdapter(entityAdapter);
            } else {
                entityAdapter.notifyDataSetChanged();
            }
            initData();
        }
    }

    @Override
    public void initData() {
        levelViewModel = ViewModelProviders.of(this).get(LevelViewModel.class);
        levelViewModel.getLevelListByCategory(this.categoryId)
                      .observe(this, new Observer<List<Level>>() {
            @Override
            public void onChanged(@Nullable List<Level> levels) {
                entityAdapter.setData (levels);
                int numberOfAllLevels = levels.size();
                int numberOfPassedLevels = levelViewModel.getPassedLevelListByCategory(categoryId);
                if (numberOfAllLevels == numberOfPassedLevels) {
                    updateCategory();
                }
            }
        });
    }

    private void updateCategory() {
        CategoryViewModel categoryViewModel = ViewModelProviders
                .of(this).get(CategoryViewModel.class);
        Category currentCategory = categoryViewModel.getCategoryById(categoryId);
        currentCategory.setPassed(true);
        categoryViewModel.update(currentCategory);
    }

}