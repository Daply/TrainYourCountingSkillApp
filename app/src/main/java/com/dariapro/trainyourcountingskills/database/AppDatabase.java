package com.dariapro.trainyourcountingskills.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dariapro.trainyourcountingskills.dao.CategoryDao;
import com.dariapro.trainyourcountingskills.dao.LevelDao;
import com.dariapro.trainyourcountingskills.dao.QuestionDao;
import com.dariapro.trainyourcountingskills.dao.RecordDao;
import com.dariapro.trainyourcountingskills.entity.Category;
import com.dariapro.trainyourcountingskills.entity.Level;
import com.dariapro.trainyourcountingskills.entity.Question;
import com.dariapro.trainyourcountingskills.entity.Record;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * @author Pleshchankova Daria
 *
 */
@Database(entities = {Category.class, Level.class, Question.class, Record.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "questions.db";

    private static Context appContext;

    public abstract CategoryDao categoryDao();
    public abstract LevelDao levelDao();
    public abstract QuestionDao questionDao();
    public abstract RecordDao recordDao();

    public static AppDatabase getDatabase(final Context context) {
        appContext = context;
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .addCallback(new Callback() {
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
            try {
                readAndLoad();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void readAndLoad() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
            JsonParser parser = new JsonParser();
            JsonElement jsonTree = parser.parse(loadJSONFromAsset(appContext));
            JsonElement jsonCategories = jsonTree.getAsJsonObject().get("categories");
            if (jsonCategories.isJsonArray()) {
                JsonArray categories = jsonCategories.getAsJsonArray();
                for (JsonElement jsonCategory: categories) {
                    String categoryTitle = jsonCategory.getAsJsonObject()
                            .get("title").getAsString();
                    String categoryNumber = jsonCategory.getAsJsonObject()
                            .get("categoryNumber").getAsString();
                    Category newCategory = new Category(categoryTitle, Long.parseLong(categoryNumber));
                    final long currentCategoryId = categoryDao.insert(newCategory);
                    JsonElement jsonLevels = jsonCategory.getAsJsonObject().get("levels");
                    if (jsonLevels.isJsonArray()) {
                        JsonArray levels = jsonLevels.getAsJsonArray();
                        for (JsonElement jsonLevel: levels) {
                            String levelTitle = jsonLevel.getAsJsonObject()
                                    .get("title").getAsString();
                            String levelNumber = jsonLevel.getAsJsonObject()
                                    .get("levelNumber").getAsString();
                            Level newLevel = new Level(levelTitle, Long.parseLong(levelNumber),
                                                       currentCategoryId);
                            long currentLevelId = levelDao.insert(newLevel);
                            JsonElement jsonQuestions = jsonLevel.getAsJsonObject().get("questions");
                            if (jsonQuestions.isJsonArray()) {
                                JsonArray questions = jsonQuestions.getAsJsonArray();
                                for (JsonElement jsonQuestion: questions) {
                                    String questionTitle = jsonQuestion.getAsJsonObject()
                                            .get("title").getAsString();
                                    String questionQuestion = jsonQuestion.getAsJsonObject()
                                            .get("question").getAsString();
                                    String questionAnswer = jsonQuestion.getAsJsonObject()
                                            .get("answer").getAsString();
                                    String questionSolution = jsonQuestion.getAsJsonObject()
                                            .get("solution").getAsString();
                                    String questionNumber = jsonQuestion.getAsJsonObject()
                                            .get("questionNumber").getAsString();
                                    Question newQuestion =
                                            new Question(questionTitle, questionQuestion,
                                                    questionAnswer, questionSolution,
                                                    Long.parseLong(questionNumber),
                                                    currentLevelId);
                                    questionDao.insert(newQuestion);
                                }
                            }
                        }
                    }
                }
            }
        }

        public String loadJSONFromAsset(Context context) {
            String json = null;
            try {
                InputStream is = context.getAssets().open("problems.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }

    }

}