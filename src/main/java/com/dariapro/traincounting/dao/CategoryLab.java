package com.dariapro.traincounting.dao;

import android.content.Context;

import com.dariapro.traincounting.entity.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryLab {

    private Context appContext = null;
    private static CategoryLab categoryLab;

    private List<Category> categories;

    public static CategoryLab get(Context context) {
        if (categoryLab == null) {
            categoryLab = new CategoryLab(context);
        }
        return categoryLab;
    }

    private CategoryLab(Context context) {
        this.appContext = context;
        this.categories = new ArrayList<Category>();
        for (int i = 0; i < 10; i++) {
            Category category = new Category();
            category.setTitle("Simple math #" + i);
            this.categories.add(category);
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public Category getCategory(UUID id) {
        for (Category category : categories) {
            if (category.getCategoryId().equals(id)) {
                return category;
            }
        }
        return null;
    }
}

