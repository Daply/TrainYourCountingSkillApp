package com.dariapro.traincounting.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "question")
public class Question implements Serializable{

    @PrimaryKey(autoGenerate = true)
    private long questionId;

    private String title = null;

    private String example = null;

    private String rightAnswer = null;

    public Question() {
        this.questionId = UUID.randomUUID().getMostSignificantBits();
        this.title = new String();
        this.example = new String();
        this.rightAnswer = new String();
    }

    public Question(String title, String example, String rightAnswer) {
        this.questionId = UUID.randomUUID().getMostSignificantBits();
        this.title = title;
        this.example = example;
        this.rightAnswer = rightAnswer;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
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
