package com.dariapro.trainyourcountingskills.adapter.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.entity.Category;
import com.dariapro.trainyourcountingskills.holder.CategoryHolder;
import com.dariapro.trainyourcountingskills.holder.EntityHolder;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

/**
 * @author Pleshchankova Daria
 *
 */
public class CategoryEntityAdapter extends EntityAdapter<Category> {

    private List<Category> categories;

    public CategoryEntityAdapter(Context context, FragmentActivity currentActivity, String mode) {
        this.currentActivity = currentActivity;
        this.context = context;
        this.categories = new ArrayList<>();
        this.modeValue = mode;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(currentActivity);
        View view = inflater.inflate(R.layout.category_item_list, parent, false);
        return new CategoryHolder(context, currentActivity, view, this.modeValue);
    }

    @Override
    public void onBindViewHolder(@NonNull EntityHolder holder, int position) {
        CategoryHolder categoryHolder = (CategoryHolder) holder;
        Category category = categories.get(position);
        categoryHolder.bindEvent(category);
    }

    @Override
    public void setData (List<Category> categories){
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
