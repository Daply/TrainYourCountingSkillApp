package com.dariapro.trainyourcountingskills.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dariapro.trainyourcountingskills.entity.Question;

import java.util.List;

/**
 * @author Pleshchankova Daria
 *
 */
@Dao
public interface QuestionDao {

    @Query("SELECT * FROM question")
    LiveData<List<Question>> getAll();

    @Query("SELECT * FROM question WHERE questionId = :id")
    Question getById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Question question);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Question... questions);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Question question);

    @Delete
    void delete(Question question);

    @Query("DELETE FROM question")
    void deleteAll();

    @Query("SELECT * FROM question WHERE questionLevelId=:levelId")
    LiveData<List<Question>> findQuestionsForLevel(final long levelId);

    @Query("SELECT * FROM question WHERE questionLevelId=:levelId ORDER BY questionNumber")
    LiveData<List<Question>> findQuestionsForLevelSorted(final long levelId);

    @Query("SELECT COUNT(questionId) FROM question WHERE questionLevelId=:levelId AND passed=1")
    int findPassedQuestionsForLevel(final long levelId);

}
