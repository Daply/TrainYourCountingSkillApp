package com.dariapro.traincounting.database;

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
import com.dariapro.traincounting.dao.RecordDao;
import com.dariapro.traincounting.entity.Category;
import com.dariapro.traincounting.entity.Level;
import com.dariapro.traincounting.entity.Question;
import com.dariapro.traincounting.entity.Record;

/**
 * @author Pleshchankova Daria
 *
 */
@Database(entities = {Category.class, Level.class, Question.class, Record.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "questions.db";

    public abstract CategoryDao categoryDao();
    public abstract LevelDao levelDao();
    public abstract QuestionDao questionDao();
    public abstract RecordDao recordDao();

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

    private void clearDb() {
        if (INSTANCE != null) {
            new AppDatabase.PopulateDbAsync(INSTANCE).execute();
        }
    }
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final CategoryDao categoryDao;
        private final LevelDao levelDao;
        private final QuestionDao questionDao;
        private final RecordDao recordDao;

        private PopulateDbAsync(AppDatabase instance) {
            categoryDao = instance.categoryDao();
            levelDao = instance.levelDao();
            questionDao = instance.questionDao();
            recordDao = instance.recordDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            categoryDao.deleteAll();
            levelDao.deleteAll();
            questionDao.deleteAll();
            recordDao.deleteAll();
            load();
            return null;
        }

        private void load() {
            final long catId = categoryDao.insert(new Category("Simple math"));
            Level lev1 = new Level("Level 1", catId);
            lev1.setLevelNumber(1);
            final long lev1Id = levelDao.insert(lev1);
            Question question1 = new Question("Question 1", "12 + 22 = ", "34", lev1Id);
            question1.setQuestionNumber(1);
            questionDao.insert(question1);
            Question question2 = new Question("Question 2", "45 + 32 = ", "77", lev1Id);
            question2.setQuestionNumber(2);
            questionDao.insert(question2);
            Question question3 = new Question("Question 3", "47 + 23 = ", "70", lev1Id);
            question3.setQuestionNumber(3);
            questionDao.insert(question3);
            Level lev2 = new Level("Level 2", catId);
            lev2.setLevelNumber(2);
            final long lev2Id = levelDao.insert(lev2);
            Question question4 = new Question("Question 1", "14 + 97 = ", "111", lev2Id);
            question4.setQuestionNumber(1);
            questionDao.insert(question4);
            Question question5 = new Question("Question 2", "65 + 39 = ", "104", lev2Id);
            question5.setQuestionNumber(2);
            questionDao.insert(question5);
            Question question6 = new Question("Question 3", "46 + 12 = ", "58", lev2Id);
            question6.setQuestionNumber(3);
            questionDao.insert(question6);
            Level lev3 = new Level("Level 3", catId);
            lev3.setLevelNumber(3);
            final long lev3Id = levelDao.insert(lev3);
            Question question7 = new Question("Question 1", "12 + 134 = ", "146", lev3Id);
            question7.setQuestionNumber(1);
            questionDao.insert(question7);
            Question question8 = new Question("Question 2", "45 + 26 = ", "71", lev3Id);
            question8.setQuestionNumber(2);
            questionDao.insert(question8);
            Question question9 = new Question("Question 3", "77 + 22 = ", "99", lev3Id);
            question9.setQuestionNumber(3);
            questionDao.insert(question9);

            final long catId1 = categoryDao.insert(new Category("Middle math"));
            Level lev11 = new Level("Level 1", catId1);
            lev11.setLevelNumber(1);
            final long lev11Id = levelDao.insert(lev11);
            Question question11 = new Question("Question 1", "273 + 762 = ", "1035", lev11Id);
            question11.setQuestionNumber(1);
            questionDao.insert(question11);
            Question question12 = new Question("Question 2", "123 + 432 = ", "555", lev11Id);
            question12.setQuestionNumber(2);
            questionDao.insert(question12);
            Question question13 = new Question("Question 3", "242 + 356 = ", "598", lev11Id);
            question13.setQuestionNumber(3);
            questionDao.insert(question13);
            Level lev21 = new Level("Level 2", catId1);
            lev21.setLevelNumber(2);
            final long lev21Id = levelDao.insert(lev21);
            Question question21 = new Question("Question 1", "358 + 456 = ", "814", lev21Id);
            question21.setQuestionNumber(1);
            questionDao.insert(question21);
            Question question22 = new Question("Question 2", "423 + 321 = ", "744", lev21Id);
            question22.setQuestionNumber(2);
            questionDao.insert(question22);
            Question question23 = new Question("Question 3", "534 + 162 = ", "696", lev21Id);
            question23.setQuestionNumber(3);
            questionDao.insert(question23);
            Level lev31 = new Level("Level 3", catId1);
            lev31.setLevelNumber(3);
            final long lev31Id = levelDao.insert(lev31);
            Question question31 = new Question("Question 1", "545 + 653 = ", "1198", lev31Id);
            question31.setQuestionNumber(1);
            questionDao.insert(question31);
            Question question32 = new Question("Question 2", "664 + 523 = ", "1187", lev31Id);
            question32.setQuestionNumber(2);
            questionDao.insert(question32);
            Question question33 = new Question("Question 3", "784 + 534 = ", "1328", lev31Id);
            question33.setQuestionNumber(3);
            questionDao.insert(question33);
        }
    }
}