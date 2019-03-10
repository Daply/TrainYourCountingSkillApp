package com.dariapro.traincounting.entity;

import java.util.UUID;

public class Question {
    private UUID questionId;
    private String title = null;
    private String example = null;
    private String rightAnswer = null;

    public Question() {
        this.questionId = UUID.randomUUID();
        this.title = new String();
        this.example = new String();
        this.rightAnswer = new String();
    }

    public Question(String title, String example, String rightAnswer) {
        this.questionId = UUID.randomUUID();
        this.title = title;
        this.example = example;
        this.rightAnswer = rightAnswer;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
