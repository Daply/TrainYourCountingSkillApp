package com.dariapro.traincounting.view.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.dariapro.traincounting.dao.CategoryDao;
import com.dariapro.traincounting.database.AppDatabase;
import com.dariapro.traincounting.entity.Category;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryDao categoryDao;
    private LiveData<List<Category>> categoriesLiveData;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryDao = AppDatabase.getDatabase(application).categoryDao();
        categoriesLiveData = categoryDao.getAll();
    }

    public LiveData<List<Category>> getCategoryList() {
        return categoriesLiveData;
    }

    public void insert(Category... categories) {
        categoryDao.insert(categories);
    }

    public void update(Category category) {
        categoryDao.update(category);
    }

    public void deleteAll() {
        categoryDao.deleteAll();
    }
}
