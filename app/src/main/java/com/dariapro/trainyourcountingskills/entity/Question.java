package com.dariapro.trainyourcountingskills.entity;

import java.io.Serializable;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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
public class Question implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "questionId")
    private long questionId;

    @ColumnInfo(name = "questionLevelId")
    private long questionLevelId;

    @ColumnInfo(name = "questionNumber")
    private long questionNumber;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "question")
    @NonNull
    private String question;

    @ColumnInfo(name = "rightAnswer")
    @NonNull
    private String rightAnswer;

    @ColumnInfo(name = "solution")
    @NonNull
    private String solution;

    @ColumnInfo(name = "passed")
    private boolean passed = false;

    @Ignore
    public Question(String title,
                    @NonNull String question,
                    @NonNull String rightAnswer) {
        this.questionId = UUID.randomUUID().getMostSignificantBits();
        this.title = title;
        this.question = question;
        this.rightAnswer = rightAnswer;
        this.solution = new String();
    }

    public Question(String title,
                    @NonNull String question,
                    @NonNull String rightAnswer,
                    String solution,
                    long questionNumber,
                    long questionLevelId) {
        this.questionId = UUID.randomUUID().getMostSignificantBits();
        this.title = title;
        this.question = question;
        this.rightAnswer = rightAnswer;
        this.solution = solution;
        this.questionNumber = questionNumber;
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

    public long getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(long questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    public String getQuestion() {
        return question;
    }

    public void setQuestion(@NonNull String question) {
        this.question = question;
    }

    @NonNull
    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(@NonNull String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    @NonNull
    public String getSolution() {
        return solution;
    }

    public void setSolution(@NonNull String solution) {
        this.solution = solution;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
