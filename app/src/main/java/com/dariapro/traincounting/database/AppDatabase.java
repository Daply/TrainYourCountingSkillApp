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

/**
 * @author Pleshchankova Daria
 *
 */
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
                            .fallbackToDestructiveMigration()
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
            final long catId = categoryDao.insert(new Category("Simple math"));
            Level lev1 = new Level("Level 1", catId);
            final long lev1Id = levelDao.insert(lev1);
            Question question1 = new Question("Question 1", "12 + 22 = ", "34", lev1Id);
            questionDao.insert(question1);
            Question question2 = new Question("Question 2", "45 + 32 = ", "77", lev1Id);
            questionDao.insert(question2);
            Question question3 = new Question("Question 3", "47 + 23 = ", "70", lev1Id);
            questionDao.insert(question3);
            Level lev2 = new Level("Level 2", catId);
            final long lev2Id = levelDao.insert(lev2);
            Question question4 = new Question("Question 1", "14 + 97 = ", "111", lev2Id);
            questionDao.insert(question4);
            Question question5 = new Question("Question 2", "65 + 39 = ", "104", lev2Id);
            questionDao.insert(question5);
            Question question6 = new Question("Question 3", "46 + 12 = ", "58", lev2Id);
            questionDao.insert(question6);
            Level lev3 = new Level("Level 3", catId);
            final long lev3Id = levelDao.insert(lev3);
            Question question7 = new Question("Question 1", "12 + 134 = ", "146", lev3Id);
            questionDao.insert(question7);
            Question question8 = new Question("Question 2", "45 + 26 = ", "71", lev3Id);
            questionDao.insert(question8);
            Question question9 = new Question("Question 3", "77 + 22 = ", "99", lev3Id);
            questionDao.insert(question9);

            final long catId1 = categoryDao.insert(new Category("Middle math"));
            Level lev11 = new Level("Level 1", catId1);
            final long lev11Id = levelDao.insert(lev11);
            Question question11 = new Question("Question 1", "273 + 762 = ", "1035", lev11Id);
            questionDao.insert(question11);
            Question question12 = new Question("Question 2", "123 + 432 = ", "555", lev11Id);
            questionDao.insert(question12);
            Question question13 = new Question("Question 3", "242 + 356 = ", "598", lev11Id);
            questionDao.insert(question13);
            Level lev21 = new Level("Level 2", catId1);
            final long lev21Id = levelDao.insert(lev21);
            Question question21 = new Question("Question 1", "358 + 456 = ", "814", lev21Id);
            questionDao.insert(question21);
            Question question22 = new Question("Question 2", "423 + 321 = ", "744", lev21Id);
            questionDao.insert(question22);
            Question question23 = new Question("Question 3", "534 + 162 = ", "696", lev21Id);
            questionDao.insert(question23);
            Level lev31 = new Level("Level 3", catId1);
            final long lev31Id = levelDao.insert(lev31);
            Question question31 = new Question("Question 1", "545 + 653 = ", "1198", lev31Id);
            questionDao.insert(question31);
            Question question32 = new Question("Question 2", "664 + 523 = ", "1187", lev31Id);
            questionDao.insert(question32);
            Question question33 = new Question("Question 3", "784 + 534 = ", "1328", lev31Id);
            questionDao.insert(question33);
        }
    }
}