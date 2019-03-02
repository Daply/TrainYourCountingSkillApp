package com.dariapro.traincounting.entity;

import java.util.UUID;

public class Question {
    private UUID questionId;
    private String example = null;
    private Answer rightAnswer = null;

    public Question(String example, Answer rightAnswer) {
        this.questionId = UUID.randomUUID();
        this.example = example;
        this.rightAnswer = rightAnswer;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public Answer getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Answer rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
