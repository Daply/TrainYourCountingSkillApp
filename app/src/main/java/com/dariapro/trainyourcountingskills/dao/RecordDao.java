package com.dariapro.trainyourcountingskills.dao;

import com.dariapro.trainyourcountingskills.entity.Record;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM record WHERE type = :type ORDER BY level")
    LiveData<List<Record>> getByTypeSortedByLevel(int type);

    @Query("SELECT * FROM record WHERE level = :level AND type = :type")
    Record getByLevelAndType(int level, int type);

    @Query("SELECT * FROM record WHERE level = :level AND type = :type AND time = :time")
    Record getRecordByLevelAndTypeAndTime(int level, int type, int time);

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
