package com.dariapro.traincounting.view.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.dariapro.traincounting.dao.QuestionDao;
import com.dariapro.traincounting.database.AppDatabase;
import com.dariapro.traincounting.entity.Question;

import java.util.List;

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
