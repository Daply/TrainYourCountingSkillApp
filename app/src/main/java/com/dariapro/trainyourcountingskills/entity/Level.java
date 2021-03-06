package com.dariapro.trainyourcountingskills.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author Pleshchankova Daria
 *
 */
@Entity(tableName = "level",
        foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "categoryId",
        childColumns = "levelCategoryId",
        onDelete = ForeignKey.CASCADE),
        indices = {@Index("title"), @Index("levelCategoryId")})
public class Level {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "levelId")
    private long levelId;

    @ColumnInfo(name = "levelCategoryId")
    private long levelCategoryId;

    @ColumnInfo(name = "levelNumber")
    private long levelNumber;

    @ColumnInfo(name = "passed")
    private boolean passed = false;

    @ColumnInfo(name = "title")
    private String title;

    public Level(@NonNull String title, long levelNumber, long levelCategoryId) {
        this.title = title;
        this.levelNumber = levelNumber;
        this.levelCategoryId = levelCategoryId;
    }

    public long getLevelId() {
        return levelId;
    }

    public void setLevelId(long levelId) {
        this.levelId = levelId;
    }

    public long getLevelCategoryId() {
        return levelCategoryId;
    }

    public void setLevelCategoryId(long levelCategoryId) {
        this.levelCategoryId = levelCategoryId;
    }

    public long getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(long levelNumber) {
        this.levelNumber = levelNumber;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
