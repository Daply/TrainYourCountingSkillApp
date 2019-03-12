package com.dariapro.traincounting.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.dariapro.traincounting.dao.QuestionDao;
import com.dariapro.traincounting.entity.Question;

@Database(entities = {Question.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract QuestionDao questionDao();
}
