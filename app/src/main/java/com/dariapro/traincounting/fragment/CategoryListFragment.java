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
import com.dariapro.traincounting.activity.CategoryActivity;
import com.dariapro.traincounting.entity.Category;
import com.dariapro.traincounting.entity.Level;
import com.dariapro.traincounting.view.model.CategoryViewModel;
import com.dariapro.traincounting.view.model.LevelViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryListFragment extends Fragment {

    public static final int REQUEST_EVENT = 1;

    private String modeValue = null;

    private RecyclerView recyclerView;
    private CategoryAdapter adapter;

    private CategoryViewModel categoryViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        modeValue = getArguments().getString(Extras.MODE);

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

    private void updateUI(){
        initData();
        if (this.modeValue.equals("simple")) {
            if (adapter == null) {
                adapter = new CategoryAdapter(this.modeValue);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void initData() {
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getCategoryList().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                adapter.setCategories(categories);
            }
        });
    }

    private class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Category category;

        private String modeValue = null;

        public TextView titleTextView;

        public CategoryHolder(View itemView, String mode) {
            super(itemView);

            itemView.setOnClickListener(this);
            titleTextView = itemView.findViewById(R.id.list_item_category_title);

            this.modeValue = mode;
        }

        public void bindEvent(Category cat){
            category = cat;
            titleTextView.setText(cat.getTitle());
        }

        @Override
        public void onClick(View v) {
            Intent intent = CategoryActivity.newIntent(getActivity(), category.getCategoryId());
            intent.putExtra(Extras.MODE, modeValue);
            startActivityForResult(intent,REQUEST_EVENT);
        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder>{

        private List<Category> categories;

        private String modeValue = null;

        public CategoryAdapter(String mode) {
            this.categories = new ArrayList<Category>();
            this.modeValue = mode;
        }

        public CategoryAdapter(List<Category> categories, String mode) {
            this.categories = categories;
            this.modeValue = mode;
        }

        @Override
        public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.category_item_list, parent, false);
            return new CategoryHolder(view, this.modeValue);
        }

        @Override
        public void onBindViewHolder(CategoryHolder holder, int position) {
            Category category = categories.get(position);
            holder.bindEvent(category);
        }

        public void setCategories (List<Category> categories){
            this.categories = categories;
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }
    }

}
