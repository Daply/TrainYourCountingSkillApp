package com.dariapro.traincounting.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dariapro.traincounting.entity.Level;
import com.dariapro.traincounting.entity.Question;

import java.util.List;

@Dao
public interface LevelDao {

    @Query("SELECT * FROM level")
    LiveData<List<Level>> getAll();

    @Query("SELECT * FROM level WHERE levelId = :id")
    Level getById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Level level);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Level... levels);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Level level);

    @Delete
    void delete(Level level);

    @Query("DELETE FROM level")
    void deleteAll();

    @Query("SELECT * FROM level WHERE levelCategoryId=:categoryId")
    LiveData<List<Level>> findLevelsForCategory(final long categoryId);

}
