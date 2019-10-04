package com.dariapro.trainyourcountingskills.dao;

import com.dariapro.trainyourcountingskills.entity.Level;

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
public interface LevelDao {

    @Query("SELECT * FROM level")
    LiveData<List<Level>> getAll();

    @Query("SELECT * FROM level ORDER BY levelNumber")
    LiveData<List<Level>> getAllSorted();

    @Query("SELECT * FROM level WHERE levelId = :id")
    Level getById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Level level);

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

    @Query("SELECT * FROM level WHERE levelCategoryId=:categoryId ORDER BY levelNumber")
    LiveData<List<Level>> findLevelsForCategorySorted(final long categoryId);

    @Query("SELECT COUNT(levelId) FROM level WHERE levelCategoryId=:categoryId AND passed=1")
    int findPassedLevelsForCategory(final long categoryId);

}
