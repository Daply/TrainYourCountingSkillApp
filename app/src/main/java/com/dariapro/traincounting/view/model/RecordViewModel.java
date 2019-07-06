package com.dariapro.traincounting.view.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.dariapro.traincounting.dao.RecordDao;
import com.dariapro.traincounting.database.AppDatabase;
import com.dariapro.traincounting.entity.Record;

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

    public Record getRecordByLevelAndType(int level, int type) {
        return recordDao.getByLevelAndType(level, type);
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
