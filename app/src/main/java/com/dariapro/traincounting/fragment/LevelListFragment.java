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
import com.dariapro.traincounting.activity.LevelListActivity;
import com.dariapro.traincounting.entity.Level;
import com.dariapro.traincounting.entity.Question;
import com.dariapro.traincounting.view.model.LevelViewModel;
import com.dariapro.traincounting.view.model.QuestionViewModel;

import java.util.ArrayList;
import java.util.List;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.modeValue = getArguments().getString(Extras.MODE);
        this.categoryId = getArguments().getLong(Extras.EXTRA_CATEGORY_ID);

        View view = inflater.inflate(R.layout.level_list_fragment, container,false);
        recyclerView = view.findViewById(R.id.level_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.level_list_fragment, menu);
    }

    private void updateUI(){
        initData();

        if (this.modeValue.equals("simple")) {
            if (adapter == null) {
                adapter = new LevelAdapter(this.modeValue);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void initData() {
        levelViewModel = ViewModelProviders.of(this).get(LevelViewModel.class);
//        levelViewModel.getLevelList().observe(this, new Observer<List<Level>>() {
//            @Override
//            public void onChanged(@Nullable List<Level> levels) {
//                adapter.setLevels(levels);
//            }
//        });
        levelViewModel.getLevelListByCategory(this.categoryId).observe(this, new Observer<List<Level>>() {
            @Override
            public void onChanged(@Nullable List<Level> levels) {
                adapter.setLevels(levels);
            }
        });
    }

    private class LevelHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Level level;

        private String modeValue = null;

        public TextView titleTextView;

        public LevelHolder(View itemView, String mode) {
            super(itemView);

            itemView.setOnClickListener(this);
            titleTextView = itemView.findViewById(R.id.list_item_level_title);

            this.modeValue = mode;
        }

        public void bindEvent(Level lev){
            level = lev;
            titleTextView.setText(lev.getTitle());
        }

        @Override
        public void onClick(View v) { ;
            Intent intent = LevelListActivity.newIntent(getActivity(), level.getLevelId());
            intent.putExtra(Extras.MODE, this.modeValue);
            startActivityForResult(intent,REQUEST_EVENT);
        }
    }

    private class LevelAdapter extends RecyclerView.Adapter<LevelHolder>{

        private List<Level> levels;

        private String modeValue = null;

        public LevelAdapter(String mode) {
            this.levels = new ArrayList<Level>();
            this.modeValue = mode;
        }

        public LevelAdapter(List<Level> levels, String mode) {
            this.levels = levels;
            this.modeValue = mode;
        }

        @Override
        public LevelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.level_item_list, parent, false);
            return new LevelHolder(view, this.modeValue);
        }

        @Override
        public void onBindViewHolder(LevelHolder holder, int position) {
            Level level = levels.get(position);
            holder.bindEvent(level);
        }

        public void setLevels(List<Level> levels){
            this.levels = levels;
        }

        @Override
        public int getItemCount() {
            return levels.size();
        }
    }

}