package com.dariapro.traincounting.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "question",
        foreignKeys = @ForeignKey(entity = Level.class,
                parentColumns = "levelId",
                childColumns = "questionLevelId",
                onDelete = CASCADE))
public class Question implements Serializable{

    @PrimaryKey(autoGenerate = true)
    private long questionId;

    private long questionLevelId;

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
        this.questionLevelId = questionLevelId;
    }

    public Question(String title, String example, String rightAnswer, long questionLevelId) {
        this.questionId = UUID.randomUUID().getMostSignificantBits();
        this.title = title;
        this.example = example;
        this.rightAnswer = rightAnswer;
        this.questionLevelId = questionLevelId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getQuestionLevelId() {
        return questionLevelId;
    }

    public void setQuestionLevelId(long questionLevelId) {
        this.questionLevelId = questionLevelId;
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
