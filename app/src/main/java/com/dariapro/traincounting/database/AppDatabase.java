package com.dariapro.traincounting.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dariapro.traincounting.dao.QuestionDao;
import com.dariapro.traincounting.entity.Question;

import java.util.List;

@Database(entities = {Question.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "questions.db";
    public abstract QuestionDao questionDao();
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Log.d("AppDatabase", "populating with data...");
                                    new AppDatabase.PopulateDbAsync(INSTANCE).execute();
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public void clearDb() {
        if (INSTANCE != null) {
            new AppDatabase.PopulateDbAsync(INSTANCE).execute();
        }
    }
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final QuestionDao questionDao;
        public PopulateDbAsync(AppDatabase instance) {
            questionDao = instance.questionDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            questionDao.deleteAll();
            load();
            return null;
        }
        public void load() {
            Question question1 = new Question("Question 1", "12 + 134 = ", "146");
            questionDao.insert(question1);
            Question question2 = new Question("Question 1", "118 + 4 = ", "122");
            questionDao.insert(question2);
            Question question3 = new Question("Question 1", "78 + 324 = ", "402");
            questionDao.insert(question3);
            LiveData<List<Question>> list = questionDao.getAll();
            System.out.println(list.getValue());
        }
    }
}