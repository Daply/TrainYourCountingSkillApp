package com.dariapro.traincounting.fragment;

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

import com.dariapro.traincounting.R;
import com.dariapro.traincounting.activity.CategoryActivity;
import com.dariapro.traincounting.entity.Category;
import com.dariapro.traincounting.entity.Mode;
import com.dariapro.traincounting.exception.ExtraIsNullException;
import com.dariapro.traincounting.view.model.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pleshchankova Daria
 *
 */
public class CategoryListFragment extends Fragment {

    public static final int REQUEST_EVENT = 1;

    private String modeValue = null;

    private RecyclerView recyclerView = null;
    private CategoryAdapter adapter = null;

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

    private void getExtras() {
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
            if (adapter == null) {
                adapter = new CategoryAdapter(this.modeValue);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
        initData();
    }

    private void initData() {
        CategoryViewModel categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getCategoryList().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                adapter.setCategories(categories);
            }
        });
    }

    private class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Category category;

        private String modeValue;

        private TextView titleTextView;
        private ImageView passedImageView;

        private CategoryHolder(View itemView, String mode) {
            super(itemView);

            itemView.setOnClickListener(this);
            titleTextView = itemView.findViewById(R.id.list_item_category_title);
            passedImageView = itemView.findViewById(R.id.list_item_category_passed);
            passedImageView.setVisibility(View.GONE);
            this.modeValue = mode;
        }

        public void bindEvent(Category category){
            this.category = category;
            titleTextView.setText(this.category.getTitle());
            if (this.category.isPassed()) {
                passedImageView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = CategoryActivity.newIntent(getActivity(), category.getCategoryId());
            intent.putExtra(getContext().getString(R.string.MODE), modeValue);
            startActivityForResult(intent,REQUEST_EVENT);
        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder>{

        private List<Category> categories;

        private String modeValue;

        private CategoryAdapter(String mode) {
            this.categories = new ArrayList<>();
            this.modeValue = mode;
        }

        @NonNull
        @Override
        public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.category_item_list, parent, false);
            return new CategoryHolder(view, this.modeValue);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
            Category category = categories.get(position);
            holder.bindEvent(category);
        }

        public void setCategories (List<Category> categories){
            if (categories == null) {
                this.categories = new ArrayList<>();
            }
            else {
                this.categories = categories;
                notifyDataSetChanged();
            }
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }
    }

}
