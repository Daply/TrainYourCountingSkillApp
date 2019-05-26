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

import com.dariapro.traincounting.dao.CategoryDao;
import com.dariapro.traincounting.dao.LevelDao;
import com.dariapro.traincounting.dao.QuestionDao;
import com.dariapro.traincounting.entity.Category;
import com.dariapro.traincounting.entity.Level;
import com.dariapro.traincounting.entity.Question;

import java.util.List;

@Database(entities = {Category.class, Level.class, Question.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "questions.db";

    public abstract CategoryDao categoryDao();
    public abstract LevelDao levelDao();
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
        private final CategoryDao categoryDao;
        private final LevelDao levelDao;
        private final QuestionDao questionDao;
        public PopulateDbAsync(AppDatabase instance) {
            categoryDao = instance.categoryDao();
            levelDao = instance.levelDao();
            questionDao = instance.questionDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            categoryDao.deleteAll();
            levelDao.deleteAll();
            questionDao.deleteAll();
            load();
            return null;
        }
        public void load() {
            Category cat1 = new Category("Simple math");
            categoryDao.insert(cat1);
            Category cat2 = new Category("Middle math");
            categoryDao.insert(cat2);
            Category cat3 = new Category("Advanced math");
            categoryDao.insert(cat3);

            List<Category> cats = categoryDao.getAll().getValue();
            for (Category c: cats) {
                Level lev1 = new Level("Level 1");
                lev1.setLevelCategoryId(c.getCategoryId());
                levelDao.insert(lev1);
                Level lev2 = new Level("Level 2");
                lev2.setLevelCategoryId(c.getCategoryId());
                levelDao.insert(lev2);
                Level lev3 = new Level("Level 3");
                lev3.setLevelCategoryId(c.getCategoryId());
                levelDao.insert(lev3);
            }

            List<Level> levels = levelDao.getAll().getValue();
            for (Level l: levels) {
                Question question1 = new Question("Question 1", "12 + 134 = ", "146");
                question1.setQuestionLevelId(l.getLevelId());
                questionDao.insert(question1);
                Question question2 = new Question("Question 2", "118 + 4 = ", "122");
                question2.setQuestionLevelId(l.getLevelId());
                questionDao.insert(question2);
                Question question3 = new Question("Question 3", "78 + 324 = ", "402");
                question3.setQuestionLevelId(l.getLevelId());
                questionDao.insert(question3);
            }
        }
    }
}