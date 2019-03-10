package com.dariapro.traincounting.dao;

import android.content.Context;

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
        this.questions = new ArrayList<Question>();
        for (int i = 0; i < 10; i++) {
            Question question = new Question();
            question.setTitle("Question " + i);
            this.questions.add(question);
        }
    }

    public List<Question> getExamples() {
        return questions;
    }

    public Question getLevel(UUID id) {
        for (Question question : questions) {
            if (question.getQuestionId().equals(id)) {
                return question;
            }
        }
        return null;
    }
}
