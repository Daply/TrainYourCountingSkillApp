package com.dariapro.traincounting.view.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.dariapro.traincounting.dao.LevelDao;
import com.dariapro.traincounting.database.AppDatabase;
import com.dariapro.traincounting.entity.Level;

import java.util.List;

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
        LiveData<List<Level>> levelsCatLiveData = this.levelDao.findLevelsForCategory(categoryId);
        return levelsCatLiveData;
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
