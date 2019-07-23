package com.dariapro.traincounting.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dariapro.traincounting.entity.Question;
import com.dariapro.traincounting.entity.Record;

import java.util.List;

/**
 * @author Pleshchankova Daria
 *
 */
@Dao
public interface RecordDao {

    @Query("SELECT * FROM record")
    LiveData<List<Record>> getAll();

    @Query("SELECT * FROM record WHERE recordId = :id")
    Record getById(long id);

    @Query("SELECT * FROM record WHERE type = :type")
    LiveData<List<Record>> getByType(int type);

    @Query("SELECT * FROM record WHERE level = :level AND type = :type")
    Record getByLevelAndType(int level, int type);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Record record);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Record... records);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Record record);

    @Delete
    void delete(Record record);

    @Query("DELETE FROM record")
    void deleteAll();

}
