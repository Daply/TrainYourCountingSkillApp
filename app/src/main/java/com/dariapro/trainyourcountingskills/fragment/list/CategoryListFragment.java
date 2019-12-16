package com.dariapro.trainyourcountingskills.fragment.list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.adapter.entity.CategoryEntityAdapter;
import com.dariapro.trainyourcountingskills.entity.Category;
import com.dariapro.trainyourcountingskills.entity.Mode;
import com.dariapro.trainyourcountingskills.exception.ExtraIsNullException;
import com.dariapro.trainyourcountingskills.viewmodel.CategoryViewModel;
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
public class CategoryListFragment extends ListFragment {

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

        View view = inflater.inflate(R.layout.category_list_fragment, container,false);
        recyclerView = view.findViewById(R.id.category_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.category_list_fragment, menu);
    }

    @Override
    public void getExtras() {
        Bundle args = getArguments();
        if (args != null) {
            try {
                modeValue = args.getString(getContext().getString(R.string.MODE));
                if (modeValue == null){
                    modeValue = Mode.RANDOM.name();
                    throw new ExtraIsNullException("Extra " + getContext().getString(R.string.MODE) +
                            " is null in " + getClass().getName());
                }
            }
            catch (ExtraIsNullException e) {
                Log.e(getContext().getString(R.string.TAG),
                        getContext().getString(R.string.MODE) +
                                " didn't passed");
            }
        }
    }

    private void updateUI() {
        if (this.modeValue.equals(Mode.SIMPLE.name())) {
            if (entityAdapter == null) {
                entityAdapter = new CategoryEntityAdapter(getContext(), getActivity(), this.modeValue);
                recyclerView.setAdapter(entityAdapter);
            } else {
                entityAdapter.notifyDataSetChanged();
            }
        }

        initData();
    }

    @Override
    public void initData() {
        CategoryViewModel categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getCategoryList().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                entityAdapter.setData(categories);
            }
        });
    }

}
