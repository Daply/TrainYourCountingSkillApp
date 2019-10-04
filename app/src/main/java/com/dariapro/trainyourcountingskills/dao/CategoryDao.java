package com.dariapro.trainyourcountingskills.dao;

import com.dariapro.trainyourcountingskills.entity.Category;

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
public interface CategoryDao {

    @Query("SELECT * FROM category")
    LiveData<List<Category>> getAll();

    @Query("SELECT * FROM category ORDER BY categoryNumber")
    LiveData<List<Category>> getAllSorted();

    @Query("SELECT * FROM category WHERE categoryId = :id")
    Category getById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Category category);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Category... categories);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Category category);

    @Delete
    void delete(Category category);

    @Query("DELETE FROM category")
    void deleteAll();

}
