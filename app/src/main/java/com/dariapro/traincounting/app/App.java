package com.dariapro.traincounting.app;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.dariapro.traincounting.dao.QuestionDao;
import com.dariapro.traincounting.database.AppDatabase;
import com.dariapro.traincounting.entity.Question;
import com.dariapro.traincounting.repository.QuestionRepository;

public class App extends Application {

    public static App instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "countingdb")
                .build();
        load();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public void load() {
        //QuestionDao questionDao = getDatabase().questionDao();
        QuestionRepository questionRepository = new QuestionRepository();
        Question question1 = new Question("Question 1", "12 + 134 = ", "146");
        questionRepository.insert(question1);
        Question question2 = new Question("Question 1", "118 + 4 = ", "122");
        questionRepository.insert(question2);
        Question question3 = new Question("Question 1", "78 + 324 = ", "402");
        questionRepository.insert(question3);
    }
}
