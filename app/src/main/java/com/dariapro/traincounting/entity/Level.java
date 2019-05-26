package com.dariapro.traincounting.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "level",
        foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "categoryId",
        childColumns = "levelCategoryId",
        onDelete = CASCADE))
public class Level {
    @PrimaryKey(autoGenerate = true)
    private long levelId;

    private long levelCategoryId;

    private boolean passed = false;

    private String title = null;

    public Level() {
        this.title = new String("Level"+this.levelId);
    }

    public Level(String title) {
        this.title = title;
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
