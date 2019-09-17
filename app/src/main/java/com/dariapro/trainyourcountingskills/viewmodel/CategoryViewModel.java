package com.dariapro.trainyourcountingskills.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.dariapro.trainyourcountingskills.dao.CategoryDao;
import com.dariapro.trainyourcountingskills.database.AppDatabase;
import com.dariapro.trainyourcountingskills.entity.Category;

import java.util.List;

/**
 * @author Pleshchankova Daria
 *
 */
public class CategoryViewModel extends AndroidViewModel {
    private CategoryDao categoryDao;
    private LiveData<List<Category>> categoriesLiveData;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryDao = AppDatabase.getDatabase(application).categoryDao();
        categoriesLiveData = categoryDao.getAll();
    }

    public Category getCategoryById(long id) {
        return categoryDao.getById(id);
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
