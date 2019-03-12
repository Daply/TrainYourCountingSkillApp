package com.dariapro.traincounting.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.dariapro.traincounting.app.App;
import com.dariapro.traincounting.dao.QuestionDao;
import com.dariapro.traincounting.database.AppDatabase;
import com.dariapro.traincounting.entity.Question;

import java.util.List;

public class QuestionRepository {

    private QuestionDao questionDao;
    private LiveData<List<Question>> questions;

    public QuestionRepository() {
        AppDatabase db = App.getInstance().getDatabase();
        questionDao = db.questionDao();
        questions = questionDao.getAll();
    }

    public LiveData<List<Question>> getAllQuestions() {
        return questions;
    }


    public void insert (Question word) {
        new insertAsyncTask(questionDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Question, Void, Void> {

        private QuestionDao mAsyncTaskDao;

        insertAsyncTask(QuestionDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Question... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}