package com.dariapro.traincounting.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dariapro.traincounting.entity.Category;
import com.dariapro.traincounting.entity.Question;

import java.util.List;

/**
 * @author Pleshchankova Daria
 *
 */
@Dao
public interface CategoryDao {

    @Query("SELECT * FROM category")
    LiveData<List<Category>> getAll();

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
