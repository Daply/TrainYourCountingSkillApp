package com.dariapro.trainyourcountingskills.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.dariapro.trainyourcountingskills.dao.RecordDao;
import com.dariapro.trainyourcountingskills.database.AppDatabase;
import com.dariapro.trainyourcountingskills.entity.Record;

import java.util.List;

/**
 * @author Pleshchankova Daria
 *
 */
public class RecordViewModel extends AndroidViewModel {
    private RecordDao recordDao;
    private LiveData<List<Record>> recordLiveData;

    public RecordViewModel(@NonNull Application application) {
        super(application);
        recordDao = AppDatabase.getDatabase(application).recordDao();
        recordLiveData = recordDao.getAll();
    }

    public LiveData<List<Record>> getRecordList() {
        return recordLiveData;
    }

    public LiveData<List<Record>> getRecordsByType(int type) {
        return recordDao.getByType(type);
    }

    public LiveData<List<Record>> getRecordsByTypeSortedByLevel(int type) {
        return recordDao.getByTypeSortedByLevel(type);
    }

    public Record getRecordByLevelAndType(int level, int type) {
        return recordDao.getByLevelAndType(level, type);
    }

    public Record getRecordByLevelAndTypeAndTime(int level, int type, int time) {
        return recordDao.getRecordByLevelAndTypeAndTime(level, type, time);
    }

    public void delete(Record record) {
        recordDao.delete(record);
    }

    public void insert(Record... records) {
        recordDao.insert(records);
    }

    public void update(Record record) {
        recordDao.update(record);
    }

    public void deleteAll() {
        recordDao.deleteAll();
    }
}
