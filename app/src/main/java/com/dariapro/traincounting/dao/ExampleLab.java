package com.dariapro.traincounting.dao;

import android.content.Context;

import com.dariapro.traincounting.app.App;
import com.dariapro.traincounting.entity.Level;
import com.dariapro.traincounting.entity.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExampleLab {

    private Context appContext = null;
    private static ExampleLab exampleLab;

    private List<Question> questions;

    public static ExampleLab get(Context context) {
        if (exampleLab == null) {
            exampleLab = new ExampleLab(context);
        }
        return exampleLab;
    }

    private ExampleLab(Context context) {
        this.appContext = context;
        QuestionDao questionDao = App.getInstance().getDatabase().questionDao();
        //this.questions = questionDao.getAll();
        this.questions = new ArrayList<>();
    }

    public List<Question> getExamples() {
        return questions;
    }

    public Question getExample(long id) {
        for (Question question : questions) {
            if (question.getQuestionId() == id) {
                return question;
            }
        }
        return null;
    }


}
