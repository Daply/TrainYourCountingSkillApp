package com.dariapro.trainyourcountingskills.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.dariapro.trainyourcountingskills.dao.LevelDao;
import com.dariapro.trainyourcountingskills.database.AppDatabase;
import com.dariapro.trainyourcountingskills.entity.Level;

import java.util.List;

/**
 * @author Pleshchankova Daria
 *
 */
public class LevelViewModel extends AndroidViewModel {
    private LevelDao levelDao;
    private LiveData<List<Level>> levelsLiveData;

    public LevelViewModel(@NonNull Application application) {
        super(application);
        levelDao = AppDatabase.getDatabase(application).levelDao();
        levelsLiveData = levelDao.getAll();
    }

    public LiveData<List<Level>> getLevelList() {
        return levelsLiveData;
    }

    public LiveData<List<Level>> getLevelListByCategory(long categoryId) {
        return this.levelDao.findLevelsForCategory(categoryId);
    }

    public LiveData<List<Level>> getLevelListByCategorySorted(long categoryId) {
        return this.levelDao.findLevelsForCategorySorted(categoryId);
    }

    public int getPassedLevelListByCategory(long categoryId) {
        return this.levelDao.findPassedLevelsForCategory(categoryId);
    }

    public Level getLevelById(long id) {
        return levelDao.getById(id);
    }

    public void insert(Level... levels) {
        levelDao.insert(levels);
    }

    public void update(Level level) {
        levelDao.update(level);
    }

    public void deleteAll() {
        levelDao.deleteAll();
    }
}
