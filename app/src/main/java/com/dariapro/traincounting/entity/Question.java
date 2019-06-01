package com.dariapro.traincounting.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Pleshchankova Daria
 *
 */
@Entity(tableName = "question",
        foreignKeys = @ForeignKey(entity = Level.class,
                parentColumns = "levelId",
                childColumns = "questionLevelId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("title"), @Index("questionLevelId")})
public class Question implements Serializable{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "questionId")
    private long questionId;

    @ColumnInfo(name = "questionLevelId")
    private long questionLevelId;

    @ColumnInfo(name = "title")
    @NonNull
    private String title = null;

    @ColumnInfo(name = "question")
    @NonNull
    private String question = null;

    @ColumnInfo(name = "rightAnswer")
    @NonNull
    private String rightAnswer = null;

    @ColumnInfo(name = "passed")
    private boolean passed = false;

    @Ignore
    public Question(String title, String question, String rightAnswer) {
        this.questionId = UUID.randomUUID().getMostSignificantBits();
        this.title = title;
        this.question = question;
        this.rightAnswer = rightAnswer;
    }

    public Question(String title, String question, String rightAnswer, long questionLevelId) {
        this.questionId = UUID.randomUUID().getMostSignificantBits();
        this.title = title;
        this.question = question;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
