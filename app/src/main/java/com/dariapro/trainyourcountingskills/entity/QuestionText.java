package com.dariapro.trainyourcountingskills.entity;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "question_text",
        foreignKeys = @ForeignKey(entity = Question.class,
                parentColumns = "questionId",
                childColumns = "questionId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("title"), @Index("questionLevelId")})
public class QuestionText implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "questionTextId")
    private long questionTextId;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "language")
    private String language;

    @ColumnInfo(name = "questionId")
    private long questionId;

    public QuestionText(@NonNull String text, @NonNull String language, @NonNull long questionId) {
        this.text = text;
        this.language = language;
        this.questionId = questionId;
    }

    public long getQuestionTextId() {
        return questionTextId;
    }

    public void setQuestionTextId(long questionTextId) {
        this.questionTextId = questionTextId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }
}
