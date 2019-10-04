package com.dariapro.trainyourcountingskills.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.activity.LevelListActivity;
import com.dariapro.trainyourcountingskills.entity.Category;
import com.dariapro.trainyourcountingskills.entity.Level;
import com.dariapro.trainyourcountingskills.entity.Mode;
import com.dariapro.trainyourcountingskills.exception.ExtraIsNullException;
import com.dariapro.trainyourcountingskills.viewmodel.CategoryViewModel;
import com.dariapro.trainyourcountingskills.viewmodel.LevelViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Pleshchankova Daria
 *
 */
public class LevelListFragment extends Fragment {

    public static final int REQUEST_EVENT = 1;

    private String modeValue = null;

    private long categoryId;

    private RecyclerView recyclerView;
    private LevelAdapter adapter;

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

    private void getExtras() {
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
            if (adapter == null) {
                adapter = new LevelAdapter(this.modeValue);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
            initData();
        }
    }

    private void initData() {
        levelViewModel = ViewModelProviders.of(this).get(LevelViewModel.class);
        levelViewModel.getLevelListByCategory(this.categoryId)
                      .observe(this, new Observer<List<Level>>() {
            @Override
            public void onChanged(@Nullable List<Level> levels) {
                adapter.setLevels(levels);
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

    private class LevelHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Level level;

        private String modeValue;

        private TextView titleTextView;
        private ImageView passedImageView;

        private LevelHolder(View itemView, String mode) {
            super(itemView);

            itemView.setOnClickListener(this);
            titleTextView = itemView.findViewById(R.id.list_item_level_title);
            passedImageView = itemView.findViewById(R.id.list_item_level_passed);
            passedImageView.setVisibility(View.GONE);

            this.modeValue = mode;
        }

        public void bindEvent(Level level){
            this.level = level;
            titleTextView.setText(this.level.getTitle());
            if (this.level.isPassed()) {
                passedImageView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = LevelListActivity.newIntent(getActivity(), level.getLevelId());
            intent.putExtra(getContext().getString(R.string.MODE), this.modeValue);
            startActivityForResult(intent,REQUEST_EVENT);
        }
    }

    private class LevelAdapter extends RecyclerView.Adapter<LevelHolder>{

        private List<Level> levels;

        private String modeValue;

        private LevelAdapter(String mode) {
            this.levels = new ArrayList<>();
            this.modeValue = mode;
        }

        @NonNull
        @Override
        public LevelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.level_item_list, parent, false);
            return new LevelHolder(view, this.modeValue);
        }

        @Override
        public void onBindViewHolder(@NonNull LevelHolder holder, int position) {
            Level level = levels.get(position);
            holder.bindEvent(level);
        }

        public void setLevels(List<Level> levels){
            this.levels = levels;
            notifyDataSetChanged();
            int numberOfAllLevels = this.levels.size();
            int numberOfPassedLevels = levelViewModel.getPassedLevelListByCategory(categoryId);
            if (numberOfAllLevels == numberOfPassedLevels) {
                updateCategory();
            }
        }

        @Override
        public int getItemCount() {
            return levels.size();
        }
    }

}