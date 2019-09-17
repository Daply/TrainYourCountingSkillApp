package com.dariapro.trainyourcountingskills.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.dariapro.trainyourcountingskills.dao.QuestionDao;
import com.dariapro.trainyourcountingskills.database.AppDatabase;
import com.dariapro.trainyourcountingskills.entity.Question;

import java.util.List;

/**
 * @author Pleshchankova Daria
 *
 */
public class QuestionViewModel extends AndroidViewModel {
    private QuestionDao questionDao;
    private LiveData<List<Question>> questionsLiveData;

    public QuestionViewModel(@NonNull Application application) {
        super(application);
        questionDao = AppDatabase.getDatabase(application).questionDao();
        questionsLiveData = questionDao.getAll();
    }

    public LiveData<List<Question>> getQuestionList() {
        return questionsLiveData;
    }

    public LiveData<List<Question>> getQuestionListByLevel(long levelId) {
        return this.questionDao.findQuestionsForLevel(levelId);
    }

    public LiveData<List<Question>> getQuestionListByLevelSorted(long levelId) {
        return this.questionDao.findQuestionsForLevelSorted(levelId);
    }

    public int getPassedQuestionListByLevel(long levelId) {
        return this.questionDao.findPassedQuestionsForLevel(levelId);
    }

    public Question getQuestionById(long questionId) {
        return this.questionDao.getById(questionId);
    }

    public void insert(Question... questions) {
        questionDao.insert(questions);
    }

    public void update(Question question) {
        questionDao.update(question);
    }

    public void deleteAll() {
        questionDao.deleteAll();
    }
}
